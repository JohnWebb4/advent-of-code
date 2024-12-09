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

        // < 5909851146
        assert_eq!(get_filesystem_checksum(input), 6386640365805);
    }
}
