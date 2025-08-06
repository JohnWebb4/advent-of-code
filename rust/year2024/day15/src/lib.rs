use std::collections::{HashMap, VecDeque};

// Assumptions
// x,y > 0 always (lazy)
// That the map is always enclosed by a border of walls (lazy)

#[derive(Clone, Debug, PartialEq, Eq, Hash)]
struct Point {
    x: i32,
    y: i32,
}

impl Point {
    fn new(x: i32, y: i32) -> Point {
        Point { x, y }
    }
}

#[derive(Clone, Debug, PartialEq)]
enum MapKey {
    Robot,
    Box,
    Wall,
}

impl MapKey {
    fn parse(key: &char) -> Result<MapKey, Error> {
        match key {
            '#' => Ok(MapKey::Wall),
            'O' => Ok(MapKey::Box),
            '@' => Ok(MapKey::Robot),
            _ => Err(Error::new("Failed to parse map key")),
        }
    }

    // fn to_char(&self) -> char {
    //     match self {
    //         MapKey::Wall => '#',
    //         MapKey::Box => 'O',
    //         MapKey::Robot => '@',
    //     }
    // }
}

#[derive(Debug, PartialEq)]
enum RobotInstruction {
    Up,
    Down,
    Left,
    Right,
}

impl RobotInstruction {
    fn parse(instruction: &str) -> Result<RobotInstruction, Error> {
        match instruction {
            "<" => Ok(RobotInstruction::Left),
            ">" => Ok(RobotInstruction::Right),
            "v" => Ok(RobotInstruction::Down),
            "^" => Ok(RobotInstruction::Up),
            _ => Err(Error::new("Failed to parse robot instruction")),
        }
    }
}

#[derive(Debug)]
pub struct Error {
    pub message: String,
}

impl Error {
    fn new(message: &str) -> Error {
        Error {
            message: message.to_string(),
        }
    }
}

struct WareHouseMap {
    map: HashMap<Point, MapKey>,
    max_x: i32,
    max_y: i32,
}

impl WareHouseMap {
    fn new(map: HashMap<Point, MapKey>, max_x: i32, max_y: i32) -> WareHouseMap {
        WareHouseMap { map, max_x, max_y }
    }

    fn get(&self, point: &Point) -> Option<&MapKey> {
        self.map.get(point)
    }

    fn insert(&mut self, point: Point, map_key: MapKey) -> Option<MapKey> {
        self.map.insert(point, map_key)
    }

    fn remove(&mut self, point: &Point) -> Option<MapKey> {
        self.map.remove(point)
    }
}

pub fn get_sum_of_box_gps_coordinates(warehouse_map_config: &str) -> i64 {
    let (mut robot, mut warehouse_map, robot_instructions) =
        parse_warehouse_map(warehouse_map_config).unwrap();

    for robot_instruction in &robot_instructions {
        let (diff_x, diff_y) = match robot_instruction {
            RobotInstruction::Up => (0_i32, -1_i32),
            RobotInstruction::Right => (1, 0),
            RobotInstruction::Down => (0, 1),
            RobotInstruction::Left => (-1, 0),
        };

        // Assuming diff_x, diff_x >= -1
        // Lazy since we know there is a border of walls around
        let mut move_stack = VecDeque::<(Point, MapKey)>::new();
        move_stack.push_front((Point::new(robot.x, robot.y), MapKey::Robot));
        while let Some((current_point, current_key)) = move_stack.pop_front() {
            let next_x = current_point.x + diff_x;
            let next_y = current_point.y + diff_y;

            if let Some(next_key) = warehouse_map.get(&Point::new(next_x, next_y)) {
                match next_key {
                    MapKey::Wall => {
                        // Blocked. Can't perform any move(s)
                        break;
                    }
                    MapKey::Box => {
                        move_stack.push_front((current_point.clone(), current_key.clone()));
                        move_stack.push_front((Point::new(next_x, next_y), next_key.clone()));
                    }
                    _ => {}
                }
            } else {
                // Empty. Can move
                warehouse_map.insert(Point::new(next_x, next_y), current_key.clone());
                warehouse_map.remove(&current_point);

                if current_key == MapKey::Robot {
                    robot = Point::new(next_x, next_y);
                }
            }
        }

        // dbg!(&robot_instruction);
        // print_map(&warehouse_map, &robot);
    }

    let mut sum = 0_i64;
    for y in 0..=warehouse_map.max_y {
        for x in 0..=warehouse_map.max_x {
            if let Some(map_key) = warehouse_map.get(&Point::new(x, y))
                && map_key == &MapKey::Box
            {
                sum += x as i64 + 100 * y as i64;
            }
        }
    }

    sum
}

// fn print_map(warehouse_map: &WareHouseMap, robot: &Point) {
//     for y in 0..=warehouse_map.max_y {
//         for x in 0..=warehouse_map.max_x {
//             if x == robot.x && y == robot.y {
//                 print!("@");
//             } else if let Some(map_key) = warehouse_map.get(&Point::new(x, y)) {
//                 print!("{}", map_key.to_char())
//             } else {
//                 print!(".")
//             }
//         }

//         println!();
//     }
// }

fn parse_warehouse_map(
    map_input: &str,
) -> Result<(Point, WareHouseMap, Vec<RobotInstruction>), Error> {
    let map_parts = map_input.split("\n\n").collect::<Vec<&str>>();

    let mut robot: Point = Point::new(0, 0);
    let mut map = HashMap::<Point, MapKey>::new();
    let mut max_x = i32::MIN;
    let mut max_y = i32::MIN;

    map_parts[0].split("\n").enumerate().for_each(|(y, line)| {
        if y as i32 > max_y {
            max_y = y as i32;
        }

        line.chars().enumerate().for_each(|(x, char)| {
            if x as i32 > max_x {
                max_x = x as i32;
            }

            if let Ok(key) = MapKey::parse(&char) {
                match key {
                    MapKey::Wall => {
                        map.insert(Point::new(x as i32, y as i32), key);
                    }
                    MapKey::Box => {
                        map.insert(Point::new(x as i32, y as i32), key);
                    }
                    MapKey::Robot => {
                        robot = Point::new(x as i32, y as i32);
                    }
                }
            }
        });
    });

    let warehouse_map = WareHouseMap::new(map, max_x, max_y);

    let robot_instructions = map_parts[1].split("").fold(
        Vec::<RobotInstruction>::new(),
        |mut v, instruction_string| {
            if let Ok(a) = RobotInstruction::parse(instruction_string) {
                v.push(a);
            }

            v
        },
    );

    Ok((robot, warehouse_map, robot_instructions))
}

#[cfg(test)]
mod tests {
    use std::fs;

    use super::*;

    const TEST_1: &str = "########
#..O.O.#
##@.O..#
#...O..#
#.#.O..#
#...O..#
#......#
########

<^^>>>vv<v>>v<<";

    const TEST_2: &str = "##########
#..O..O.O#
#......O.#
#.OO..O.O#
#..O@..O.#
#O#..O...#
#O..O..O.#
#.OO.O.OO#
#....O...#
##########

<vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^
vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v
><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<
<<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^
^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><
^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^
>^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^
<><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>
^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>
v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^";

    #[test]
    fn it_works() {
        let input = fs::read_to_string("./input.txt").unwrap();

        assert_eq!(get_sum_of_box_gps_coordinates(TEST_1), 2028);
        assert_eq!(get_sum_of_box_gps_coordinates(TEST_2), 10092);
        assert_eq!(get_sum_of_box_gps_coordinates(&input), 1499739);
    }
}
