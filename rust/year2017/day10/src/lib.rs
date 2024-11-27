pub fn get_product_of_knot_tying(list_size: usize, input: &str) -> usize {
    let mut list = (0..list_size).collect::<Vec<usize>>();
    let list_length = list.len();

    let mut index = 0;
    let mut skip_size = 0;

    let sequence = input
        .split(',')
        .map(|l| l.parse::<usize>().unwrap())
        .collect::<Vec<usize>>();

    for length in sequence {
        if length <= list_length {
            (0..length / 2).for_each(|i| {
                list.swap(
                    (index + i) % list_length,
                    (index + length - i - 1) % list_length,
                );
            });

            index += length + skip_size;
            skip_size += 1;
        }
    }

    list[0] * list[1]
}

pub fn get_knot_hash(input: &str) -> String {
    let mut list = (0..256).collect::<Vec<usize>>();
    let list_length = list.len();

    let mut index: usize = 0;
    let mut skip_size: usize = 0;

    let mut sequence = input
        .chars()
        .map(|c| (c as u8) as usize)
        .collect::<Vec<usize>>();
    sequence.append(&mut ([17, 31, 73, 47, 23].to_vec()));

    for _ in 0..64 {
        for length in &sequence {
            if *length <= list_length {
                (0..length / 2).for_each(|i| {
                    list.swap(
                        (index + i) % list_length,
                        (index + length - i - 1) % list_length,
                    );
                });

                index += length + skip_size;
                skip_size += 1;
            }
        }
    }

    let hash = (0..16)
        .map(|i| {
            format!(
                "{:0>2x}",
                list[(16 * i)..(16 * i + 16)].iter().fold(0, |s, i| s ^ i)
            )
        })
        .collect::<Vec<String>>();

    hash.join("")
}

#[cfg(test)]
mod tests {
    use std::fs;

    use crate::{get_knot_hash, get_product_of_knot_tying};

    #[test]
    fn test_get_product_of_knot_tying() {
        let file = fs::read_to_string("./input.txt").unwrap();
        let input = file.as_str();

        assert_eq!(get_product_of_knot_tying(5, "3,4,1,5"), 12);
        assert_eq!(get_product_of_knot_tying(256, input), 48705);

        assert_eq!(get_knot_hash(""), "a2582a3a0e66e6e86e3812dcb672a272");
        assert_eq!(
            get_knot_hash("AoC 2017"),
            "33efeb34ea91902bb2f59c9920caa6cd"
        );
        assert_eq!(get_knot_hash("1,2,3"), "3efbe78a8d82f29979031a4aa0b16a9d");
        assert_eq!(get_knot_hash("1,2,4"), "63960835bcdc130f0b66d7ff4f6a5a8e");
        assert_eq!(get_knot_hash(input), "1c46642b6f2bc21db2a2149d0aeeae5d");
    }
}
