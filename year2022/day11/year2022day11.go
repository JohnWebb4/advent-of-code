package year2022day11

import (
	"fmt"
	"regexp"
	"strconv"
	"strings"
)

func GetMonkeyBusinessLevel(input string, numRounds int, hasBored bool) int {
	monkeyInputs := strings.Split(input, "\n\n")
	monkeys := []*Monkey{}

	testLCM := 1
	for _, monkeyInput := range monkeyInputs {
		monkey, err := parseMoneky(monkeyInput)
		if err != nil {
			fmt.Printf("Error parsing monkey: %v\n", err)
		}

		monkeys = append(monkeys, monkey)
		testLCM = testLCM * monkey.test
	}

	for iRound := 0; iRound < numRounds; iRound++ {
		for _, monkey := range monkeys {
			updateMonkey(monkey, monkeys, hasBored, testLCM)
		}
	}

	maxItemsInspected := 0
	secondMaxItemsInspected := 0
	for _, monkey := range monkeys {
		if monkey.itemsInspected > maxItemsInspected {
			secondMaxItemsInspected = maxItemsInspected
			maxItemsInspected = monkey.itemsInspected
		} else if monkey.itemsInspected > secondMaxItemsInspected {
			secondMaxItemsInspected = monkey.itemsInspected
		}
	}

	return maxItemsInspected * secondMaxItemsInspected
}

func updateMonkey(monkey *Monkey, monkeys []*Monkey, hasBored bool, testLCM int) {
	for len(monkey.items) > 0 {
		// Inspection
		item := monkey.items[0]
		updatedItem := item

		// Operation
		operation := monkey.operation
		if operation.operator == MULTIPLY {
			if operation.amount == "old" {
				updatedItem = item * item
			} else {
				amount, err := strconv.Atoi(operation.amount)
				if err != nil {
					fmt.Printf("Error parsing operation amount")
				}

				updatedItem = item * amount
			}
		} else if operation.operator == ADD {
			if operation.amount == "old" {
				updatedItem = item + item
			} else {
				amount, err := strconv.Atoi(operation.amount)
				if err != nil {
					fmt.Printf("Error parsing operation amount")
				}

				updatedItem = item + amount
			}
		}

		// Bored
		if hasBored {
			updatedItem = updatedItem / 3
		}

		updatedItem = updatedItem % testLCM

		// Test
		if updatedItem%monkey.test == 0 {
			monkeys[monkey.testTrueIndex].items = append(monkeys[monkey.testTrueIndex].items, updatedItem)
		} else {
			monkeys[monkey.testFalseIndex].items = append(monkeys[monkey.testFalseIndex].items, updatedItem)
		}

		monkey.itemsInspected++

		monkey.items = monkey.items[1:]
	}
}

func parseMoneky(monkeyInput string) (*Monkey, error) {
	lines := strings.Split(monkeyInput, "\n")

	startItemsInput := strings.Trim(lines[1], " ")
	startItems, err := parseStartItems(startItemsInput)
	if err != nil {
		return nil, err
	}

	operationInput := strings.Trim(lines[2], " ")
	operation, err := parseOperation(operationInput)
	if err != nil {
		return nil, err
	}

	testInput := strings.Trim(lines[3], " ")
	test, err := parseTest(testInput)
	if err != nil {
		return nil, err
	}

	testTrueIndexInput := strings.Trim(lines[4], " ")
	testTrueIndex, err := parseTestTrueIndex(testTrueIndexInput)
	if err != nil {
		return nil, err
	}

	testFalseIndexInput := strings.Trim(lines[5], " ")
	testFalseIndex, err := parseTestFalseIndex(testFalseIndexInput)
	if err != nil {
		return nil, err
	}

	return NewMonkey(startItems, operation, test, testTrueIndex, testFalseIndex, 0), nil
}

func parseStartItems(startItemsInput string) ([]int, error) {
	reStartItems := regexp.MustCompile(`Starting items: (\d+),? ?(\d+)?,? ?(\d+)?,? ?(\d+)?,? ?(\d+)?,? ?(\d+)?,? ?(\d+)?,? ?(\d+)?,? ?(\d+)?,? ?(\d+)?,? ?(\d+)?`)
	startItemsString := reStartItems.FindAllStringSubmatch(startItemsInput, -1)[0]
	startItems := []int{}
	for i := 1; i < len(startItemsString); i++ {
		if startItemsString[i] != "" {
			startItem, err := strconv.Atoi(startItemsString[i])

			if err != nil {
				return nil, err
			}

			startItems = append(startItems, startItem)
		}

	}

	return startItems, nil
}

func parseOperation(operationInput string) (*Operation, error) {
	reOperation := regexp.MustCompile(`Operation: new = old (.+) (.+)`)
	operationParse := reOperation.FindAllStringSubmatch(operationInput, -1)

	operation := NewOperation(Operator(operationParse[0][1]), operationParse[0][2])

	return operation, nil

}

func parseTest(testInput string) (int, error) {
	reTest := regexp.MustCompile(`Test: divisible by (\d+)`)
	testParse := reTest.FindAllStringSubmatch(testInput, -1)

	amountString := testParse[0][1]

	amount, err := strconv.Atoi(amountString)
	if err != nil {
		return -1, err
	}

	return amount, nil
}

func parseTestTrueIndex(testTrueIndexInput string) (int, error) {
	reTestTrueIndex := regexp.MustCompile(`If true: throw to monkey (\d+)`)
	testTrueIndexParse := reTestTrueIndex.FindAllStringSubmatch(testTrueIndexInput, -1)

	amountString := testTrueIndexParse[0][1]
	amount, err := strconv.Atoi(amountString)
	if err != nil {
		return -1, err
	}

	return amount, nil
}

func parseTestFalseIndex(testFalseIndexInput string) (int, error) {
	reTestFalseIndex := regexp.MustCompile(`If false: throw to monkey (\d+)`)
	testFalseIndexParse := reTestFalseIndex.FindAllStringSubmatch(testFalseIndexInput, -1)

	amountString := testFalseIndexParse[0][1]
	amount, err := strconv.Atoi(amountString)
	if err != nil {
		return -1, err
	}

	return amount, nil
}
