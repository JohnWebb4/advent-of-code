use std::collections::{BinaryHeap, HashMap, HashSet, LinkedList};

use direction::Direction;
use map::{get_map_key, new_map, Map};
use path::{new_path, Path};
use pipe::Pipe;
use rectangle::new_rectangle;
use vector::new_vector;

use crate::vector::Vector;

mod direction;
mod map;
mod path;
mod pipe;
mod rectangle;
mod vector;

pub fn count_enclosed_tiles(input: &str) -> usize {
    let map = read_map(input);

    find_enclosed_tiles(&map)
}

pub fn get_steps_to_get_farthest(input: &str) -> usize {
    let map = read_map(input);

    find_steps_to_farthest_point(&map)
}

fn find_enclosed_tiles(map: &Map) -> usize {
    // Picks' and shoelace. Gosh darn it
    let mut outer_pipe: Vec<Vector> = vec![];

    // Remove the loop
    let mut fill_queue = LinkedList::<Vector>::new();
    let mut has_seen = HashSet::<Vector>::new();
    fill_queue.push_back(new_vector(map.get_start_x(), map.get_start_y()));
    has_seen.insert(new_vector(map.get_start_x(), map.get_start_y()));

    while !fill_queue.is_empty() {
        if let Some(Vector { x, y }) = fill_queue.pop_front() {
            if let Some(cell) = map.get_value(x, y) {
                if cell != &Pipe::Ground {
                    outer_pipe.push(new_vector(x, y));

                    let mut nearby_points: Vec<(Direction, usize, usize)> = vec![];

                    // Shoelace is counter clockwise so order try left, down right up

                    if x > 0 {
                        nearby_points.push((Direction::Left, x - 1, y));
                    }

                    if y < map.get_height() - 1 {
                        nearby_points.push((Direction::Down, x, y + 1));
                    }

                    if x < map.get_width() - 1 {
                        nearby_points.push((Direction::Right, x + 1, y));
                    }

                    if y > 0 {
                        nearby_points.push((Direction::Up, x, y - 1));
                    }

                    for vec in nearby_points.iter() {
                        let (direction, next_x, next_y) = vec;

                        if let Some(next_cell) = map.get_value(*next_x, *next_y) {
                            if next_cell != &Pipe::Ground && cell.is_connected(direction, next_cell)
                            {
                                let next_point = new_vector(*next_x, *next_y);
                                if !has_seen.contains(&next_point) {
                                    fill_queue.push_back(next_point);
                                    has_seen.insert(next_point);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Calculate shoelace for area

    let shoelace_a = outer_pipe
        .iter()
        .enumerate()
        .map(|(i, Vector { x, .. })| {
            let next_y = outer_pipe[(i + 1) % outer_pipe.len()].y;

            (x * next_y) as i32
        })
        .sum::<i32>();
    let shoelace_b = outer_pipe
        .iter()
        .enumerate()
        .map(|(i, Vector { y, .. })| {
            let next_x = outer_pipe[(i + 1) % outer_pipe.len()].x;

            (y * next_x) as i32
        })
        .sum::<i32>();

    let area = (shoelace_a - shoelace_b).abs() / 2;

    // Reverse Pick's theorem for # interior points
    let exteriour_points = outer_pipe.len() as i32;

    (area + 1 - (exteriour_points / 2)) as usize
}

fn find_steps_to_farthest_point(map: &Map) -> usize {
    let initial_path = new_path(map.get_start_x(), map.get_start_y(), 0);

    let mut farthest_steps = usize::MIN;
    let mut shortest_step_to_point = HashMap::<String, usize>::new();

    let mut heap = BinaryHeap::<Path>::new();

    shortest_step_to_point.insert(get_map_key(initial_path.x, initial_path.y), 0);

    heap.push(initial_path);

    while let Some(Path {
        x, y, num_steps, ..
    }) = heap.pop()
    {
        if num_steps > farthest_steps {
            farthest_steps = num_steps;
        }

        // Move around
        if let Some(value) = map.get_value(x, y) {
            let x_i = x as i32;
            let y_i = y as i32;

            // Move all directions
            [
                (Direction::Up, x_i, y_i - 1),
                (Direction::Right, x_i + 1, y_i),
                (Direction::Down, x_i, y_i + 1),
                (Direction::Left, x_i - 1, y_i),
            ]
            .iter()
            .for_each(|(direction, next_x_i, next_y_i)| {
                if next_x_i >= &0 && next_y_i >= &0 {
                    let next_x = *next_x_i as usize;
                    let next_y = *next_y_i as usize;

                    if next_x < map.get_width()
                        && next_y < map.get_height()
                        && value.is_connected(direction, map.get_value(next_x, next_y).unwrap())
                    {
                        let next_path = new_path(next_x, next_y, num_steps + 1);

                        let next_key = get_map_key(next_path.x, next_path.y);
                        if let std::collections::hash_map::Entry::Vacant(e) =
                            shortest_step_to_point.entry(next_key)
                        {
                            e.insert(next_path.num_steps);
                            heap.push(next_path);
                        }
                    }
                }
            });
        }
    }

    farthest_steps
}

fn read_map(input: &str) -> Map {
    let mut start_position = new_vector(0, 0);

    let inner_map =
        input
            .split('\n')
            .enumerate()
            .fold(HashMap::new(), |mut map, (line_i, line)| {
                line.chars().enumerate().for_each(|(char_i, char)| {
                    if char == 'S' {
                        start_position = new_vector(char_i, line_i);
                    }

                    // unwrap, so it crashes if unknown type
                    map.insert(get_map_key(char_i, line_i), Pipe::from_char(char).unwrap());
                });

                map
            });

    let lines = input.split('\n').collect::<Vec<&str>>();
    let width = lines[0].len();
    let height = lines.len();

    let bounds = new_rectangle(width, height);

    new_map(inner_map, bounds, start_position)
}

#[cfg(test)]
mod test {
    use std::fs;

    use crate::{count_enclosed_tiles, get_steps_to_get_farthest};

    const TEST_INPUT_1: &str = ".....
.S-7.
.|.|.
.L-J.
.....";

    const TEST_INPUT_2: &str = "...........
.S-------7.
.|F-----7|.
.||.....||.
.||.....||.
.|L-7.F-J|.
.|..|.|..|.
.L--J.L--J.
...........";

    const TEST_INPUT_3: &str = "..........
.S------7.
.|F----7|.
.||....||.
.||....||.
.|L-7F-J|.
.|..||..|.
.L--JL--J.
..........";

    const TEST_INPUT_4: &str = ".F----7F7F7F7F-7....
.|F--7||||||||FJ....
.||.FJ||||||||L7....
FJL7L7LJLJ||LJ.L-7..
L--J.L7...LJS7F-7L7.
....F-J..F7FJ|L7L7L7
....L7.F7||L7|.L7L7|
.....|FJLJ|FJ|F7|.LJ
....FJL-7.||.||||...
....L---J.LJ.LJLJ...";

    const TEST_INPUT_5: &str = "FF7FSF7F7F7F7F7F---7
L|LJ||||||||||||F--J
FL-7LJLJ||||||LJL-77
F--JF--7||LJLJ7F7FJ-
L---JF-JLJ.||-FJLJJ7
|F|F-JF---7F7-L7L|7|
|FFJF7L7F-JF7|JL---7
7-L-JL7||F7|L7F-7F7|
L.L7LFJ|||||FJL7||LJ
L7JLJL-JLJLJL--JLJ.L";

    #[test]
    fn test_steps_to_get_farthest() {
        assert_eq!(4, get_steps_to_get_farthest(TEST_INPUT_1));

        let input = fs::read_to_string("./input.txt").unwrap();

        assert_eq!(6931, get_steps_to_get_farthest(input.as_str()));

        assert_eq!(4, count_enclosed_tiles(TEST_INPUT_2));

        assert_eq!(4, count_enclosed_tiles(TEST_INPUT_3));

        assert_eq!(8, count_enclosed_tiles(TEST_INPUT_4));

        assert_eq!(10, count_enclosed_tiles(TEST_INPUT_5));

        assert_eq!(357, count_enclosed_tiles(input.as_str()));
    }
}
