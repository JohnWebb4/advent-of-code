use comparison::Comparison;
use once_cell::sync::Lazy;
use part::{new_part, Part};
use regex::Regex;
use rule::{new_rule, Rule};
use workflow::{new_workflow, Workflow};

use crate::category::Category;

mod category;
mod comparison;
mod part;
mod rule;
mod workflow;

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

fn get_accepted_parts(workflows: &[Workflow], parts: &[Part]) -> Vec<Part> {
    vec![]
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
    use crate::get_sum_accepted_ratings;

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
    }
}
