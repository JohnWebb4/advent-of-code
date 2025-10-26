use std::collections::{HashMap, HashSet, LinkedList};

#[derive(Debug)]
struct TrailPath {
    trail_head: String,
    x: usize,
    y: usize,
}

pub fn get_sum_of_all_trailheads(input: &str) -> u32 {
    let map = get_map(input);

    let rows = input.split("\n").collect::<Vec<&str>>();
    let y_max = rows.len();
    let x_max = rows[0].len();

    let trail_heads = map
        .iter()
        .filter(|(_k, v)| v == &&0)
        .map(|(k, _v)| k)
        .collect::<Vec<&String>>();

    let mut trail_head_score_map = trail_heads.iter().fold(HashMap::new(), |mut m, s| {
        m.insert((*s).clone(), HashSet::<String>::new());

        m
    });

    let mut trail_path_queue = trail_heads
        .iter()
        .map(|trail_head| {
            let (x, y) = get_map_coordinates_from_key(trail_head);

            TrailPath {
                trail_head: trail_head.to_string(),
                x,
                y,
            }
        })
        .collect::<LinkedList<TrailPath>>();

    while !trail_path_queue.is_empty() {
        let trail_path = trail_path_queue.pop_front().unwrap();

        [(0_i64, 1_i64), (0, -1), (1, 0), (-1, 0)]
            .iter()
            .for_each(|(x_diff, y_diff)| {
                let new_x = (trail_path.x as i64) + x_diff;
                let new_y = (trail_path.y as i64) + y_diff;
                let trail_head = trail_path.trail_head.clone();

                if new_x >= 0 && new_x < x_max as i64 {
                    if new_y >= 0 && new_y < y_max as i64 {
                        let current_key = get_map_key(trail_path.x, trail_path.y);
                        let current_height = map.get(&current_key).unwrap();

                        let new_key = get_map_key(new_x as usize, new_y as usize);
                        let new_height = map.get(&new_key).unwrap();

                        if new_height == &(current_height + 1) {
                            if new_height == &9 {
                                let entry = trail_head_score_map.entry(trail_head);
                                entry.and_modify(|set| {
                                    (*set).insert(new_key);
                                });
                            } else {
                                let new_trail_path = TrailPath {
                                    trail_head: trail_head.clone(),
                                    x: new_x as usize,
                                    y: new_y as usize,
                                };

                                trail_path_queue.push_back(new_trail_path);
                            }
                        }
                    }
                }
            });
    }

    trail_head_score_map
        .values()
        .fold(0, |sum, set| sum + set.len() as u32)
}

pub fn get_rating_of_all_trailheads(input: &str) -> u32 {
    let map = get_map(input);

    let rows = input.split("\n").collect::<Vec<&str>>();
    let y_max = rows.len();
    let x_max = rows[0].len();

    let trail_heads = map
        .iter()
        .filter(|(_k, v)| v == &&0)
        .map(|(k, _v)| k)
        .collect::<Vec<&String>>();

    let mut trail_head_rating_map = trail_heads.iter().fold(HashMap::new(), |mut m, s| {
        m.insert((*s).clone(), 0);

        m
    });

    let mut trail_path_queue = trail_heads
        .iter()
        .map(|trail_head| {
            let (x, y) = get_map_coordinates_from_key(trail_head);

            TrailPath {
                trail_head: trail_head.to_string(),
                x,
                y,
            }
        })
        .collect::<LinkedList<TrailPath>>();

    while !trail_path_queue.is_empty() {
        let trail_path = trail_path_queue.pop_front().unwrap();

        [(0_i64, 1_i64), (0, -1), (1, 0), (-1, 0)]
            .iter()
            .for_each(|(x_diff, y_diff)| {
                let new_x = (trail_path.x as i64) + x_diff;
                let new_y = (trail_path.y as i64) + y_diff;
                let trail_head = trail_path.trail_head.clone();

                if new_x >= 0 && new_x < x_max as i64 {
                    if new_y >= 0 && new_y < y_max as i64 {
                        let current_key = get_map_key(trail_path.x, trail_path.y);
                        let current_height = map.get(&current_key).unwrap();

                        let new_key = get_map_key(new_x as usize, new_y as usize);
                        let new_height = map.get(&new_key).unwrap();

                        if new_height == &(current_height + 1) {
                            if new_height == &9 {
                                let entry = trail_head_rating_map.entry(trail_head);
                                entry.and_modify(|count| {
                                    *count = *count + 1;
                                });
                            } else {
                                let new_trail_path = TrailPath {
                                    trail_head: trail_head.clone(),
                                    x: new_x as usize,
                                    y: new_y as usize,
                                };

                                trail_path_queue.push_back(new_trail_path);
                            }
                        }
                    }
                }
            });
    }

    trail_head_rating_map
        .values()
        .fold(0, |sum, count| sum + *count as u32)
}


fn get_map(map_string: &str) -> HashMap<String, i32> {
    map_string
        .split("\n")
        .enumerate()
        .fold(HashMap::<String, i32>::new(), |mut m, (y, r)| {
            r.chars().enumerate().for_each(|(x, c)| {
                let i = c.to_string().parse().unwrap_or(-1);

                m.insert(get_map_key(x, y), i);
            });

            m
        })
}

fn get_map_key(x: usize, y: usize) -> String {
    format!("{},{}", x, y)
}

fn get_map_coordinates_from_key(key: &String) -> (usize, usize) {
    let parts = key
        .split(",")
        .map(|s| s.parse().unwrap())
        .collect::<Vec<usize>>();

    (parts[0], parts[1])
}

#[cfg(test)]
mod tests {
    use std::fs;

    use super::*;

    const TEST_1: &str = "0123
1234
8765
9876";

    const TEST_2: &str = "...0...
...1...
...2...
6543456
7.....7
8.....8
9.....9";

    const TEST_3: &str = "..90..9
...1.98
...2..7
6543456
765.987
876....
987....";

    const TEST_4: &str = "10..9..
2...8..
3...7..
4567654
...8..3
...9..2
.....01";

    const TEST_5: &str = "89010123
78121874
87430965
96549874
45678903
32019012
01329801
10456732";

const TEST_6: &str = ".....0.
..4321.
..5..2.
..6543.
..7..4.
..8765.
..9....";

const TEST_7: &str = "..90..9
...1.98
...2..7
6543456
765.987
876....
987....";

const TEST_8: &str = "012345
123456
234567
345678
4.6789
56789.";

const TEST_9: &str = "89010123
78121874
87430965
96549874
45678903
32019012
01329801
10456732";

    #[test]
    fn it_should_get_sum_of_all_trailheads() {
        let input = fs::read_to_string("./input.txt").unwrap();

        assert_eq!(get_sum_of_all_trailheads(TEST_1), 1);
        assert_eq!(get_sum_of_all_trailheads(TEST_2), 2);
        assert_eq!(get_sum_of_all_trailheads(TEST_3), 4);
        assert_eq!(get_sum_of_all_trailheads(TEST_4), 3);
        assert_eq!(get_sum_of_all_trailheads(TEST_5), 36);
        assert_eq!(get_sum_of_all_trailheads(&input), 593);

        assert_eq!(get_rating_of_all_trailheads(TEST_6), 3);
        assert_eq!(get_rating_of_all_trailheads(TEST_7), 13);
        assert_eq!(get_rating_of_all_trailheads(TEST_8), 227);
        assert_eq!(get_rating_of_all_trailheads(TEST_9), 81);
        assert_eq!(get_rating_of_all_trailheads(&input), 1192);
    }
}
