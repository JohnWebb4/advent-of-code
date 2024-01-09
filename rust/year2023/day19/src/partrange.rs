#[derive(Clone, Debug)]
pub struct PartRange {
    pub x: (i32, i32),
    pub m: (i32, i32),
    pub a: (i32, i32),
    pub s: (i32, i32),
    pub position: String,
}

pub fn new_partrange(
    x: (i32, i32),
    m: (i32, i32),
    a: (i32, i32),
    s: (i32, i32),
    position: String,
) -> PartRange {
    PartRange {
        x,
        m,
        a,
        s,
        position,
    }
}

impl PartRange {
    pub fn contains(&self, other: &PartRange) -> bool {
        [
            (self.x, other.x),
            (self.m, other.m),
            (self.a, other.a),
            (self.s, other.s),
        ]
        .iter()
        .all(|((self_start, self_end), (other_start, other_end))| {
            self_start <= other_start && other_end <= self_end
        })
    }
}
