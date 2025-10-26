use std::collections::{HashMap, HashSet, LinkedList};

use regex::Regex;

pub fn get_bottom_program(input: &str) -> String {
    let instructions = input.split('\n').collect::<Vec<&str>>();
    let re = Regex::new(r"(\w+) \((\d+)\)(?: -> (.*))?").unwrap();

    let mut name_set = HashSet::<String>::new();
    let mut child_set = HashSet::<String>::new();

    for instruction in instructions.iter() {
        let a = re.captures(instruction).unwrap();

        let name = &a[1];
        // let id = a[2].parse::<usize>().unwrap();
        if let Some(children) = a.get(3).map(|m| {
            m.as_str()
                .split(", ")
                .map(|s| s.to_string())
                .collect::<Vec<String>>()
        }) {
            for child in children {
                child_set.insert(child);
            }
        }

        name_set.insert(name.to_string());
    }

    let diff = name_set.difference(&child_set).collect::<Vec<&String>>();

    diff[0].to_string()
}

pub fn get_fixed_balanced_weight(input: &str) -> usize {
    let instructions = input.split('\n').collect::<Vec<&str>>();
    let re = Regex::new(r"(\w+) \((\d+)\)(?: -> (.*))?").unwrap();

    let mut subtower_weight_map = HashMap::<String, usize>::new();
    let mut weight_map = HashMap::<String, usize>::new();
    let mut instruction_queue = LinkedList::<&str>::new();

    for instruction in instructions {
        instruction_queue.push_back(instruction);
    }

    while !instruction_queue.is_empty() {
        let instruction = instruction_queue.pop_front().unwrap();

        let a = re.captures(instruction).unwrap();

        let name = &a[1];
        let weight = a[2].parse::<usize>().unwrap();
        weight_map.insert(name.to_string(), weight);

        if let Some(children) = a.get(3).map(|m| {
            m.as_str()
                .split(", ")
                .map(|s| s.to_string())
                .collect::<Vec<String>>()
        }) {
            if children
                .iter()
                .all(|child| subtower_weight_map.get(child).is_some())
            {
                let subtower_weights = children
                    .iter()
                    .map(|child| subtower_weight_map.get(child).unwrap_or(&0))
                    .collect::<Vec<&usize>>();

                let counts = subtower_weights.iter().enumerate().fold(
                    HashMap::<&&usize, Vec<usize>>::new(),
                    |mut m: HashMap<&&usize, Vec<usize>>, (i, w)| {
                        if let Some(current) = m.get(w) {
                            let mut new_index = current.clone();
                            new_index.push(i);

                            m.insert(w, new_index.to_vec());
                        } else {
                            m.insert(w, [i].to_vec());
                        }

                        m
                    },
                );

                if counts.len() > 1 {
                    let unbalanced_enum = counts
                        .iter()
                        .filter(|(_, c)| c.len() == 1)
                        .collect::<Vec<(&&&usize, _)>>()[0];

                    let balanced_weight = counts
                        .iter()
                        .filter(|(_, c)| c.len() > 1)
                        .map(|(w, _)| w)
                        .collect::<Vec<&&&usize>>()[0];

                    let (unbalanced_subtower_weight, unbalanced_indicies) = unbalanced_enum;
                    let unbalanced_child = children[unbalanced_indicies[0]].clone();
                    if let Some(unbalanced_child_weight) = weight_map.get(&unbalanced_child) {
                        return unbalanced_child_weight + ***balanced_weight
                            - ***unbalanced_subtower_weight;
                    }

                    return 0;
                }

                subtower_weight_map.insert(
                    name.to_string(),
                    weight + subtower_weights.iter().fold(0, |s, w| s + *w),
                );
            } else {
                instruction_queue.push_back(instruction);
            }
        } else {
            subtower_weight_map.insert(name.to_string(), weight);
        }
    }

    println!("HMMMM {:?}", subtower_weight_map);

    0
}

#[cfg(test)]
mod tests {
    use std::fs;

    use super::*;

    const TEST_1: &str = "pbga (66)
xhth (57)
ebii (61)
havc (66)
ktlj (57)
fwft (72) -> ktlj, cntj, xhth
qoyq (66)
padx (45) -> pbga, havc, qoyq
tknk (41) -> ugml, padx, fwft
jptl (61)
ugml (68) -> gyxo, ebii, jptl
gyxo (61)
cntj (57)";

    #[test]
    fn test_get_bottom_program() {
        let file = fs::read_to_string("./input.txt").unwrap();
        let input = file.as_str();

        assert_eq!(get_bottom_program(TEST_1), "tknk");
        assert_eq!(get_bottom_program(input), "fbgguv");

        assert_eq!(get_fixed_balanced_weight(TEST_1), 60);
        assert_eq!(get_fixed_balanced_weight(input), 1864);
    }
}
