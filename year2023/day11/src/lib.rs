use std::vec;

use galaxy::{new_galaxy, Galaxy};
use pixel::Pixel;

mod galaxy;
mod pixel;

pub fn sum_of_lengths(input: &str, num_expansions: usize) -> usize {
    let galaxies = read_galaxies(input);

    let expanded_galaxies = expand(&galaxies, num_expansions);

    get_lengths_between_galaxies(&expanded_galaxies)
        .iter()
        .fold(0, |sum, lengths| sum + lengths.iter().sum::<usize>())
}

fn get_lengths_between_galaxies(galaxies: &[Galaxy]) -> Vec<Vec<usize>> {
    galaxies
        .iter()
        .enumerate()
        .map(|(galaxy_i, galaxy)| {
            galaxies
                .iter()
                .skip(galaxy_i)
                .map(|other_galaxy| {
                    galaxy.x.abs_diff(other_galaxy.x) + galaxy.y.abs_diff(other_galaxy.y)
                })
                .collect::<Vec<usize>>()
        })
        .collect::<Vec<Vec<usize>>>()
}

fn read_galaxies(input: &str) -> Vec<Galaxy> {
    input
        .split('\n')
        .enumerate()
        .fold(vec![], |mut galaxies, (y, row)| {
            row.chars().enumerate().for_each(|(x, pixel_char)| {
                if let Ok(pixel) = Pixel::from_char(pixel_char) {
                    if pixel == Pixel::Galaxy {
                        galaxies.push(new_galaxy(x, y))
                    }
                }
            });

            galaxies
        })
}

fn expand(galaxies: &[Galaxy], num_expansions: usize) -> Vec<Galaxy> {
    let x_max = galaxies.iter().fold(usize::MIN, |x_max, galaxy| {
        if galaxy.x > x_max {
            return galaxy.x;
        }

        x_max
    });
    let y_max = galaxies.iter().fold(usize::MIN, |y_max, galaxy| {
        if galaxy.y > y_max {
            return galaxy.y;
        }

        y_max
    });

    // Expand columns
    let empty_rows = (0..=y_max)
        .filter(|original_y| !galaxies.iter().any(|galaxy| galaxy.y == *original_y))
        .collect::<Vec<usize>>();

    let empty_columns = (0..=x_max)
        .filter(|original_x| !galaxies.iter().any(|galaxy| galaxy.x == *original_x))
        .collect::<Vec<usize>>();

    galaxies
        .iter()
        .map(|Galaxy { x, y }| {
            let new_x = x
                + (num_expansions - 1)
                    * empty_columns
                        .iter()
                        .filter(|empty_column| empty_column < &x)
                        .count();
            let new_y = y
                + (num_expansions - 1)
                    * empty_rows.iter().filter(|empty_row| empty_row < &y).count();

            new_galaxy(new_x, new_y)
        })
        .collect::<Vec<Galaxy>>()
}

#[cfg(test)]
mod test {
    use std::fs;

    use crate::sum_of_lengths;

    const TEST_INPUT_1: &str = "...#......
.......#..
#.........
..........
......#...
.#........
.........#
..........
.......#..
#...#.....";

    #[test]
    fn test_sum_of_lengths() {
        assert_eq!(374, sum_of_lengths(TEST_INPUT_1, 2));

        let input = fs::read_to_string("./input.txt").unwrap();

        assert_eq!(10885634, sum_of_lengths(input.as_str(), 2));

        assert_eq!(1030, sum_of_lengths(TEST_INPUT_1, 10));

        assert_eq!(8410, sum_of_lengths(TEST_INPUT_1, 100));

        assert_eq!(707505470642, sum_of_lengths(input.as_str(), 1_000_000));
    }
}
