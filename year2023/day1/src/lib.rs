pub mod year2023day1 {
    use std::cmp;
    use std::error::Error;
    use std::fmt::{self};

    use regex::Regex;

    #[derive(Debug)]
    pub struct ParseError {
        msg: String,
    }
    impl Error for ParseError {}
    impl fmt::Display for ParseError {
        fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
            let msg = &self.msg;

            write!(f, r"Parse Error: {msg}")
        }
    }

    pub fn sum_calibration_values(input: &str) -> Result<i32, ParseError> {
        let lines = input.split('\n');

        let result: Result<Vec<i32>, ParseError> = lines
            .map(|s| -> Result<i32, ParseError> {
                let mut first_digit = '\0';
                let mut last_digit = '\0';

                for c in s.chars() {
                    if c.is_ascii_digit() {
                        first_digit = c;
                        break;
                    }
                }

                for c in s.chars().rev() {
                    if c.is_ascii_digit() {
                        last_digit = c;
                        break;
                    }
                }

                let value_string = first_digit.to_string() + &last_digit.to_string();

                match value_string.parse::<i32>() {
                    Ok(v) => Ok(v),
                    Err(e) => Err(ParseError { msg: e.to_string() }),
                }
            })
            .collect::<Result<Vec<i32>, ParseError>>();

        match result {
            Ok(v) => Ok(v.into_iter().sum()),
            Err(e) => Err(e),
        }
    }

    pub fn sum_calibration_values_spelled(input: &str) -> Result<i32, ParseError> {
        let lines = input.split('\n');
        let re = Regex::new(r"^(one|two|three|four|five|six|seven|eight|nine|[0-9])").unwrap();

        let result: Result<Vec<i32>, ParseError> = lines
            .map(|s| -> Result<i32, ParseError> {
                let mut fields: Vec<Option<&str>> = vec![];
                let mut s_i = 0;
                while s_i < s.len() {
                    let length = cmp::min(6, s.len() - s_i);
                    let substring = &s[s_i..s_i + length];

                    for f1 in re.captures_iter(&substring).map(|c| {
                        c.iter()
                            .map(|rmatch| rmatch.map(|v| v.as_str()))
                            .collect::<Vec<Option<&str>>>()
                    }) {
                        fields.push(f1[0]);
                    }

                    s_i += 1;
                }

                let first_digit = get_digit_char(fields[0].unwrap());
                let last_digit = get_digit_char(fields[fields.len() - 1].unwrap());

                let value_string = first_digit.to_string() + &last_digit.to_string();
                let value = value_string.parse::<i32>();

                match value {
                    Ok(v) => Ok(v),
                    Err(e) => Err(ParseError { msg: e.to_string() }),
                }
            })
            .collect::<Result<Vec<i32>, ParseError>>();

        match result {
            Ok(v) => Ok(v.into_iter().sum()),
            Err(e) => Err(e),
        }
    }

    fn get_digit_char(digit_string: &str) -> char {
        if digit_string == "one" {
            return '1';
        } else if digit_string == "two" {
            return '2';
        } else if digit_string == "three" {
            return '3';
        } else if digit_string == "four" {
            return '4';
        } else if digit_string == "five" {
            return '5';
        } else if digit_string == "six" {
            return '6';
        } else if digit_string == "seven" {
            return '7';
        } else if digit_string == "eight" {
            return '8';
        } else if digit_string == "nine" {
            return '9';
        }

        return digit_string.chars().next().unwrap();
    }
}

#[cfg(test)]
mod tests {
    use std::fs;

    use crate::year2023day1::{sum_calibration_values, sum_calibration_values_spelled};

    const TEST_INPUT: &str = "1abc2
pqr3stu8vwx
a1b2c3d4e5f
treb7uchet";

    const TEST_INPUT_2: &str = "two1nine
eightwothree
abcone2threexyz
xtwone3four
4nineeightseven2
zoneight234
234oneight
7pqrstsixteen";

    #[test]
    fn test_sum_values() {
        let input_string =
            fs::read_to_string("./input.txt").expect("Failed to ready year 2023 day 1 input file");
        let input = input_string.as_str();

        assert_eq!(142, sum_calibration_values(TEST_INPUT).unwrap());

        // > 46863
        assert_eq!(55108, sum_calibration_values(input).unwrap());

        assert_eq!(309, sum_calibration_values_spelled(TEST_INPUT_2).unwrap());

        // > 55108
        // > 56322
        assert_eq!(56324, sum_calibration_values_spelled(input).unwrap());
    }
}
