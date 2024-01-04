#[derive(Clone, Debug, PartialEq)]
pub enum Cell {
    Space,
    MirrorClockwise, // Not best name but works for now
    MirrorCounterClockwise,
    SplitVertical,
    SplitHorizontal,
}

impl Cell {
    pub fn from_char(c: char) -> Result<Cell, ()> {
        match c {
            '.' => Ok(Cell::Space),
            '/' => Ok(Cell::MirrorClockwise),
            '\\' => Ok(Cell::MirrorCounterClockwise),
            '|' => Ok(Cell::SplitVertical),
            '-' => Ok(Cell::SplitHorizontal),
            _ => Err(()),
        }
    }
}
