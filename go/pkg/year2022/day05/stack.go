package year2022day05

import "errors"

type Stack []string

func (stack Stack) IsEmpty() bool {
	return len(stack) == 0
}

func (stack *Stack) Push(str string) {
	*stack = append(*stack, str)
}

func (stack *Stack) Pop() (string, error) {
	if stack.IsEmpty() {
		return "", errors.New("Stack is empty")
	} else {
		length := len(*stack)
		element := (*stack)[length-1]
		*stack = (*stack)[:length-1]

		return element, nil
	}
}
