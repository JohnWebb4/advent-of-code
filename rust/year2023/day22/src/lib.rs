use std::fmt;

use brick::{new_brick, Brick};
use brickmap::{new_brickmap, BrickMap};
use vec3::{new_vec3, Vec3};

mod brick;
mod brickmap;
mod vec3;

#[derive(Debug)]
struct ParseError {}

impl fmt::Display for ParseError {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(f, "Failed to parse value")
    }
}

impl std::error::Error for ParseError {}

pub fn get_safetly_disintegrated(input: &str) -> u32 {
    let map = read_map(input);

    0
}

fn read_map(input: &str) -> Result<BrickMap, ParseError> {
    let bricks = input
        .split('\n')
        .map(read_brick)
        .collect::<Result<Vec<Brick>, ParseError>>();

    bricks.map(new_brickmap)
}

fn read_brick(brick_str: &str) -> Result<Brick, ParseError> {
    if let Ok(vecs) = brick_str
        .split('~')
        .map(read_vec3)
        .collect::<Result<Vec<Vec3>, ParseError>>()
    {
        if let [start, end] = vecs.as_slice() {
            Ok(new_brick(start.clone(), end.clone()))
        } else {
            Err(ParseError {})
        }
    } else {
        Err(ParseError {})
    }
}

fn read_vec3(vec_str: &str) -> Result<Vec3, ParseError> {
    let coordinates = vec_str
        .split(',')
        .map(|coordinate_str| coordinate_str.parse::<u32>().unwrap())
        .collect::<Vec<u32>>();

    if let [x, y, z] = coordinates[..] {
        Ok(new_vec3(x, y, z))
    } else {
        Err(ParseError {})
    }
}

#[cfg(test)]
mod test {
    use crate::get_safetly_disintegrated;

    pub const TEST_INPUT_1: &str = "1,0,1~1,2,1
0,0,2~2,0,2
0,2,3~2,2,3
0,0,4~0,2,4
2,0,5~2,2,5
0,1,6~2,1,6
1,1,8~1,1,9";

    #[test]
    fn test_get_safetly_disintegrated() {
        assert_eq!(5, get_safetly_disintegrated(TEST_INPUT_1));
    }
}
