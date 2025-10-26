pub fn sum_prev_extrapolated_values(input: &str) -> i32 {
    let histories = input
        .split('\n')
        .map(|history| {
            history
                .split(' ')
                .map(|v| v.parse::<i32>().unwrap())
                .collect::<Vec<i32>>()
        })
        .map(|v| get_prev_value(&v))
        .collect::<Vec<i32>>();

    histories.iter().sum()
}

pub fn sum_extrapolated_values(input: &str) -> i32 {
    let histories = input
        .split('\n')
        .map(|history| {
            history
                .split(' ')
                .map(|v| v.parse::<i32>().unwrap())
                .collect::<Vec<i32>>()
        })
        .map(|v| get_next_value(&v))
        .collect::<Vec<i32>>();

    histories.iter().sum()
}

fn get_prev_value(history: &[i32]) -> i32 {
    let mut reduced_histories = vec![history.to_vec()];

    while !reduced_histories[reduced_histories.len() - 1]
        .iter()
        .filter(|v| **v != 0)
        .collect::<Vec<&i32>>()
        .is_empty()
    {
        reduced_histories.push(reduce(reduced_histories.last().unwrap()));
    }

    let mut prev_diff = 0;
    reduced_histories = reduced_histories
        .into_iter()
        .rev()
        .map(|mut diff_values| {
            diff_values.insert(0, diff_values.first().unwrap() - prev_diff);

            prev_diff = *diff_values.first().unwrap();

            diff_values
        })
        .collect::<Vec<Vec<i32>>>();

    *reduced_histories.last().unwrap().first().unwrap()
}

fn get_next_value(history: &[i32]) -> i32 {
    let mut reduced_histories = vec![history.to_vec()];

    while !reduced_histories[reduced_histories.len() - 1]
        .iter()
        .filter(|v| **v != 0)
        .collect::<Vec<&i32>>()
        .is_empty()
    {
        reduced_histories.push(reduce(reduced_histories.last().unwrap()));
    }

    let mut prev_diff = 0;
    reduced_histories = reduced_histories
        .into_iter()
        .rev()
        .map(|mut diff_values| {
            diff_values.push(diff_values.last().unwrap() + prev_diff);

            prev_diff = *diff_values.last().unwrap();

            diff_values
        })
        .collect::<Vec<Vec<i32>>>();

    *reduced_histories.last().unwrap().last().unwrap()
}

fn reduce(history: &[i32]) -> Vec<i32> {
    history
        .iter()
        .enumerate()
        .skip(1)
        .map(|(i, v)| v - history[std::cmp::max(0_i32, (i as i32) - 1) as usize])
        .collect::<Vec<i32>>()
}

#[cfg(test)]
mod test {
    use std::fs;

    use crate::{sum_extrapolated_values, sum_prev_extrapolated_values};

    const TEST_INPUT_1: &str = "0 3 6 9 12 15
1 3 6 10 15 21
10 13 16 21 30 45";

    #[test]
    fn test_sum_extrapolated_values() {
        assert_eq!(114, sum_extrapolated_values(TEST_INPUT_1));

        let input = fs::read_to_string("./input.txt").unwrap();

        assert_eq!(2043183816, sum_extrapolated_values(input.as_str()));

        assert_eq!(2, sum_prev_extrapolated_values(TEST_INPUT_1));

        assert_eq!(0, sum_prev_extrapolated_values(input.as_str()));
    }
}
