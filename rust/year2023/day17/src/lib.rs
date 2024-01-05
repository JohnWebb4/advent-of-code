use std::collections::{BinaryHeap, HashMap};

use direction::Direction;
use map::{new_map, Map};
use path::{new_path, Path};

mod direction;
mod map;
mod path;

pub fn get_heat_loss(input: &str) -> u32 {
    let heat_map = read_map(input);

    find_least_heat_loss(&heat_map)
}

fn find_least_heat_loss(heat_map: &Map) -> u32 {
    let mut paths = BinaryHeap::<Path>::new();
    let mut has_seen = HashMap::<(usize, usize), u32>::new();
    paths.push(new_path(0, 0, None, None, None, 0, vec![]));

    // Lazy
    let mut min_heat_loss = u32::MAX;
    while !paths.is_empty() {
        if let Some(path) = paths.pop() {
            // Don't consider is worse than min
            if path.heat_loss < min_heat_loss {
                if path.path.starts_with(&[
                    (1, 0),
                    (2, 0),
                    (2, 1),
                    (3, 1),
                    (4, 1),
                    (5, 1),
                    (5, 0),
                    (6, 0),
                    (7, 0),
                    (8, 0),
                    (8, 1),
                    (8, 2),
                    (9, 2),
                    (10, 2),
                    (10, 3),
                    (10, 4),
                    (11, 4),
                    (11, 5),
                    (11, 6),
                    (11, 7),
                ]) {
                    println!(
                        "Found solution {path:?} {:?} {:?}",
                        heat_map.get_cell(12, 7),
                        has_seen.get(&(12, 7))
                    );
                }

                if path.x == heat_map.x_max && path.y == heat_map.y_max {
                    min_heat_loss = path.heat_loss
                } else {
                    [
                        (path.x as i32, (path.y as i32) - 1, Direction::Up),
                        (path.x as i32 + 1, path.y as i32, Direction::Right),
                        (path.x as i32, path.y as i32 + 1, Direction::Down),
                        ((path.x as i32) - 1, path.y as i32, Direction::Left),
                    ]
                    .iter()
                    .for_each(|(next_x_i, next_y_i, next_direction)| {
                        if next_x_i >= &0
                            && next_x_i <= &(heat_map.x_max as i32)
                            && next_y_i >= &0
                            && next_y_i <= &(heat_map.y_max as i32)
                            // Cannot move in the same direction four times
                            && !(path.direction.as_ref().is_some_and(|dir| {
                                path.prev_direction.as_ref().is_some_and(|prev| {
                                    path.prev_prev_direction.as_ref().is_some_and(|prev_prev| {
                                        dir == next_direction
                                            && prev == next_direction
                                            && prev_prev == next_direction
                                    })
                                })
                            }))
                        {
                            let next_x = *next_x_i as usize;
                            let next_y = *next_y_i as usize;

                            let heat_loss = heat_map.get_cell(next_x, next_y).unwrap();

                            // TEMP
                            let mut vec_path = path.path.clone();
                            vec_path.push((next_x, next_y));

                            let next_path = new_path(
                                next_x,
                                next_y,
                                Some(next_direction.clone()),
                                path.direction.clone(),
                                path.prev_direction.clone(),
                                path.heat_loss + heat_loss,
                                vec_path,
                            );

                            if let Some(prev_heat_loss) = has_seen.get(&(next_path.x, next_path.y))
                            {
                                if prev_heat_loss >= &next_path.heat_loss {
                                    has_seen
                                        .insert((next_path.x, next_path.y), next_path.heat_loss);
                                    paths.push(next_path);
                                }
                            } else {
                                has_seen.insert((next_path.x, next_path.y), next_path.heat_loss);
                                paths.push(next_path);
                            }
                        }
                    });
                }
            }
        }
    }

    min_heat_loss
}

fn read_map(input: &str) -> Map {
    let mut x_max = usize::MIN;
    let mut y_max = usize::MIN;

    let heat_loss = input.split('\n').enumerate().fold(
        HashMap::<(usize, usize), u32>::new(),
        |mut map, (row_i, row)| {
            if row_i > y_max {
                y_max = row_i;
            }

            row.chars().enumerate().for_each(|(col_i, loss)| {
                if col_i > x_max {
                    x_max = col_i;
                }

                map.insert((col_i, row_i), loss.to_string().parse::<u32>().unwrap());
            });

            map
        },
    );

    new_map(heat_loss, x_max, y_max)
}

#[cfg(test)]
mod test {
    use std::fs;

    use crate::get_heat_loss;

    const TEST_INPUT_1: &str = "2413432311323
3215453535623
3255245654254
3446585845452
4546657867536
1438598798454
4457876987766
3637877979653
4654967986887
4564679986453
1224686865563
2546548887735
4322674655533";

    #[test]
    fn test_heat_loss() {
        assert_eq!(102, get_heat_loss(TEST_INPUT_1));

        let input = fs::read_to_string("./input.txt").unwrap();

        // < 871
        assert_eq!(0, get_heat_loss(input.as_str()));
    }
}
