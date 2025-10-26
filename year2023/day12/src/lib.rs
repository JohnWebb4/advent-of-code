use std::collections::HashMap;

use spring::Spring;

mod spring;

pub fn sum_of_folded_counts(input: &str) -> usize {
    let unfolded_input = unfold(input);

    unfolded_input
        .as_str()
        .split('\n')
        .map(get_count)
        .sum::<usize>()
}

pub fn sum_of_counts(input: &str) -> usize {
    // get_count(input.split('\n').collect::<Vec<&str>>()[0])

    input.split('\n').map(get_count).sum::<usize>()
}

fn unfold(input: &str) -> String {
    input
        .split('\n')
        .map(unfold_line)
        .collect::<Vec<String>>()
        .join("\n")
}

fn unfold_line(input: &str) -> String {
    let input_parts = input.split(' ').collect::<Vec<&str>>();
    let unfolded_record = [input_parts[0]].repeat(5).join("?");
    let unfolded_groups = [input_parts[1]].repeat(5).join(",");

    [unfolded_record, unfolded_groups].join(" ")
}

fn get_count(input: &str) -> usize {
    let input_parts = input.split(' ').collect::<Vec<&str>>();
    let record = input_parts[0];
    let group_string = input_parts[1];

    let springs = record
        .chars()
        .map(|c| Spring::from_char(c).unwrap())
        .collect::<Vec<Spring>>();
    let groups = group_string
        .split(',')
        .map(|g| g.parse::<usize>().unwrap())
        .collect::<Vec<usize>>();

    // Populate all possibilities
    let mut has_solved_map = HashMap::<(usize, usize), usize>::new();
    has_solved_map.insert((springs.len(), groups.len()), 1);
    has_solved_map.insert((springs.len() + 1, groups.len()), 1);
    for spring_i in (0..springs.len()).rev() {
        let spring = springs[spring_i];

        match spring {
            Spring::Damaged | Spring::Unknown => {
                // Could be at end of group. Pass on previous end
                let next_end = *has_solved_map
                    .get(&(spring_i + 1, groups.len()))
                    .unwrap_or(&0);

                has_solved_map.insert((spring_i, groups.len()), next_end);
            }
            _ => {}
        }

        groups.iter().enumerate().for_each(|(group_i, _)| {
            match spring {
                Spring::Damaged => {
                    // Cannnot check group on damanged. Skip to next spring.
                    has_solved_map.insert(
                        (spring_i, group_i),
                        get_damaged_possibilities(
                            &has_solved_map,
                            &springs,
                            &groups,
                            spring_i,
                            group_i,
                        ),
                    );
                }
                Spring::Operational => {
                    has_solved_map.insert(
                        (spring_i, group_i),
                        get_operational_possibilities(
                            &has_solved_map,
                            &springs,
                            &groups,
                            spring_i,
                            group_i,
                        ),
                    );
                }
                Spring::Unknown => {
                    // Check if damaged
                    let damanged_next_spring_possibilities = get_damaged_possibilities(
                        &has_solved_map,
                        &springs,
                        &groups,
                        spring_i,
                        group_i,
                    );

                    // Check if operational
                    let operational_next_spring_possibilities = get_operational_possibilities(
                        &has_solved_map,
                        &springs,
                        &groups,
                        spring_i,
                        group_i,
                    );

                    // Possibilities is sum of both
                    has_solved_map.insert(
                        (spring_i, group_i),
                        damanged_next_spring_possibilities + operational_next_spring_possibilities,
                    );
                }
            }
        });
    }

    // pretty_print_possibilities(&has_solved_map);

    *has_solved_map.get(&(0, 0)).unwrap()
}

fn get_damaged_possibilities(
    has_solved_map: &HashMap<(usize, usize), usize>,
    springs: &[Spring],
    groups: &[usize],
    spring_i: usize,
    group_i: usize,
) -> usize {
    if group_i == groups.len() && spring_i >= springs.len() {
        // If at the end of the groups
        1
    } else {
        *has_solved_map.get(&(spring_i + 1, group_i)).unwrap_or(&0)
    }
}

fn get_operational_possibilities(
    has_solved_map: &HashMap<(usize, usize), usize>,
    springs: &[Spring],
    groups: &[usize],
    spring_i: usize,
    group_i: usize,
) -> usize {
    let group = groups[group_i];
    let end_spring = spring_i + group;

    if group_i == groups.len() - 1 && spring_i >= springs.len() {
        // If at the end of the groups
        1
    } else if end_spring > springs.len()
        || (spring_i..end_spring).any(|i| springs[i] == Spring::Damaged)
        || (end_spring < springs.len() && springs[end_spring] == Spring::Operational)
    {
        // If not enough springs for group
        // Or if the group is too short
        // Or if the group is too long
        0
    } else {
        // Possible. Move to next group
        *has_solved_map
            .get(&(end_spring + 1, group_i + 1))
            .unwrap_or(&0)
    }
}

// fn pretty_print_possibilities(has_solved_map: &HashMap<(usize, usize), usize>) {
//     let mut possibilities = has_solved_map
//         .iter()
//         .collect::<Vec<(&(usize, usize), &usize)>>();

//     possibilities.sort_by(|((spring_a, group_a), _), ((spring_b, group_b), _)| {
//         spring_a.cmp(spring_b).then(group_a.cmp(group_b))
//     });

//     possibilities.iter().for_each(|(key, value)| {
//         println!("HI {key:?} => {value:?}");
//     });
// }

#[cfg(test)]
mod test {
    use std::fs;

    use crate::{sum_of_counts, sum_of_folded_counts};

    const TEST_INPUT_1: &str = "???.### 1,1,3
.??..??...?##. 1,1,3
?#?#?#?#?#?#?#? 1,3,1,6
????.#...#... 4,1,1
????.######..#####. 1,6,5
?###???????? 3,2,1";
    #[test]
    fn test_sum_of_counts() {
        assert_eq!(21, sum_of_counts(TEST_INPUT_1));

        let input = fs::read_to_string("./input.txt").unwrap();

        assert_eq!(7622, sum_of_counts(input.as_str()));

        assert_eq!(525152, sum_of_folded_counts(TEST_INPUT_1));

        assert_eq!(4964259839627, sum_of_folded_counts(input.as_str()));
    }
}
