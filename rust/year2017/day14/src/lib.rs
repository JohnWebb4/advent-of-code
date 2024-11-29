use std::collections::{HashSet, LinkedList};

pub fn count_squares_used(key_string: &str) -> usize {
    (0..128)
        .map(|row_i| get_knot_hash(&get_key_row_string(key_string, row_i)))
        .map(get_bits_from_knot_hash)
        .fold(0, |sum, bit_string| {
            sum + bit_string.chars().fold(0_usize, |s, bit| {
                if bit == '1' {
                    return s + 1;
                }

                s
            })
        })
}

pub fn count_regions(key_string: &str) -> usize {
    let mut used_square_set = HashSet::<(usize, usize)>::new();
    let mut seen_square_set = HashSet::<(usize, usize)>::new();

    (0..128)
        .map(|row_i| get_knot_hash(&get_key_row_string(key_string, row_i)))
        .map(get_bits_from_knot_hash)
        .enumerate()
        .for_each(|(y, bit_string)| {
            bit_string.chars().enumerate().for_each(|(x, bit)| {
                if bit == '1' {
                    used_square_set.insert((x, y));
                }
            });
        });

    let mut regioun_count = 0;
    for square in used_square_set.iter() {
        if !seen_square_set.contains(square) {
            let mut region_search_queue = LinkedList::<(usize, usize)>::new();
            region_search_queue.push_back(square.clone());

            while !region_search_queue.is_empty() {
                let (search_x, search_y) = region_search_queue.pop_front().unwrap();

                let mut next_square_list = Vec::<(usize, usize)>::new();

                if search_y < 127 {
                    next_square_list.push((search_x, search_y + 1));
                }
                if search_y > 0 {
                    next_square_list.push((search_x, search_y - 1));
                }

                if search_x < 127 {
                    next_square_list.push((search_x + 1, search_y));
                }
                if search_x > 0 {
                    next_square_list.push((search_x - 1, search_y));
                }

                next_square_list.iter().for_each(|next_square| {
                    if used_square_set.contains(next_square)
                        && !seen_square_set.contains(next_square)
                    {
                        region_search_queue.push_back(next_square.clone());
                        seen_square_set.insert(next_square.clone());
                    }
                })
            }

            regioun_count += 1;
            seen_square_set.insert(square.clone());
        }
    }

    regioun_count
}

pub fn get_bits_from_knot_hash(knot_hash: String) -> String {
    let knot_hash_chars = knot_hash.chars().collect::<Vec<_>>();

    knot_hash_chars
        .iter()
        .map(|hex| format!("{:04b}", u16::from_str_radix(&hex.to_string(), 16).unwrap()))
        .fold("".to_string(), |s, binary| s + binary.as_str())
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

pub fn get_key_row_string(key_string: &str, index: usize) -> String {
    format!("{}-{}", key_string, index)
}

#[cfg(test)]
mod tests {
    use super::*;

    const TEST_1: &str = "flqrgnkx";
    const INPUT: &str = "ljoxqyyw";

    #[test]
    fn test_count_squares_used() {
        assert_eq!(count_squares_used(TEST_1), 8108);
        assert_eq!(count_squares_used(INPUT), 8316);

        assert_eq!(count_regions(TEST_1), 1242);
        assert_eq!(count_regions(INPUT), 1074);
    }
}
