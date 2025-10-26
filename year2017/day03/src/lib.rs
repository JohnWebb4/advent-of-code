use std::collections::HashMap;

pub fn get_steps(address: usize) -> usize {
    if address == 1 {
        return 0;
    }

    let mut i = 1;
    while i * i < address {
        i += 2;
    }

    let mut remaining = address - (i - 2) * (i - 2) - 1;

    let mut x = (i / 2) as i32;
    let mut y = -(((i / 2) - 1) as i32);

    for _ in 0..(i - 2) {
        if remaining <= 0 {
            return (x.abs() + y.abs()) as usize;
        }

        y += 1;
        remaining -= 1;
    }

    for _ in 0..(i - 1) {
        if remaining <= 0 {
            return (x.abs() + y.abs()) as usize;
        }

        x -= 1;
        remaining -= 1;
    }

    for _ in 0..(i - 1) {
        if remaining <= 0 {
            return (x.abs() + y.abs()) as usize;
        }

        y -= 1;
        remaining -= 1;
    }

    for _ in 0..(i - 1) {
        if remaining <= 0 {
            return (x.abs() + y.abs()) as usize;
        }

        x += 1;
        remaining -= 1;
    }

    return 0;
}

pub fn get_first_larger(value: usize) -> usize {
    if value == 1 {
        return 1;
    }

    let mut map = HashMap::<String, usize>::new();
    map.insert(get_key(0, 0), 1);

    let mut loop_size = 3;
    let mut x = 1_i32;
    let mut y = 0_i32;

    while loop_size < 100 {
        for _ in 0..(loop_size - 2) {
            let cell = get_cell_value(&map, x, y);
            map.insert(get_key(x, y), cell);

            if cell > value {
                return cell;
            }

            y += 1;
        }

        for _ in 0..(loop_size - 1) {
            let cell = get_cell_value(&map, x, y);
            map.insert(get_key(x, y), cell);

            if cell > value {
                return cell;
            }

            x -= 1;
        }

        for _ in 0..(loop_size - 1) {
            let cell = get_cell_value(&map, x, y);
            map.insert(get_key(x, y), cell);

            if cell > value {
                return cell;
            }

            y -= 1;
        }

        for _ in 0..(loop_size) {
            let cell = get_cell_value(&map, x, y);

            if cell > value {
                return cell;
            }

            map.insert(get_key(x, y), cell);

            x += 1;
        }

        loop_size += 2;
    }

    return 0;
}

pub fn get_cell_value(map: &HashMap<String, usize>, x: i32, y: i32) -> usize {
    let res = [
        (x - 1, y + 1),
        (x, y + 1),
        (x + 1, y + 1),
        (x - 1, y),
        (x + 1, y),
        (x - 1, y - 1),
        (x, y - 1),
        (x + 1, y - 1),
    ]
    .map(|(x_1, y_1)| *map.get(&get_key(x_1, y_1)).unwrap_or(&0));

    res.iter().sum()
}

pub fn get_key(x: i32, y: i32) -> String {
    format!("X{},Y{}", x, y)
}

#[cfg(test)]
mod tests {
    use crate::{get_first_larger, get_steps};

    #[test]
    fn test_get_steps() {
        assert_eq!(get_steps(1), 0);
        assert_eq!(get_steps(12), 3);
        assert_eq!(get_steps(23), 2);
        assert_eq!(get_steps(1024), 31);
        assert_eq!(get_steps(368078), 371);

        assert_eq!(get_first_larger(1), 1);
        assert_eq!(get_first_larger(3), 4);
        assert_eq!(get_first_larger(6), 10);
        assert_eq!(get_first_larger(24), 25);
        assert_eq!(get_first_larger(119), 122);
        assert_eq!(get_first_larger(800), 806);
        assert_eq!(get_first_larger(368078), 369601);
    }
}
