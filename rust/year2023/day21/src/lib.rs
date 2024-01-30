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

    println!("{:?}", has_seen_final_plot);

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
    use crate::get_garden_plots_in_steps;

    const TEST_INPUT: &str = "...........
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
        assert_eq!(16, get_garden_plots_in_steps(6, TEST_INPUT));
    }
}
