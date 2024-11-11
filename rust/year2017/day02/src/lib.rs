pub fn checksum(input: &str) -> i32 {
    input.split('\n').fold(0, |sum, row| {
        let row_d = row
            .split_ascii_whitespace()
            .map(|d| d.parse::<i32>().unwrap())
            .collect::<Vec<i32>>();

        let min = row_d.iter().min().unwrap();
        let max = row_d.iter().max().unwrap();

        sum + max - min
    })
}

pub fn checksum_divide(input: &str) -> i32 {
    input.split('\n').fold(0, |sum, row| {
        let row_d = row
            .split_ascii_whitespace()
            .map(|d| d.parse::<i32>().unwrap())
            .collect::<Vec<i32>>();

        let (div_1, div_2) = row_d
            .iter()
            .enumerate()
            .filter_map(|(i, d)| {
                for (j, d_2) in row_d.iter().enumerate() {
                    if i != j {
                        if d % d_2 == 0 {
                            return Some((d, d_2));
                        }
                    }
                }

                return None;
            })
            .next()
            .unwrap();

        sum + (div_1 / div_2)
    })
}

#[cfg(test)]
mod tests {
    use std::fs;

    use super::*;

    const TEST_INPUT_1: &str = "5 1 9 5
7 5 3
2 4 6 8";

    const TEST_INPUT_2: &str = "5 9 2 8
9 4 7 3
3 8 6 5";

    #[test]
    fn test_checksum() {
        let input = fs::read_to_string("./input.txt").unwrap();
        let input_string = input.as_str();

        assert_eq!(checksum(TEST_INPUT_1), 18);
        assert_eq!(checksum(input_string), 46402);

        assert_eq!(checksum_divide(TEST_INPUT_2), 9);
        assert_eq!(checksum_divide(input_string), 265);
    }
}
