package year2022day18_test

import (
	"johnwebb4/adventofcode/year2022day18"
	"os"
	"testing"
)

const test = `2,2,2
1,2,2
3,2,2
2,1,2
2,3,2
2,2,1
2,2,3
2,2,4
2,2,6
1,2,5
3,2,5
2,1,5
2,3,5`

func TestGetSurfaceAreaOfLavaDroplet(t *testing.T) {
	AssertPartOne(t, "Test", 64, test)

	data, err := os.ReadFile("./input.txt")
	if err != nil {
		t.Fatalf("Failed to read input file %v", err)
	}
	input := string(data)

	AssertPartOne(t, "Input", 3662, input)

	AssertPartTwo(t, "Test", 58, test)

	AssertPartTwo(t, "Input", 2060, input) // x < 2652
}

func AssertPartOne(t *testing.T, name string, exptected int, input string) {
	result := year2022day18.GetSurfaceAreaOfLavaDroplet(input)

	if result != exptected {
		t.Errorf("Advent of Code Year 2022 Day 18 Part One %s Failed. Expcted %d but got %d\n", name, exptected, result)
	}
}

func AssertPartTwo(t *testing.T, name string, expected int, input string) {
	result := year2022day18.GetSurfaceAreaWithoutPockets(input)

	if result != expected {
		t.Errorf("Advent of Code Year 2022 Day 18 Part Two %s Failed. Expcted %d but got %d\n", name, expected, result)
	}
}
