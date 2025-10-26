use std::collections::HashMap;

pub fn get_distance_between_lists(list_string: &str) -> usize {
    let list = list_string
        .lines()
        .map(|l| {
            l.split_whitespace()
                .map(|d| d.parse::<i32>().unwrap())
                .collect::<Vec<i32>>()
        })
        .collect::<Vec<Vec<i32>>>();

    let mut sorted_row_1 = list.iter().map(|row| (*row)[0]).collect::<Vec<i32>>();
    sorted_row_1.sort();

    let mut sorted_row_2 = list.iter().map(|row| (*row)[1]).collect::<Vec<i32>>();
    sorted_row_2.sort();

    sorted_row_1
        .iter()
        .enumerate()
        .map(|(i, d)| (d - sorted_row_2[i]).unsigned_abs() as usize)
        .sum()
}

pub fn get_similarity_score(list_string: &str) -> usize {
    let list = list_string
        .lines()
        .map(|l| {
            l.split_whitespace()
                .map(|d| d.parse::<i32>().unwrap())
                .collect::<Vec<i32>>()
        })
        .collect::<Vec<Vec<i32>>>();

    let row_1 = list.iter().map(|row| (*row)[0]).collect::<Vec<i32>>();

    let row_2_map =
        list.iter()
            .map(|row| (*row)[1])
            .fold(HashMap::<i32, usize>::new(), |mut m, d| {
                let count = m.get(&d).unwrap_or(&0);
                m.insert(d, count + 1);
                m
            });

    row_1
        .iter()
        .map(|d| {
            let count_2 = row_2_map.get(d).unwrap_or(&0);

            (*d as usize) * count_2
        })
        .sum::<usize>()
}

#[cfg(test)]
mod tests {
    use std::fs;

    use super::*;

    const TEST_1: &str = "3   4
4   3
2   5
1   3
3   9
3   3";

    #[test]
    fn test_get_distance_between_lists() {
        let file = fs::read_to_string("./input.txt").unwrap();
        let input = file.as_str();

        assert_eq!(get_distance_between_lists(TEST_1), 11);
        assert_eq!(get_distance_between_lists(input), 2264607);

        assert_eq!(get_similarity_score(TEST_1), 31);
        assert_eq!(get_similarity_score(input), 0);
    }
}
