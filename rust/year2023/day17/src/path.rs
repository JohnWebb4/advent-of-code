use std::cmp;

use crate::direction::Direction;

#[derive(Debug, PartialEq, Eq)]
pub struct Path {
    pub x: usize,
    pub y: usize,
    pub direction: Option<Direction>,
    pub prev_direction: Option<Direction>,
    pub prev_prev_direction: Option<Direction>,
    pub heat_loss: u32,

    pub path: Vec<(usize, usize)>,
}

pub fn new_path(
    x: usize,
    y: usize,
    direction: Option<Direction>,
    prev_direction: Option<Direction>,
    prev_prev_direction: Option<Direction>,
    heat_loss: u32,
    path: Vec<(usize, usize)>,
) -> Path {
    Path {
        x,
        y,
        direction,
        prev_direction,
        prev_prev_direction,
        heat_loss,
        path,
    }
}

impl Ord for Path {
    fn cmp(&self, other: &Self) -> cmp::Ordering {
        self.x
            .cmp(&other.x)
            .then(self.y.cmp(&other.y))
            .then(other.heat_loss.cmp(&self.heat_loss))
    }
}

impl PartialOrd for Path {
    fn partial_cmp(&self, other: &Self) -> Option<std::cmp::Ordering> {
        Some(self.cmp(other))
    }
}
