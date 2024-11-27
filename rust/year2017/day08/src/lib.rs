use std::{collections::HashMap, str::FromStr};

use once_cell::sync::Lazy;
use regex::Regex;

#[derive(Debug)]
enum Action {
    Inc,
    Dec,
}

impl FromStr for Action {
    type Err = ();
    fn from_str(s: &str) -> Result<Self, Self::Err> {
        if s == "inc" {
            return Ok(Action::Inc);
        } else if s == "dec" {
            return Ok(Action::Dec);
        }

        Err(())
    }
}

#[derive(Debug)]
enum Operand {
    Less,
    Greater,
    LessEqual,
    GreaterEqual,
    NotEqual,
    Equal,
}

impl FromStr for Operand {
    type Err = ();

    fn from_str(s: &str) -> Result<Self, Self::Err> {
        if s == "<" {
            return Ok(Operand::Less);
        } else if s == "<=" {
            return Ok(Operand::LessEqual);
        } else if s == "==" {
            return Ok(Operand::Equal);
        } else if s == "!=" {
            return Ok(Operand::NotEqual);
        } else if s == ">=" {
            return Ok(Operand::GreaterEqual);
        } else if s == ">" {
            return Ok(Operand::Greater);
        }

        return Err(());
    }
}

#[derive(Debug)]
struct Condition {
    register: String,
    operand: Operand,
    amount: i32,
}

fn new_condition(register: String, operand: Operand, amount: i32) -> Condition {
    Condition {
        register,
        operand,
        amount,
    }
}

impl Condition {
    fn test(self: &Self, value: i32) -> bool {
        match self.operand {
            Operand::Less => return value < self.amount,
            Operand::LessEqual => return value <= self.amount,
            Operand::Equal => return value == self.amount,
            Operand::NotEqual => return value != self.amount,
            Operand::GreaterEqual => return value >= self.amount,
            Operand::Greater => return value > self.amount,
        }
    }
}

#[derive(Debug)]
struct Instruction {
    register: String,
    action: Action,
    amount: i32,
    condition: Condition,
}

fn new_instruction(
    register: String,
    action: Action,
    amount: i32,
    condition: Condition,
) -> Instruction {
    Instruction {
        register,
        action,
        amount,
        condition,
    }
}

static INSTRUCTION_RE: Lazy<Regex> =
    Lazy::new(|| Regex::new(r"(\w+) (\w+) (-?\d+) if (.*)").unwrap());
static CONDITION_RE: Lazy<Regex> =
    Lazy::new(|| regex::Regex::new(r"(\w+) ([><=!]+) (-?\d+)").unwrap());

impl FromStr for Instruction {
    type Err = ();

    fn from_str(s: &str) -> Result<Self, Self::Err> {
        if let Some(instruction_capture) = INSTRUCTION_RE.captures(s) {
            let register = &instruction_capture[1];

            if let Ok(action) = instruction_capture[2].parse::<Action>() {
                if let Ok(amount) = &instruction_capture[3].parse::<i32>() {
                    if let Some(condition_capture) = CONDITION_RE.captures(&instruction_capture[4])
                    {
                        let cond_register = &condition_capture[1];

                        if let Ok(cond_operand) = condition_capture[2].parse::<Operand>() {
                            if let Ok(cond_amount) = &condition_capture[3].parse::<i32>() {
                                let condition = new_condition(
                                    cond_register.to_string(),
                                    cond_operand,
                                    *cond_amount,
                                );

                                return Ok(new_instruction(
                                    register.to_string(),
                                    action,
                                    *amount,
                                    condition,
                                ));
                            }
                        }
                    }
                }
            }
        }

        Err(())
    }
}

pub fn get_largest_register(instruction_input: &str) -> i32 {
    let instructions = instruction_input
        .split("\n")
        .map(str::parse::<Instruction>)
        .map(|r| r.unwrap())
        .collect::<Vec<Instruction>>();

    let mut register_map = HashMap::<String, i32>::new();
    for instruction in &instructions {
        let condition_register = register_map
            .get(&instruction.condition.register)
            .unwrap_or(&0);

        if instruction.condition.test(*condition_register) {
            let register = register_map.get(&instruction.register).unwrap_or(&0);
            match instruction.action {
                Action::Inc => {
                    register_map.insert(
                        instruction.register.to_string(),
                        register + instruction.amount,
                    );
                }
                Action::Dec => {
                    register_map.insert(
                        instruction.register.to_string(),
                        register - instruction.amount,
                    );
                }
            }
        }
    }

    let max = register_map
        .iter()
        .max_by(|(_, count_1), (_, count_2)| count_1.cmp(count_2))
        .map(|(_, i)| i)
        .unwrap();

    *max
}

pub fn get_largest_ever_register(instruction_input: &str) -> i32 {
    let instructions = instruction_input
        .split("\n")
        .map(str::parse::<Instruction>)
        .map(|r| r.unwrap())
        .collect::<Vec<Instruction>>();

    let mut register_map = HashMap::<String, i32>::new();
    let mut max_total = i32::MIN;

    for instruction in &instructions {
        let condition_register = register_map
            .get(&instruction.condition.register)
            .unwrap_or(&0);

        if instruction.condition.test(*condition_register) {
            let register = register_map.get(&instruction.register).unwrap_or(&0);
            match instruction.action {
                Action::Inc => {
                    register_map.insert(
                        instruction.register.to_string(),
                        register + instruction.amount,
                    );
                }
                Action::Dec => {
                    register_map.insert(
                        instruction.register.to_string(),
                        register - instruction.amount,
                    );
                }
            }
        }

        if let Some(max) = register_map
            .iter()
            .max_by(|(_, count_1), (_, count_2)| count_1.cmp(count_2))
            .map(|(_, i)| i)
        {
            if max_total < *max {
                max_total = *max;
            }
        }
    }

    max_total
}

#[cfg(test)]
mod tests {
    use std::fs;

    use super::*;

    const TEST_1: &str = "b inc 5 if a > 1
a inc 1 if b < 5
c dec -10 if a >= 1
c inc -20 if c == 10";

    #[test]
    fn it_works() {
        let file = fs::read_to_string("./input.txt").unwrap();
        let input = file.as_str();

        assert_eq!(get_largest_register(TEST_1), 1);
        assert_eq!(get_largest_register(input), 4888);

        assert_eq!(get_largest_ever_register(TEST_1), 10);
        assert_eq!(get_largest_ever_register(input), 7774);
    }
}
