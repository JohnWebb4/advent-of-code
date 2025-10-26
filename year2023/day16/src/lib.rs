use std::collections::{HashMap, HashSet};

use beam::Beam;
use cell::Cell;
use map::{new_map, Map};

use crate::{beam::new_beam, direction::Direction};

mod beam;
mod cell;
mod direction;
mod map;

pub fn get_max_energized_tiles(input: &str) -> usize {
    let map = read_map(input);
    let mut max_energized_tiles = usize::MIN;
    let x_max = map.x_max;
    let y_max = map.y_max;

    // Check top and bottom
    for x in 0..=map.x_max {
        let top_energized_tiles =
            get_energized_tiles_from_beam(&map, new_beam(x, 0, Direction::South));

        if top_energized_tiles > max_energized_tiles {
            max_energized_tiles = top_energized_tiles;
        }

        let bottom_energized_tiles =
            get_energized_tiles_from_beam(&map, new_beam(x, y_max, Direction::North));

        if bottom_energized_tiles > max_energized_tiles {
            max_energized_tiles = bottom_energized_tiles;
        }
    }

    for y in 0..=map.y_max {
        let left_energized_tiles =
            get_energized_tiles_from_beam(&map, new_beam(0, y, Direction::East));

        if left_energized_tiles > max_energized_tiles {
            max_energized_tiles = left_energized_tiles;
        }

        let right_energized_tiles =
            get_energized_tiles_from_beam(&map.clone(), new_beam(x_max, y, Direction::West));

        if right_energized_tiles > max_energized_tiles {
            max_energized_tiles = right_energized_tiles;
        }
    }

    max_energized_tiles
}

pub fn get_energized_tiles(input: &str) -> usize {
    let map = read_map(input);

    get_energized_tiles_from_beam(&map, new_beam(0, 0, Direction::East))
}

fn get_energized_tiles_from_beam(map: &Map, start_beam: Beam) -> usize {
    let mut has_seen_beam = HashSet::<Beam>::new();
    let mut energized_tile_set = HashSet::<(usize, usize)>::new();
    let mut beams = vec![start_beam];

    while !beams.is_empty() {
        beams = beams.iter().fold(vec![], |mut beams, beam| {
            let cell = map.get_cell(beam.x, beam.y).unwrap();

            energized_tile_set.insert((beam.x, beam.y));

            match cell {
                Cell::Space => {
                    // Continue in direction
                    if let Ok(next_beam) = move_direction(map, beam, &beam.direction) {
                        if !has_seen_beam.contains(&next_beam) {
                            has_seen_beam.insert(next_beam.clone());

                            beams.push(next_beam);
                        }
                    }
                }
                Cell::MirrorClockwise => {
                    match beam.direction {
                        Direction::North => {
                            // Move east
                            if let Ok(next_beam) = move_direction(map, beam, &Direction::East) {
                                if !has_seen_beam.contains(&next_beam) {
                                    has_seen_beam.insert(next_beam.clone());

                                    beams.push(next_beam);
                                }
                            }
                        }
                        Direction::East => {
                            // Move north
                            if let Ok(next_beam) = move_direction(map, beam, &Direction::North) {
                                if !has_seen_beam.contains(&next_beam) {
                                    has_seen_beam.insert(next_beam.clone());

                                    beams.push(next_beam);
                                }
                            }
                        }
                        Direction::South => {
                            // Move south
                            if let Ok(next_beam) = move_direction(map, beam, &Direction::West) {
                                if !has_seen_beam.contains(&next_beam) {
                                    has_seen_beam.insert(next_beam.clone());

                                    beams.push(next_beam);
                                }
                            }
                        }
                        Direction::West => {
                            if let Ok(next_beam) = move_direction(map, beam, &Direction::South) {
                                if !has_seen_beam.contains(&next_beam) {
                                    has_seen_beam.insert(next_beam.clone());

                                    beams.push(next_beam);
                                }
                            }
                        }
                    };
                }
                Cell::MirrorCounterClockwise => {
                    match beam.direction {
                        Direction::North => {
                            if let Ok(next_beam) = move_direction(map, beam, &Direction::West) {
                                if !has_seen_beam.contains(&next_beam) {
                                    has_seen_beam.insert(next_beam.clone());

                                    beams.push(next_beam);
                                }
                            }
                        }
                        Direction::East => {
                            if let Ok(next_beam) = move_direction(map, beam, &Direction::South) {
                                if !has_seen_beam.contains(&next_beam) {
                                    has_seen_beam.insert(next_beam.clone());

                                    beams.push(next_beam);
                                }
                            }
                        }
                        Direction::South => {
                            if let Ok(next_beam) = move_direction(map, beam, &Direction::East) {
                                if !has_seen_beam.contains(&next_beam) {
                                    has_seen_beam.insert(next_beam.clone());

                                    beams.push(next_beam);
                                }
                            }
                        }
                        Direction::West => {
                            if let Ok(next_beam) = move_direction(map, beam, &Direction::North) {
                                if !has_seen_beam.contains(&next_beam) {
                                    has_seen_beam.insert(next_beam.clone());

                                    beams.push(next_beam);
                                }
                            }
                        }
                    };
                }
                Cell::SplitHorizontal => match beam.direction {
                    Direction::North | Direction::South => {
                        // Split
                        if let Ok(next_beam) = move_direction(map, beam, &Direction::East) {
                            if !has_seen_beam.contains(&next_beam) {
                                has_seen_beam.insert(next_beam.clone());

                                beams.push(next_beam);
                            }
                        }

                        if let Ok(next_beam) = move_direction(map, beam, &Direction::West) {
                            if !has_seen_beam.contains(&next_beam) {
                                has_seen_beam.insert(next_beam.clone());

                                beams.push(next_beam);
                            }
                        }
                    }
                    Direction::East | Direction::West => {
                        // Continue
                        if let Ok(next_beam) = move_direction(map, beam, &beam.direction) {
                            if !has_seen_beam.contains(&next_beam) {
                                has_seen_beam.insert(next_beam.clone());

                                beams.push(next_beam);
                            }
                        }
                    }
                },
                Cell::SplitVertical => match beam.direction {
                    Direction::North | Direction::South => {
                        // Continue
                        if let Ok(next_beam) = move_direction(map, beam, &beam.direction) {
                            if !has_seen_beam.contains(&next_beam) {
                                has_seen_beam.insert(next_beam.clone());

                                beams.push(next_beam);
                            }
                        }
                    }
                    Direction::East | Direction::West => {
                        if let Ok(next_beam) = move_direction(map, beam, &Direction::North) {
                            if !has_seen_beam.contains(&next_beam) {
                                has_seen_beam.insert(next_beam.clone());

                                beams.push(next_beam);
                            }
                        }

                        if let Ok(next_beam) = move_direction(map, beam, &Direction::South) {
                            if !has_seen_beam.contains(&next_beam) {
                                has_seen_beam.insert(next_beam.clone());

                                beams.push(next_beam);
                            }
                        }
                    }
                },
            }

            beams
        });
    }

    energized_tile_set.len()
}

