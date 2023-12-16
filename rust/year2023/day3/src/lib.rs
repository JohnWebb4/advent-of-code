mod map;

use std::{cmp::max, cmp::min, fmt::Error};

use crate::map::{build_map, Map};

pub fn get_sum_of_parts(input: &str) -> Result<i32, Error> {
    let mut sum_of_parts = 0;

    let map = read_map(input);

    let x_max = map.get_x_max();
    let y_max = map.get_y_max();

    for y in 0..y_max + 1 {
        let mut current_number_string: String = String::from("");

        for x in 0..x_max + 1 {
            let cell_option = map.get(x, y);

            match cell_option {
                Some(cell) => {
                    if !current_number_string.is_empty() {
                        // Currently reading a part number
                        if cell.is_ascii_digit() {
                            // Continue to read
                            current_number_string.push(*cell);
                        } else {
                            if is_part(x, y, current_number_string.len(), &map) {
                                let part: i32 = current_number_string.parse().unwrap();

                                sum_of_parts += part;
                            }

                            // Clear current number
                            current_number_string = String::from("");
                        }
                    } else {
                        // Currently not reading a part number
                        if cell.is_ascii_digit() {
                            // Begin reading part number
                            current_number_string.push(*cell);
                        }
                        // Otherwise continue
                    }
                }
                None => panic!("Missing key {x} {y}"),
            }
        }

        if !current_number_string.is_empty()
            && is_part(x_max + 1, y, current_number_string.len(), &map)
        {
            let part: i32 = current_number_string.parse().unwrap();

            sum_of_parts += part
        }
    }

    Ok(sum_of_parts)
}

pub fn get_sum_of_gear_ratios(input: &str) -> Result<i32, Error> {
    let map = read_map(input);

    let mut sum_gear_ratios = 0;

    for y in 0..(map.get_y_max() + 1) {
        for x in 0..(map.get_x_max() + 1) {
            let cell = map.get(x, y).unwrap();

            if *cell == '*' {
                let gear_ratio_result = get_gear_ratio(x, y, &map);

                if let Ok(gear_ratio) = gear_ratio_result {
                    sum_gear_ratios += gear_ratio;
                }
            }
        }
    }

    Ok(sum_gear_ratios)
}

fn read_map(input: &str) -> Map {
    let rows: Vec<&str> = input.split('\n').collect();
    let mut map = build_map();

    for (row_i, row) in rows.iter().enumerate() {
        for (char_i, char) in row.chars().enumerate() {
            map.insert(char_i, row_i, char);
        }
    }

    map
}

fn get_gear_ratio(x: usize, y: usize, map: &Map) -> Result<i32, Error> {
    let y_min = max(y as i64 - 1, 0) as usize;
    let y_max = min(y + 1, map.get_y_max());

    let x_min = max(x as i64 - 1, 0) as usize;
    let x_max = min(x + 1, map.get_x_max());

    let mut gears: Vec<i32> = vec![];

    for y in y_min..(y_max + 1) {
        let mut x = x_min;
        while x < (x_max + 1) {
            let cell_option = map.get(x, y);

            if let Some(cell) = cell_option {
                if cell.is_ascii_digit() {
                    let mut value_string = String::from(*cell);

                    // search left
                    let mut x_start = x as i64 - 1;
                    while x_start >= 0 {
                        let next_cell_option = map.get(x_start as usize, y);

                        if let Some(next_cell) = next_cell_option {
                            if next_cell.is_ascii_digit() {
                                value_string.insert(0, *next_cell);
                            } else {
                                break;
                            }
                        }

                        x_start -= 1;
                    }

                    let mut x_end = x + 1;
                    while x_end <= map.get_x_max() {
                        let next_cell_option = map.get(x_end, y);

                        if let Some(next_cell) = next_cell_option {
                            if next_cell.is_ascii_digit() {
                                value_string.push(*next_cell);
                            } else {
                                break;
                            }
                        }

                        x += 1;
                        x_end += 1;
                    }

                    let gear = value_string.parse().unwrap();

                    gears.push(gear)
                }
            }

            x += 1;
        }
    }

    if gears.len() == 2 {
        return Ok(gears[0] * gears[1]);
    }

    Err(Error {})
}

fn is_part(x: usize, y: usize, length: usize, map: &Map) -> bool {
    let y_min = max((y as i64) - 1, 0) as usize;
    let y_max = min(y + 1, map.get_y_max());

    let x_min = max((x as i64) - (length as i64) - 1, 0) as usize;
    let x_max = min(x, map.get_x_max());

    for y in y_min..(y_max + 1) {
        for x in x_min..(x_max + 1) {
            let cell_option = map.get(x, y);

            if let Some(cell) = cell_option {
                if !cell.is_ascii_digit() && *cell != '.' {
                    return true;
                }
            }
        }
    }

    false
}

#[cfg(test)]
mod test {
    use std::fs;

    use crate::{get_sum_of_gear_ratios, get_sum_of_parts};

    const TEST_INPUT_1: &str = "467..114..
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
...$.*....
.664.598..";

    #[test]
    fn sum_of_parts() {
        assert_eq!(4361, get_sum_of_parts(TEST_INPUT_1).unwrap());

        let input = fs::read_to_string("./input.txt").expect("Failed to read input file");

        // > 477858
        assert_eq!(
            521601,
            get_sum_of_parts(input.as_str()).expect("Year 2023 Day 3. Failed")
        );

        assert_eq!(
            467835,
            get_sum_of_gear_ratios(TEST_INPUT_1).expect("Year 2023 Day 3 Part Two Test. Failed")
        );

        assert_eq!(
            80694070,
            get_sum_of_gear_ratios(input.as_str()).expect("Year 2023 Day 3 Part Two Input. Failed")
        );
    }
}
