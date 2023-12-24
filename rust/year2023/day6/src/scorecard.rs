use crate::race::Race;


pub struct ScoreCard {
    pub races: Vec<Race>
}

pub fn new_scorecard(races: Vec<Race>) -> ScoreCard {
    ScoreCard{ races }
}