use once_cell::sync::Lazy;
use regex::Regex;

static PROGRAM_RE: Lazy<Regex> = Lazy::new(|| Regex::new(r"mul\(\d+,\d+\)").unwrap());
static DONT_RE: Lazy<Regex> = Lazy::new(|| Regex::new(r"don't\(\).*?do\(\)?").unwrap());
static MULTIPLY_RE: Lazy<Regex> = Lazy::new(|| Regex::new(r"mul\((\d+),(\d+)\)").unwrap());

pub fn run_corropted_program(instruction_string: &str) -> i32 {
    PROGRAM_RE
        .find_iter(instruction_string)
        .map(|program_capture| {
            let multiply_capture = MULTIPLY_RE.captures(program_capture.as_str()).unwrap();

            multiply_capture[1].parse::<i32>().unwrap()
                * multiply_capture[2].parse::<i32>().unwrap()
        })
        .sum()
}

pub fn run_corropted_program_2(instruction_string: &str) -> i32 {
    let clean_string = DONT_RE
        .replace_all(&instruction_string.replace("\n", ""), "")
        .to_string();

    dbg!(&clean_string);

    run_corropted_program(&clean_string.as_str())
}

#[cfg(test)]
mod tests {
    use std::fs;

    use crate::{run_corropted_program, run_corropted_program_2};

    const TEST_1: &str = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))";
    const TEST_2: &str =
        "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";

    #[test]
    fn it_works() {
        let file = fs::read_to_string("./input.txt").unwrap();
        let input = file.as_str();

        assert_eq!(run_corropted_program(TEST_1), 161);
        assert_eq!(run_corropted_program(input), 182780583);

        assert_eq!(run_corropted_program_2(TEST_2), 48);
        assert_eq!(run_corropted_program_2(input), 90772405);
    }
}
