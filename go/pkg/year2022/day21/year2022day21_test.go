package year2022day21_test

import (
	year2022day21 "johnwebb4/adventofcode/year2022day21"
	"os"
	"testing"
)

const testInput = `root: pppw + sjmn
dbpl: 5
cczh: sllz + lgvd
zczc: 2
ptdq: humn - dvpt
dvpt: 3
lfqf: 4
humn: 5
ljgn: 2
sjmn: drzm * dbpl
sllz: 4
pppw: cczh / lfqf
lgvd: ljgn * ptdq
drzm: hmdt - zczc
hmdt: 32`

func TestMonkeyNumber(t *testing.T) {
	AssertPartOne(t, "Test", 152, testInput)

	data, err := os.ReadFile("./input.txt")
	if err != nil {
		t.Fatalf("Year 2022 day 21. Failed to ready input file.")
	}
	input := string(data)

	AssertPartOne(t, "Input", 256997859093114, input)

	// AssertPartTwo(t, "Test", 301, testInput)

	AssertPartTwo(t, "Input", 3_952_288_690_726, input)
}

func AssertPartOne(t *testing.T, name string, expected int, input string) {
	result, err := year2022day21.GetRootMonkeyNumber(input)

	if err != nil {
		t.Fatalf("Year 2022 Day 21 %s. Error %v", name, err)
	}

	if result != expected {
		t.Errorf("Year 2022 Day 21 %s. Expected %d but got %d", name, expected, result)
	}
}

func AssertPartTwo(t *testing.T, name string, expected int, input string) {
	result, err := year2022day21.GetYourNumber(input)

	if err != nil {
		t.Fatalf("Year 2022 Day 21 %s. Error %v", name, err)
	}

	if result != expected {
		t.Errorf("Year 2022 Day 21 %s. Expected %d but got %d", name, expected, result)
	}
}
