use crate::direction::Direction;

#[derive(Clone, Debug, Eq, Hash, PartialEq)]
pub struct Beam {
    pub x: usize,
    pub y: usize,
    pub direction: Direction,
}

pub fn new_beam(x: usize, y: usize, direction: Direction) -> Beam {
    Beam { x, y, direction }
}
