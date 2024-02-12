#[derive(Clone, Debug)]
pub struct Vec3 {
    pub x: u32,
    pub y: u32,
    pub z: u32,
}

pub fn new_vec3(x: u32, y: u32, z: u32) -> Vec3 {
    Vec3 { x, y, z }
}
