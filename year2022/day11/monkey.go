package year2022day11

type Monkey struct {
	items          []int
	operation      *Operation
	test           int
	testTrueIndex  int
	testFalseIndex int
	itemsInspected int
}

func NewMonkey(items []int, operation *Operation, test int, testTrueIndex int, testFalseIndex int, itemsInspected int) *Monkey {
	return &Monkey{
		items:          items,
		operation:      operation,
		test:           test,
		testTrueIndex:  testTrueIndex,
		testFalseIndex: testFalseIndex,
		itemsInspected: itemsInspected,
	}
}
