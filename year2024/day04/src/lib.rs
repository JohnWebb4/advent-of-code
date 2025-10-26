pub fn count_xmas(word_search_string: &str) -> usize {
    let word_search_map = word_search_string
        .split("\n")
        .map(|line| line.chars().collect::<Vec<char>>())
        .collect::<Vec<Vec<char>>>();

    let x_max = word_search_map.len();
    // Assuming all rows are the same length
    let y_max = word_search_map[0].len();

    let mut xmas_count = 0_usize;

    let xmas_string = "XMAS";

    let mut x = 0_usize;
    while x < x_max {
        let mut y = 0_usize;
        while y < y_max {
            [
                (x <= x_max - xmas_string.len(), 1, 0),
                (x >= xmas_string.len() - 1, -1, 0),
                (y <= y_max - xmas_string.len(), 0, 1),
                (y >= xmas_string.len() - 1, 0, -1),
                (
                    x <= x_max - xmas_string.len() && y <= y_max - xmas_string.len(),
                    1,
                    1,
                ),
                (
                    x <= x_max - xmas_string.len() && y >= xmas_string.len() - 1,
                    1,
                    -1,
                ),
                (
                    x >= xmas_string.len() - 1 && y <= y_max - xmas_string.len(),
                    -1,
                    1,
                ),
                (
                    x >= xmas_string.len() - 1 && y >= xmas_string.len() - 1,
                    -1,
                    -1,
                ),
            ]
            .iter()
            .for_each(|(condition, x_step, y_step)| {
                if *condition {
                    let word = get_word(
                        &word_search_map,
                        x as i32,
                        y as i32,
                        *x_step,
                        *y_step,
                        xmas_string.len(),
                    );

                    if word == xmas_string {
                        xmas_count += 1;
                    }
                }
            });

            y += 1;
        }

        x += 1;
    }

    xmas_count
}

fn get_word(
    char_map: &Vec<Vec<char>>,
    x_start: i32,
    y_start: i32,
    x_step: i32,
    y_step: i32,
    length: usize,
) -> String {
    (0..length as i32)
        .map(|i| {
            char_map[(x_start + i * x_step) as usize][(y_start + i * y_step) as usize].to_string()
        })
        .collect::<Vec<String>>()
        .join("")
}

pub fn count_xmas_cross(word_search_string: &str) -> usize {
    let word_search_map = word_search_string
        .split("\n")
        .map(|line| line.chars().collect::<Vec<char>>())
        .collect::<Vec<Vec<char>>>();

    let x_max = word_search_map.len();
    // Assuming all rows are the same length
    let y_max = word_search_map[0].len();

    let mut xmas_count = 0_usize;

    let mut x = 1_usize;
    while x < x_max - 1 {
        let mut y = 1_usize;
        while y < y_max - 1 {
            if word_search_map[x][y] == 'A' {
                if check_xmas_cross(&word_search_map, x, y) {
                    xmas_count += 1;
                }
            }

            y += 1;
        }

        x += 1;
    }

    xmas_count
}

fn check_xmas_cross(char_map: &Vec<Vec<char>>, x: usize, y: usize) -> bool {
    let left_size_valid = (char_map[x - 1][y - 1] == 'M' && char_map[x + 1][y + 1] == 'S')
        || (char_map[x - 1][y - 1] == 'S' && char_map[x + 1][y + 1] == 'M');

    let right_side_valid = (char_map[x + 1][y - 1] == 'M' && char_map[x - 1][y + 1] == 'S')
        || (char_map[x + 1][y - 1] == 'S' && char_map[x - 1][y + 1] == 'M');

    return left_size_valid && right_side_valid;
}

#[cfg(test)]
mod tests {
    use std::fs;

    use super::*;

    const TEST_1: &str = "MMMSXXMASM
MSAMXMSMSA
AMXSXMAAMM
MSAMASMSMX
XMASAMXAMM
XXAMMXXAMA
SMSMSASXSS
SAXAMASAAA
MAMMMXMMMM
MXMXAXMASX";

    #[test]
    fn count_xmax() {
        let file = fs::read_to_string("./input.txt").unwrap();
        let input = file.as_str();

        assert_eq!(count_xmas(TEST_1), 18);
        assert_eq!(count_xmas(input), 2547);

        assert_eq!(count_xmas_cross(TEST_1), 9);
        assert_eq!(count_xmas_cross(input), 1939);
    }
}
