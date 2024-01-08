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
