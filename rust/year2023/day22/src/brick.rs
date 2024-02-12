use crate::vec3::Vec3;

#[derive(Clone, Debug)]
pub struct Brick {
    pub start: Vec3,
    pub end: Vec3,
}

pub fn new_brick(start: Vec3, end: Vec3) -> Brick {
    Brick { start, end }
}