fn move_direction(map: &Map, beam: &Beam, direction: &Direction) -> Result<Beam, ()> {
    match direction {
        Direction::North => {
            if beam.y > 0 && beam.y <= map.y_max {
                Ok(new_beam(beam.x, beam.y - 1, *direction))
            } else {
                Err(())
            }
        }
        Direction::East => {
            if beam.x < map.x_max {
                Ok(new_beam(beam.x + 1, beam.y, *direction))
            } else {
                Err(())
            }
        }
        Direction::South => {
            if beam.y < map.y_max {
                Ok(new_beam(beam.x, beam.y + 1, *direction))
            } else {
                Err(())
            }
        }
        Direction::West => {
            if beam.x > 0 && beam.x <= map.x_max {
                Ok(new_beam(beam.x - 1, beam.y, *direction))
            } else {
                Err(())
            }
        }
    }
}

fn read_map(input: &str) -> Map {
    let mut x_max = usize::MIN;
    let mut y_max = usize::MIN;

    let inner_map = input.split('\n').enumerate().fold(
        HashMap::<(usize, usize), Cell>::new(),
        |mut map, (row_i, row)| {
            if row_i > y_max {
                y_max = row_i;
            }

            row.chars().enumerate().for_each(|(char_i, c)| {
                if char_i > x_max {
                    x_max = char_i;
                }

                if let Ok(cell) = Cell::from_char(c) {
                    map.insert((char_i, row_i), cell);
                }
            });

            map
        },
    );

    new_map(inner_map, x_max, y_max)
}

#[cfg(test)]
mod test {
    use std::fs;

    use crate::{get_energized_tiles, get_max_energized_tiles};

    const TEST_INPUT_1: &str = r".|...\....
|.-.\.....
.....|-...
........|.
..........
.........\
..../.\\..
.-.-/..|..
.|....-|.\
..//.|....";

    #[test]
    fn test_energized_tiles() {
        assert_eq!(46, get_energized_tiles(TEST_INPUT_1));

        let input = fs::read_to_string("./input.txt").unwrap();

        assert_eq!(6740, get_energized_tiles(input.as_str()));

        assert_eq!(51, get_max_energized_tiles(TEST_INPUT_1));

        assert_eq!(7041, get_max_energized_tiles(input.as_str()));
    }
}
