use crate::cell::Cell;
use std::collections::HashMap;

#[derive(Debug, Clone)]
pub struct Note {
    inner_map: HashMap<(usize, usize), Cell>,
    pub x_max: usize,
    pub y_max: usize,
}

pub fn new_note(inner_map: HashMap<(usize, usize), Cell>, x_max: usize, y_max: usize) -> Note {
    Note {
        inner_map,
        x_max,
        y_max,
    }
}

impl Note {
    pub fn get_cell(&self, x: usize, y: usize) -> Option<&Cell> {
        self.inner_map.get(&(x, y))
    }

    pub fn set_cell(&mut self, x: usize, y: usize, cell: Cell) -> Option<Cell> {
        self.inner_map.insert((x, y), cell)
    }
}

// impl Note {
//     pub fn pretty_fmt(&self) {
//         let mut rows = (0..=self.y_max)
//             .map(|y| {
//                 let mut row = (0..=self.x_max)
//                     .map(move |x| (x, y, self.get_cell(x, y).unwrap()))
//                     .collect::<Vec<(usize, usize, &Cell)>>();

//                 row.sort_by(|(x_a, ..), (x_b, ..)| x_a.cmp(x_b));

//                 row
//             })
//             .collect::<Vec<Vec<(usize, usize, &Cell)>>>();

//         rows.sort_by(|row_a, row_b| row_a[0].1.cmp(&row_b[0].1));

//         println!("x_max {} y_max {} {rows:?}", self.x_max, self.y_max);
//     }
// }
