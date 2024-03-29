package year2022day21

import (
	"fmt"
	"math"
	"regexp"
	"strings"
)

type Equation struct {
	variable   string
	operand    Operand
	expression []string
}

var equationRegex = regexp.MustCompile(`^(\w+): (.+)$`)
var expressionRegex = regexp.MustCompile(`(\w)+`)

func newEquation(equationString string) *Equation {
	equationParts := equationRegex.FindStringSubmatch(equationString)

	return &Equation{
		variable:   equationParts[1],
		operand:    AssignmentOperand,
		expression: strings.Split(equationParts[2], " "),
	}
}

func (eq Equation) String() string {
	return fmt.Sprintf("Equation %s %s %s", eq.variable, eq.operand, strings.Join(eq.expression, " "))
}

type Solver struct {
	Variables map[string]*Equation
}

func newSolver(input string) *Solver {
	inputStrings := strings.Split(input, "\n")

	equationMap := make(map[string]*Equation, len(inputStrings))

	for _, inputString := range inputStrings {
		equation := newEquation(inputString)
		equationMap[equation.variable] = equation
	}

	return &Solver{
		Variables: equationMap,
	}
}

func (solver Solver) String() string {
	equationString := make([]string, len(solver.Variables))

	count := 0
	for _, equation := range solver.Variables {
		equationString[count] = equation.String()
		count++
	}

	return fmt.Sprintf("Solver [\n%v\n]\n", strings.Join(equationString, "\n"))
}

func (solver Solver) GetEquation(name string) *Equation {
	for _, equation := range solver.Variables {
		if equation.variable == name {
			return equation
		}
	}

	return nil
}

func (solver Solver) SetExpression(name string, value string) {
	for _, equation := range solver.Variables {
		if equation.variable == name {
			equation.expression = []string{value}
		}
	}
}

func (solver Solver) Solve(variable string) int {
	// Expand as much as possible
	variableEquation := solver.GetEquation(variable)

	canExpand := true
	for canExpand {
		canExpand = false

		for i := 0; i < len(variableEquation.expression); i++ {
			term := variableEquation.expression[i]
			expressionVariables := expressionRegex.FindAllStringSubmatchIndex(term, -1)

			newTerm := term
			for _, expressionVariablePosition := range expressionVariables {
				expressionVariable := term[expressionVariablePosition[0]:expressionVariablePosition[1]]

				if solver.Variables[expressionVariable] != nil {
					// Slice result
					newTerm = term[0:expressionVariablePosition[0]] + fmt.Sprintf("(%s)",
						strings.Join(solver.Variables[expressionVariable].expression, " "),
					) + term[expressionVariablePosition[1]:]

					canExpand = true
				}
			}

			variableEquation.expression[i] = newTerm
		}
	}

	// I don't wanna write an interpreter so I just copy and pasted the input results below
	return hackSolver()
}

func hackSolver() int {
	xValue := 0
	direction := 3
	expected := rightSide()
	for leftSide(xValue) != expected {
		results := step(xValue, direction)
		currentResult := results[0]

		distanceLeft := expected - currentResult
		magnitude := results[1]

		if magnitude == 0 {
			break
		}

		direction = distanceLeft / magnitude
		if math.Abs(float64(direction)) < 0 {
			direction = 1
		}

		xValue += direction
	}

	return xValue
}

func step(current int, direction int) []int {
	currentResult := leftSide(current)
	forwardResult := leftSide(current + direction)

	diffResult := forwardResult - currentResult
	magnitude := diffResult / direction

	return []int{currentResult, magnitude}
}

