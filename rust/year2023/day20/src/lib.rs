use std::collections::{HashMap, LinkedList};

use module::{new_module, Module, ModuleType};
use once_cell::sync::Lazy;
use regex::Regex;

use crate::pulsetype::PulseType;

mod module;
mod pulsetype;

pub fn get_product_low_high_pulses(input: &str) -> usize {
    let module_map = read_module_map(input);

    let (low_pulses, high_pulses) = press_button(&module_map);

    low_pulses * high_pulses
}

pub fn press_button(module_map: &HashMap<String, Module>) -> (usize, usize) {
    let mut low_pulses = 0;
    let mut high_pulses = 0;

    let mut module_state_map = HashMap::<String, PulseType>::new();
    let mut module_queue = LinkedList::<(PulseType, &String)>::new();

    let button_name = "button".to_string();

    for _i in 0..1000 {
        module_queue.push_back((PulseType::Low, &button_name));

        while !module_queue.is_empty() {
            let (pulse_type, module_name) = module_queue.pop_front().unwrap();
            let module_state = module_state_map.get(module_name).unwrap_or(&PulseType::Low);
            let module = module_map.get(module_name).unwrap();

            println!("Looking at {pulse_type:?} {module_name} {module_state:?}");

            let next_module_state = match module.module_type {
                Some(ModuleType::Conjunction) => {}
                Some(ModuleType::FlipFlop) => {
                    if pulse_type == PulseType::Low {
                        // Flip
                        match module_state {
                            PulseType::Low => PulseType::High,
                            PulseType::High => PulseType::Low,
                        }
                    } else {
                        module_state.clone()
                    }
                }
                None => PulseType::Low,
            };

            match next_module_state {
                PulseType::Low => {
                    low_pulses += module.children_names.len();
                }
                PulseType::High => {
                    high_pulses += module.children_names.len();
                }
            };

            module.children_names.iter().for_each(|child| {
                module_queue.push_back((next_module_state.clone(), child));
            });

            module_state_map.insert(module_name.clone(), next_module_state);
        }
    }

    println!("Low {low_pulses} {high_pulses}");

    (low_pulses, high_pulses)
}

fn read_module_map(input: &str) -> HashMap<String, Module> {
    let mut module_map = HashMap::new();
    module_map.insert(
        "button".to_string(),
        new_module(
            "button".to_string(),
            None,
            ["broadcaster".to_string()].to_vec(),
        ),
    );

    input
        .split('\n')
        .fold(module_map, |mut map, module_string| {
            let module = read_module(module_string);

            map.insert(module.name.clone(), module);

            map
        })
}

fn read_module(input: &str) -> Module {
    let module_regex = Lazy::new(|| Regex::new(r"([%&])?(\w+) -> ((?:\w+(, )?)+)").unwrap());
    let captures = module_regex.captures(input).unwrap();

    let module_type = captures.get(1).map(|type_string| {
        ModuleType::from_char(type_string.as_str().chars().next().unwrap()).unwrap()
    });
    let name = captures.get(2).unwrap().as_str().to_string();
    let children_names = captures
        .get(3)
        .unwrap()
        .as_str()
        .to_string()
        .split(", ")
        .map(|s| s.to_string())
        .collect::<Vec<String>>();

    new_module(name, module_type, children_names)
}

#[cfg(test)]
mod test {
    use crate::get_product_low_high_pulses;

    const TEST_INPUT_1: &str = "broadcaster -> a, b, c
%a -> b
%b -> c
%c -> inv
&inv -> a";

    #[test]
    fn test_get_product_low_high_pulses() {
        assert_eq!(32000000, get_product_low_high_pulses(TEST_INPUT_1))
    }
}
