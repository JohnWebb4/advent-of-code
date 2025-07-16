use std::collections::{HashMap, VecDeque};

pub fn count_stones_after_blinking_x_times(input: &str, total_times: u64) -> usize {
    let init_stones = input
        .split(" ")
        .map(|s| s.to_string().parse::<i64>().unwrap())
        .collect::<Vec<i64>>();

    let mut stones_dict = HashMap::<(Vec<i64>, u64), usize>::new();

    let mut work_stack = VecDeque::<(Vec<i64>, u64)>::new();
    work_stack.push_front((init_stones.clone(), total_times));

    while let Some((stones, remaining_times)) = work_stack.pop_front() {
        if remaining_times == 0 {
            stones_dict.insert((stones.clone(), 0), stones.len());
            continue;
        }

        let next_stones = stones
            .iter()
            .flat_map(|stone| {
                let stone_string = stone.to_string().chars().collect::<Vec<char>>();
                if stone == &0 {
                    [1].to_vec()
                } else if stone_string.len() % 2 == 0 {
                    let left_string = &stone_string[..stone_string.len() / 2]
                        .iter()
                        .map(|c| c.to_string())
                        .collect::<Vec<String>>()
                        .join("");
                    let left = left_string.parse::<i64>().unwrap();

                    let right_string = &stone_string[stone_string.len() / 2..]
                        .iter()
                        .map(|c| c.to_string())
                        .collect::<Vec<String>>()
                        .join("");
                    let right = right_string.parse::<i64>().unwrap();

                    [left, right].to_vec()
                } else {
                    [stone * 2024].to_vec()
                }
            })
            .collect::<Vec<i64>>();

        // If solved
        if next_stones
            .iter()
            .all(|s| stones_dict.contains_key(&([*s].to_vec(), remaining_times - 1)))
        {
            let result = next_stones
                .iter()
                .map(|s| {
                    stones_dict
                        .get(&([*s].to_vec(), remaining_times - 1))
                        .unwrap()
                })
                .sum();

            stones_dict.insert((stones.clone(), remaining_times), result);
        } else {
            work_stack.push_front((stones.clone(), remaining_times));

            for next_stone in next_stones {
                if !stones_dict.contains_key(&([next_stone].to_vec(), remaining_times - 1)) {
                    work_stack.push_front(([next_stone].to_vec(), remaining_times - 1));
                }
            }
        }
    }

    *stones_dict.get(&(init_stones, total_times)).unwrap()
}

#[cfg(test)]
mod tests {
    use std::fs;

    use crate::count_stones_after_blinking_x_times;

    const TEST_1: &str = "0 1 10 99 999";
    const TEST_2: &str = "125 17";

    #[test]
    fn it_works() {
        let input = fs::read_to_string("./input.txt").unwrap();

        assert_eq!(count_stones_after_blinking_x_times(TEST_1, 1), 7);
        assert_eq!(count_stones_after_blinking_x_times(TEST_2, 6), 22);
        assert_eq!(count_stones_after_blinking_x_times(TEST_2, 25), 55312);
        assert_eq!(count_stones_after_blinking_x_times(&input, 25), 193607);

        assert_eq!(count_stones_after_blinking_x_times(&input, 75), 229557103025807);
    }
}
