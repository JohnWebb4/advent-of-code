package advent_of_code_test

import (
	"testing"

	advent_of_code "johnwebb.dev/advent_of_code/pkg/year_2016_day_10"
)

func TestBalanceBots(t *testing.T) {
	if advent_of_code.BalanceBots() != "hi" {
		t.Errorf("Wrong balance bots")
	}
}