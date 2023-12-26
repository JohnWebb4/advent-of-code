use std::collections::HashMap;

use crate::{pipe::Pipe, rectangle::Rectangle, vector::Vector};

#[derive(Debug)]
pub struct Map {
    inner_map: HashMap<String, Pipe>,
    bounds: Rectangle,
    start_position: Vector,
}

pub fn new_map(inner_map: HashMap<String, Pipe>, bounds: Rectangle, start_position: Vector) -> Map {
    Map {
        inner_map,
        bounds,
        start_position,
    }
}

impl Map {
    pub fn get_value(&self, x: usize, y: usize) -> Option<&Pipe> {
        self.inner_map.get(&get_map_key(x, y))
    }

    pub fn get_width(&self) -> usize {
        self.bounds.width
    }

    pub fn get_height(&self) -> usize {
        self.bounds.height
    }

    pub fn get_start_x(&self) -> usize {
        self.start_position.x
    }

    pub fn get_start_y(&self) -> usize {
        self.start_position.y
    }
}

pub fn get_map_key(x: usize, y: usize) -> String {
    format!("x{x},y{y}")
}
