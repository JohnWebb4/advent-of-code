use std::collections::HashMap;

pub struct Map {
    map: HashMap<String, char>,
    x_max: usize,
    y_max: usize,
}

impl Map {
    pub fn get(&self, x: usize, y: usize) -> Option<&char> {
        let value = self.map.get(&get_char_key(x, y));

        match value {
            Some(v) => {
                let c = v;

                Some(c)
            }
            None => None,
        }
    }

    pub fn insert(&mut self, x: usize, y: usize, c: char) -> Option<char> {
        if x > self.x_max {
            self.x_max = x;
        }

        if y > self.y_max {
            self.y_max = y;
        }

        self.map.insert(get_char_key(x, y), c)
    }

    pub fn get_x_max(&self) -> usize {
        self.x_max
    }

    pub fn get_y_max(&self) -> usize {
        self.y_max
    }
}

fn get_char_key(x: usize, y: usize) -> String {
    let key = format!("x:{x},y:{y}");

    key
}

pub fn build_map() -> Map {
    Map {
        map: HashMap::new(),
        x_max: 0,
        y_max: 0,
    }
}
