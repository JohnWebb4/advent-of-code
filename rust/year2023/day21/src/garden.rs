use std::collections::HashMap;

#[derive(Debug, PartialEq)]
pub enum Cell {
    Garden,
    Start,
    Rock,
}

impl Cell {
    pub fn from_char(c: char) -> Result<Cell, ()> {
        match c {
            '.' => Ok(Cell::Garden),
            '#' => Ok(Cell::Rock),
            'S' => Ok(Cell::Start),
            _ => Err(()),
        }
    }
}

#[derive(Debug)]
pub struct Garden {
    pub start_x: usize,
    pub start_y: usize,

    pub height: usize,
    pub width: usize,

    inner_map: HashMap<String, Cell>,
}

pub fn new_garden(
    inner_map: HashMap<String, Cell>,
    start_x: usize,
    start_y: usize,
    width: usize,
    height: usize,
) -> Garden {
    Garden {
        inner_map,
        start_x,
        start_y,
        width,
        height,
    }
}

impl Garden {
    pub fn get_key(x: usize, y: usize) -> String {
        format!("{x}, {y}")
    }

    pub fn get_cell(&self, x: usize, y: usize) -> Option<&Cell> {
        self.inner_map.get(&Garden::get_key(x, y))
    }

    // pub fn print_debug(&self) {
    //     for y in 0..self.height {
    //         for x in 0..self.width {
    //             let cell = self.get_cell(x, y).unwrap();

    //             match cell {
    //                 Cell::Garden => print!("."),
    //                 Cell::Rock => print!("#"),
    //                 Cell::Start => print!("S"),
    //                 _ => {}
    //             }
    //         }

    //         println!()
    //     }
    // }
}
