package year2022day14_test

import (
	"johnwebb4/adventofcode/year2022day14"
	"os"
	"testing"
)

const test1 = `498,4 -> 498,6 -> 496,6
503,4 -> 502,4 -> 502,9 -> 494,9`

func TestGetNumSandBeforeRest(t *testing.T) {
	assertPartOne(t, "Test1", 24, test1)

	data, err := os.ReadFile("./input.txt")

	if err != nil {
		t.Fatalf("Year 2022 Day 14. Failed to read input file %v", err)
	}

	input := string(data)

	// > 593
	assertPartOne(t, "Input", 625, input)

	assertPartTwo(t, "Test1", 93, test1)

	assertPartTwo(t, "Input", 25193, input)
}

func assertPartOne(t *testing.T, name string, expected int, input string) {
	result, err := year2022day14.GetNumSandBeforeRest(input)

	if err != nil {
		t.Fatalf("Year 2022 Day 14 Part One %s. Error %v", name, err)
	}

	if result != expected {
		t.Errorf("Year 2022 Day 14 Part One %s. Expected %d but got %d", name, expected, result)
	}
}

func assertPartTwo(t *testing.T, name string, expected int, input string) {
	result, err := year2022day14.GetNumSandBeforeClogSource(input)

	if err != nil {
		t.Fatalf("Year 2022 Day 14 Part Two %s. Error %v", name, err)
	}

	if result != expected {
		t.Errorf("Year 2022 Day 14 Part Two %s. Expected %d but got %d", name, expected, result)
	}
}
