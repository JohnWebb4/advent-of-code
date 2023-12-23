use std::cmp::{max, min};

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

    pub fn intersect(&self, filter_range: &Range, should_log: bool) -> Option<Range> {
        let ranges: Vec<Range> = vec![];

        let self_dest_start = self.dest_start;
        let self_dest_end = self.dest_start + self.length;

        let range_source_start = filter_range.source_start;
        let range_source_end = filter_range.source_start + filter_range.length;

        let nonoverlapping_start_length =
            max(min(range_source_start - self_dest_start, self.length), 0);
        let nonoverlapping_end_length = max(min(self_dest_end - range_source_end, self.length), 0);

        let max_start = max(self_dest_start, range_source_start);
        let min_end = min(self_dest_end, range_source_end);
        let overlapping_length = max(min(min_end - max_start, self.length), 0);

        if overlapping_length > 0 {
            return Some(Range {
                source_start: self.source_start + nonoverlapping_start_length,
                dest_start: filter_range.dest_start + nonoverlapping_start_length,
                length: overlapping_length,
            });
        }

        if should_log {
            println!("Intersected {self:?} with {filter_range:?} => {self_dest_start} {self_dest_end}, {range_source_start} {range_source_end} => {nonoverlapping_start_length} {overlapping_length} {nonoverlapping_end_length} => {ranges:?}");
        }

        None
    }
}
