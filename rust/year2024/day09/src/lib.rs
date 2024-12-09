pub fn get_filesystem_checksum(disk_map_string: &str) -> u128 {
    let disk_map = disk_map_string
        .chars()
        .map(|c| c.to_string().parse::<u8>().unwrap())
        .collect::<Vec<u8>>();

    let files = disk_map.iter().step_by(2).enumerate().collect::<Vec<_>>();
    let spaces = disk_map.iter().skip(1).step_by(2).collect::<Vec<&u8>>();

    let disk = files
        .iter()
        .enumerate()
        .fold(vec![], |mut v, (file_i, (file_id, file_length))| {
            for _ in 0..**file_length {
                v.push(file_id.to_string().chars().next().unwrap());
            }

            for _ in 0..**spaces.get(file_i).unwrap_or(&&0) {
                v.push('.');
            }

            v
        });

    let mut disk_i = 0;
    let mut sorted_disk_i = disk.len() - 1;
    let mut sorted_disk: Vec<char> = vec![];

    while disk_i <= sorted_disk_i {
        if disk[disk_i] == '.' {
            sorted_disk.push(disk[sorted_disk_i]);

            sorted_disk_i -= 1;
            while disk[sorted_disk_i] == '.' {
                sorted_disk_i -= 1;
            }
        } else {
            sorted_disk.push(disk[disk_i]);
        }

        disk_i += 1;
    }

    dbg!(&sorted_disk);

    sorted_disk.iter().enumerate().fold(0, |sum, (c_i, c)| {
        sum + c_i as u128 * c.to_string().parse::<u128>().unwrap()
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

        // assert_eq!(get_filesystem_checksum(TEST_1), 0);
        assert_eq!(get_filesystem_checksum(TEST_2), 1928);

        // < 5909851146
        assert_eq!(get_filesystem_checksum(input), 0);
    }
}
