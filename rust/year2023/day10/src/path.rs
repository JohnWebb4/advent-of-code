#[derive(Debug, Eq, PartialEq)]
pub struct Path {
    cost: usize,
    pub x: usize,
    pub y: usize,
    pub num_steps: usize,
}

impl Ord for Path {
    fn cmp(&self, other: &Self) -> std::cmp::Ordering {
        other.cost.cmp(&self.cost)
    }
}

impl PartialOrd for Path {
    fn partial_cmp(&self, other: &Self) -> Option<std::cmp::Ordering> {
        Some(self.cmp(other))
    }
}

pub fn new_path(x: usize, y: usize, num_steps: usize) -> Path {
    let cost = get_cost(num_steps);

    Path {
        cost,
        x,
        y,
        num_steps,
    }
}

fn get_cost(num_steps: usize) -> usize {
    num_steps
}
