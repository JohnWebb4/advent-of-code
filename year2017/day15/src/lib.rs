// use std::collections::HashMap;

const GENERATOR_A_FACTOR: u64 = 16807;
const GENERATOR_B_FACTOR: u64 = 48271;
const GENERATOR_REMAINDER: u64 = 2147483647;

pub fn get_judge_count(starting_values_string: &str) -> u32 {
    let starting_value_re = regex::Regex::new(r"Generator (\w+) starts with (\d+)").unwrap();

    let starting_values = starting_values_string
        .split('\n')
        .map(|value| {
            let value_capture = starting_value_re.captures(value).unwrap();

            value_capture[2].parse::<u64>().unwrap()
        })
        .collect::<Vec<u64>>();

    let mut generator_a = starting_values[0];
    let mut generator_b = starting_values[1];

    let mut judge_count: u32 = 0;

    for i in 0..=40_000_000 {
        if i % 1_000_000 == 0 {
            println!("{}", i);
        }

        if i > 0 {
            let binary_a = format!("{:032b}", generator_a);
            let binary_b = format!("{:032b}", generator_b);

            let trailing_binary_a = &binary_a[binary_a.len() - 16..];
            let trailing_binary_b = &binary_b[binary_b.len() - 16..];

            if trailing_binary_a == trailing_binary_b {
                judge_count += 1;
            }
        }

        let next_generator_a = (generator_a * GENERATOR_A_FACTOR) % GENERATOR_REMAINDER;
        let next_generator_b = (generator_b * GENERATOR_B_FACTOR) % GENERATOR_REMAINDER;

        generator_a = next_generator_a;
        generator_b = next_generator_b;
    }

    judge_count
}

pub fn get_judge_final_count(starting_values_string: &str) -> u32 {
    let starting_value_re = regex::Regex::new(r"Generator (\w+) starts with (\d+)").unwrap();

    let starting_values = starting_values_string
        .split('\n')
        .map(|value| {
            let value_capture = starting_value_re.captures(value).unwrap();

            value_capture[2].parse::<u64>().unwrap()
        })
        .collect::<Vec<u64>>();

    let mut generator_a = starting_values[0];
    let mut generator_b = starting_values[1];

    let mut judge_count: u32 = 0;

    for i in 0..=5_000_000 {
        if i % 1_000_000 == 0 {
            println!("{}", i);
        }

        if i > 0 {
            let binary_a = format!("{:032b}", generator_a);
            let binary_b = format!("{:032b}", generator_b);

            let trailing_binary_a = &binary_a[binary_a.len() - 16..];
            let trailing_binary_b = &binary_b[binary_b.len() - 16..];

            if trailing_binary_a == trailing_binary_b {
                judge_count += 1;
            }
        }

        let next_generator_a = get_next_generator_a(generator_a);
        let next_generator_b = get_next_generator_b(generator_b);

        generator_a = next_generator_a;
        generator_b = next_generator_b;
    }

    judge_count
}

fn get_next_generator_a(generator_a: u64) -> u64 {
    let mut next_generator_a = (generator_a * GENERATOR_A_FACTOR) % GENERATOR_REMAINDER;

    while next_generator_a % 4 != 0 {
        next_generator_a = (next_generator_a * GENERATOR_A_FACTOR) % GENERATOR_REMAINDER;
    }

    next_generator_a
}

fn get_next_generator_b(generator_b: u64) -> u64 {
    let mut next_generator_b = (generator_b * GENERATOR_B_FACTOR) % GENERATOR_REMAINDER;

    while next_generator_b % 8 != 0 {
        next_generator_b = (next_generator_b * GENERATOR_B_FACTOR) % GENERATOR_REMAINDER;
    }

    next_generator_b
}

#[cfg(test)]
mod tests {
    use super::*;

    const TEST_1: &str = "Generator A starts with 65
Generator B starts with 8921";
    const INPUT: &str = "Generator A starts with 512
Generator B starts with 191";

    #[test]
    fn test_get_judge_count() {
        assert_eq!(get_judge_count(TEST_1), 588);
        assert_eq!(get_judge_count(INPUT), 567);

        assert_eq!(get_judge_final_count(TEST_1), 309);
        assert_eq!(get_judge_final_count(INPUT), 323);
    }
}
