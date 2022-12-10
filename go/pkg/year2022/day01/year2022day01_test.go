package year2022day01_test

import (
	"johnwebb4/adventcode/year2022day01"
	"testing"
)

func TestMain(t *testing.T) {
	got := year2022day01.CountCalories("")
	expected := 1

	if got != expected {
		t.Fatalf("Advent of code 2022 day 01 test 1 failed expected %d but got %d", expected, got)
	}
}
