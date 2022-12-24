package year2022day11

type Operator string

const (
	ADD      Operator = "+"
	MULTIPLY Operator = "*"
)

type Operation struct {
	operator Operator
	amount   string
}

func NewOperation(operator Operator, amount string) *Operation {
	return &Operation{
		operator: operator,
		amount:   amount,
	}
}
