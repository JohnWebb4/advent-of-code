package year2022day01_test

import (
	"johnwebb4/adventcode/year2022day01"
	"os"
	"testing"
)

func TestMain(t *testing.T) {
	test := `1000
2000
3000

4000

5000
6000

7000
8000
9000

10000
	`
	got := year2022day01.CountCalories(test)
	expected := 24000

	if got != expected {
		t.Fatalf("Advent of code 2022 day 01 part one test failed expected %d but got %d", expected, got)
	}

	dat, err := os.ReadFile("./input.txt")

	if err != nil {
		t.Fatalf("Failed to read input.txt: %s", err)
	}

	input := string(dat)
	got = year2022day01.CountCalories(input)

	expected = 72240

	if got != expected {
		t.Fatalf("Advent of code 2022 day 01 part 1 expected %d but got %d", expected, got)
	}

	got = year2022day01.CountTopThree(test)

	expected = 45000

	if got != expected {
		t.Fatalf("Advent of code 2022 day 01 part 2 test test expected %d but got %d", expected, got)
	}

	got = year2022day01.CountTopThree(input)

	expected = 210957

	if got != expected {
		t.Fatalf("Advent of code 2022 day 01 part 2 expected %d but got %d", expected, got)
	}
}
