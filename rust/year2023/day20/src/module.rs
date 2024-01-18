use std::collections::HashMap;

use crate::pulsetype::PulseType;

#[derive(Clone, Debug)]
pub enum ModuleType {
    FlipFlop,
    Conjunction,
}

impl ModuleType {
    pub fn from_char(c: char) -> Result<ModuleType, ()> {
        match c {
            '%' => Ok(ModuleType::FlipFlop),
            '&' => Ok(ModuleType::Conjunction),
            _ => Err(()),
        }
    }
}

#[derive(Clone, Debug)]
pub struct Module {
    pub name: String,
    pub module_type: Option<ModuleType>,
    pub input_signals: HashMap<String, PulseType>,
    pub state: PulseType,
    pub children_names: Vec<String>,
}

pub fn new_module(
    name: String,
    module_type: Option<ModuleType>,
    children_names: Vec<String>,
    input_signals: HashMap<String, PulseType>,
    state: PulseType,
) -> Module {
    Module {
        name,
        module_type,
        input_signals,
        children_names,
        state,
    }
}
