#[derive(Clone, Debug)]
pub struct Lens {
    pub label: String,
    pub length: usize,
}

pub fn new_lens(label: String, length: usize) -> Lens {
    Lens { label, length }
}
