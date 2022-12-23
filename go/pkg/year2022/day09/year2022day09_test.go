package year2022day09_test

import (
	"johnwebb4/adventofcode/year2022day09"
	"os"
	"testing"
)

func TestCountTailPositions(t *testing.T) {
	test := `R 4
U 4
L 3
D 1
R 4
D 1
L 5
R 2`

	got := year2022day09.CountTailPositions(test, 2)
	expected := 13

	if got != expected {
		t.Errorf("Advent of code year 2022 day 09 part one test. Expected %d but got %d", expected, got)
	}

	data, err := os.ReadFile("./input.txt")
	if err != nil {
		t.Errorf("Advent of code year 2022 day 09 part one. Failed to read input file")
	}
	input := string(data)

	got = year2022day09.CountTailPositions(input, 2)
	expected = 6745

	if got != expected {
		t.Errorf("Advent of code year 2022 day 09 part one input. Expected %d but got %d", expected, got)
	}

	got = year2022day09.CountTailPositions(test, 10)
	expected = 1

	if got != expected {
		t.Errorf("Advent of code year 2022 day 09 part two test. Expected %d but got %d", expected, got)
	}

	got = year2022day09.CountTailPositions(input, 10)
	expected = 2793

	if got != expected {
		t.Errorf("Advent of code year 2022 day 09 part two input. Expected %d but got %d", expected, got)
	}
}
