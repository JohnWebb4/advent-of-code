use almanac::read_almanac;
use utils::UnexepectedError;

mod almanac;
mod utils;

pub fn get_lowest_location_with_initial_seed(input: &str) -> Result<i64, UnexepectedError> {
    let almanac = read_almanac(input).unwrap();

    let mut min_location = i64::MAX;

    for seed in almanac.seeds.iter() {
        println!("Looking at {seed}");

        let soil = almanac.seed_to_soil_map.get(seed).unwrap_or(seed);
        let fertilizer = almanac.soil_to_fertilizer_map.get(soil).unwrap_or(soil);
        let water = almanac
            .fertilizer_to_water_map
            .get(fertilizer)
            .unwrap_or(fertilizer);
        let light = almanac.water_to_light_map.get(water).unwrap_or(water);
        let temperature = almanac.light_to_temperature_map.get(light).unwrap_or(light);
        let humidity = almanac
            .temperature_to_humidity_map
            .get(temperature)
            .unwrap_or(temperature);
        let location = *almanac
            .humidity_to_location_map
            .get(humidity)
            .unwrap_or(humidity);

        if location < min_location {
            min_location = location;
        }
    }

    Ok(min_location)
}

#[cfg(test)]
mod test {
    use std::fs;

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
        assert_eq!(
            35,
            get_lowest_location_with_initial_seed(TEST_INPUT_1)
                .expect("Year 2023 Day 5 Part One Test")
        );

        let input =
            fs::read_to_string("./input.txt").expect("Year 2023 Day 5. Failed to read input");

        assert_eq!(
            0,
            get_lowest_location_with_initial_seed(input.as_str())
                .expect("Year 2023 Day 5 Part One Input")
        );
    }
}
