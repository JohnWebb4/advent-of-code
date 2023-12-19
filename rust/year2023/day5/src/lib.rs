pub fn get_lowest_location_with_initial_seed(input: &str) -> i32 {
    let almanac_parts_string = input.split('\n');

    for almanac_part_string in almanac_parts_string {
        println!("Almanac part\n{almanac_part_string}");
    }

    0
}

#[cfg(test)]
mod test {
    use crate::get_lowest_location_with_initial_seed;

    const TEST_INPUT_1: &str = "seeds: 79 14 55 13

seed-to-soil map:
50 98 2
52 50 48

soil-to-fertilizer map:
0 15 37
37 52 2
39 0 15

fertilizer-to-water map:
49 53 8
0 11 42
42 0 7
57 7 4

water-to-light map:
88 18 7
18 25 70

light-to-temperature map:
45 77 23
81 45 19
68 64 13

temperature-to-humidity map:
0 69 1
1 0 69

humidity-to-location map:
60 56 37
56 93 4";

    #[test]
    fn test_year_2023_day_5() {
        assert_eq!(35, get_lowest_location_with_initial_seed(TEST_INPUT_1))
    }
}
