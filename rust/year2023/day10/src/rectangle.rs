#[derive(Debug)]
pub struct Rectangle {
    // pub x: usize,
    // pub y: usize,
    pub width: usize,
    pub height: usize,
}

pub fn new_rectangle(width: usize, height: usize) -> Rectangle {
    Rectangle {
        // x,
        // y,
        width,
        height,
    }
}
