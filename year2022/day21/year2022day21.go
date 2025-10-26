package year2022day21

import (
	"fmt"
	"regexp"
	"strconv"
	"strings"
)

type Operand string

const AddOperand Operand = "+"
const MinusOperand Operand = "-"
const MultiplyOperand Operand = "*"
const DivideOperand Operand = "/"
const AssignmentOperand Operand = "="
const EqualityOperand Operand = "=="
const VariableExpression string = "x"

type Operation struct {
	Operand Operand
	Monkey1 string
	Monkey2 string
}

func newOperation(operand Operand, monkey1 string, monkey2 string) *Operation {
	return &Operation{
		Operand: operand,
		Monkey1: monkey1,
		Monkey2: monkey2,
	}
}

func (op Operation) String() string {
	return fmt.Sprintf("%s %s %s", op.Monkey1, op.Operand, op.Monkey2)
}

type Monkey struct {
	Name      string
	Value     int
	Operation *Operation
	IsReady   bool
}

func newMonkey(name string, operation *Operation, value int, isReady bool) *Monkey {
	return &Monkey{
		Name:      name,
		Value:     value,
		Operation: operation,
		IsReady:   isReady,
	}
}

func (monkey Monkey) String() string {
	operationString := "<nil>"
	if monkey.Operation != nil {
		operationString = monkey.Operation.String()
	}

	return fmt.Sprintf("Monkey %s; Value %d; Operation %s; Ready %v", monkey.Name, monkey.Value, operationString, monkey.IsReady)
}

var monkeyRegex = regexp.MustCompile(`^(\w+): (.+)$`)
var operationRegex = regexp.MustCompile(`^(\w+) (.) (\w+)$`)

func GetRootMonkeyNumber(input string) (int, error) {
	monkeys := readMonkeys(input)
	monkeyMap := make(map[string]*Monkey)
	for _, monkey := range monkeys {
		monkeyMap[monkey.Name] = monkey
	}

	rootMonkey := monkeyMap["root"]

	for !rootMonkey.IsReady {
		for _, monkey := range monkeys {
			if !monkey.IsReady && monkey.Operation != nil {
				// Check parent monkeys ready
				monkey1 := monkeyMap[monkey.Operation.Monkey1]
				monkey2 := monkeyMap[monkey.Operation.Monkey2]

				if monkey1.IsReady && monkey2.IsReady {
					operand := monkey.Operation.Operand
					newValue := monkey1.Value

					if operand == AddOperand {
						newValue += monkey2.Value
					} else if operand == MinusOperand {
						newValue -= monkey2.Value
					} else if operand == MultiplyOperand {
						newValue *= monkey2.Value
					} else if operand == DivideOperand {
						newValue /= monkey2.Value
					}

					monkey.Value = newValue
					monkey.IsReady = true
				}
			}
		}
	}

	return rootMonkey.Value, nil
}

func GetYourNumber(input string) (int, error) {
	solver := newSolver(input)

	solver.SetExpression("humn", VariableExpression)

	// Hacky
	rootEquation := solver.GetEquation("root")
	rootEquation.expression[1] = string(EqualityOperand)

	return solver.Solve("root"), nil
}

func readMonkeys(input string) []*Monkey {
	monkeyStrings := strings.Split(input, "\n")
	monkeys := make([]*Monkey, len(monkeyStrings))

	for i, monkeyString := range monkeyStrings {
		monkeyGroups := monkeyRegex.FindStringSubmatch(monkeyString)
		name := monkeyGroups[1]
		operationString := monkeyGroups[2]

		value, err := strconv.Atoi(operationString)
		isReady := true

		if err != nil {
			value = 0
			isReady = false
		}

		operation := readOperation(operationString)

		monkeys[i] = newMonkey(name, operation, value, isReady)
	}

	return monkeys
}

func readOperation(operationString string) *Operation {
	operationGroups := operationRegex.FindStringSubmatch(operationString)

	if len(operationGroups) != 4 {
		return nil
	}

	monkey1 := operationGroups[1]
	operand := operationGroups[2]
	monkey2 := operationGroups[3]

	return newOperation(Operand(operand), monkey1, monkey2)
}
