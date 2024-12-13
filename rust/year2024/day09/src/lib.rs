pub fn get_filesystem_checksum(disk_map_string: &str) -> usize {
    let disk_map = disk_map_string
        .chars()
        .map(|c| c.to_string().parse::<_>().unwrap())
        .collect::<Vec<usize>>();

    // Note: This is really memory hungry
    let files = disk_map.iter().step_by(2).enumerate().collect::<Vec<_>>();
    let spaces = disk_map.iter().skip(1).step_by(2).collect::<Vec<&usize>>();

    let disk = files
        .iter()
        .enumerate()
        .fold(vec![], |mut v, (file_i, (file_id, file_length))| {
            for _ in 0..**file_length {
                v.push(Some(file_id));
            }

            for _ in 0..**spaces.get(file_i).unwrap_or(&&0) {
                v.push(None);
            }

            v
        });

    let mut disk_i = 0;
    let mut sorted_disk_i = disk.len() - 1;
    let mut sorted_disk: Vec<usize> = vec![];

    while disk_i <= sorted_disk_i {
        if disk[disk_i] == None {
            sorted_disk.push(*disk.get(sorted_disk_i).unwrap_or(&None).unwrap_or(&0));

            sorted_disk_i -= 1;
            while disk[sorted_disk_i] == None {
                sorted_disk_i -= 1;
            }
        } else {
            sorted_disk.push(*disk.get(disk_i).unwrap_or(&None).unwrap_or(&0));
        }

        disk_i += 1;
    }

    sorted_disk.iter().enumerate().fold(0, |sum, (c_i, c)| {
        sum + c_i * c.to_string().parse::<usize>().unwrap()
    })
}
pub fn get_filesystem_checksum_whole(disk_map_string: &str) -> usize {
    let disk_map = disk_map_string
        .chars()
        .map(|c| c.to_string().parse::<_>().unwrap())
        .collect::<Vec<usize>>();

    // Note: This is really memory hungry
    let files = disk_map.iter().step_by(2).enumerate().collect::<Vec<_>>();
    let spaces = disk_map.iter().skip(1).step_by(2).collect::<Vec<&usize>>();

    let mut space_i = 0_usize;
    let mut file_with_space = files
        .iter()
        .enumerate()
        .map(|(file_i, (file_id, file_length))| {
            // TODO
            let current_space_i = space_i;

            space_i += **file_length;
            space_i += **spaces.get(file_i).unwrap_or(&&0);

            (current_space_i, *file_id, **file_length)
        })
        .collect::<Vec<(usize, usize, usize)>>();

    println!("{:?}", (10..1).collect::<Vec<i32>>());

    let mut has_changes = true;
    while has_changes {
        has_changes = false;

        for i in 0..file_with_space.len() - 1 {
            let space_start = file_with_space[i].0 + file_with_space[i].2;
            let space_length = file_with_space[i + 1].0 - space_start;

            let mut j= file_with_space.len() - 1
            while j > i {
                if file_with_space[j].2 <= space_length {
                    file_with_space[j].0 = space_start;
                    file_with_space.sort_by(|file_1, file_2| file_1.0.cmp(&file_2.0));
                    has_changes = true;
                    break;
                }

                j -= 1;
            }

            if has_changes {
                break;
            }
        }

        dbg!(&file_with_space);
    }

    0
}

#[cfg(test)]
mod tests {
    use std::fs;

    use super::*;

    const TEST_1: &str = "12345";
    const TEST_2: &str = "2333133121414131402";

    #[test]
    fn it_works() {
        let file = fs::read_to_string("./input.txt").unwrap();
        let input = file.as_str();

        assert_eq!(get_filesystem_checksum(TEST_1), 60);
        assert_eq!(get_filesystem_checksum(TEST_2), 1928);
        assert_eq!(get_filesystem_checksum(input), 6386640365805);

        assert_eq!(get_filesystem_checksum_whole(TEST_2), 2858);
    }
}
