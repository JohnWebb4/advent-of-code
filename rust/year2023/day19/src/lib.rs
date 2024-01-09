use std::collections::HashMap;

use comparison::Comparison;
use once_cell::sync::Lazy;
use part::{new_part, Part};
use partrange::PartRange;
use regex::Regex;
use rule::{new_rule, Rule};
use workflow::{new_workflow, Workflow};

use crate::{category::Category, partrange::new_partrange};

mod category;
mod comparison;
mod part;
mod partrange;
mod rule;
mod workflow;

const RANGE_MAX: i32 = 4000;

pub fn get_sum_accepted_combinations(input: &str) -> u128 {
    let sections = input.split("\n\n").collect::<Vec<&str>>();
    let workflows = sections[0]
        .split('\n')
        .map(read_workflow)
        .collect::<Vec<Workflow>>();

    let accepted_ranges = get_accepted_ranges(&workflows);

    accepted_ranges.iter().fold(0, |sum, accepted_range| {
        sum + [
            accepted_range.x,
            accepted_range.m,
            accepted_range.a,
            accepted_range.s,
        ]
        .iter()
        .fold(None, |possibilities, (start, end)| {
            let length = end - start + 1; // Inclusive range

            if let Some(possibilities) = possibilities {
                Some((length as u128) * possibilities)
            } else {
                Some(length as u128)
            }
        })
        .unwrap()
    })
}

pub fn get_sum_accepted_ratings(input: &str) -> i32 {
    let sections = input.split("\n\n").collect::<Vec<&str>>();
    let workflows = sections[0]
        .split('\n')
        .map(read_workflow)
        .collect::<Vec<Workflow>>();

    let parts = sections[1]
        .split('\n')
        .map(read_part)
        .collect::<Vec<Part>>();

    let accepted = get_accepted_parts(&workflows, &parts);

    accepted
        .iter()
        .fold(0, |sum, part| sum + part.x + part.m + part.a + part.s)
}

fn get_accepted_ranges(workflows: &[Workflow]) -> Vec<PartRange> {
    let mut ranges = vec![new_partrange(
        (0, RANGE_MAX),
        (0, RANGE_MAX),
        (0, RANGE_MAX),
        (0, RANGE_MAX),
        String::from("in"),
    )];

    let workflow_map = workflows.iter().fold(HashMap::new(), |mut map, workflow| {
        map.insert(&workflow.name, workflow);

        map
    });

    let mut accepted_ranges = vec![];

    while !ranges.is_empty() {
        ranges = ranges.iter().fold(vec![], |mut new_ranges, partrange| {
            if let Some(workflow) = workflow_map.get(&partrange.position) {
                let mut current_partrange_option = Some(partrange.clone());

                println!("Look at {partrange:?} at {workflow:?}");

                for rule in &workflow.rules {
                    if let Some(current_partrange) = current_partrange_option.clone() {
                        let (start, end) = match rule.category {
                            Category::X => current_partrange.x,
                            Category::M => current_partrange.m,
                            Category::A => current_partrange.a,
                            Category::S => current_partrange.s,
                        };

                        let (pass_range, fail_range) = match rule.comparison {
                            Comparison::Greater => {
                                let pass_range = if end > rule.amount {
                                    Some(((rule.amount + 1).max(start), end))
                                } else {
                                    None
                                };

                                let fail_range = if start <= rule.amount {
                                    Some((start, (rule.amount.min(end))))
                                } else {
                                    None
                                };

                                (pass_range, fail_range)
                            }
                            Comparison::Less => {
                                let pass_range = if start < rule.amount {
                                    Some((start, (rule.amount - 1).min(end)))
                                } else {
                                    None
                                };

                                let fail_range = if end >= rule.amount {
                                    Some((rule.amount.max(start), end))
                                } else {
                                    None
                                };

                                (pass_range, fail_range)
                            }
                        };

                        if let Some(pass_range) = pass_range {
                            if pass_range.0 < pass_range.1 {
                                let mut pass_partrange = current_partrange.clone();
                                match rule.category {
                                    Category::X => {
                                        pass_partrange.x = pass_range;
                                    }
                                    Category::M => {
                                        pass_partrange.m = pass_range;
                                    }
                                    Category::A => {
                                        pass_partrange.a = pass_range;
                                    }
                                    Category::S => {
                                        pass_partrange.s = pass_range;
                                    }
                                }

                                pass_partrange.position = rule.target.clone();

                                if pass_partrange.position == "A" {
                                    accepted_ranges.push(pass_partrange)
                                } else if workflow_map.get(&pass_partrange.position).is_some() {
                                    new_ranges.push(pass_partrange);
                                }
                            } else {
                                panic!("Invalid Pass range {pass_range:?}");
                            }
                        }

                        if let Some(fail_range) = fail_range {
                            if fail_range.0 < fail_range.1 {
                                let mut fail_partrange = current_partrange.clone();

                                match rule.category {
                                    Category::X => {
                                        fail_partrange.x = fail_range;
                                    }
                                    Category::M => {
                                        fail_partrange.m = fail_range;
                                    }
                                    Category::A => {
                                        fail_partrange.a = fail_range;
                                    }
                                    Category::S => {
                                        fail_partrange.s = fail_range;
                                    }
                                }

                                current_partrange_option = Some(fail_partrange);
                            } else {
                                panic!("Invalid Fail range {fail_range:?}");
                            }
                        } else {
                            // Fail range is none. No passing rules
                            current_partrange_option = None;

                            break;
                        }
                    }
                }

                if let Some(current_partrange) = current_partrange_option.clone() {
                    // Remaining area goes to else
                    let mut else_partrange = current_partrange.clone();
                    else_partrange.position = workflow.else_target.clone();

                    // Validate
                    if [
                        else_partrange.x,
                        else_partrange.m,
                        else_partrange.a,
                        else_partrange.s,
                    ]
                    .iter()
                    .all(|(start, end)| start <= end)
                    {
                        if else_partrange.position == "A" {
                            accepted_ranges.push(else_partrange);
                        } else if workflow_map.get(&else_partrange.position).is_some() {
                            new_ranges.push(else_partrange);
                        }
                    }
                }
            }

            new_ranges
        });
    }

    accepted_ranges
}

