package year2022day12_test

import (
	"johnwebb4/adventofcode/year2022day12"
	"os"
	"testing"
)

func TestFindFewestStepsToSignal(t *testing.T) {
	data, err := os.ReadFile("./test.txt")
	if err != nil {
		t.Fatalf("Advent of code year 2022 day 12. Failed to read test file.")
	}
	test := string(data)

	got := year2022day12.FindFewestStepsToSignal(test)
	expected := 31

	if got != expected {
		t.Errorf("Advent of code year 2022 day 12 part one test. Expected %d but got %d", expected, got)
	}

	data, err = os.ReadFile("./input.txt")
	if err != nil {
		t.Fatalf("Advent of code year 2022 day 12. Failed to read input file")
	}
	input := string(data)

	got = year2022day12.FindFewestStepsToSignal(input)
	expected = 520

	if got != expected {
		t.Errorf("Advent of code year 2022 part one input. Expected %d but got %d", expected, got)
	}

	got = year2022day12.FindFewestStepsToSignalFromLow(test)
	expected = 29

	if got != expected {
		t.Errorf("Advent of code year 2022 part two test. Expected %d but got %d", expected, got)
	}

	got = year2022day12.FindFewestStepsToSignalFromLow(input)
	expected = 508

	if got != expected {
		t.Errorf("Advent of code year 2022 part two input. Expected %d but got %d", expected, got)
	}
}
