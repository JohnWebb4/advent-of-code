use std::collections::HashMap;

use crate::cell::Cell;

#[derive(Clone, Debug)]
pub struct Map {
    inner_map: HashMap<(usize, usize), Cell>,
    pub x_max: usize,
    pub y_max: usize,
}

pub fn new_map(inner_map: HashMap<(usize, usize), Cell>, x_max: usize, y_max: usize) -> Map {
    Map {
        inner_map,
        x_max,
        y_max,
    }
}

impl Map {
    pub fn get_cell(&self, x: usize, y: usize) -> Option<&Cell> {
        self.inner_map.get(&(x, y))
    }
}
