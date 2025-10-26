pub fn get_trip_severity(layer_record_string: &str) -> u32 {
    let layers_records = layer_record_string.split('\n').collect::<Vec<&str>>();

    let mut severity_sum = 0;
    for layer_string in layers_records {
        let parts = layer_string.split(": ").collect::<Vec<&str>>();
        let layer = parts[0].parse::<u32>().unwrap();

        let range = parts[1].parse::<u32>().unwrap();

        let is_position_zero = layer % (2 * range - 2) == 0;

        if is_position_zero {
            severity_sum += layer * range;
        }
    }

    severity_sum
}

pub fn get_shortest_delay_to_pass(layer_record_string: &str) -> u32 {
    let layers_records = layer_record_string
        .split('\n')
        .map(|layer_string| {
            let parts = layer_string.split(": ").collect::<Vec<&str>>();

            (
                parts[0].parse::<u32>().unwrap(),
                parts[1].parse::<u32>().unwrap(),
            )
        })
        .collect::<Vec<_>>();

    let mut delay = 0;
    let mut can_pass = false;

    while !can_pass {
        can_pass = true;

        for (layer, range) in layers_records.iter() {
            let is_position_zero = (layer + delay) % (2 * range - 2) == 0;

            if is_position_zero {
                can_pass = false;
                delay += 1;
                break;
            }
        }
    }

    delay
}

#[cfg(test)]
mod tests {
    use std::fs;

    use super::*;

    const TEST_1: &str = "0: 3
1: 2
4: 4
6: 4";

    #[test]
    fn test_get_trip_severity() {
        let file = fs::read_to_string("./input.txt").unwrap();
        let input = file.as_str();

        assert_eq!(get_trip_severity(TEST_1), 24);
        assert_eq!(get_trip_severity(input), 1588);

        assert_eq!(get_shortest_delay_to_pass(TEST_1), 10);
        assert_eq!(get_shortest_delay_to_pass(input), 3865118);
    }
}
