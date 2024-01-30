use std::collections::HashMap;

pub enum Cell {
    Garden,
    Start,
    Rock,
}

pub struct Garden {
    inner_map: HashMap<String, Cell>,
}

pub fn new_garden() -> Garden {
    Garden {
        inner_map: HashMap::new(),
    }
}

impl Garden {
    pub fn get_key(x: usize, y: usize) -> String {
        format!("{x}, {y}")
    }

    pub fn get_cell(&self, x: usize, y: usize) -> Option<&Cell> {
        self.inner_map.get(&Garden::get_key(x, y))
    }
}
