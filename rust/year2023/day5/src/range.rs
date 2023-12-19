#[derive(Debug)]
pub struct Range {
    pub source_start: i64,
    pub dest_start: i64,
    pub length: i64,
}

impl Range {
    pub fn get(&self, value: i64) -> Option<i64> {
        let displacement = value - self.source_start;

        if displacement >= 0 && displacement < self.length {
            Some(self.dest_start + displacement)
        } else {
            None
        }
    }
}
