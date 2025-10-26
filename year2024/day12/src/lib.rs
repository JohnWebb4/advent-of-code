use std::collections::{HashMap, HashSet, VecDeque};

#[derive(Debug)]
struct FenceMap {
    map: HashMap<(usize, usize), char>,
    pub x_max: usize,
    pub y_max: usize,
}

impl FenceMap {
    fn new(map: HashMap<(usize, usize), char>, x_max: usize, y_max: usize) -> FenceMap {
        FenceMap { map, x_max, y_max }
    }

    fn get(&self, x: usize, y: usize) -> Option<&char> {
        self.map.get(&(x, y))
    }
}

pub fn get_total_price_of_fencing_all_regions(input: &str) -> u64 {
    let fence_map = get_fence_map(input);

    let mut total_price = 0;

    let mut has_visited = HashSet::<(usize, usize)>::new();

    for y in 0..=fence_map.y_max {
        for x in 0..=fence_map.x_max {
            if let Some(cell) = fence_map.get(x, y) {
                if !has_visited.contains(&(x, y)) {
                    // New region
                    let mut perimeter = 0;
                    let mut area = 0;

                    let mut region_scanner = VecDeque::<(usize, usize)>::new();
                    region_scanner.push_back((x, y));

                    while let Some((next_x, next_y)) = region_scanner.pop_front() {
                        if !has_visited.contains(&(next_x, next_y)) {
                            // Look for nearby neighbors (no diaganols)
                            perimeter += 4;
                            let mut neighbors = Vec::new();
                            if next_x > 0 {
                                neighbors.push((next_x - 1, next_y));
                            }
                            if next_x < fence_map.x_max {
                                neighbors.push((next_x + 1, next_y));
                            }
                            if next_y > 0 {
                                neighbors.push((next_x, next_y - 1))
                            }
                            if next_y < fence_map.y_max {
                                neighbors.push((next_x, next_y + 1))
                            }

                            neighbors.iter().for_each(|(neighbor_x, neighbor_y)| {
                                if fence_map.get(*neighbor_x, *neighbor_y) == Some(cell) {
                                    // Interior side
                                    perimeter -= 1;

                                    // Next section of region
                                    if !has_visited.contains(&(*neighbor_x, *neighbor_y)) {
                                        region_scanner.push_back((*neighbor_x, *neighbor_y));
                                    }
                                }
                            });

                            area += 1;
                            has_visited.insert((next_x, next_y));
                        }
                    }

                    total_price += area * perimeter;
                }
            }
        }
    }

    total_price
}

pub fn get_bulk_price_of_fencing_all_regions(input: &str) -> u64 {
    let fence_map = get_fence_map(input);

    let mut total_price = 0;

    let mut has_visited = HashSet::<(usize, usize)>::new();

    for y in 0..=fence_map.y_max {
        for x in 0..=fence_map.x_max {
            if let Some(cell) = fence_map.get(x, y) {
                if !has_visited.contains(&(x, y)) {
                    let mut corners = 0;
                    let mut area = 0;

                    let mut region_scanner = VecDeque::<(usize, usize)>::new();
                    region_scanner.push_back((x, y));

                    while let Some((next_x, next_y)) = region_scanner.pop_front() {
                        if !has_visited.contains(&(next_x, next_y)) {
                            // Look for nearby neighbors (no diaganols)
                            let mut neighbors = Vec::new();
                            if next_x > 0 {
                                neighbors.push((next_x - 1, next_y));
                            }
                            if next_x < fence_map.x_max {
                                neighbors.push((next_x + 1, next_y));
                            }
                            if next_y > 0 {
                                neighbors.push((next_x, next_y - 1))
                            }
                            if next_y < fence_map.y_max {
                                neighbors.push((next_x, next_y + 1))
                            }

                            // Exterior corners
                            if (next_x == 0 || fence_map.get(next_x - 1, next_y) != Some(cell))
                                && (next_y == 0 || fence_map.get(next_x, next_y - 1) != Some(cell))
                            {
                                corners += 1;
                            }
                            if (next_x == fence_map.x_max
                                || fence_map.get(next_x + 1, next_y) != Some(cell))
                                && (next_y == 0 || fence_map.get(next_x, next_y - 1) != Some(cell))
                            {
                                corners += 1;
                            }
                            if (next_x == 0 || fence_map.get(next_x - 1, next_y) != Some(cell))
                                && (next_y == fence_map.y_max
                                    || fence_map.get(next_x, next_y + 1) != Some(cell))
                            {
                                corners += 1
                            }
                            if (next_x == fence_map.x_max
                                || fence_map.get(next_x + 1, next_y) != Some(cell))
                                && (next_y == fence_map.y_max
                                    || fence_map.get(next_x, next_y + 1) != Some(cell))
                            {
                                corners += 1
                            }
                            // Interior corners
                            if next_x > 0
                                && next_y > 0
                                && fence_map.get(next_x - 1, next_y) == Some(cell)
                                && fence_map.get(next_x, next_y - 1) == Some(cell)
                                && fence_map.get(next_x - 1, next_y - 1) != Some(cell)
                            {
                                corners += 1
                            }
                            if next_x < fence_map.x_max
                                && next_y > 0
                                && fence_map.get(next_x + 1, next_y) == Some(cell)
                                && fence_map.get(next_x, next_y - 1) == Some(cell)
                                && fence_map.get(next_x + 1, next_y - 1) != Some(cell)
                            {
                                corners += 1
                            }
                            if next_x > 0
                                && next_y < fence_map.y_max
                                && fence_map.get(next_x - 1, next_y) == Some(cell)
                                && fence_map.get(next_x, next_y + 1) == Some(cell)
                                && fence_map.get(next_x - 1, next_y + 1) != Some(cell)
                            {
                                corners += 1
                            }
                            if next_x < fence_map.x_max
                                && next_y < fence_map.y_max
                                && fence_map.get(next_x + 1, next_y) == Some(cell)
                                && fence_map.get(next_x, next_y + 1) == Some(cell)
                                && fence_map.get(next_x + 1, next_y + 1) != Some(cell)
                            {
                                corners += 1
                            }

                            neighbors.iter().for_each(|(neighbor_x, neighbor_y)| {
                                if fence_map.get(*neighbor_x, *neighbor_y) == Some(cell) {
                                    // Next section of region
                                    if !has_visited.contains(&(*neighbor_x, *neighbor_y)) {
                                        region_scanner.push_back((*neighbor_x, *neighbor_y));
                                    }
                                }
                            });

                            area += 1;
                            has_visited.insert((next_x, next_y));
                        }
                    }

                    total_price += area * corners;
                }
            }
        }
    }

    total_price
}