fn get_accepted_parts(workflows: &[Workflow], parts: &[Part]) -> Vec<Part> {
    let workflow_map = workflows.iter().fold(HashMap::new(), |mut map, workflow| {
        map.insert(&workflow.name, workflow);

        map
    });

    parts
        .iter()
        .filter_map(|part| {
            let mut pos = &String::from("in");

            while let Some(workflow) = workflow_map.get(pos) {
                let mut has_match = false;

                for rule in &workflow.rules {
                    let value = match rule.category {
                        Category::X => part.x,
                        Category::M => part.m,
                        Category::A => part.a,
                        Category::S => part.s,
                    };

                    match rule.comparison {
                        Comparison::Greater => {
                            if value > rule.amount {
                                pos = &rule.target;
                                has_match = true;
                                break;
                            }
                        }
                        Comparison::Less => {
                            if value < rule.amount {
                                pos = &rule.target;
                                has_match = true;
                                break;
                            }
                        }
                    }
                }

                if !has_match {
                    pos = &workflow.else_target;
                }
            }

            if pos == "A" {
                return Some(part.clone());
            }

            None
        })
        .collect::<Vec<Part>>()
}

fn read_workflow(input: &str) -> Workflow {
    let workflow_re = Lazy::new(|| Regex::new(r"^(\w+)\{((\w+[<>]\d+:\w+,?)+)+(\w+)\}").unwrap());
    let captures = workflow_re.captures(input).unwrap();

    let capture_len = captures.len();

    let name = captures.get(1).unwrap().as_str().to_string();
    let rules = captures
        .get(2)
        .unwrap()
        .as_str()
        .split(',')
        .filter_map(|rule_string| {
            if rule_string.is_empty() {
                None
            } else {
                Some(read_rule(rule_string))
            }
        })
        .collect::<Vec<Rule>>();
    let else_target = captures.get(capture_len - 1).unwrap().as_str().to_string();

    new_workflow(name, rules, else_target)
}

fn read_rule(input: &str) -> Rule {
    let rule_re = Lazy::new(|| Regex::new(r"(\w+)([<>])(\w+):(\w+)").unwrap());
    let captures = rule_re.captures(input).unwrap();

    let category =
        Category::from_char(captures.get(1).unwrap().as_str().chars().next().unwrap()).unwrap();
    let comparison =
        Comparison::from_char(captures.get(2).unwrap().as_str().chars().next().unwrap()).unwrap();

    let amount = captures.get(3).unwrap().as_str().parse::<i32>().unwrap();
    let target = captures.get(4).unwrap().as_str().to_string();

    new_rule(category, comparison, amount, target)
}

fn read_part(input: &str) -> Part {
    let part_re = Lazy::new(|| Regex::new(r"\{x=(\d+),m=(\d+),a=(\d+),s=(\d+)\}").unwrap());
    let captures = part_re.captures(input).unwrap();

    let x = captures.get(1).unwrap().as_str().parse::<i32>().unwrap();
    let m = captures.get(2).unwrap().as_str().parse::<i32>().unwrap();
    let a = captures.get(3).unwrap().as_str().parse::<i32>().unwrap();
    let s = captures.get(4).unwrap().as_str().parse::<i32>().unwrap();

    new_part(x, m, a, s)
}

#[cfg(test)]
mod test {
    use std::fs;

    use crate::{get_sum_accepted_combinations, get_sum_accepted_ratings};

    const TEST_INPUT_1: &str = "px{a<2006:qkq,m>2090:A,rfg}
pv{a>1716:R,A}
lnx{m>1548:A,A}
rfg{s<537:gd,x>2440:R,A}
qs{s>3448:A,lnx}
qkq{x<1416:A,crn}
crn{x>2662:A,R}
in{s<1351:px,qqz}
qqz{s>2770:qs,m<1801:hdj,R}
gd{a>3333:R,R}
hdj{m>838:A,pv}

{x=787,m=2655,a=1222,s=2876}
{x=1679,m=44,a=2067,s=496}
{x=2036,m=264,a=79,s=2244}
{x=2461,m=1339,a=466,s=291}
{x=2127,m=1623,a=2188,s=1013}";

    #[test]
    fn test_get_sum_accepted_ratings() {
        assert_eq!(19114, get_sum_accepted_ratings(TEST_INPUT_1));

        let input = fs::read_to_string("./input.txt").unwrap();

        // assert_eq!(362930, get_sum_accepted_ratings(input.as_str()));

        assert_eq!(167409079868000, get_sum_accepted_combinations(TEST_INPUT_1));
    }
}
