package advent_of_code_test

import (
	"os"
	"testing"

	advent_of_code "johnwebb.dev/advent_of_code/pkg/year_2016_day_10"
)

func TestBalanceBots(t *testing.T) {
	test1Instructions := `value 5 goes to bot 2
bot 2 gives low to bot 1 and high to bot 0
value 3 goes to bot 1
bot 1 gives low to output 1 and high to bot 0
bot 0 gives low to output 2 and high to output 0
value 2 goes to bot 2`

	dat, err := os.ReadFile("../../test/year_2016_day_10/input.txt")

	if err != nil {
		panic(err)
	}

	inputInstructions := string(dat)

	// Example
	expected := 0
	result := advent_of_code.BalanceBots(test1Instructions, []int{3, 5})
	if expected != result {
		t.Errorf("Wrong balance bots -> test. Expected %d but got %d.", expected, result)
	}

	// Part One
	expected = 98
	result = advent_of_code.BalanceBots(inputInstructions, []int{17, 61})
	if expected != result {
		t.Errorf("Wrong balance bots -> part one. Expected %d but got %d.", expected, result)
	}
}