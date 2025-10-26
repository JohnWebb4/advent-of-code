use std::collections::LinkedList;

pub fn get_total_calibration_result(calibration_equations_string: &str) -> i64 {
    let calibration_equation = calibration_equations_string
        .split("\n")
        .map(|equation_string| {
            let equation_parts = equation_string.split(": ").collect::<Vec<&str>>();

            let total = equation_parts[0].parse::<i64>().unwrap();

            let addends = equation_parts[1]
                .split(" ")
                .map(|addend_string| addend_string)
                .collect::<Vec<&str>>();

            return (total, addends);
        })
        .collect::<Vec<_>>();

    calibration_equation
        .iter()
        .filter(|(total, addends)| is_valid_equation(total, addends))
        .fold(0, |s, (total, _)| s + total)
}

fn is_valid_equation(total: &i64, addends: &Vec<&str>) -> bool {
    let mut partial_equation_queue = LinkedList::<(Vec<&str>, usize)>::new();
    partial_equation_queue.push_back((addends.clone(), 0));

    while !partial_equation_queue.is_empty() {
        if let Some((possible, current_i)) = partial_equation_queue.pop_front() {
            let mut multiply = possible.clone();
            multiply.insert(2 * current_i + 1, "*");

            let mut add = possible.clone();
            add.insert(2 * current_i + 1, "+");

            if current_i >= addends.len() - 2 {
                if [multiply, add].iter().any(|equation| {
                    let mut prev_val_opt: Option<&str> = None;
                    equation
                        .iter()
                        .skip(1)
                        .fold(equation[0].parse::<i64>().unwrap(), |acc, val| {
                            if let Ok(num) = val.parse::<i64>() {
                                if let Some(prev_val) = prev_val_opt {
                                    match prev_val {
                                        "*" => {
                                            prev_val_opt = Some(val);
                                            return acc * num;
                                        }
                                        "+" => {
                                            prev_val_opt = Some(val);
                                            return acc + num;
                                        }
                                        _ => {}
                                    }
                                }
                            }

                            prev_val_opt = Some(val);
                            acc
                        })
                        == *total
                }) {
                    return true;
                }
            } else {
                partial_equation_queue.push_back((multiply, current_i + 1));
                partial_equation_queue.push_back((add, current_i + 1));
            }
        }
    }

    return false;
}

pub fn get_total_calibration_result_concat(calibration_equations_string: &str) -> i64 {
    let calibration_equation = calibration_equations_string
        .split("\n")
        .map(|equation_string| {
            let equation_parts = equation_string.split(": ").collect::<Vec<&str>>();

            let total = equation_parts[0].parse::<i64>().unwrap();

            let addends = equation_parts[1]
                .split(" ")
                .map(|addend_string| addend_string)
                .collect::<Vec<&str>>();

            return (total, addends);
        })
        .collect::<Vec<_>>();

    calibration_equation
        .iter()
        .filter(|(total, addends)| is_valid_equation_concat(total, addends))
        .fold(0, |s, (total, _)| s + total)
}

fn is_valid_equation_concat(total: &i64, addends: &Vec<&str>) -> bool {
    let mut partial_equation_queue = LinkedList::<(Vec<&str>, usize)>::new();
    partial_equation_queue.push_back((addends.clone(), 0));

    while !partial_equation_queue.is_empty() {
        if let Some((possible, current_i)) = partial_equation_queue.pop_front() {
            let mut multiply = possible.clone();
            multiply.insert(2 * current_i + 1, "*");

            let mut add = possible.clone();
            add.insert(2 * current_i + 1, "+");

            let mut concat = possible.clone();
            concat.insert(2 * current_i + 1, "||");

            if current_i >= addends.len() - 2 {
                if [multiply, add, concat].iter().any(|equation| {
                    let mut prev_val_opt: Option<&str> = None;
                    equation
                        .iter()
                        .skip(1)
                        .fold(equation[0].parse::<i64>().unwrap(), |acc, val| {
                            if let Ok(num) = val.parse::<i64>() {
                                if let Some(prev_val) = prev_val_opt {
                                    match prev_val {
                                        "*" => {
                                            prev_val_opt = Some(val);
                                            return acc * num;
                                        }
                                        "+" => {
                                            prev_val_opt = Some(val);
                                            return acc + num;
                                        }
                                        "||" => {
                                            prev_val_opt = Some(val);
                                            return (acc.to_string() + &num.to_string())
                                                .parse::<i64>()
                                                .unwrap();
                                        }
                                        _ => {}
                                    }
                                }
                            }

                            prev_val_opt = Some(val);
                            acc
                        })
                        == *total
                }) {
                    return true;
                }
            } else {
                partial_equation_queue.push_back((multiply, current_i + 1));
                partial_equation_queue.push_back((add, current_i + 1));
                partial_equation_queue.push_back((concat, current_i + 1));
            }
        }
    }

    return false;
}

#[cfg(test)]
mod tests {
    use std::fs;

    use super::*;

    const TEST_1: &str = "190: 10 19
3267: 81 40 27
83: 17 5
156: 15 6
7290: 6 8 6 15
161011: 16 10 13
192: 17 8 14
21037: 9 7 18 13
292: 11 6 16 20";

    #[test]
    fn test_get_total_calibration_result() {
        let file = fs::read_to_string("./input.txt").unwrap();
        let input = file.as_str();

        assert_eq!(get_total_calibration_result(TEST_1), 3_749);
        assert_eq!(get_total_calibration_result(input), 8_401_132_154_762);

        assert_eq!(get_total_calibration_result_concat(TEST_1), 11_387);
        assert_eq!(
            get_total_calibration_result_concat(input),
            95_297_119_227_552
        );
    }
}
