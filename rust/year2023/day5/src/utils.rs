use std::fmt;

#[derive(Debug, Clone)]
pub struct UnexepectedError {
    pub msg: String,
}

impl fmt::Display for UnexepectedError {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(f, "{}", self.msg)
    }
}
