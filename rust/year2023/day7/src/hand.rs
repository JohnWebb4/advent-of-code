#[derive(Debug, Clone, PartialEq, Hash, Eq)]
pub struct Hand {
    pub cards: Vec<i32>,
    pub bid_amount: i32,
}

pub fn new_hand(cards: Vec<i32>, bid_amount: i32) -> Hand {
    Hand { cards, bid_amount }
}
