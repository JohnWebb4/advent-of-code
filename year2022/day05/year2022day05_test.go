package year2022day05_test

import (
	year2022day05 "johnwebb4/adventcode/year2022day04"
	"os"
	"testing"
)

func TestTopCrates(t *testing.T) {
	data, err := os.ReadFile("./test.txt")
	if err != nil {
		t.Fatalf("Advent of code 2022 day 05. Failed to read test file")
	}
	test := string(data)

	got := year2022day05.GetTopCrates(test)
	expected := "CMZ"

	if got != expected {
		t.Fatalf("Advent of code 2022 day 05 part one test. Expected '%s' but got '%s'", expected, got)
	}

	data, err = os.ReadFile("./input.txt")
	if err != nil {
		t.Fatalf("Advent of code year 2022 day 05. Failed to read input file")
	}
	input := string(data)

	got = year2022day05.GetTopCrates(input)
	expected = "FWNSHLDNZ"

	if got != expected {
		t.Fatalf("Advent of code 2022 day 05 part one input. Expected '%s' but got '%s'", expected, got)
	}

	got = year2022day05.GetTpoCrates9001(test)
	expected = "MCD"

	if got != expected {
		t.Fatalf("Advent of code 2022 day 05 part two test. Expected '%s' but got '%s'", expected, got)
	}

	got = year2022day05.GetTpoCrates9001(input)
	expected = "RNRGDNFQG"

	if got != expected {
		t.Fatalf("Advent of code 2022 day 05 part two input. Expected '%s' but got '%s'", expected, got)
	}
}
