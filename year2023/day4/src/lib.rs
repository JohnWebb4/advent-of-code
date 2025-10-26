use std::{
    collections::{HashMap, HashSet},
    fmt::Error,
    num::ParseIntError,
};

pub fn get_total_points(input: &str) -> Result<i32, Error> {
    let card_strings = input.split('\n');

    let mut sum_points = 0;

    for card_string in card_strings {
        let points = get_points(card_string).unwrap();

        sum_points += points;
    }

    Ok(sum_points)
}

pub fn get_total_scratchcards(input: &str) -> Result<i32, Error> {
    let card_strings = input.split('\n');

    let mut sum_scratchcards = 0;
    let mut num_scratchcards_map: HashMap<i32, i32> = HashMap::new();

    for (card_i, card_string) in card_strings.into_iter().rev().enumerate() {
        let count_winning_numbers = get_count_winning_numbers(card_string).unwrap();
        let mut num_scratchcards = 1;

        if count_winning_numbers > 0 {
            for diff_i in 1..(count_winning_numbers + 1) {
                num_scratchcards += num_scratchcards_map
                    .get(&((card_i as i32) - diff_i))
                    .unwrap();
            }
        }

        num_scratchcards_map.insert(card_i as i32, num_scratchcards);

        sum_scratchcards += num_scratchcards;
    }

    Ok(sum_scratchcards)
}

fn get_points(card_string: &str) -> Result<i32, Error> {
    let count_winning_numbers = get_count_winning_numbers(card_string).unwrap();

    if count_winning_numbers == 0 {
        Ok(0)
    } else {
        Ok(2_i32.pow((count_winning_numbers - 1).try_into().unwrap()))
    }
}

fn get_count_winning_numbers(card_string: &str) -> Result<i32, Error> {
    let card_parts: Vec<&str> = card_string.split(": ").collect();
    let card_values = card_parts[1];
    let card_value_parts: Vec<&str> = card_values.split(" | ").collect();
    let mut count_winning_numbers = 0;

    let winning_numbers_string = card_value_parts[0];
    let mut winning_set: HashSet<i32> = HashSet::new();
    for winning_number_string in winning_numbers_string.split(' ') {
        let winning_number_result: Result<i32, ParseIntError> = winning_number_string.parse();

        if let Ok(winning_number) = winning_number_result {
            winning_set.insert(winning_number);
        }
    }

    let your_numbers_string = card_value_parts[1];
    for your_number_string in your_numbers_string.split(' ') {
        let your_number_result: Result<i32, ParseIntError> = your_number_string.parse();

        if let Ok(your_number) = your_number_result {
            if winning_set.contains(&your_number) {
                count_winning_numbers += 1;
            }
        }
    }

    Ok(count_winning_numbers)
}

#[cfg(test)]
mod test {
    use std::fs;

    const TEST_INPUT_1: &str = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11";

    #[test]
    fn test_day4() {
        use crate::{get_total_points, get_total_scratchcards};

        assert_eq!(
            13,
            get_total_points(TEST_INPUT_1).expect("Year 2023 Day 4 Part One Test 1. Failed")
        );

        let input_string = fs::read_to_string("./input.txt").unwrap();
        let input = input_string.as_str();

        assert_eq!(
            32609,
            get_total_points(input).expect("Year 2023 Day 4 Part One Input. Failed")
        );

        assert_eq!(
            30,
            get_total_scratchcards(TEST_INPUT_1).expect("Year 2023 Day 4 Part Two Test 1. Failed")
        );

        assert_eq!(
            14624680,
            get_total_scratchcards(input).expect("Year 2023 Day 4 Part Two Input. Failed")
        );
    }
}
