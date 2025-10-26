#[derive(Debug, PartialEq)]
pub enum Direction {
    Up,
    Right,
    Down,
    Left,
}

impl Direction {
    pub fn from_char(c: char) -> Result<Direction, ()> {
        match c {
            'U' => Ok(Direction::Up),
            'R' => Ok(Direction::Right),
            'D' => Ok(Direction::Down),
            'L' => Ok(Direction::Left),
            _ => Err(()),
        }
    }
}
