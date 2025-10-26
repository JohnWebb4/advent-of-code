pub fn solve_captcha(captcha: &str) -> usize {
    let chars = captcha.chars().collect::<Vec<char>>();

    chars.iter().enumerate().fold(0, |sum, (i, d)| {
        if &chars[(i + 1) % chars.len()] == d {
            sum + (d.to_digit(10).unwrap() as usize)
        } else {
            sum
        }
    })
}

pub fn solve_new_captcha(captcha: &str) -> usize {
    let chars = captcha.chars().collect::<Vec<char>>();

    chars.iter().enumerate().fold(0, |sum, (i, d)| {
        if &chars[(i + (chars.len() / 2)) % chars.len()] == d {
            sum + (d.to_digit(10).unwrap() as usize)
        } else {
            sum
        }
    })
}

#[cfg(test)]
mod tests {
    use std::fs;

    use crate::{solve_captcha, solve_new_captcha};

    const TEST_INPUT_1: &str = "1122";
    const TEST_INPUT_2: &str = "1111";
    const TEST_INPUT_3: &str = "1234";
    const TEST_INPUT_4: &str = "91212129";

    #[test]
    fn test_solve_captcha() {
        let input_string =
            fs::read_to_string("./input.txt").expect("Failed to ready year 2017 day 1 input file");
        let input = input_string.as_str();

        assert_eq!(solve_captcha(TEST_INPUT_1), 3);
        assert_eq!(solve_captcha(TEST_INPUT_2), 4);
        assert_eq!(solve_captcha(TEST_INPUT_3), 0);
        assert_eq!(solve_captcha(TEST_INPUT_4), 9);

        assert_eq!(solve_captcha(input), 1177);

        assert_eq!(solve_new_captcha("1212"), 6);
        assert_eq!(solve_new_captcha("1221"), 0);
        assert_eq!(solve_new_captcha("123425"), 4);
        assert_eq!(solve_new_captcha("123123"), 12);
        assert_eq!(solve_new_captcha("12131415"), 4);

        assert_eq!(solve_new_captcha(input), 1060);
    }
}
