#[derive(Debug, PartialEq, Eq, Clone, Copy, Hash)]
pub enum Spring {
    Operational,
    Damaged,
    Unknown,
}

impl Spring {
    pub fn from_char(value: char) -> Result<Spring, ()> {
        match value {
            '#' => Ok(Spring::Operational),
            '.' => Ok(Spring::Damaged),
            '?' => Ok(Spring::Unknown),
            _ => Err(()),
        }
    }
}
