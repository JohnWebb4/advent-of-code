package year2022day17_test

import (
	"johnwebb4/adventofcode/year2022day17"
	"os"
	"testing"
)

const test = ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>"

func TestYear2022Day17(t *testing.T) {
	AssertPartOne(t, "Test", test, 3068)

	data, err := os.ReadFile("./input.txt")
	if err != nil {
		t.Fatalf("Error reading input file %v", err)
	}

	input := string(data)

	AssertPartOne(t, "Input", input, 3209)

	AssertPartTwo(t, "Test", test, 1514285714288)

	AssertPartTwo(t, "Input", input, 1580758017509)
}

func AssertPartOne(t *testing.T, name string, input string, expected int) {
	result := year2022day17.HowTallIsTowerOfRocks(input, 2022)

	if result != expected {
		t.Errorf("Failed Part One %s: Expected %d but got %d", name, expected, result)
	}
}

func AssertPartTwo(t *testing.T, name string, input string, expected int) {
	result := year2022day17.HowTallIsTowerOfRocks(input, 1_000_000_000_000)

	if result != expected {
		t.Errorf("Failed Part Two %s: Expected %d but got %d", name, expected, result)
	}
}
