#[derive(Debug, Clone, PartialEq)]
pub enum Cell {
    Rounded,
    Square,
    Space,
}

impl Cell {
    pub fn from_char(c: char) -> Result<Cell, ()> {
        match c {
            'O' => Ok(Cell::Rounded),
            '#' => Ok(Cell::Square),
            '.' => Ok(Cell::Space),
            _ => Err(()),
        }
    }
}
