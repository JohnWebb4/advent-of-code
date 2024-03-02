use std::{collections::HashMap, fmt};

use brick::{new_brick, Brick};
use brickmap::{new_brickmap, BrickMap};
use uuid::Uuid;
use vec3::{new_vec3, Vec3};

mod brick;
mod brickmap;
mod vec3;

#[derive(Debug, PartialEq)]
pub struct ParseError {}

impl fmt::Display for ParseError {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(f, "Failed to parse value")
    }
}

impl std::error::Error for ParseError {}

pub fn get_safetly_disintegrated(input: &str) -> Result<u32, ParseError> {
    read_brickmap(input).map(count_safetly_disintegrated_bricks)
}

fn count_safetly_disintegrated_bricks(brickmap: BrickMap) -> u32 {
    while !is_settled(&brickmap) {
        let (has_change, brickmap) = settle(&brickmap);

        if !has_change {
            panic!("NO change")
        }
    }

    0
}

fn settle(brickmap: &BrickMap) -> (bool, BrickMap) {
    (false, new_brickmap(vec![]))
}

fn is_settled(brickmap: &BrickMap) -> bool {
    let cell_map = brickmap
        .bricks
        .iter()
        .fold(HashMap::new(), |mut map, brick| {
            for x in brick.start.x..=brick.end.x {
                for y in brick.start.y..=brick.end.y {
                    for z in brick.start.z..=brick.end.z {
                        map.insert(coordinate_to_string(x, y, z), brick.id.clone());
                    }
                }
            }

            map
        });

    brickmap.bricks.iter().all(|brick| {
        for x in brick.start.x..=brick.end.x {
            for y in brick.start.y..=brick.end.y {
                for z in brick.start.z..=brick.end.z {
                    if z == 1 {
                        // If touching the ground
                        return true;
                    } else {
                        // Check if cell below it
                        let has_floor = cell_map
                            .get(&coordinate_to_string(x, y, z - 1))
                            .is_some_and(|cell| cell != &brick.id);

                        if has_floor {
                            return true;
                        } else {
                            continue;
                        }
                    }
                }
            }
        }

        false
    })
}

fn read_brickmap(input: &str) -> Result<BrickMap, ParseError> {
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
            Ok(new_brick(
                Uuid::new_v4().to_string(),
                start.clone(),
                end.clone(),
            ))
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

fn coordinate_to_string(x: u32, y: u32, z: u32) -> String {
    format!("x:{x},y:{y},z:{z}")
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
        assert_eq!(Ok(5), get_safetly_disintegrated(TEST_INPUT_1));
    }
}
