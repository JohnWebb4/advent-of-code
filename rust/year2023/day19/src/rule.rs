use crate::{category::Category, comparison::Comparison};

pub struct Rule {
    pub category: Category,
    pub comparison: Comparison,
    pub amount: i32,
    pub target: String,
}

pub fn new_rule(category: Category, comparison: Comparison, amount: i32, target: String) -> Rule {
    Rule {
        category,
        comparison,
        amount,
        target,
    }
}
