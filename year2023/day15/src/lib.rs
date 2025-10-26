use lens::{new_lens, Lens};
use regex::Regex;

mod lens;

pub fn get_focusing_power(input: &str) -> usize {
    let instruction_regex = Regex::new(r"(\w+)([=-])(\d+)?").unwrap();

    let boxes = input.split(',').fold(
        (0..256).map(|_| vec![]).collect::<Vec<Vec<Lens>>>(),
        |mut boxes, step| {
            let capture = instruction_regex.captures(step).unwrap();

            let label = capture.get(1).unwrap().as_str();
            let operation = capture.get(2).unwrap().as_str();
            let length_option = capture.get(3).map(|c| c.as_str());

            let box_i = hash_instruction(label);

            let mut new_box = boxes[box_i].clone();

            match operation {
                "=" => {
                    let length = length_option.unwrap().parse::<usize>().unwrap();

                    if let Some(lens) = new_box.iter_mut().find(|lens| lens.label == label) {
                        lens.length = length;
                    } else {
                        let new_lens = new_lens(label.to_string(), length);

                        new_box.push(new_lens);
                    }
                }
                "-" => {
                    new_box = new_box
                        .into_iter()
                        .filter(|lens| lens.label != label)
                        .collect::<Vec<Lens>>();
                }
                _ => {}
            }

            boxes[box_i] = new_box;

            boxes
        },
    );

    return boxes.iter().enumerate().fold(0, |sum, (box_i, the_box)| {
        sum + the_box
            .iter()
            .enumerate()
            .map(|(lens_i, lens)| (box_i + 1) * (lens_i + 1) * lens.length)
            .sum::<usize>()
    });
}

pub fn sum_of_results(input: &str) -> usize {
    input.split(',').map(hash_instruction).sum()
}

pub fn hash_instruction(step: &str) -> usize {
    step.chars().fold(0, |mut sum, c| {
        sum += c as usize;
        sum *= 17;
        sum %= 256;

        sum
    })
}

#[cfg(test)]
mod test {
    use std::fs;

    use crate::{get_focusing_power, sum_of_results};

    const TEST_INPUT_1: &str = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7";

    #[test]
    fn test_sum_of_results() {
        assert_eq!(1320, sum_of_results(TEST_INPUT_1));

        let input = fs::read_to_string("./input.txt").unwrap();

        assert_eq!(512797, sum_of_results(input.as_str()));

        assert_eq!(145, get_focusing_power(TEST_INPUT_1));

        assert_eq!(262454, get_focusing_power(input.as_str()));
    }
}
