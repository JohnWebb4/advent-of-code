#[derive(Debug)]
pub struct Galaxy {
    pub x: usize,
    pub y: usize,
}

pub fn new_galaxy(x: usize, y: usize) -> Galaxy {
    Galaxy { x, y }
}
