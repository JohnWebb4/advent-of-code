use crate::brick::Brick;

#[derive(Debug)]
pub struct BrickMap {
    pub bricks: Vec<Brick>,
}

pub fn new_brickmap(bricks: Vec<Brick>) -> BrickMap {
    BrickMap { bricks }
}
