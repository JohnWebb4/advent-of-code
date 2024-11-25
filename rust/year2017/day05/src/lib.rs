pub fn count_steps_to_outside(input: &str) -> usize {
    let mut instructions = input
        .split('\n')
        .map(|instruction| instruction.parse().unwrap())
        .collect::<Vec<i32>>();

    let mut num_steps = 0;
    let mut address = 0_i32;
    let instructions_length = instructions.len() as i32;

    while address < instructions_length as i32 && address >= 0 {
        let next_address = address + instructions[address as usize];

        if next_address > instructions_length || next_address < 0 {
            return num_steps;
        }

        instructions[address as usize] += 1;
        address = next_address;
        num_steps += 1;
    }

    num_steps
}

pub fn count_steps_to_outside_strange(input: &str) -> usize {
    let mut instructions = input
        .split('\n')
        .map(|instruction| instruction.parse().unwrap())
        .collect::<Vec<i32>>();

    let mut num_steps = 0;
    let mut address = 0_i32;
    let instructions_length = instructions.len() as i32;

    while address < instructions_length as i32 && address >= 0 {
        let next_address = address + instructions[address as usize];

        if next_address > instructions_length || next_address < 0 {
            return num_steps;
        }

        if instructions[address as usize] >= 3 {
            instructions[address as usize] -= 1;
        } else {
            instructions[address as usize] += 1;
        }

        address = next_address;
        num_steps += 1;
    }

    num_steps
}

#[cfg(test)]
mod tests {
    use std::fs;

    use super::*;

    #[test]
    fn test_count_steps_to_outside() {
        let file = fs::read_to_string("./input.txt").unwrap();
        let input = file.as_str();

        assert_eq!(
            count_steps_to_outside(
                "0
3
0
1
-3"
            ),
            5
        );

        assert_eq!(count_steps_to_outside(input), 373543);

        assert_eq!(
            count_steps_to_outside_strange(
                "0
3
0
1
-3"
            ),
            10
        );

        assert_eq!(count_steps_to_outside_strange(input), 0);
    }
}
