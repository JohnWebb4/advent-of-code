#[derive(Clone, Debug, PartialEq)]
pub struct Node {
    pub name: String,
    pub left: String,
    pub right: String,
}

pub fn new_node(name: String, left: String, right: String) -> Node {
    Node { name, left, right }
}
