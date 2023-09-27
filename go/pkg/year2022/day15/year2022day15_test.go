package year2022day15_test

import (
	"johnwebb4/adventofcode/year2022day15"
	"os"
	"testing"
)

const test = `Sensor at x=2, y=18: closest beacon is at x=-2, y=15
Sensor at x=9, y=16: closest beacon is at x=10, y=16
Sensor at x=13, y=2: closest beacon is at x=15, y=3
Sensor at x=12, y=14: closest beacon is at x=10, y=16
Sensor at x=10, y=20: closest beacon is at x=10, y=16
Sensor at x=14, y=17: closest beacon is at x=10, y=16
Sensor at x=8, y=7: closest beacon is at x=2, y=10
Sensor at x=2, y=0: closest beacon is at x=2, y=10
Sensor at x=0, y=11: closest beacon is at x=2, y=10
Sensor at x=20, y=14: closest beacon is at x=25, y=17
Sensor at x=17, y=20: closest beacon is at x=21, y=22
Sensor at x=16, y=7: closest beacon is at x=15, y=3
Sensor at x=14, y=3: closest beacon is at x=15, y=3
Sensor at x=20, y=1: closest beacon is at x=15, y=3`

func TestBeaconExclusion(t *testing.T) {
	assertEqualPartOne(t, "part one testing", 26, test, 10)

	data, err := os.ReadFile("./input.txt")
	if err != nil {
		t.Fatalf("advent of code year 2022 day 15. jailed to read input file")
	}
	input := string(data)

	assertEqualPartOne(t, "part one input", 4919281, input, 2000000)

	assertEqualPartTwo(t, "part two test", 56000011, test, 20)

	assertEqualPartTwo(t, "part two input", 12630143363767, input, 4000000)
}

func assertEqualPartOne(t *testing.T, name string, expected int, input string, y int) {
	got, err := year2022day15.GetNumPositionsWithoutBeacon(input, y)

	if err != nil {
		t.Errorf("advent of code year 2022 day 15. %s failed %v", name, err)
	}

	if got != expected {
		t.Errorf("advent of code year 2022 day 15. %s expected %d but got %d", name, expected, got)
	}
}

func assertEqualPartTwo(t *testing.T, name string, expected int, input string, max int) {
	got, err := year2022day15.GetTuningFrequencyOfBeacon(input, max)

	if err != nil {
		t.Errorf("advent of code year 2022 day 15. %s failed %v", name, err)
	}

	if got != expected {
		t.Errorf("advent of code year 2022 day 15. %s expected %d but got %d", name, expected, got)
	}
}
