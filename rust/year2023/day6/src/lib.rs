use once_cell::sync::Lazy;
use race::Race;
use regex::Regex;

use scorecard::{new_scorecard, ScoreCard};

use crate::race::new_race;

mod race;

mod scorecard;

pub fn product_of_ways_to_beat_races(input: &str) -> i32 {
    let scorecard = read_input(input);

    let num_ways_to_beat = scorecard.races.iter().map(count_ways_to_win);

    num_ways_to_beat.reduce(|a: i32, b: i32| a * b).unwrap()
}

pub fn count_ways_to_beat_long_race(input: &str) -> i32 {
    let scorecard = read_long_input(input);

    let num_ways_to_beat = scorecard.races.iter().map(count_ways_to_win);

    num_ways_to_beat.reduce(|a: i32, b: i32| a * b).unwrap()
}

fn count_ways_to_win(race: &Race) -> i32 {
    if race.time == 0 {
        return 0;
    }

    (1..race.time)
        .map(|start_time| {
            let speed = start_time;
            let time_to_travel = race.time - start_time;

            let distance_traveled = speed * time_to_travel;

            if distance_traveled > race.winning_distance {
                return 1;
            }

            0
        })
        .sum()
}

fn read_long_input(input: &str) -> ScoreCard {
    let parts: Vec<&str> = input.split('\n').collect();

    let time_part = parts[0].trim();
    let distance_part = parts[1].trim();

    let re = Lazy::new(|| Regex::new(r"\s+").unwrap());

    let time_values: Vec<&str> = time_part.split(":").map(str::trim).collect();
    let time: i64 = re.replace_all(time_values[1], "").parse().unwrap();

    let distance_values: Vec<&str> = distance_part.split(":").map(str::trim).collect();
    let winning_distance: i64 = re.replace_all(distance_values[1], "").parse().unwrap();

    new_scorecard(vec![new_race(time, winning_distance)])
}

fn read_input(input: &str) -> ScoreCard {
    let parts: Vec<&str> = input.split('\n').collect();

    let time_part = parts[0].trim();
    let distance_part = parts[1].trim();

    let re = Lazy::new(|| Regex::new(r"\s+").unwrap());

    let time_rows: Vec<&str> = re.split(time_part).collect();
    let distance_rows: Vec<&str> = re.split(distance_part).collect();

    let races = time_rows
        .iter()
        .enumerate()
        .skip(1)
        .map(|(i, time_string)| {
            new_race(
                (*time_string).parse::<i64>().unwrap(),
                distance_rows[i].parse::<i64>().unwrap(),
            )
        })
        .collect();

    new_scorecard(races)
}

#[cfg(test)]
mod test {
    use std::fs;

    use crate::{count_ways_to_beat_long_race, product_of_ways_to_beat_races};

    const TEST_INPUT_1: &str = "Time:      7  15   30
Distance:  9  40  200";

    #[test]
    fn test_product_of_ways_to_beat_races() {
        assert_eq!(288, product_of_ways_to_beat_races(TEST_INPUT_1));

        let input: String = fs::read_to_string("./input.txt").unwrap();

        assert_eq!(861300, product_of_ways_to_beat_races(input.as_str()));

        assert_eq!(71503, count_ways_to_beat_long_race(TEST_INPUT_1));

        assert_eq!(28101347, count_ways_to_beat_long_race(input.as_str()));
    }
}
