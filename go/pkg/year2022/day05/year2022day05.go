package year2022day05

import (
	"fmt"
	"strconv"
	"strings"
)

func GetTopCrates(input string) string {
	parts := strings.Split(input, "\n\n")
	initialInput := parts[0]
	instructionString := parts[1]
	instructions := strings.Split(instructionString, "\n")

	stacks := convertCratesToStacks(initialInput)

	for _, instruction := range instructions {
		_, err := handleInstruction(instruction, stacks)

		if err != nil {
			fmt.Printf("failed to handle instruction, %s\n", err)
		}
	}

	strArr := []string{}
	for _, stack := range stacks {
		crate, err := stack.Pop()

		if err == nil {
			strArr = append(strArr, crate)
		}
	}

	return strings.Join(strArr, "")
}

func GetTpoCrates9001(input string) string {
	parts := strings.Split(input, "\n\n")
	initialInput := parts[0]
	instructionString := parts[1]
	instructions := strings.Split(instructionString, "\n")

	stacks := convertCratesToStacks(initialInput)

	for _, instruction := range instructions {
		_, err := handleInstruction9001(instruction, stacks)

		if err != nil {
			fmt.Printf("failed to handle instruction, %s\n", err)
		}
	}

	strArr := []string{}
	for _, stack := range stacks {
		crate, err := stack.Pop()

		if err == nil {
			strArr = append(strArr, crate)
		}
	}

	return strings.Join(strArr, "")
}

func handleInstruction(instruction string, stacks []*Stack) ([]*Stack, error) {
	if strings.HasPrefix(instruction, "move") {
		return handleInstructionMove(instruction, stacks)
	}

	return stacks, fmt.Errorf("missing instruction %s", instruction)
}

func handleInstructionMove(instruction string, stacks []*Stack) ([]*Stack, error) {
	parts := strings.Split(instruction, " ")
	count, err := strconv.Atoi(parts[1])

	if err == nil {
		sourceIndex, err := strconv.Atoi(parts[3])

		if err == nil {
			targetIndex, err := strconv.Atoi(parts[5])

			if err == nil {

				for i := 0; i < count; i++ {
					crate, err := stacks[sourceIndex-1].Pop()

					if err == nil {
						stacks[targetIndex-1].Push(crate)
					}
				}

			}
		}
	}

	if err == nil {
		return stacks, nil
	} else {
		return stacks, fmt.Errorf("failed to handle move instruction %s: %s", instruction, err)
	}

}

func handleInstruction9001(instruction string, stacks []*Stack) ([]*Stack, error) {
	if strings.HasPrefix(instruction, "move") {
		return handleInstructionMove9001(instruction, stacks)
	}

	return stacks, fmt.Errorf("missing instruction %s", instruction)
}

func handleInstructionMove9001(instruction string, stacks []*Stack) ([]*Stack, error) {
	parts := strings.Split(instruction, " ")
	count, err := strconv.Atoi(parts[1])

	if err == nil {
		sourceIndex, err := strconv.Atoi(parts[3])

		if err == nil {
			targetIndex, err := strconv.Atoi(parts[5])

			if err == nil {

				tempStack := new(Stack)
				for i := 0; i < count; i++ {
					crate, err := stacks[sourceIndex-1].Pop()

					if err == nil {
						tempStack.Push(crate)
					}
				}

				for len(*tempStack) > 0 {
					crate, err := tempStack.Pop()

					if err == nil {
						stacks[targetIndex-1].Push(crate)
					}
				}

			}
		}
	}

	if err == nil {
		return stacks, nil
	} else {
		return stacks, fmt.Errorf("failed to handle move instruction %s: %s", instruction, err)
	}

}

func convertCratesToStacks(initialInput string) []*Stack {
	rows := strings.Split(initialInput, "\n")

	stacks := []*Stack{}

	for i := 0; i < (len(rows[0])+1)/4; i += 1 {
		stacks = append(stacks, new(Stack))
	}

	for i := len(rows) - 2; i >= 0; i-- {
		row := rows[i]

		for j := 0; 4*j+1 < len(row); j += 1 {
			crate := string(row[4*j+1])

			if crate != " " {
				stacks[j].Push(crate)
			}
		}

	}

	return stacks
}
