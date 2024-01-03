use std::collections::HashSet;

#[derive(Debug, Clone)]
pub struct Pannel {
    square_set: HashSet<(usize, usize)>,
    pub x_max: usize,
    pub y_max: usize,
}

pub fn new_pannel(square_set: HashSet<(usize, usize)>, x_max: usize, y_max: usize) -> Pannel {
    Pannel {
        square_set,
        x_max,
        y_max,
    }
}

impl Pannel {
    pub fn contains(&self, x: usize, y: usize) -> bool {
        self.square_set.contains(&(x, y))
    }

    // pub fn pretty_print(&self) {
    //     let mut entries = self.square_set.iter().collect::<Vec<&(usize, usize)>>();

    //     entries.sort_by(|(x_a, y_a), (x_b, y_b)| y_a.cmp(y_b).then(x_a.cmp(x_b)));

    //     println!("Pannel x_max {} y_max {}", self.x_max, self.y_max);
    //     entries.iter().for_each(|entry| {
    //         println!("{entry:?}");
    //     })
    // }
}
