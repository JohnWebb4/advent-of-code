use crate::range::Range;
use crate::utils::UnexepectedError;

#[derive(Debug)]
pub struct Almanac {
    pub seeds: Vec<i64>,
    pub seed_to_soil_ranges: Vec<Range>,
    pub soil_to_fertilizer_ranges: Vec<Range>,
    pub fertilizer_to_water_ranges: Vec<Range>,
    pub water_to_light_ranges: Vec<Range>,
    pub light_to_temperature_ranges: Vec<Range>,
    pub temperature_to_humidity_ranges: Vec<Range>,
    pub humidity_to_location_ranges: Vec<Range>,
}

impl Almanac {
    pub fn get_seed_to_soil(&self, seed: i64) -> Option<i64> {
        get_ranges_value(&self.seed_to_soil_ranges, seed)
    }

    pub fn get_soil_to_fertilizer(&self, soil: i64) -> Option<i64> {
        get_ranges_value(&self.soil_to_fertilizer_ranges, soil)
    }

    pub fn get_fertilizer_to_water(&self, fertilizer: i64) -> Option<i64> {
        get_ranges_value(&self.fertilizer_to_water_ranges, fertilizer)
    }

    pub fn get_water_to_light(&self, water: i64) -> Option<i64> {
        get_ranges_value(&self.water_to_light_ranges, water)
    }

    pub fn get_light_to_temperature(&self, light: i64) -> Option<i64> {
        get_ranges_value(&self.light_to_temperature_ranges, light)
    }

    pub fn get_temperature_to_humidity(&self, temperature: i64) -> Option<i64> {
        get_ranges_value(&self.temperature_to_humidity_ranges, temperature)
    }

    pub fn get_humidity_to_location(&self, humidity: i64) -> Option<i64> {
        get_ranges_value(&self.humidity_to_location_ranges, humidity)
    }
}

pub fn read_almanac(input: &str) -> Result<Almanac, UnexepectedError> {
    let almanac_parts_string = input.split("\n\n");

    let mut seeds_option: Option<Vec<i64>> = None;
    let mut seed_to_soil_ranges_option: Option<Vec<Range>> = None;
    let mut soil_to_fertilizer_ranges_option: Option<Vec<Range>> = None;
    let mut fertilizer_to_water_ranges_option: Option<Vec<Range>> = None;
    let mut water_to_light_ranges_option: Option<Vec<Range>> = None;
    let mut light_to_temperature_ranges_option: Option<Vec<Range>> = None;
    let mut temperature_to_humidity_ranges_option: Option<Vec<Range>> = None;
    let mut humidity_to_location_ranges_option: Option<Vec<Range>> = None;

    for almanac_part_string in almanac_parts_string {
        let lines: Vec<&str> = almanac_part_string.split('\n').collect();

        let title = lines[0];
        if title.starts_with("seeds: ") {
            let title_parts: Vec<&str> = title.split(": ").collect();

            seeds_option = Some(
                title_parts[1]
                    .split(' ')
                    .map(|seed_string| seed_string.parse().unwrap())
                    .collect(),
            );
        } else if title.starts_with("seed-to-soil map") {
            seed_to_soil_ranges_option = Some(read_ranges(almanac_part_string).unwrap());
        } else if title.starts_with("soil-to-fertilizer map") {
            soil_to_fertilizer_ranges_option = Some(read_ranges(almanac_part_string).unwrap());
        } else if title.starts_with("fertilizer-to-water ma") {
            fertilizer_to_water_ranges_option = Some(read_ranges(almanac_part_string).unwrap());
        } else if title.starts_with("water-to-light map") {
            water_to_light_ranges_option = Some(read_ranges(almanac_part_string).unwrap());
        } else if title.starts_with("light-to-temperature map") {
            light_to_temperature_ranges_option = Some(read_ranges(almanac_part_string).unwrap());
        } else if title.starts_with("temperature-to-humidity map") {
            temperature_to_humidity_ranges_option = Some(read_ranges(almanac_part_string).unwrap());
        } else if title.starts_with("humidity-to-location map") {
            humidity_to_location_ranges_option = Some(read_ranges(almanac_part_string).unwrap());
        } else {
            return Err(UnexepectedError {
                msg: format!("Unexpected title {title}"),
            });
        }
    }

    if let (
        Some(seeds),
        Some(seed_to_soil_ranges),
        Some(soil_to_fertilizer_ranges),
        Some(fertilizer_to_water_ranges),
        Some(water_to_light_ranges),
        Some(light_to_temperature_ranges),
        Some(temperature_to_humidity_ranges),
        Some(humidity_to_location_ranges),
    ) = (
        seeds_option,
        seed_to_soil_ranges_option,
        soil_to_fertilizer_ranges_option,
        fertilizer_to_water_ranges_option,
        water_to_light_ranges_option,
        light_to_temperature_ranges_option,
        temperature_to_humidity_ranges_option,
        humidity_to_location_ranges_option,
    ) {
        Ok(Almanac {
            seeds,
            seed_to_soil_ranges,
            soil_to_fertilizer_ranges,
            fertilizer_to_water_ranges,
            water_to_light_ranges,
            light_to_temperature_ranges,
            temperature_to_humidity_ranges,
            humidity_to_location_ranges,
        })
    } else {
        Err(UnexepectedError {
            msg: "Failed to readl almanac".to_string(),
        })
    }
}

fn read_ranges(range_string: &str) -> Result<Vec<Range>, UnexepectedError> {
    let mut ranges: Vec<Range> = vec![];
    let range_strings = range_string.split('\n').skip(1);

    for range_string in range_strings {
        if let [dest_start, source_start, range_length] = range_string
            .trim()
            .split(' ')
            .map(|v| v.parse().unwrap())
            .collect::<Vec<i64>>()[..]
        {
            ranges.push(Range {
                source_start,
                dest_start,
                length: range_length,
            });
        } else {
            return Err(UnexepectedError {
                msg: format!("Failed to read range {range_string}"),
            });
        }
    }

    Ok(ranges)
}

fn get_ranges_value(ranges: &[Range], value: i64) -> Option<i64> {
    for range in ranges.iter() {
        if let Some(dest_value) = range.get(value) {
            return Some(dest_value);
        }
    }

    None
}
