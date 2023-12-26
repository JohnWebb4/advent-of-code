#[derive(Debug, PartialEq, Eq, Hash, Clone, Copy)]
pub struct Vector {
    pub x: usize,
    pub y: usize,
}

pub fn new_vector(x: usize, y: usize) -> Vector {
    Vector { x, y }
}
