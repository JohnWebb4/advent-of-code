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
    pub children_names: Vec<String>,
}

pub fn new_module(
    name: String,
    module_type: Option<ModuleType>,
    children_names: Vec<String>,
) -> Module {
    Module {
        name,
        module_type,
        children_names,
    }
}
