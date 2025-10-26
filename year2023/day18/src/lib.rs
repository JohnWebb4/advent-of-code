use direction::Direction;
use instruction::{new_instruction, Instruction};

mod direction;
mod instruction;

pub fn get_hex_cubic_meters_of_lava(input: &str) -> u128 {
    let instructions = input
        .split('\n')
        .map(read_instruction)
        .collect::<Vec<Instruction>>();

    get_hex_meters_of_lava_from_intruction(&instructions)
}

pub fn get_cubic_meters_of_lava(input: &str) -> u128 {
    let instructions = input
        .split('\n')
        .map(read_instruction)
        .collect::<Vec<Instruction>>();

    get_meters_of_lava_from_intruction(&instructions)
}

fn get_hex_meters_of_lava_from_intruction(instructions: &[Instruction]) -> u128 {
    let mut x = 0_i128;
    let mut y = 0_i128;
    let mut points = vec![(0, 0)];

    for instruction in instructions {
        let hex = &instruction.color[2..instruction.color.len() - 1].to_string();

        let amount = u128::from_str_radix(&hex[0..5], 16).unwrap();

        let direction = match hex.chars().skip(5).next() {
            Some('0') => Ok(Direction::Right),
            Some('1') => Ok(Direction::Down),
            Some('2') => Ok(Direction::Left),
            Some('3') => Ok(Direction::Up),
            _ => Err(()),
        }
        .unwrap();

        for _i in 0..amount {
            match direction {
                Direction::Up => {
                    y -= 1;
                }
                Direction::Right => {
                    x += 1;
                }
                Direction::Down => {
                    y += 1;
                }
                Direction::Left => {
                    x -= 1;
                }
            }

            points.push((x, y));
        }
    }

    points.pop();

    get_area(&points)
}

fn get_meters_of_lava_from_intruction(instructions: &[Instruction]) -> u128 {
    let mut x = 0_i128;
    let mut y = 0_i128;
    let mut points = vec![(0, 0)];

    for instruction in instructions {
        for _i in 0..instruction.amount {
            match instruction.direction {
                Direction::Up => {
                    y -= 1;
                }
                Direction::Right => {
                    x += 1;
                }
                Direction::Down => {
                    y += 1;
                }
                Direction::Left => {
                    x -= 1;
                }
            }

            points.push((x, y));
        }
    }

    points.pop();

    get_area(&points)
}

fn get_area(points: &[(i128, i128)]) -> u128 {
    let mut area = 0_i128;

    let mut start_point = (i128::MIN, i128::MIN);

    let mut iter = points.iter().peekable();

    while let Some((x, y)) = iter.next() {
        if start_point == (i128::MIN, i128::MIN) {
            start_point = (*x, *y);
        }

        if let Some((next_x, next_y)) = iter.peek() {
            area += x * next_y - y * next_x;
        } else {
            // End. Loop
            let (next_x, next_y) = start_point;

            area += x * next_y - y * next_x;
        }
    }

    let u_area = area / 2;

    let interior_points = u_area.unsigned_abs() + 1 - (points.len() as u128 / 2);

    points.len() as u128 + interior_points
}

fn read_instruction(input: &str) -> Instruction {
    let parts = input.split(' ').collect::<Vec<&str>>();
    let direction = Direction::from_char(parts[0].chars().next().unwrap()).unwrap();
    let amount = parts[1].parse::<u32>().unwrap();
    let color = parts[2].to_string();

    new_instruction(direction, amount, color)
}

#[cfg(test)]
mod test {
    use std::fs;

    use crate::{get_cubic_meters_of_lava, get_hex_cubic_meters_of_lava};

    const TEST_INPUT_1: &str = "R 6 (#70c710)
D 5 (#0dc571)
L 2 (#5713f0)
D 2 (#d2c081)
R 2 (#59c680)
D 2 (#411b91)
L 5 (#8ceee2)
U 2 (#caa173)
L 1 (#1b58a2)
U 2 (#caa171)
R 2 (#7807d2)
U 3 (#a77fa3)
L 2 (#015232)
U 2 (#7a21e3)";

    #[test]
    fn test_get_meters_of_lava() {
        assert_eq!(62, get_cubic_meters_of_lava(TEST_INPUT_1));

        let input = fs::read_to_string("./input.txt").unwrap();

        assert_eq!(47045, get_cubic_meters_of_lava(input.as_str()));

        assert_eq!(952408144115, get_hex_cubic_meters_of_lava(TEST_INPUT_1));

        assert_eq!(
            147839570293376,
            get_hex_cubic_meters_of_lava(input.as_str())
        );
    }
}
