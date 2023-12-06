package year2022day25_test

import (
	"fmt"
	year2022day25 "johnwebb4/adventofcode/year2022day25"
	"os"
	"testing"
)

var testSnafuToDecimal = [][]interface{}{
	// Decimal to snafu list
	{"1", 1},
	{"2", 2},
	{"1=", 3},
	{"1-", 4},
	{"10", 5},
	{"11", 6},
	{"12", 7},
	{"2=", 8},
	{"2-", 9},
	{"20", 10},
	{"1=0", 15},
	{"1-0", 20},
	{"1=11-2", 2022},
	{"1-0---0", 12345},
	{"1121-1110-1=0", 314159265},

	// // Snafu to decimal list
	{"1=-0-2", 1747},
	{"12111", 906},
	{"2=0=", 198},
	{"21", 11},
	{"2=01", 201},
	{"111", 31},
	{"20012", 1257},
	{"112", 32},
	{"1=-1=", 353},
	{"1-12", 107},
	{"12", 7},
	{"1=", 3},
	{"122", 37},
}

const testInput1 = `1=-0-2
12111
2=0=
21
2=01
111
20012
112
1=-1=
1-12
12
1=
122`

func TestGetSnafu(t *testing.T) {

	for iSnafuToDecimal, snafuToDecimal := range testSnafuToDecimal {
		assertSnafuToDecimal(t, fmt.Sprintf("Snafu %d", iSnafuToDecimal), snafuToDecimal[1].(int), snafuToDecimal[0].(string))

		assertDecimalToSnafu(t, fmt.Sprintf("Decimal %d", iSnafuToDecimal), snafuToDecimal[0].(string), snafuToDecimal[1].(int))
	}

	assertPartOne(t, "Test 1", "2=-1=0", testInput1)

	data, err := os.ReadFile("./input.txt")

	if err != nil {
		t.Fatalf("Year 2022 Day 25. Error reading input file %v", err)
	}

	input := string(data)

	assertPartOne(t, "Input", "2---0-1-2=0=22=2-011", input)
}

func assertDecimalToSnafu(t *testing.T, name string, expected string, input int) {
	result, err := year2022day25.GetSnafuFromDecimal(input)

	if err != nil {
		t.Fatalf("Year 2022 Day 25 %s. Error %v", name, err)
	}

	if result != expected {
		t.Errorf("Year 2022 Day 25 %s. Expected %s but got %s", name, expected, result)
	}
}

func assertSnafuToDecimal(t *testing.T, name string, expected int, input string) {
	result := year2022day25.GetDecimalFromSnafu(input)

	if result != expected {
		t.Errorf("Year 2022 Day 25 Snafu to Decimal %s. Expected %d but got %d", name, expected, result)
	}
}

func assertPartOne(t *testing.T, name string, expected string, input string) {
	result, err := year2022day25.SumSnafuNumbers(input)

	if err != nil {
		t.Fatalf("Year 2022 Day 25 %s. Error %v", name, err)
	}

	if result != expected {
		t.Errorf("Year 2022 Day 25 Part One. Expected '%s' but got '%s'", expected, result)
	}
}
