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
        if disk[disk_i].is_none() {
            sorted_disk.push(*disk.get(sorted_disk_i).unwrap_or(&None).unwrap_or(&0));

            sorted_disk_i -= 1;
            while disk[sorted_disk_i].is_none() {
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

#[derive(Clone, Debug)]
struct File {
    id: usize,
    length: usize,
    position: usize,
}

#[derive(Debug)]
struct Space {
    start: usize,
    length: usize,
}

pub fn get_filesystem_checksum_whole(disk_map_string: &str) -> usize {
    let disk_map = disk_map_string
        .chars()
        .map(|c| c.to_string().parse::<_>().unwrap())
        .collect::<Vec<usize>>();

    let (mut files, mut spaces) = get_files_and_spaces(disk_map);

    let mut can_move = true;
    while can_move {
        can_move = false;

        for i in (0..files.len()).rev() {
            for space in &mut spaces {
                if space.length >= files[i].length && files[i].position > space.start {
                    let new_file_start = space.start;

                    space.length -= files[i].length;
                    space.start += files[i].length;

                    files[i].position = new_file_start;

                    can_move = true;
                    break;
                }
            }
        }
    }

    let mut sum = 0;
    for file in files {
        for i in 0..file.length {
            sum += file.id * (file.position + i)
        }
    }

    sum
}

fn get_files_and_spaces(disk_map: Vec<usize>) -> (Vec<File>, Vec<Space>) {
    let mut files: Vec<File> = Vec::new();
    let mut spaces: Vec<Space> = Vec::new();

    let mut id = 0;
    let mut position = 0;

    for (i, length) in disk_map.iter().enumerate() {
        if i % 2 == 0 {
            // Is  a file
            files.push(File {
                id,
                position,
                length: *length,
            });

            id += 1;
            position += length;
        } else {
            // Empty space
            spaces.push(Space {
                start: position,
                length: *length,
            });

            position += length;
        }
    }

    (files, spaces)
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
        assert_eq!(get_filesystem_checksum_whole(input), 6423258376982);
    }
}
