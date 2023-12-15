pub mod year2022day2 {
    use std::fmt::Error;

    use once_cell::sync::Lazy;
    use regex::Regex;

    pub fn get_sum_of_ids(input: &str) -> Result<i32, Error> {
        let game_strings = input.split('\n');
        let reg_game_name: Lazy<Regex> = Lazy::new(|| Regex::new(r"Game (\d+)").unwrap());

        let mut sum_of_ids = 0;

        for game_string in game_strings {
            let game_parts: Vec<&str> = game_string.split(':').collect();
            let game_name_string = game_parts[0];
            let game_value_string = game_parts[1];

            let game_id: i32 = reg_game_name
                .captures_iter(game_name_string)
                .next()
                .map(|re_match| re_match.extract())
                .map(|(_, r): (&str, [&str; 1])| r[0])
                .unwrap()
                .parse()
                .unwrap();

            let set_strings = game_value_string.split(';');

            let mut is_valid = true;
            for set_string in set_strings {
                let mut red_count = 0;
                let mut green_count = 0;
                let mut blue_count = 0;

                let marble_strings = set_string.trim().split(',');

                for marble_string in marble_strings {
                    let marble_values: Vec<&str> = marble_string.trim().split(' ').collect();

                    let marble_amount: i32 = marble_values[0].parse().unwrap();
                    let marble_color = marble_values[1];

                    if marble_color == "red" {
                        red_count += marble_amount;
                    } else if marble_color == "green" {
                        green_count += marble_amount;
                    } else if marble_color == "blue" {
                        blue_count += marble_amount;
                    } else {
                        return Err(Error {});
                    }
                }

                if red_count > 12 || green_count > 13 || blue_count > 14 {
                    is_valid = false
                }
            }

            if is_valid {
                sum_of_ids += game_id;
            }
        }

        Ok(sum_of_ids)
    }

    pub fn get_sum_of_power_of_sets(input: &str) -> Result<u128, Error> {
        let game_strings = input.split('\n');

        let mut sum_of_powers: u128 = 0;

        for game_string in game_strings {
            let game_parts: Vec<&str> = game_string.split(':').collect();
            let game_value_string = game_parts[1];

            let set_strings = game_value_string.split(';');

            let mut max_red_count: u32 = 0;
            let mut max_green_count: u32 = 0;
            let mut max_blue_count: u32 = 0;

            for set_string in set_strings {
                let mut red_count: u32 = 0;
                let mut green_count: u32 = 0;
                let mut blue_count: u32 = 0;

                let marble_strings = set_string.trim().split(',');

                for marble_string in marble_strings {
                    let marble_values: Vec<&str> = marble_string.trim().split(' ').collect();

                    let marble_amount: u32 = marble_values[0].parse().unwrap();
                    let marble_color = marble_values[1];

                    if marble_color == "red" {
                        red_count += marble_amount;
                    } else if marble_color == "green" {
                        green_count += marble_amount;
                    } else if marble_color == "blue" {
                        blue_count += marble_amount;
                    } else {
                        return Err(Error {});
                    }
                }

                if red_count > max_red_count {
                    max_red_count = red_count;
                }

                if green_count > max_green_count {
                    max_green_count = green_count;
                }

                if blue_count > max_blue_count {
                    max_blue_count = blue_count;
                }
            }

            let power: u32 = max_red_count * max_green_count * max_blue_count;

            sum_of_powers += u128::from(power);
        }

        Ok(sum_of_powers)
    }
}

#[cfg(test)]
mod test {
    use std::fs;

    use crate::year2022day2::{get_sum_of_ids, get_sum_of_power_of_sets};

    const TEST_INPUT_1: &str = r"Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
    Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
    Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
    Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
    Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green";

    #[test]
    fn test_sum_of_ids() {
        let input = fs::read_to_string("./input.txt").unwrap();

        assert_eq!(8, get_sum_of_ids(TEST_INPUT_1).unwrap());

        // > 224
        assert_eq!(2716, get_sum_of_ids(input.as_str()).unwrap());

        assert_eq!(2286, get_sum_of_power_of_sets(TEST_INPUT_1).unwrap());

        assert_eq!(72227, get_sum_of_power_of_sets(input.as_str()).unwrap());
    }
}
