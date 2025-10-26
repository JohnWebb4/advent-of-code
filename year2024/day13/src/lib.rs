use std::fmt::Debug;

use regex::Regex;

// Note: A,B buttons X,Y are positive from input
// Note: A/B can only be pressed 100 times each
// Note: Claw X,Y are always positive from input
// Note: X,Y < 20,000 from input for all buttons and claw positions

#[derive(Clone, Debug, Eq, PartialEq)]
struct ClawConfig {
    a_x: u64,
    a_y: u64,

    b_x: u64,
    b_y: u64,

    claw_x: u64,
    claw_y: u64,
}

impl ClawConfig {
    fn parse(input: &str) -> Result<ClawConfig, Error> {
        let lines = input.split("\n").collect::<Vec<&str>>();

        let a_reg = Regex::new(r"Button A: X\+(\d+), Y\+(\d+)").unwrap();
        let a_cap = a_reg
            .captures(lines[0])
            .ok_or(Error::new("Can't parse a button"))?;
        let a_x = a_cap[1].parse::<u64>().unwrap();
        let a_y = a_cap[2].parse::<u64>().unwrap();

        let b_reg = Regex::new(r"Button B: X\+(\d+), Y\+(\d+)").unwrap();
        let b_cap = b_reg
            .captures(lines[1])
            .ok_or(Error::new("Can't parse b button"))?;
        let b_x = b_cap[1].parse::<u64>().unwrap();
        let b_y = b_cap[2].parse::<u64>().unwrap();

        let claw_reg = Regex::new(r"Prize: X=(\d+), Y=(\d+)").unwrap();
        let claw_cap = claw_reg
            .captures(lines[2])
            .ok_or(Error::new("Can't parse claw position"))?;
        let claw_x = claw_cap[1].parse::<u64>().unwrap();
        let claw_y = claw_cap[2].parse::<u64>().unwrap();

        Ok(ClawConfig {
            a_x,
            a_y,
            b_x,
            b_y,
            claw_x,
            claw_y,
        })
    }
}

pub struct Error {
    pub message: String,
}

impl Error {
    fn new(message: &str) -> Error {
        Error {
            message: message.to_string(),
        }
    }
}

impl Debug for Error {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        f.write_str(&format!("Claw config parse Error: {}", self.message))
    }
}

pub fn count_fewest_tokens_to_win_all_possible_prizes(input: &str) -> u64 {
    input
        .split("\n\n")
        .map(|claw_machine_input| {
            count_fewest_tokens_to_win_prize(ClawConfig::parse(claw_machine_input).unwrap())
                .unwrap_or(0) as u64
        })
        .sum()
}

fn count_fewest_tokens_to_win_prize(config: ClawConfig) -> Result<u64, Error> {
    let a_times_top =
        (config.claw_x as i64 * config.b_y as i64) - (config.b_x as i64 * config.claw_y as i64);
    let a_times_bottom =
        (config.a_x as i64 * config.b_y as i64) - (config.b_x as i64 * config.a_y as i64);

    if a_times_top % a_times_bottom == 0 {
        let a_times = a_times_top / a_times_bottom;

        let b_times_top = config.claw_x as i64 - (config.a_x as i64 * a_times);

        if b_times_top % config.b_x as i64 == 0 {
            let b_times = b_times_top / config.b_x as i64;

            return Ok(3 * a_times as u64 + b_times as u64);
        }
    }

    Err(Error::new("Failed to find solution"))
}

pub fn count_fewest_tokens_to_win_all_possible_prizes_corrected(input: &str) -> u64 {
    input
        .split("\n\n")
        .map(|claw_machine_input| {
            let mut config = ClawConfig::parse(claw_machine_input).unwrap();

            config.claw_x += 10000000000000;
            config.claw_y += 10000000000000;

            count_fewest_tokens_to_win_prize(config).unwrap_or(0)
        })
        .sum()
}

#[cfg(test)]
mod tests {
    use std::fs;

    use super::*;

    const TEST_1: &str = "Button A: X+94, Y+34
Button B: X+22, Y+67
Prize: X=8400, Y=5400

Button A: X+26, Y+66
Button B: X+67, Y+21
Prize: X=12748, Y=12176

Button A: X+17, Y+86
Button B: X+84, Y+37
Prize: X=7870, Y=6450

Button A: X+69, Y+23
Button B: X+27, Y+71
Prize: X=18641, Y=10279";

    #[test]
    fn it_counts_fewest_tokens_to_win_all_prizes() {
        let input = fs::read_to_string("./input.txt").unwrap();

        assert_eq!(count_fewest_tokens_to_win_all_possible_prizes(TEST_1), 480);
        assert_eq!(
            count_fewest_tokens_to_win_all_possible_prizes(&input),
            35729
        );

        assert_eq!(
            count_fewest_tokens_to_win_all_possible_prizes_corrected(TEST_1),
            875318608908
        );
        assert_eq!(
            count_fewest_tokens_to_win_all_possible_prizes_corrected(&input),
            88584689879723
        )
    }
}
