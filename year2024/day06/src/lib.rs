use std::collections::HashSet;

#[derive(Debug)]
enum Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT,
}

#[derive(Debug)]
struct Guard {
    pub x: i32,
    pub y: i32,
    pub direction: Direction,
}

#[derive(Debug, PartialEq)]
pub struct LoopError {
    msg: String,
}

pub fn count_distinct_positions_before_leave(map_string: &str) -> Result<usize, LoopError> {
    let mut map = map_string
        .split("\n")
        .map(|line| line.chars().collect::<Vec<char>>())
        .collect::<Vec<Vec<char>>>();

    let x_max = map[0].len() as i32;
    let y_max = map.len() as i32;

    let mut guard_opt: Option<Guard> = None;
    for x in 0..x_max as usize {
        for y in 0..y_max as usize {
            if let Ok(direction) = get_direction_from_char(map[y][x]) {
                guard_opt = Some(Guard {
                    x: x as i32,
                    y: y as i32,
                    direction: direction,
                });
                map[y][x] = '.';
                break;
            }
        }

        if guard_opt.is_some() {
            break;
        }
    }

    let mut position_set = HashSet::<(i32, i32)>::new();
    if let Some(mut guard) = guard_opt {
        position_set.insert((guard.x, guard.y));

        // Inifinite loop detection is 10x initial solution
        const INIFINITE_LOOP_SHORT_CIRCUIT: u32 = 50_000;
        let mut count = 0_u32;
        while guard.x < x_max && guard.y < y_max && count < INIFINITE_LOOP_SHORT_CIRCUIT {
            let (x_step, y_step) = get_x_y_from_direction(&guard.direction);

            let next_x = guard.x as i32 + x_step;
            let next_y = guard.y as i32 + y_step;

            if next_x < 0 || next_x >= x_max {
                break;
            }

            if next_y < 0 || next_y >= y_max {
                break;
            }

            match map[next_y as usize][next_x as usize] {
                '.' => {
                    guard.x = next_x;
                    guard.y = next_y;

                    position_set.insert((next_x, next_y));
                }
                '#' => {
                    let next_direction = rotate_90_degrees_right(&guard.direction);
                    guard.direction = next_direction;
                }
                _ => {
                    return Err(LoopError {
                        msg: "Failed to parse".to_string(),
                    });
                }
            };

            count += 1;
        }

        dbg!(&count);
        if count == INIFINITE_LOOP_SHORT_CIRCUIT {
            return Err(LoopError {
                msg: "Infinite Loop".to_string(),
            });
        }
    }

    Ok(position_set.len())
}

pub fn count_possible_obstructions_for_loop(map_string: &str) -> usize {
    let map = map_string
        .split("\n")
        .map(|line| line.chars().collect::<Vec<char>>())
        .collect::<Vec<Vec<char>>>();

    let x_max = map[0].len();
    let y_max = map.len();

    let mut possible_obstruction_set = HashSet::<(usize, usize)>::new();
    for y in 0..y_max {
        for x in 0..x_max {
            println!("{} of {} and {} of {}", x, x_max, y, y_max);

            if map[y][x] == '.' {
                // Will this lead to a loop
                let mut possible_infinite_map = map.clone();
                possible_infinite_map[y][x] = '#';

                if let Err(loop_error) = count_distinct_positions_before_leave(
                    possible_infinite_map
                        .iter()
                        .map(|l| l.iter().collect())
                        .collect::<Vec<String>>()
                        .join("\n")
                        .as_str(),
                ) {
                    if loop_error.msg == "Infinite Loop" {
                        possible_obstruction_set.insert((x, y));
                    }
                }
            }
        }
    }

    possible_obstruction_set.len()
}

fn rotate_90_degrees_right(d: &Direction) -> Direction {
    match d {
        &Direction::UP => Direction::RIGHT,
        &Direction::RIGHT => Direction::DOWN,
        &Direction::DOWN => Direction::LEFT,
        &Direction::LEFT => Direction::UP,
    }
}

fn get_x_y_from_direction(d: &Direction) -> (i32, i32) {
    match d {
        Direction::UP => (0, -1),
        Direction::RIGHT => (1, 0),
        Direction::DOWN => (0, 1),
        Direction::LEFT => (-1, 0),
    }
}

fn get_direction_from_char(c: char) -> Result<Direction, ()> {
    match c {
        '^' => Ok(Direction::UP),
        '>' => Ok(Direction::RIGHT),
        'v' => Ok(Direction::DOWN),
        '<' => Ok(Direction::LEFT),
        _ => Err(()),
    }
}

#[cfg(test)]
mod tests {
    use std::fs;

    use super::*;

    const TEST_1: &str = "....#.....
.........#
..........
..#.......
.......#..
..........
.#..^.....
........#.
#.........
......#...";

    #[test]
    fn test_count_distinct_positions_before_leave() {
        let file = fs::read_to_string("./input.txt").unwrap();
        let input = file.as_str();

        assert_eq!(count_distinct_positions_before_leave(TEST_1), Ok(41));
        assert_eq!(count_distinct_positions_before_leave(input), Ok(5444));

        assert_eq!(count_possible_obstructions_for_loop(TEST_1), 6);
        assert_eq!(count_possible_obstructions_for_loop(input), 1946);
    }
}
