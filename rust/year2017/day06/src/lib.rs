use std::collections::HashSet;

pub fn count_redistribution_cycles_till_config_repeat(input: &str) -> usize {
    let mut banks_set = HashSet::<String>::new();
    let mut banks = input
        .split(' ')
        .map(|bank_s| bank_s.parse::<i32>().unwrap())
        .collect::<Vec<i32>>();
    let banks_length = banks.len();

    let mut redistribution_cycle_count = 0;
    while !banks_set.contains(&get_key(&banks)) {
        banks_set.insert(get_key(&banks));

        let max_index = banks
            .iter()
            .enumerate()
            .max_by(|(_, a), (_, b)| a.cmp(b).then(std::cmp::Ordering::Greater))
            .map(|(index, _)| index)
            .unwrap();

        let max_value = banks[max_index];
        banks[max_index] = 0;

        for i in 1..=max_value {
            banks[(max_index + i as usize) % banks_length] += 1;
        }

        redistribution_cycle_count += 1;
    }

    redistribution_cycle_count
}

pub fn get_redistribution_loop_size(input: &str) -> usize {
    let mut banks_set = HashSet::<String>::new();
    let mut banks = input
        .split(' ')
        .map(|bank_s| bank_s.parse::<i32>().unwrap())
        .collect::<Vec<i32>>();
    let banks_length = banks.len();

    while !banks_set.contains(&get_key(&banks)) {
        banks_set.insert(get_key(&banks));

        let max_index = banks
            .iter()
            .enumerate()
            .max_by(|(_, a), (_, b)| a.cmp(b).then(std::cmp::Ordering::Greater))
            .map(|(index, _)| index)
            .unwrap();

        let max_value = banks[max_index];
        banks[max_index] = 0;

        for i in 1..=max_value {
            banks[(max_index + i as usize) % banks_length] += 1;
        }
    }

    let mut loop_size = 1;
    let loop_start_key = get_key(&banks);

    let max_index = banks
        .iter()
        .enumerate()
        .max_by(|(_, a), (_, b)| a.cmp(b).then(std::cmp::Ordering::Greater))
        .map(|(index, _)| index)
        .unwrap();

    let max_value = banks[max_index];
    banks[max_index] = 0;

    for i in 1..=max_value {
        banks[(max_index + i as usize) % banks_length] += 1;
    }

    while loop_start_key != get_key(&banks) {
        let max_index = banks
            .iter()
            .enumerate()
            .max_by(|(_, a), (_, b)| a.cmp(b).then(std::cmp::Ordering::Greater))
            .map(|(index, _)| index)
            .unwrap();

        let max_value = banks[max_index];
        banks[max_index] = 0;

        for i in 1..=max_value {
            banks[(max_index + i as usize) % banks_length] += 1;
        }

        loop_size += 1;
    }

    loop_size
}

fn get_key(banks: &[i32]) -> String {
    banks
        .iter()
        .map(|bank| bank.to_string())
        .collect::<Vec<String>>()
        .join(" ")
}

#[cfg(test)]
mod tests {
    use super::*;

    const TEST_1: &str = "0 2 7 0";
    const INPUT: &str = "0 5 10 0 11 14 13 4 11 8 8 7 1 4 12 11";

    #[test]
    fn test_count_redistribution_cycles_till_config_repeat() {
        assert_eq!(count_redistribution_cycles_till_config_repeat(TEST_1), 5);
        assert_eq!(count_redistribution_cycles_till_config_repeat(INPUT), 7864);

        assert_eq!(get_redistribution_loop_size(TEST_1), 4);
        assert_eq!(get_redistribution_loop_size(INPUT), 1695);
    }
}
