use std::collections::{HashMap, HashSet};

pub fn count_unique_antinodes(map_string: &str) -> usize {
    let lines = map_string.split("\n").collect::<Vec<&str>>();
    let x_max = lines[0].len() as i32;
    let y_max = lines.len() as i32;

    let reciever_groups = map_string.split("\n").enumerate().fold(
        HashMap::<char, Vec<(i32, i32)>>::new(),
        |mut map, (i_line, line)| {
            line.chars().enumerate().for_each(|(i_c, c)| {
                if c != '.' {
                    map.entry(c)
                        .or_insert(vec![])
                        .push((i_c as i32, i_line as i32));
                }
            });

            map
        },
    );

    let mut antinode_set = HashSet::<(i32, i32)>::new();

    for (_, group) in reciever_groups.iter() {
        let mut i = 0;
        while i < group.len() {
            let mut j = i + 1;
            while j < group.len() {
                // Calculate two anti nodes
                let node_1 = group[i];
                let node_2 = group[j];

                let mut left_node = node_1;
                let mut right_node = node_2;
                // Incase node_2 occurs before node_1. I don't think this happens but :shrug:
                if node_1.0 >= node_2.0 {
                    left_node = node_2;
                    right_node = node_1;
                }

                let x_diff = right_node.0 - left_node.0;
                let y_diff = right_node.1 - left_node.1;

                let anti_node_1 = (left_node.0 - x_diff, left_node.1 - y_diff);
                if anti_node_1.0 >= 0
                    && anti_node_1.0 < x_max
                    && anti_node_1.1 >= 0
                    && anti_node_1.1 < y_max
                {
                    antinode_set.insert(anti_node_1);
                }

                let anti_node_2 = (right_node.0 + x_diff, right_node.1 + y_diff);
                if anti_node_2.0 >= 0
                    && anti_node_2.0 < x_max
                    && anti_node_2.1 >= 0
                    && anti_node_2.1 < y_max
                {
                    antinode_set.insert(anti_node_2);
                }

                j += 1;
            }

            i += 1;
        }
    }

    antinode_set.len()
}

pub fn count_unique_antinodes_harmonics(map_string: &str) -> usize {
    let lines = map_string.split("\n").collect::<Vec<&str>>();
    let x_max = lines[0].len() as i32;
    let y_max = lines.len() as i32;

    let reciever_groups = map_string.split("\n").enumerate().fold(
        HashMap::<char, Vec<(i32, i32)>>::new(),
        |mut map, (i_line, line)| {
            line.chars().enumerate().for_each(|(i_c, c)| {
                if c != '.' {
                    map.entry(c)
                        .or_insert(vec![])
                        .push((i_c as i32, i_line as i32));
                }
            });

            map
        },
    );

    let mut antinode_set = HashSet::<(i32, i32)>::new();

    for (_, group) in reciever_groups.iter() {
        for antenna in group {
            antinode_set.insert(antenna.clone());
        }
    }

    for (_, group) in reciever_groups.iter() {
        let mut i = 0;
        while i < group.len() {
            let mut j = i + 1;
            while j < group.len() {
                // Calculate two anti nodes
                let node_1 = group[i];
                let node_2 = group[j];

                let mut left_node = node_1;
                let mut right_node = node_2;
                // Incase node_2 occurs before node_1. I don't think this happens but :shrug:
                if node_1.0 >= node_2.0 {
                    left_node = node_2;
                    right_node = node_1;
                }

                let x_diff = right_node.0 - left_node.0;
                let y_diff = right_node.1 - left_node.1;

                let mut harmonic_1_step = 1;
                while left_node.0 - harmonic_1_step * x_diff >= 0
                    && left_node.0 - harmonic_1_step * x_diff < x_max
                    && left_node.1 - harmonic_1_step * y_diff >= 0
                    && left_node.1 - harmonic_1_step * y_diff < y_max
                {
                    let anti_node_1 = (
                        left_node.0 - harmonic_1_step * x_diff,
                        left_node.1 - harmonic_1_step * y_diff,
                    );

                    antinode_set.insert(anti_node_1);
                    harmonic_1_step += 1;
                }

                let mut harmonic_2_step = 1;
                while right_node.0 + harmonic_2_step * x_diff >= 0
                    && right_node.0 + harmonic_2_step * x_diff < x_max
                    && right_node.1 + harmonic_2_step * y_diff >= 0
                    && right_node.1 + harmonic_2_step * y_diff < y_max
                {
                    let anti_node_2 = (
                        right_node.0 + harmonic_2_step * x_diff,
                        right_node.1 + harmonic_2_step * y_diff,
                    );

                    antinode_set.insert(anti_node_2);
                    harmonic_2_step += 1;
                }

                j += 1;
            }

            i += 1;
        }
    }

    antinode_set.len()
}

#[cfg(test)]
mod tests {
    use std::fs;

    use super::*;

    const TEST_1: &str = "............
........0...
.....0......
.......0....
....0.......
......A.....
............
............
........A...
.........A..
............
............";

    #[test]
    fn test_count_unique_locations() {
        let file = fs::read_to_string("./input.txt").unwrap();
        let input = file.as_str();

        assert_eq!(count_unique_antinodes(TEST_1), 14);
        assert_eq!(count_unique_antinodes(input), 348);

        assert_eq!(count_unique_antinodes_harmonics(TEST_1), 34);
        assert_eq!(count_unique_antinodes_harmonics(input), 1221);
    }
}
