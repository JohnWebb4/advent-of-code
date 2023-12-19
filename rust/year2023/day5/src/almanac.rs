use std::collections::HashMap;

use crate::utils::UnexepectedError;

#[derive(Debug)]
pub struct Almanac {
    pub seeds: Vec<i64>,
    pub seed_to_soil_map: HashMap<i64, i64>,
    pub soil_to_fertilizer_map: HashMap<i64, i64>,
    pub fertilizer_to_water_map: HashMap<i64, i64>,
    pub water_to_light_map: HashMap<i64, i64>,
    pub light_to_temperature_map: HashMap<i64, i64>,
    pub temperature_to_humidity_map: HashMap<i64, i64>,
    pub humidity_to_location_map: HashMap<i64, i64>,
}

pub fn read_almanac(input: &str) -> Result<Almanac, UnexepectedError> {
    let almanac_parts_string = input.split("\n\n");

    let mut seeds_option: Option<Vec<i64>> = None;
    let mut seed_to_soil_map_option: Option<HashMap<i64, i64>> = None;
    let mut soil_to_fertilizer_map_option: Option<HashMap<i64, i64>> = None;
    let mut fertilizer_to_water_map_option: Option<HashMap<i64, i64>> = None;
    let mut water_to_light_map_option: Option<HashMap<i64, i64>> = None;
    let mut light_to_temperature_map_option: Option<HashMap<i64, i64>> = None;
    let mut temperature_to_humidity_map_option: Option<HashMap<i64, i64>> = None;
    let mut humidity_to_location_map_option: Option<HashMap<i64, i64>> = None;

    for almanac_part_string in almanac_parts_string {
        let lines: Vec<&str> = almanac_part_string.split('\n').collect();

        let title = lines[0];
        if title.starts_with("seeds: ") {
            let title_parts: Vec<&str> = title.split(": ").collect();

            println!("Title parts {title_parts:?}");

            seeds_option = Some(
                title_parts[1]
                    .split(' ')
                    .map(|seed_string| seed_string.parse().unwrap())
                    .collect(),
            );
        } else if title.starts_with("seed-to-soil map") {
            seed_to_soil_map_option = Some(read_range_map(almanac_part_string).unwrap());
        } else if title.starts_with("soil-to-fertilizer map") {
            soil_to_fertilizer_map_option = Some(read_range_map(almanac_part_string).unwrap());
        } else if title.starts_with("fertilizer-to-water ma") {
            fertilizer_to_water_map_option = Some(read_range_map(almanac_part_string).unwrap());
        } else if title.starts_with("water-to-light map") {
            water_to_light_map_option = Some(read_range_map(almanac_part_string).unwrap());
        } else if title.starts_with("light-to-temperature map") {
            light_to_temperature_map_option = Some(read_range_map(almanac_part_string).unwrap());
        } else if title.starts_with("temperature-to-humidity map") {
            temperature_to_humidity_map_option = Some(read_range_map(almanac_part_string).unwrap());
        } else if title.starts_with("humidity-to-location map") {
            humidity_to_location_map_option = Some(read_range_map(almanac_part_string).unwrap());
        } else {
            return Err(UnexepectedError {
                msg: format!("Unexpected title {title}"),
            });
        }
    }

    if let (
        Some(seeds),
        Some(seed_to_soil_map),
        Some(soil_to_fertilizer_map),
        Some(fertilizer_to_water_map),
        Some(water_to_light_map),
        Some(light_to_temperature_map),
        Some(temperature_to_humidity_map),
        Some(humidity_to_location_map),
    ) = (
        seeds_option,
        seed_to_soil_map_option,
        soil_to_fertilizer_map_option,
        fertilizer_to_water_map_option,
        water_to_light_map_option,
        light_to_temperature_map_option,
        temperature_to_humidity_map_option,
        humidity_to_location_map_option,
    ) {
        Ok(Almanac {
            seeds,
            seed_to_soil_map,
            soil_to_fertilizer_map,
            fertilizer_to_water_map,
            water_to_light_map,
            light_to_temperature_map,
            temperature_to_humidity_map,
            humidity_to_location_map,
        })
    } else {
        Err(UnexepectedError {
            msg: "Failed to readl almanac".to_string(),
        })
    }
}

fn read_range_map(range_string: &str) -> Result<HashMap<i64, i64>, UnexepectedError> {
    let mut range_map: HashMap<i64, i64> = HashMap::new();
    let range_strings = range_string.split('\n').skip(1);

    for range_string in range_strings {
        if let [dest_start, source_start, range_length] = range_string
            .trim()
            .split(' ')
            .map(|v| v.parse().unwrap())
            .collect::<Vec<i64>>()[..]
        {
            if range_length > 10_000 {
                return Err(UnexepectedError {
                    msg: format!("Really big range {dest_start} {source_start} {range_length}"),
                });
            }

            for i in 0..range_length {
                range_map.insert(source_start + i, dest_start + i);
            }
        } else {
            return Err(UnexepectedError {
                msg: format!("Failed to read range {range_string}"),
            });
        }
    }

    Ok(range_map)
}
