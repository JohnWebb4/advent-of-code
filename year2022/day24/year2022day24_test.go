package year2022day24_test

import (
	year2022day24 "johnwebb4/adventofcode/year2022day24"
	"os"
	"testing"
)

const testInput1 = `#.#####
#.....#
#>....#
#.....#
#...v.#
#.....#
#####.#`

const testInput2 = `#.######
#>>.<^<#
#.<..<<#
#>v.><>#
#<^v^^>#
######.#`

func TestFewestMinutes(t *testing.T) {
	assertPartOne(t, "Test 1", 10, testInput1)
	assertPartOne(t, "Test 2", 18, testInput2)

	data, err := os.ReadFile("./input.txt")
	if err != nil {
		t.Fatalf("Year 2022 Day 24. Failed to read input file %v", err)
	}

	input := string(data)

	assertPartOne(t, "Input", 266, input)

	assertPartTwo(t, "Test 2", 54, testInput2)

	assertPartTwo(t, "Input", 853, input)
}

func assertPartOne(t *testing.T, name string, expected int, input string) {
	result := year2022day24.GetFewestMinutesToAvoidBlizzard(input)

	if result != expected {
		t.Errorf("Year 2022 Day 24 Part One %s. Expected %d but got %d", name, expected, result)
	}
}

func assertPartTwo(t *testing.T, name string, expected int, input string) {
	result := year2022day24.GetFewestMinutesToAvoidBlizzardThereAndBack(input)

	if result != expected {
		t.Errorf("Year 2022 Day 24 Part Two %s. Expected %d but got %d", name, expected, result)
	}
}
