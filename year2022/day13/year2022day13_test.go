package year2022day13_test

import (
	"johnwebb4/adventofcode/year2022day13"
	"os"
	"testing"
)

const test1 = `[1,1,3,1,1]
[1,1,5,1,1]

[[1],[2,3,4]]
[[1],4]

[9]
[[8,7,6]]

[[4,4],4,4]
[[4,4],4,4,4]

[7,7,7,7]
[7,7,7]

[]
[3]

[[[]]]
[[]]

[1,[2,[3,[4,[5,6,7]]]],8,9]
[1,[2,[3,[4,[5,6,0]]]],8,9]`

func TestSumIndiciesOfPairs(t *testing.T) {
	assertPartOne(t, "Test1", 13, test1)

	data, err := os.ReadFile("./input.txt")

	if err != nil {
		t.Fatalf("Year 2022 Day 13. Error %v", err)
	}

	input := string(data)

	assertPartOne(t, "Input", 5675, input)

	assertPartTwo(t, "Test1", 140, test1)

	assertPartTwo(t, "Input", 20383, input)
}

func assertPartOne(t *testing.T, name string, expected int, input string) {
	result, err := year2022day13.GetSumIndiciesPairs(input)

	if err != nil {
		t.Fatalf("Year 2022 Day 13 %s. Error %v", name, err)
	}

	if result != expected {
		t.Errorf("Year 2022 Day 13 %s. Expected %d but got %d", name, expected, result)
	}
}

func assertPartTwo(t *testing.T, name string, expected int, input string) {
	result, err := year2022day13.GetProductOfSortedDecoderPackets(input)

	if err != nil {
		t.Fatalf("Year 2022 Day 13 Part Two %s. Error %v", name, err)
	}

	if result != expected {
		t.Errorf("Year 2022 Day 13 Part Two %s. Expected %d but got %d", name, expected, result)
	}
}