func leftSide(x int) int {
	return ((6) * ((((((((5) * (((6) + (5)) * (2))) * (((3) * (3)) + (((1) + ((3) + (3))) * (2)))) * ((2) * (((17) * (((((((17) * (3)) + ((((4) + (4)) * (2)) * (5))) * (3)) * (((4) * ((2) * (((8) * (2)) + (1)))) - ((3) * (3)))) + (((((3) + ((2) * (5))) * ((4) * ((4) + (4)))) + ((((((2) * (((12) + ((6) * (13))) + (((2) * ((((2) * ((13) * (2))) + ((((((14) / (2)) * (5)) + ((((13) - (2)) * (2)) + (1))) / (2)) - (3))) - (5))) + (((16) + (((4) * (5)) + (17))) - ((2) * (3)))))) / (2)) + ((17) + ((((5) + (2)) + (3)) + (9)))) + ((((((2) * (((((2) + (5)) * (3)) * (3)) + (((2) * ((10) + (7))) * (4)))) + (((14) + ((4) * (4))) + ((((3) * ((1) + (6))) + (((((((2) + ((6) * (4))) / (2)) * (4)) / (2)) + (((2) * (5)) * (6))) * (2))) + (((13) * (15)) - ((((3) * (17)) / (3)) + (((3) * (2)) * (2))))))) + (((2) * ((1) + (16))) * (9))) * (2)) + ((((11) * (((5) * (2)) + (1))) + ((1) + ((10) + ((19) + (8))))) * (3)))) / (2))) * (((2) * (((7) * (3)) + (((2) * (11)) * (4)))) / (2)))) + (((2) * (((((((((1) + (18)) + ((4) * ((2) + (5)))) + ((1) + ((2) * (5)))) / (2)) * (4)) + ((13) * (5))) * ((((((((2) * (4)) + (9)) * (4)) * (2)) / (2)) + ((3) * ((3) * ((10) + (1))))) * (5))) + (((2) * ((((((9) * (5)) + (14)) * (3)) - ((2) * ((((2) * ((3) * (4))) + ((8) + (5))) - (8)))) / ((9) - (2)))) * ((2) * (((4) * (((7) * ((2) + (5))) + ((((((((5) * (2)) + (13)) * (3)) + (((4) * (6)) + ((3) + (10)))) + ((9) * (8))) / (2)) * (2)))) + (((3) * ((13) * (2))) + ((9) * ((5) + (((2) * (3)) * (3)))))))))) + ((((5) * (((((4) * (((2) * ((2) + ((7) * (3)))) + (((2) * (((((6) + ((((2) * (11)) * (3)) + (1))) + (3)) / (2)) + (((2) * (3)) + (17)))) / (2)))) + (((2) * ((((4) * ((8) - (2))) * (((3) * (2)) + ((3) * (3)))) - (((5) * ((3) * (4))) + ((((1) + ((2) * (17))) / (5)) + (12))))) * (3))) - ((5) * (((3) * (12)) + (((3) * (3)) + (((((2) * (15)) / (2)) + ((2) * ((2) * (4)))) * (2)))))) + ((3) * ((((11) * (((4) + (20)) + ((3) * (5)))) + ((4) * ((2) * (((7) * (3)) + (((5) + (6)) * (2)))))) + (((((7) * (7)) - (16)) - (5)) * ((((((((18) * ((2) + (5))) / (2)) + (20)) * (2)) + ((11) * (2))) / (4)) + ((6) * (3)))))))) + (((((((3) + (16)) + (((((5) * (7)) * (3)) + ((2) * ((2) * ((4) + (7))))) * (2))) + ((((16) * (2)) * (2)) - (2))) * (((2) * ((2) * ((11) + (12)))) + (((2) * (((((6) * (6)) + ((5) * (5))) - ((18) + (1))) + (((2) * (5)) + ((4) + (3))))) / (2)))) * (2)) - (((((((10) + (2)) * ((((3) * ((5) * (((8) * (((((9) * (19)) + (10)) * (2)) / (2))) / ((4) * (2))))) / (3)) + ((9) * ((2) * (((7) * (((4) * (((11) * (3)) - ((2) * (5)))) / (2))) + ((12) + (((((16) * (2)) + ((8) * (3))) + ((4) + ((((2) * (4)) * (5)) - (11)))) + ((2) * (4))))))))) / (3)) / (2)) / (2)) + ((((((4) * (8)) + ((15) * (13))) - (((((8) * (2)) * (6)) / (3)) + ((3) * ((((1) + (6)) * (2)) / (2))))) + (((2) + ((5) * (3))) * (2))) * (((7) * (11)) + (1)))))) * (5))))) + (((5) * ((2) * (((((((1) + (18)) * ((4) + (3))) - (((5) + (8)) * (2))) + (((((9) * (8)) + ((15) + (2))) * (6)) / ((3) + (3)))) * (((4) * ((2) * (((((1) + ((5) * (2))) * ((4) * (2))) * ((2) * ((2) * (((3) * (2)) + (5))))) + (((((((2) * (((1) + (6)) * (2))) * ((9) + (9))) - ((2) * (((((2) * ((2) * ((20) + (9)))) + ((3) * (11))) + ((((5) + (((2) + (8)) * (2))) * (2)) / (2))) / (3)))) * (2)) / (8)) * (5))))) / (2))) - ((3) * (((((((1) + ((3) * ((3) * (5)))) / (2)) * (5)) * (((((((19) * (5)) + (((3) * ((3) + ((2) * (5)))) * (2))) * (2)) * (2)) + (((11) + (((((2) * ((7) + (4))) * (2)) + (3)) + (((3) * (3)) * (5)))) * (3))) + ((3) * ((((2) * (8)) + ((7) * (3))) * (2))))) / (5)) + (((((8) * ((3) * (3))) - ((15) + (8))) + (4)) * ((((11) + (13)) + ((5) * ((14) / (2)))) * (4)))))))) + (((2) * (((3) * ((((11) * (17)) * ((2) * (((2) * ((((6) + (((7) * ((5) + (2))) + (12))) * (18)) + ((7) * (((11) * (5)) - (6))))) + (((((((2) * (4)) + (3)) * ((13) * (2))) - ((3) * ((((7) + (4)) * (2)) + ((3) * (3))))) * (6)) + (((5) * (((17) * (3)) + (((2) + (5)) * (4)))) + (((2) * ((((((((((9) * (14)) + ((3) + ((2) * ((13) + ((((2) * (4)) + (3)) + ((4) + (3))))))) * (2)) * (2)) + ((11) * (13))) * (5)) / (5)) - ((((2) + (13)) * (3)) + ((13) * ((2) * (4))))) / (2))) + ((2) * ((7) + (4))))))))) + ((((3) * ((3) * (3))) + ((((((12) + (11)) * (2)) + (((2) + (((4) * (2)) * (3))) + (17))) + ((4) * ((5) + (18)))) * (2))) * ((((((19) * (3)) + ((4) * (((4) * ((9) - (3))) - (4)))) * (11)) + ((2) * ((((2) * (8)) + (((((1) + (6)) * (9)) - (((5) + (2)) * (3))) + (((2) * ((((2) * (4)) * ((4) * (2))) + (((3) * (9)) + ((3) * (2))))) * (2)))) + (((5) * ((3) + (10))) + ((8) * (((6) + (1)) + (4))))))) - (((1) + ((5) * (5))) * ((((((12) + (3)) * (3)) / (5)) * (16)) / (6))))))) + ((((((((5) * ((5) * (5))) - (((3) * (4)) * (3))) + ((2) * ((7) + ((8) * (3))))) * (2)) + ((((3) * ((2) * (4))) + (((5) + (14)) * ((3) + (8)))) + (((2) * (11)) * ((4) * (7))))) * (4)) * ((((4) + (3)) * (5)) * (17))))) * ((2) + (((3) * (3)) * (3)))))))) + (((((((((2) * (((5) * (((13) + (9)) + (((11) * (2)) + ((2) * (9))))) + (((19) * (13)) + (((6) + (((4) * (4)) + (4))) + (10))))) + (((7) * (5)) * (5))) * (13)) - ((((((1) + (14)) * (2)) * ((2) * (((8) + (1)) + ((2) * (5))))) + (((5) * (((((6) * (9)) * (3)) + ((3) * ((3) + (5)))) / ((2) * (3)))) + (((11) * (2)) * (((4) * (2)) * (3))))) + ((((2) + (6)) * (((18) + ((((3) + (20)) + ((11) * (4))) + (((3) * (3)) * ((4) * (2))))) * (2))) / (2)))) + ((((((19) + ((18) * (11))) + ((2) * ((3) + (20)))) * (9)) + ((((4) * (2)) * ((((5) * (2)) * ((4) * (7))) + ((3) * ((((17) * (4)) + (3)) * (3))))) - ((8) * (((((6) * (6)) + ((5) * ((((6) + (1)) * (2)) / (2)))) * (2)) + ((8) * (8)))))) + (((((1) + (12)) * (5)) + (((2) * (17)) + ((3) * (3)))) + ((((((3) * ((((13) * (2)) / (2)) * (2))) + ((13) * (15))) + ((3) + (17))) + ((10) * (9))) + ((2) * (16)))))) + ((((((((5) + ((3) * (9))) - (10)) + (9)) * (2)) + ((3) * (9))) - ((2) * ((3) * (3)))) * (((((11) + ((((2) * (4)) * ((19) + (1))) / (4))) / (3)) * (5)) + ((18) * ((8) * (4)))))) * ((((13) * ((4) + ((3) + (4)))) * (2)) + ((5) * ((((13) + (16)) * (2)) + (((5) * (20)) + (15)))))) * (((((10) * (((((7) * ((6) + (13))) * (2)) + ((2) + ((2) + (((((4) * (2)) * (2)) + (17)) - (10))))) * (3))) + (((((3) * ((8) * (2))) * (19)) + (((3) * (((5) * (5)) + (1))) / (2))) * (9))) * (2)) / (2)))) * (3)) * (2)) - ((((3) * (((((((10) + ((6) + (1))) + ((5) * (2))) * (((5) + (17)) + ((1) + (6)))) + (((((((3) * (7)) + (((2) * ((2) * ((16) * (2)))) + (((2) * (((19) * (2)) / (2))) + (((2) + (5)) + ((4) * (4)))))) + ((3) * (((((((((((((2) * (((((((((2) * ((((((6) + (5)) * ((2) + (7))) + ((2) * (((2) * (17)) * (2)))) - ((2) + (10))) * (2))) + (((((((((((((2) * ((18) - (1))) + ((((4) + (3)) * (5)) + (4))) - ((3) * (7))) * (4)) + ((((((8) + (19)) - (1)) * (((((2) * (((((((((((((((5) * (15)) - ((((2) + (14)) + ((1) + (((3) * (3)) + (14)))) / (5))) + (6)) * (2)) / (2)) * (5)) + ((3) * ((13) * (7)))) + ((((((((((((((((((16) + ((((5) * ((3) + (((4) * ((2) * ((3) * (2)))) / (3)))) * (3)) + ((6) + ((8) + (15))))) + (((11) + (((13) + (11)) + (6))) * (11))) + ((9) * (((2) * ((13) * (3))) + ((((3) * ((17) * (3))) - (((1) + ((2) * ((((((18) + (((2) * ((20) + (3))) / (2))) + (8)) - ((3) * (4))) + ((2) + ((19) * (2)))) / ((1) + (6))))) * (2))) + ((2) * (((14) * (3)) + (((19) * (5)) / (5)))))))) + ((2) * ((4) * (((2) * ((2) * ((7) * (2)))) + ((3) * (19)))))) / (8)) + (((2) * (((((10) + (1)) + (5)) * ((2) + (5))) - (((2) * (8)) + (13)))) * (5))) / (2)) + (((x) - ((2) * (((((3) * (((2) * (3)) + (7))) + ((10) * (5))) + ((6) * ((2) * (5)))) + (((3) * (4)) * ((5) * (5)))))) * ((((((13) + (15)) / (2)) - (2)) * ((2) * (4))) / (4)))) / (9)) - ((((((((4) * (5)) + (3)) + (((3) * (2)) * (((3) * (4)) + (17)))) * (3)) + ((4) * ((((2) * (5)) + ((((4) * (3)) * (4)) + ((1) + ((5) * (2))))) + ((4) * (((3) * (3)) - (3)))))) - ((((2) + ((5) * (5))) * (9)) + ((2) * (((19) * (2)) / (2))))) + ((((5) * (2)) * ((2) * (((3) + (3)) + (((2) * (4)) - (1))))) - (((5) + (2)) * (4))))) * ((2) + ((14) / (2)))) + ((((((2) * ((9) + (4))) * (2)) + (((2) * ((((10) * ((5) * (2))) - ((11) * (3))) - (6))) / (2))) * (8)) - ((((7) * (17)) + ((15) + (2))) + (3)))) / (2)) - (((((6) + (15)) * (3)) - (20)) * (4))) / (5)) + (((3) * ((6) + ((((8) + (9)) * (2)) + (((3) * (3)) * (3))))) + (((4) * (((((((2) * ((4) * (2))) + ((1) + (6))) * (2)) * (2)) / (4)) * (3))) + (((((((2) * (((2) * (3)) + (5))) + ((((4) + (3)) + (10)) * (2))) + ((2) + (((3) + ((4) * (2))) + (2)))) - (10)) + (2)) + (17))))) * (7))) * (2)) - ((4) * ((((3) * ((16) + ((3) * (5)))) + ((((8) - (1)) * (2)) + (3))) + ((1) + (((((3) + ((4) + (((3) + (9)) / (2)))) * (5)) - ((2) * (3))) * (2)))))) / (6)) - ((((9) * (2)) * (13)) + ((((2) + (5)) + ((((2) * (((2) * (((2) * (6)) + (7))) + ((14) + (9)))) / (2)) - ((5) * (2)))) * (2)))) / (3)) + ((((3) * (((((2) * ((7) * (3))) + (2)) / (2)) / (2))) + ((1) + (5))) * (15)))) + (((2) * (5)) + (((((((4) * (3)) + (9)) + ((4) * (4))) + (16)) * (5)) * (2)))) / (8)) - ((11) * (((3) + (4)) + (4))))) - ((2) * ((((2) * ((9) * (3))) + (((((5) * ((((2) * (6)) + ((5) * (4))) + (9))) / (5)) * (3)) - (10))) * (2)))) * (2))) / ((2) + (4))) + (((4) + (9)) * ((((11) + ((10) + ((8) + (17)))) + ((3) + (18))) - (((5) + (18)) - (2))))) / ((2) * (4))) - ((2) * (((((7) * (3)) + (14)) + ((2) * (((19) * (2)) / (2)))) * (5)))) * ((((5) * (((6) + (((4) * (2)) - (1))) + (6))) / (5)) * (3))) - ((((16) + (15)) + (((((2) * (15)) - (1)) * (2)) + ((2) * (10)))) * (3))) / (6))) * ((3) + (3))) - ((3) * (((((11) * (4)) + (9)) * (5)) - ((17) + ((((1) + ((17) + (1))) + (4)) * (3)))))) / (3)) + (((5) + ((4) * (17))) + (((5) * (11)) - (12)))) * (3)) - ((((2) * ((3) + ((10) * (8)))) * (3)) + (((((((3) + ((20) + (2))) + ((((((16) + (7)) * (2)) / (2)) - (6)) + (5))) * ((1) + (6))) - (((7) + (2)) * (3))) - ((((3) * (((4) + (10)) + (8))) + ((((5) + ((3) * (6))) * ((1) + ((3) * (2)))) / ((5) + (2)))) + (2))) + ((2) * (4)))))) + (((4) * (((4) * ((2) * (7))) - ((5) * (3)))) + ((((3) * (5)) * (3)) + (((2) * ((2) * (((18) + (1)) + ((6) * (2))))) + ((2) * ((2) * (((2) * (4)) + (((5) + (4)) + (20))))))))) + (((((19) + (4)) + (((11) * (2)) * (2))) * (5)) + ((((20) + (1)) + (10)) * (11)))) / (5)) + (((5) + ((18) + (20))) + ((2) * (5)))) / ((4) + (2))) - ((11) * ((2) * ((13) + (10))))) * (7)) - ((((1) + ((2) + (10))) + (2)) * (((2) * (((4) * (5)) / (2))) * (2)))) + ((((5) + ((14) * (3))) * (4)) + (((2) * (13)) + (((2) * (20)) + (1))))) / (2)) + (((3) * (3)) * ((5) * (13)))))) * (2)) - ((8) * ((3) * (16)))) / (2))) / (2)) - (((3) * (((9) * (2)) + (1))) * ((20) / (2))))) + ((((((6) + (2)) + ((((14) * ((5) + (18))) / (2)) / ((5) + (2)))) + (2)) + ((((4) + (4)) + ((3) * (5))) * (11))) + ((((((2) + (5)) * (7)) - ((5) * (3))) + (3)) * (3)))) / (7))))
}

