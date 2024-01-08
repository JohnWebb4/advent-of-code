#[derive(Debug)]
pub enum Comparison {
    Less,
    Greater,
}

impl Comparison {
    pub fn from_char(c: char) -> Result<Comparison, ()> {
        match c {
            '<' => Ok(Comparison::Less),
            '>' => Ok(Comparison::Greater),
            _ => Err(()),
        }
    }
}
