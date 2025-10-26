use std::collections::HashMap;

use node::Node;
use num::integer::lcm;
use once_cell::sync::Lazy;
use regex::Regex;

use crate::node::new_node;

mod node;

pub fn get_all_required_steps(input: &str) -> usize {
    let input_parts = input.split("\n\n").collect::<Vec<&str>>();
    let instructions = input_parts[0].chars().collect::<Vec<char>>();
    let network = read_input(input_parts[1]);

    let end_nodes = network
        .iter()
        .filter(|(name, _)| name.ends_with('Z'))
        .map(|(_, node)| node)
        .collect::<Vec<&Node>>();

    let sizes = network
        .iter()
        .filter(|(name, _)| name.ends_with('A'))
        .map(|(_name, node)| get_all_steps_to_point(&instructions, &network, node, &end_nodes))
        .collect::<Vec<Vec<usize>>>();

    sizes
        .iter()
        .fold(1, |num_steps, size_b| lcm(num_steps, size_b[0]))
}

pub fn get_required_steps(input: &str) -> usize {
    let input_parts = input.split("\n\n").collect::<Vec<&str>>();
    let instructions = input_parts[0].chars().collect::<Vec<char>>();
    let network = read_input(input_parts[1]);

    let start_node = network.get("AAA").unwrap();
    let end_node = network.get("ZZZ").unwrap();

    get_all_steps_to_point(&instructions, &network, start_node, &[end_node])[0]
}

fn get_all_steps_to_point(
    instructions: &Vec<char>,
    network: &HashMap<&str, Node>,
    start_node: &Node,
    end_nodes: &[&Node],
) -> Vec<usize> {
    let mut all_num_steps = vec![];

    let mut current_num_steps = 0;
    let mut current_node = start_node;

    let mut remaining_end_nodes = end_nodes
        .iter()
        .map(|node| (*node).clone())
        .collect::<Vec<Node>>();

    while !remaining_end_nodes.is_empty() && current_num_steps < 50_000 {
        let instruction = instructions[current_num_steps % instructions.len()];

        if remaining_end_nodes.contains(current_node) {
            all_num_steps.push(current_num_steps);

            remaining_end_nodes = remaining_end_nodes
                .iter()
                .filter(|node| *node.name != current_node.name)
                .map(|node| (*node).clone())
                .collect::<Vec<Node>>();
        }

        if instruction == 'R' {
            current_node = network.get(current_node.right.as_str()).unwrap();
        } else if instruction == 'L' {
            current_node = network.get(current_node.left.as_str()).unwrap();
        }

        current_num_steps += 1
    }

    all_num_steps
}

fn read_input(input: &str) -> HashMap<&str, Node> {
    let node_regex = Lazy::new(|| Regex::new(r"(\w+) = \((\w+), (\w+)\)").unwrap());

    input
        .split('\n')
        .fold(HashMap::new(), |mut map, node_string| {
            for (_, [node, left_node, right_node]) in node_regex
                .captures_iter(node_string)
                .map(|c| c.extract::<3>())
            {
                map.insert(
                    node,
                    new_node(
                        node.to_string(),
                        left_node.to_string(),
                        right_node.to_string(),
                    ),
                );
            }

            map
        })
}

#[cfg(test)]
mod test {
    use std::fs;

    use crate::{get_all_required_steps, get_required_steps};

    const TEST_INPUT_1: &str = "RL

AAA = (BBB, CCC)
BBB = (DDD, EEE)
CCC = (ZZZ, GGG)
DDD = (DDD, DDD)
EEE = (EEE, EEE)
GGG = (GGG, GGG)
ZZZ = (ZZZ, ZZZ)";

    const TEST_INPUT_2: &str = "LLR

AAA = (BBB, BBB)
BBB = (AAA, ZZZ)
ZZZ = (ZZZ, ZZZ)";

    const TEST_INPUT_3: &str = "LR

11A = (11B, XXX)
11B = (XXX, 11Z)
11Z = (11B, XXX)
22A = (22B, XXX)
22B = (22C, 22C)
22C = (22Z, 22Z)
22Z = (22B, 22B)
XXX = (XXX, XXX)";

    #[test]
    fn test_get_required_steps() {
        assert_eq!(2, get_required_steps(TEST_INPUT_1));

        assert_eq!(6, get_required_steps(TEST_INPUT_2));

        let input = fs::read_to_string("./input.txt").unwrap();

        assert_eq!(24_253, get_required_steps(input.as_str()));

        assert_eq!(6, get_all_required_steps(TEST_INPUT_3));

        assert_eq!(12357789728873, get_all_required_steps(input.as_str()));
    }
}
