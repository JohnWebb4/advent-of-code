#[derive(Debug, PartialEq, Eq, Clone)]
pub enum Pixel {
    Galaxy,
    Space,
}

impl Pixel {
    pub fn from_char(value: char) -> Result<Pixel, ()> {
        match value {
            '#' => Ok(Pixel::Galaxy),
            '.' => Ok(Pixel::Space),
            _ => Err(()),
        }
    }
}
