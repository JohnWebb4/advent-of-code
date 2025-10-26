package year2022day20_test

import (
	"fmt"
	year2022day20 "johnwebb4/adventofcode/year2022day19"
	"os"
	"testing"
)

const test1Input = `1
2
-3
3
-2
0
4`

func TestGrooveCoordinates(t *testing.T) {
	// Assert mixed file
	assertMixedFile(t, "MixFile1", "1, 2, -3, 3, -2, 0, 4", test1Input, 0)
	assertMixedFile(t, "MixFile2", "2, 1, -3, 3, -2, 0, 4", test1Input, 1)
	assertMixedFile(t, "MixFile3", "1, -3, 2, 3, -2, 0, 4", test1Input, 2)
	assertMixedFile(t, "MixFile3", "1, 2, 3, -2, -3, 0, 4", test1Input, 3)
	assertMixedFile(t, "MixFile4", "1, 2, -2, -3, 0, 3, 4", test1Input, 4)
	assertMixedFile(t, "MixFile5", "1, 2, -3, 0, 3, 4, -2", test1Input, 5)
	assertMixedFile(t, "MixFile6", "1, 2, -3, 0, 3, 4, -2", test1Input, 6)
	assertMixedFile(t, "MixFile7", "1, 2, -3, 4, 0, 3, -2", test1Input, 7)

	assertPartOne(t, "Test1", 3, test1Input)

	data, err := os.ReadFile("./input.txt")
	if err != nil {
		t.Fatalf("Year 2022 Day 20. Error reading input file %v", err)
	}

	input := string(data)

	// !4038
	// !-6069
	// !-10381
	// !8574
	assertPartOne(t, "Input", 1087, input)

	tests := []string{
		"811589153, 1623178306, -2434767459, 2434767459, -1623178306, 0, 3246356612",
		"0, -2434767459, 3246356612, -1623178306, 2434767459, 1623178306, 811589153",
		"0, 2434767459, 1623178306, 3246356612, -2434767459, -1623178306, 811589153",
		"0, 811589153, 2434767459, 3246356612, 1623178306, -1623178306, -2434767459",
		"0, 1623178306, -2434767459, 811589153, 2434767459, 3246356612, -1623178306",
		"0, 811589153, -1623178306, 1623178306, -2434767459, 3246356612, 2434767459",
		"0, 811589153, -1623178306, 3246356612, -2434767459, 1623178306, 2434767459",
		"0, -2434767459, 2434767459, 1623178306, -1623178306, 811589153, 3246356612",
		"0, 1623178306, 3246356612, 811589153, -2434767459, 2434767459, -1623178306",
		"0, 811589153, 1623178306, -2434767459, 3246356612, 2434767459, -1623178306",
		"0, -2434767459, 1623178306, 3246356612, -1623178306, 2434767459, 811589153",
	}

	for testI, test := range tests {
		assertDecryptMixedFile(t, fmt.Sprintf("Test%d", testI), test, test1Input, testI)
	}

	assertPartTwo(t, "Test", 1623178306, test1Input)

	assertPartTwo(t, "Input", 13084440324666, input)
}

func assertMixedFile(t *testing.T, name string, expected string, input string, maxIterations int) {
	result, err := year2022day20.MixFileFromInput(input, maxIterations)

	if err != nil {
		t.Fatalf("Year 2022 Day 20 Part One %s. Error %v", name, err)
	}

	if result != expected {
		t.Errorf("Year 2022 Day 20 Part One %s. Expected %s but got %s", name, expected, result)
	}

}

func assertDecryptMixedFile(t *testing.T, name string, expected string, input string, numMixes int) {
	result, err := year2022day20.DecryptAndMixFileFromInput(input, numMixes)

	if err != nil {
		t.Fatalf("Year 2022 Day 20 Part Two %s. Error %v", name, err)
	}

	if result != expected {
		t.Errorf("Year 2022 Day 20 Part Two %s. Expected %s but got %s", name, expected, result)
	}
}

func assertPartOne(t *testing.T, name string, expected int, input string) {
	result, err := year2022day20.GetSumGrooveCoordinates(input)

	if err != nil {
		t.Fatalf("Year 2022 Day 20 Part One %s. Error %v", name, err)
	}

	if result != expected {
		t.Errorf("Year 2022 Day 20 Part One %s. Expected %d but got %d", name, expected, result)
	}
}

func assertPartTwo(t *testing.T, name string, expected int, input string) {
	result, err := year2022day20.GetDecryptedSumGrooveCoordinates(input)

	if err != nil {
		t.Fatalf("Year 2022 Day 20 Part Two %s. Error %v", name, err)
	}

	if result != expected {
		t.Errorf("Year 2022 Day 20 Part Two %s. Expected %d but got %d", name, expected, result)
	}

}
