pub fn count_steps(direction_string: &str) -> usize {
    let directions = direction_string.split(",").collect::<Vec<&str>>();

    let mut nesw: i32 = 0;
    let mut nwse: i32 = 0;

    for direction in directions {
        match direction {
            "ne" => {
                nesw += 1;
            }
            "sw" => {
                nesw -= 1;
            }
            "nw" => {
                nwse += 1;
            }
            "se" => {
                nwse -= 1;
            }
            "n" => {
                nwse += 1;
                nesw += 1;
            }
            "s" => {
                nwse -= 1;
                nesw -= 1;
            }
            _ => {}
        }
    }

    get_distance(&nesw, &nwse)
}

pub fn count_furthest_distance(direction_string: &str) -> usize {
    let directions = direction_string.split(",").collect::<Vec<&str>>();

    let mut nesw: i32 = 0;
    let mut nwse: i32 = 0;

    let mut furthest_distance = usize::MIN;

    for direction in directions {
        match direction {
            "ne" => {
                nesw += 1;
            }
            "sw" => {
                nesw -= 1;
            }
            "nw" => {
                nwse += 1;
            }
            "se" => {
                nwse -= 1;
            }
            "n" => {
                nwse += 1;
                nesw += 1;
            }
            "s" => {
                nwse -= 1;
                nesw -= 1;
            }
            _ => {}
        }

        let distance = get_distance(&nesw, &nwse);
        if furthest_distance < distance {
            furthest_distance = distance;
        }
    }

    furthest_distance
}

fn get_distance(nesw: &i32, nwse: &i32) -> usize {
    let mut ns_abs: i32 = 0;
    let mut nesw_abs = *nesw;
    let mut nwse_abs = *nwse;

    while nesw_abs > 0 && nwse_abs > 0 {
        nesw_abs -= 1;
        nwse_abs -= 1;
        ns_abs += 1;
    }

    while nesw_abs < 0 && nwse_abs < 0 {
        nesw_abs += 1;
        nwse_abs += 1;
        ns_abs -= 1;
    }

    (ns_abs.abs() + nesw_abs.abs() + nwse_abs.abs()) as usize
}

#[cfg(test)]
mod tests {
    use std::fs;

    use super::*;

    #[test]
    fn test_count_steps() {
        let file = fs::read_to_string("./input.txt").unwrap();
        let input = file.as_str();

        assert_eq!(count_steps("ne,ne,ne"), 3);
        assert_eq!(count_steps("ne,ne,sw,sw"), 0);
        assert_eq!(count_steps("ne,ne,s,s"), 2);
        assert_eq!(count_steps("se,sw,se,sw,sw"), 3);
        assert_eq!(count_steps(input), 808);
        assert_eq!(count_furthest_distance(input), 1556);
    }
}
