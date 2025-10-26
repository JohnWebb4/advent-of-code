#[derive(Debug)]
pub struct Race {
    pub time: i64,
    pub winning_distance: i64,
}

pub fn new_race(time: i64, winning_distance: i64) -> Race {
    Race {
        time,
        winning_distance,
    }
}
