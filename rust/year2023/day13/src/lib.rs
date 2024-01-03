use std::collections::HashMap;

use cell::Cell;
use note::{new_note, Note};

mod cell;
mod note;

pub fn get_summary_of_smidge(input: &str) -> usize {
    input.split("\n\n").map(summary_of_smidge).sum()
}

pub fn get_summary_of_notes(input: &str) -> usize {
    input.split("\n\n").map(summary_of_input).sum()
}

fn summary_of_smidge(input: &str) -> usize {
    let original_note = read_note(input);

    let original_mirror = summaries_of_note(&original_note)[0];

    for y in 0..=original_note.y_max {
        for x in 0..=original_note.x_max {
            // Clone
            let mut new_note = original_note.clone();
            let current_cell = new_note.get_cell(x, y).unwrap();

            match current_cell {
                Cell::Ash => {
                    new_note.set_cell(x, y, Cell::Rock);
                }
                Cell::Rock => {
                    new_note.set_cell(x, y, Cell::Ash);
                }
            }

            let mirrors = summaries_of_note(&new_note);
            let new_mirrors = mirrors
                .iter()
                .filter(|mirror| mirror != &&original_mirror)
                .collect::<Vec<&usize>>();

            if !new_mirrors.is_empty() && new_mirrors[0] != &original_mirror {
                return *new_mirrors[0];
            }
        }
    }

    0
}

fn summary_of_input(input: &str) -> usize {
    let note = read_note(input);

    summaries_of_note(&note)[0]
}

fn summaries_of_note(note: &Note) -> Vec<usize> {
    let mut summaries: Vec<usize> = vec![];

    for x in 0..note.x_max {
        // Check vertical

        let is_mirror = (1..=note.x_max).all(|width| {
            if x + width <= note.x_max && (x as i32) - (width as i32) >= -1 {
                let x_start = x + 1 - width;
                let x_end = x + width;

                return (0..=note.y_max).all(|y| {
                    (note.get_cell(x_start, y).unwrap()).eq(note.get_cell(x_end, y).unwrap())
                });
            }

            true
        });

        if is_mirror {
            summaries.push(x + 1);
        }
    }

    for y in 0..note.y_max {
        let is_mirror = (1..note.y_max).all(|width| {
            if y + width <= note.y_max && (y as i32) - (width as i32) >= -1 {
                let y_start = y + 1 - width;
                let y_end = y + width;

                return (0..=note.x_max).all(|x| {
                    note.get_cell(x, y_start)
                        .unwrap()
                        .eq(note.get_cell(x, y_end).unwrap())
                });
            }

            true
        });

        if is_mirror {
            summaries.push(100 * (y + 1));
        }
    }

    summaries
}

fn read_note(input: &str) -> Note {
    let mut x_max = usize::MIN;
    let mut y_max = usize::MIN;

    let inner_map =
        input
            .split('\n')
            .enumerate()
            .fold(HashMap::new(), |mut map, (line_i, line)| {
                if line_i > y_max {
                    y_max = line_i;
                }

                line.chars().enumerate().for_each(|(char_i, c)| {
                    if char_i > x_max {
                        x_max = char_i
                    }

                    map.insert((char_i, line_i), Cell::from_char(c).unwrap());
                });

                map
            });

    new_note(inner_map, x_max, y_max)
}

#[cfg(test)]
mod test {
    use std::fs;

    use crate::{get_summary_of_notes, get_summary_of_smidge};

    const TEST_INPUT_1: &str = "#.##..##.
..#.##.#.
##......#
##......#
..#.##.#.
..##..##.
#.#.##.#.

#...##..#
#....#..#
..##..###
#####.##.
#####.##.
..##..###
#....#..#";

    #[test]
    pub fn test_get_summary_of_notes() {
        assert_eq!(405, get_summary_of_notes(TEST_INPUT_1));

        let input = fs::read_to_string("./input.txt").unwrap();

        assert_eq!(28895, get_summary_of_notes(input.as_str()));

        assert_eq!(400, get_summary_of_smidge(TEST_INPUT_1));

        assert_eq!(31603, get_summary_of_smidge(input.as_str()));
    }
}
