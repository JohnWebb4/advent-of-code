use std::collections::HashMap;

#[derive(Debug)]
pub struct Map {
    heat_loss: HashMap<(usize, usize), u32>,
    pub x_max: usize,
    pub y_max: usize,
}

pub fn new_map(heat_loss: HashMap<(usize, usize), u32>, x_max: usize, y_max: usize) -> Map {
    Map {
        heat_loss,
        x_max,
        y_max,
    }
}

impl Map {
    pub fn get_cell(&self, x: usize, y: usize) -> Option<&u32> {
        self.heat_loss.get(&(x, y))
    }
}
