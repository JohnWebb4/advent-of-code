use std::{
    cmp::Ordering,
    collections::{HashMap, HashSet, LinkedList},
};

use garden::{new_garden, Cell, Garden};

mod garden;

pub fn get_garden_plots_in_steps(num_steps: u32, input: &str) -> usize {
    let garden = read_garden(input);

    let mut has_seen_path = HashSet::<(usize, usize, u32)>::new();
    let mut has_seen_final_plot = HashSet::<(usize, usize)>::new();

    let mut path_queue = LinkedList::<(usize, usize, u32)>::new();
    path_queue.push_back((garden.start_x, garden.start_y, 0));

    while !path_queue.is_empty() {
        let (x, y, step_i) = path_queue.pop_front().unwrap();

        match step_i.cmp(&num_steps) {
            Ordering::Equal => {
                has_seen_final_plot.insert((x, y));
            }
            Ordering::Less => {
                [[x + 1, y], [x - 1, y], [x, y + 1], [x, y - 1]]
                    .iter()
                    .for_each(|[next_x, next_y]| {
                        let next_cell = garden.get_cell(*next_x, *next_y);

                        match next_cell {
                            Some(Cell::Garden) | Some(Cell::Start) => {
                                if !has_seen_path.contains(&(*next_x, *next_y, step_i + 1)) {
                                    path_queue.push_back((*next_x, *next_y, step_i + 1));

                                    has_seen_path.insert((*next_x, *next_y, step_i + 1));
                                }
                            }
                            _ => {}
                        }
                    });
            }
            Ordering::Greater => {}
        }
    }

    has_seen_final_plot.len()
}

pub fn get_garden_plots_in_steps_infinite(num_steps: u32, input: &str) -> usize {
    let garden = read_garden(input);

    let mut has_seen_path = HashSet::<(i32, i32, usize, usize, u32)>::new();
    let mut has_seen_final_plot = HashSet::<(i32, i32)>::new();

    let mut path_queue = LinkedList::<(i32, i32, usize, usize, u32)>::new();
    path_queue.push_back((
        garden.start_x as i32,
        garden.start_y as i32,
        garden.start_x,
        garden.start_y,
        0,
    ));

    while !path_queue.is_empty() {
        let (x, y, rel_x, rel_y, step_i) = path_queue.pop_front().unwrap();

        match step_i.cmp(&num_steps) {
            Ordering::Equal => {
                has_seen_final_plot.insert((x, y));
            }
            Ordering::Less => {
                [[1_i32, 0], [-1, 0], [0, 1], [0, -1]]
                    .iter()
                    .for_each(|[diff_x, diff_y]| {
                        let next_x = x + diff_x;
                        let next_y = y + diff_y;

                        // This only works with diff_x is 1, 0, or -1
                        let next_rel_x = if rel_x == 0 && diff_x < &0 {
                            garden.width - 1
                        } else if rel_x >= garden.width - 1 && diff_x > &0 {
                            0
                        } else {
                            ((rel_x as i32) + *diff_x) as usize
                        };

                        let next_rel_y = if rel_y == 0 && diff_y < &0 {
                            garden.height - 1
                        } else if rel_y >= garden.height - 1 && diff_y > &0 {
                            0
                        } else {
                            ((rel_y as i32) + *diff_y) as usize
                        };

                        let next_cell = garden.get_cell(next_rel_x, next_rel_y);

                        match next_cell {
                            Some(Cell::Garden) | Some(Cell::Start) => {
                                if !has_seen_path.contains(&(
                                    next_x,
                                    next_y,
                                    next_rel_x,
                                    next_rel_y,
                                    step_i + 1,
                                )) {
                                    path_queue.push_back((
                                        next_x,
                                        next_y,
                                        next_rel_x,
                                        next_rel_y,
                                        step_i + 1,
                                    ));

                                    has_seen_path.insert((
                                        next_x,
                                        next_y,
                                        next_rel_x,
                                        next_rel_y,
                                        step_i + 1,
                                    ));
                                }
                            }
                            _ => {}
                        }
                    });
            }
            Ordering::Greater => {}
        }
    }

    has_seen_final_plot.len()
}

fn read_garden(input: &str) -> Garden {
    let mut start_x = 0;
    let mut start_y = 0;
    let mut width = 0;
    let mut height = 0;

    let inner_map = input.split('\n').enumerate().fold(
        HashMap::<String, Cell>::new(),
        |mut map, (row_i, row)| {
            row.chars().enumerate().for_each(|(col_i, c)| {
                let cell = Cell::from_char(c).unwrap();

                if cell == Cell::Start {
                    start_x = col_i;
                    start_y = row_i;
                }

                map.insert(Garden::get_key(col_i, row_i), cell);

                if col_i > width {
                    width = col_i;
                }
            });

            if row_i > height {
                height = row_i;
            }

            map
        },
    );

    new_garden(inner_map, start_x, start_y, width + 1, height + 1)
}

#[cfg(test)]
mod test {
    use std::fs;

    use crate::{get_garden_plots_in_steps, get_garden_plots_in_steps_infinite};

    const TEST_INPUT_1: &str = "...........
.....###.#.
.###.##..#.
..#.#...#..
....#.#....
.##..S####.
.##..#...#.
.......##..
.##.#.####.
.##..##.##.
...........";

    #[test]
    fn test_get_garden_plots_in_steps() {
        assert_eq!(16, get_garden_plots_in_steps(6, TEST_INPUT_1));

        // let input = fs::read_to_string("./input.txt").unwrap();

        // assert_eq!(3682, get_garden_plots_in_steps(64, input.as_str()));

        assert_eq!(16, get_garden_plots_in_steps_infinite(6, TEST_INPUT_1));
        assert_eq!(50, get_garden_plots_in_steps_infinite(10, TEST_INPUT_1));
        assert_eq!(1594, get_garden_plots_in_steps_infinite(50, TEST_INPUT_1));
        assert_eq!(6536, get_garden_plots_in_steps_infinite(100, TEST_INPUT_1));
        // assert_eq!(
        //     167004,
        //     get_garden_plots_in_steps_infinite(500, TEST_INPUT_1)
        // );
    }
}
