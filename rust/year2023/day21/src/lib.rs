use garden::{new_garden, Garden};

mod garden;

pub fn get_garden_plots_in_steps(num_steps: usize, input: &str) -> usize {
    println!("Hello, world!");
    0
}

fn read_garden() -> Garden {
    new_garden()
}

#[cfg(test)]
mod test {
    use crate::get_garden_plots_in_steps;

    const TEST_INPUT: &str = "...........
.....###.#.
.###.##..#.
..#.#...#..
....#.#....
.##..S####.
.##..#...#.
.......##..
.##.#.####.
.##..##.##.
...........";

    #[test]
    fn test_get_garden_plots_in_steps() {
        assert_eq!(16, get_garden_plots_in_steps(6, TEST_INPUT))
    }
}
