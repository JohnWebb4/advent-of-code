use crate::direction::Direction;

#[derive(Debug)]
pub struct Instruction {
    pub direction: Direction,
    pub amount: u32,
    pub color: String,
}

pub fn new_instruction(direction: Direction, amount: u32, color: String) -> Instruction {
    Instruction {
        direction,
        amount,
        color,
    }
}
