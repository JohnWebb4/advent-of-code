#[derive(Clone, Debug)]
pub struct Part {
    pub x: i32,
    pub m: i32,
    pub a: i32,
    pub s: i32,
}

pub fn new_part(x: i32, m: i32, a: i32, s: i32) -> Part {
    Part { x, m, a, s }
}
