use std::collections::{HashMap, HashSet};

use cell::Cell;
use pannel::{new_pannel, Pannel};

mod cell;
mod pannel;

pub fn get_total_load_after_spin_cycle(input: &str) -> usize {
    let (pannel, rounded_rocks) = read_pannel(input);

    let mut spun_rocks = rounded_rocks.clone();
    let mut has_seen = HashMap::<Vec<(usize, usize)>, usize>::new();
    let mut i = 0_usize;
    while i < 1_000_000_000 {
        if let Some(first_seen_i) = has_seen.get(&spun_rocks) {
            // We are in a loop
            // If at least one loop
            let loop_size = i - first_seen_i;
            let num_loops = (1_000_000_000 - i) / loop_size;

            if num_loops > 0 {
                i += loop_size * num_loops;
            } else {
                // Not enough space to loop. Just finish it out
                let new_spun_rocks = spin(&pannel, &spun_rocks);

                spun_rocks = new_spun_rocks;

                i += 1;
            }
        } else {
            let new_spun_rocks = spin(&pannel, &spun_rocks);

            has_seen.insert(spun_rocks, i);

            spun_rocks = new_spun_rocks;

            i += 1;
        }
    }

    spun_rocks
        .iter()
        .fold(0, |sum, (_x, y)| sum + pannel.y_max + 1 - y)
}

pub fn get_total_load_of_north_beams(input: &str) -> usize {
    let (pannel, rounded_rocks) = read_pannel(input);

    let tilted_rounded_rocks = tilt_north(&pannel, &rounded_rocks);

    tilted_rounded_rocks
        .iter()
        .fold(0, |sum, (_x, y)| sum + pannel.y_max + 1 - y)
}

fn spin(pannel: &Pannel, spun_rocks: &[(usize, usize)]) -> Vec<(usize, usize)> {
    let mut result = tilt_east(
        pannel,
        &tilt_south(pannel, &tilt_west(pannel, &tilt_north(pannel, spun_rocks))),
    );

    result.sort_by(|(x, y), (x_b, y_b)| y.cmp(y_b).then(x.cmp(x_b)));

    result
}

fn tilt_east(pannel: &Pannel, rounded_rocks: &[(usize, usize)]) -> Vec<(usize, usize)> {
    let mut sorted_rounded_rocks = rounded_rocks.to_vec();
    sorted_rounded_rocks.sort_by(|(x, y), (x_b, y_b)| x_b.cmp(x).then(y.cmp(y_b)));

    sorted_rounded_rocks
        .iter_mut()
        .fold(vec![], |mut v: Vec<(usize, usize)>, (x, y)| {
            let mut new_x = *x;
            while new_x < pannel.x_max
                && !pannel.contains(new_x + 1, *y)
                && !v.contains(&(new_x + 1, *y))
            {
                new_x += 1;
            }

            v.push((new_x, *y));

            v
        })
}

fn tilt_south(pannel: &Pannel, rounded_rocks: &[(usize, usize)]) -> Vec<(usize, usize)> {
    let mut sorted_rounded_rocks = rounded_rocks.to_vec();
    sorted_rounded_rocks.sort_by(|(x, y), (x_b, y_b)| y_b.cmp(y).then(x.cmp(x_b)));

    sorted_rounded_rocks
        .iter_mut()
        .fold(vec![], |mut v: Vec<(usize, usize)>, (x, y)| {
            let mut new_y = *y;
            while new_y < pannel.y_max
                && !pannel.contains(*x, new_y + 1)
                && !v.contains(&(*x, new_y + 1))
            {
                new_y += 1;
            }

            v.push((*x, new_y));

            v
        })
}

fn tilt_west(pannel: &Pannel, rounded_rocks: &[(usize, usize)]) -> Vec<(usize, usize)> {
    let mut sorted_rounded_rocks = rounded_rocks.to_vec();
    sorted_rounded_rocks.sort_by(|(x, y), (x_b, y_b)| x.cmp(x_b).then(y.cmp(y_b)));

    sorted_rounded_rocks
        .iter_mut()
        .fold(vec![], |mut v: Vec<(usize, usize)>, (x, y)| {
            let mut new_x = *x;
            while new_x > 0 && !pannel.contains(new_x - 1, *y) && !v.contains(&(new_x - 1, *y)) {
                new_x -= 1;
            }

            v.push((new_x, *y));

            v
        })
}

fn tilt_north(pannel: &Pannel, rounded_rocks: &[(usize, usize)]) -> Vec<(usize, usize)> {
    let mut sorted_rounded_rocks = rounded_rocks.to_vec();
    sorted_rounded_rocks.sort_by(|(x, y), (x_b, y_b)| y.cmp(y_b).then(x.cmp(x_b)));

    sorted_rounded_rocks
        .iter_mut()
        .fold(vec![], |mut v: Vec<(usize, usize)>, (x, y)| {
            let mut new_y = *y;
            while new_y > 0 && !pannel.contains(*x, new_y - 1) && !v.contains(&(*x, new_y - 1)) {
                new_y -= 1;
            }

            v.push((*x, new_y));

            v
        })
}

fn read_pannel(input: &str) -> (Pannel, Vec<(usize, usize)>) {
    let mut x_max = usize::MIN;
    let mut y_max = usize::MIN;
    let mut rounded_rocks: Vec<(usize, usize)> = vec![];

    let square_set = input.split('\n').enumerate().fold(
        HashSet::<(usize, usize)>::new(),
        |mut set, (row_i, row)| {
            if row_i > y_max {
                y_max = row_i;
            }

            row.chars().enumerate().for_each(|(char_i, c)| {
                if char_i > x_max {
                    x_max = char_i;
                }

                match Cell::from_char(c) {
                    Ok(Cell::Rounded) => {
                        rounded_rocks.push((char_i, row_i));
                    }
                    Ok(Cell::Square) => {
                        set.insert((char_i, row_i));
                    }
                    _ => {}
                }
            });

            set
        },
    );

    (new_pannel(square_set, x_max, y_max), rounded_rocks)
}

#[cfg(test)]
mod test {
    use std::fs;

    use crate::{get_total_load_after_spin_cycle, get_total_load_of_north_beams};

    const TEST_INPUT_1: &str = "O....#....
O.OO#....#
.....##...
OO.#O....O
.O.....O#.
O.#..O.#.#
..O..#O..O
.......O..
#....###..
#OO..#....";

    #[test]
    pub fn test_get_total_load_of_north_beams() {
        assert_eq!(136, get_total_load_of_north_beams(TEST_INPUT_1));

        let input = fs::read_to_string("./input.txt").unwrap();

        assert_eq!(106648, get_total_load_of_north_beams(input.as_str()));

        assert_eq!(64, get_total_load_after_spin_cycle(TEST_INPUT_1));

        // Really slow. About 144 spins till repeats
        assert_eq!(87700, get_total_load_after_spin_cycle(input.as_str()));
    }
}
