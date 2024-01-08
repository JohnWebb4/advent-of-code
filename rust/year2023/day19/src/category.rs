#[derive(Debug)]
pub enum Category {
    X,
    M,
    A,
    S,
}

impl Category {
    pub fn from_char(c: char) -> Result<Category, ()> {
        match c {
            'x' => Ok(Category::X),
            'm' => Ok(Category::M),
            'a' => Ok(Category::A),
            's' => Ok(Category::S),
            _ => Err(()),
        }
    }
}