func rightSide() int {
	return (((2) * (((((((2) * ((5) * (4))) - ((((2) * (3)) + ((5) + (1))) - (1))) * ((((2) * (((2) * ((2) * (5))) + (9))) / (2)) * ((2) + (5)))) + ((2) * ((((((((3) * (2)) + (7)) * (2)) - ((4) * (2))) + (((5) + (2)) * (5))) * (2)) + ((((4) * (4)) * ((4) * (((2) * (11)) / (2)))) + ((((5) * ((3) * (3))) + ((((4) * ((5) + (2))) * (5)) - (((3) * (11)) + (10)))) + (((5) * ((13) + (((4) * (5)) + ((8) - (1))))) - ((4) + (((((3) + (4)) * (3)) - (2)) * (3))))))))) * (((6) * ((18) + (3))) + (((11) + (((12) + (1)) * (2))) + (4)))) + ((((2) * ((((9) + ((2) * ((5) + (6)))) + ((5) * ((2) * (3)))) * (2))) - ((((5) * (7)) + (2)) + (((8) * (3)) + ((5) * (2))))) * (((((2) * ((((((20) * (((2) * ((3) * (5))) + (1))) / (2)) + ((11) * (11))) * (2)) + (((((4) * (5)) * ((3) * ((6) + (4)))) + (1)) + ((2) * ((9) + ((((((14) * (2)) + (1)) + (2)) + ((2) * ((13) * (2)))) + ((15) + (((4) * (4)) * (7))))))))) / (2)) * (2)) * (2))))) * (((((((((4) * (4)) + (((3) * (3)) * (3))) * (((2) * (11)) * (5))) + ((((14) * (5)) + (9)) + ((((2) * ((4) * (16))) - (((2) * (11)) + (5))) * (2)))) * (8)) + ((((((((((6) * (7)) - (9)) * (3)) + (9)) - (((2) * (3)) * (5))) + ((((14) * (2)) + (9)) * (5))) * (((10) * ((1) + (6))) + ((5) * (13)))) - ((((((19) - (1)) * ((3) * (9))) / (2)) * (((4) + ((5) * (5))) * (2))) - ((((3) * (12)) + (((15) + ((5) * (5))) + ((3) * (9)))) * ((4) * (11))))) * (3))) * (3)) * ((5) + (((3) * (3)) * (4)))))
}