fn get_fence_map(input: &str) -> FenceMap {
    let mut x_max: usize = 0;
    let mut y_max: usize = 0;
    let map = input.split("\n").enumerate().fold(
        HashMap::<(usize, usize), char>::new(),
        |mut m, (row_i, row)| {
            if row_i > y_max {
                y_max = row_i;
            }

            row.chars().enumerate().for_each(|(col_i, char)| {
                if col_i > x_max {
                    x_max = col_i;
                }

                m.insert((row_i, col_i), char);
            });

            m
        },
    );

    FenceMap::new(map, x_max, y_max)
}

#[cfg(test)]
mod tests {
    // use std::fs;

    use std::fs;

    use super::get_total_price_of_fencing_all_regions;
    use crate::get_bulk_price_of_fencing_all_regions;

    const TEST_1: &str = "AAAA
BBCD
BBCC
EEEC";

    const TEST_2: &str = "OOOOO
OXOXO
OOOOO
OXOXO
OOOOO";

    const TEST_3: &str = "RRRRIICCFF
RRRRIICCCF
VVRRRCCFFF
VVRCCCJFFF
VVVVCJJCFE
VVIVCCJJEE
VVIIICJJEE
MIIIIIJJEE
MIIISIJEEE
MMMISSJEEE";

    const TEST_4: &str = "EEEEE
EXXXX
EEEEE
EXXXX
EEEEE";

    const TEST_5: &str = "AAAAAA
AAABBA
AAABBA
ABBAAA
ABBAAA
AAAAAA";

    #[test]
    fn it_gets_total_price_of_fencing_all_regions() {
        let input = fs::read_to_string("./input.txt").unwrap();

        assert_eq!(get_total_price_of_fencing_all_regions(TEST_1), 140);
        assert_eq!(get_total_price_of_fencing_all_regions(TEST_2), 772);
        assert_eq!(get_total_price_of_fencing_all_regions(TEST_3), 1930);
        assert_eq!(get_total_price_of_fencing_all_regions(&input), 1489582);

        assert_eq!(get_bulk_price_of_fencing_all_regions(TEST_1), 80);
        assert_eq!(get_bulk_price_of_fencing_all_regions(TEST_2), 436);
        assert_eq!(get_bulk_price_of_fencing_all_regions(TEST_4), 236);
        assert_eq!(get_bulk_price_of_fencing_all_regions(TEST_5), 368);
        assert_eq!(get_bulk_price_of_fencing_all_regions(TEST_3), 1206);
        assert_eq!(get_bulk_price_of_fencing_all_regions(&input), 914966);
    }
}
