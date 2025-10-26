use crate::direction::Direction;

#[derive(Debug, PartialEq, Clone, Copy)]
#[repr(u8)]
pub enum Pipe {
    Vertical = b'|',
    Horizontal = b'-',
    NorthEast = b'L',
    NorthWest = b'J',
    SouthWest = b'7',
    SouthEast = b'F',
    Ground = b'.',
    Start = b'S',
}

impl Pipe {
    pub fn from_char(value: char) -> Result<Pipe, ()> {
        match value {
            '|' => Ok(Pipe::Vertical),
            '-' => Ok(Pipe::Horizontal),
            'L' => Ok(Pipe::NorthEast),
            'J' => Ok(Pipe::NorthWest),
            '7' => Ok(Pipe::SouthWest),
            'F' => Ok(Pipe::SouthEast),
            '.' => Ok(Pipe::Ground),
            'S' => Ok(Pipe::Start),
            _ => Err(()),
        }
    }

    pub fn is_connected(&self, direction: &Direction, other: &Pipe) -> bool {
        if self == &Pipe::Ground {
            return false;
        }

        match direction {
            Direction::Up => {
                if self == &Pipe::Horizontal || self == &Pipe::SouthWest || self == &Pipe::SouthEast
                {
                    return false;
                }

                other == &Pipe::Vertical
                    || other == &Pipe::SouthWest
                    || other == &Pipe::SouthEast
                    || other == &Pipe::Start
            }
            Direction::Right => {
                if self == &Pipe::Vertical || self == &Pipe::NorthWest || self == &Pipe::SouthWest {
                    return false;
                }

                other == &Pipe::Horizontal
                    || other == &Pipe::NorthWest
                    || other == &Pipe::SouthWest
                    || other == &Pipe::Start
            }
            Direction::Down => {
                if self == &Pipe::Horizontal || self == &Pipe::NorthWest || self == &Pipe::NorthEast
                {
                    return false;
                }

                other == &Pipe::Vertical
                    || other == &Pipe::NorthWest
                    || other == &Pipe::NorthEast
                    || other == &Pipe::Start
            }
            Direction::Left => {
                if self == &Pipe::Vertical || self == &Pipe::NorthEast || self == &Pipe::SouthEast {
                    return false;
                }

                other == &Pipe::Horizontal
                    || other == &Pipe::NorthEast
                    || other == &Pipe::SouthEast
                    || other == &Pipe::Start
            }
        }
    }
}
