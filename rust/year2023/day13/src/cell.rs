#[derive(Debug, PartialEq, Clone)]
pub enum Cell {
    Ash,
    Rock,
}

impl Cell {
    pub fn from_char(c: char) -> Result<Cell, ()> {
        match c {
            '.' => Ok(Cell::Ash),
            '#' => Ok(Cell::Rock),
            _ => Err(()),
        }
    }
}
