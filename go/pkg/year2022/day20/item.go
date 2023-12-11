package year2022day20

import "fmt"

type Item struct {
	Value int
}

func (item Item) String() string {
	return fmt.Sprintf("Item{ %d }", item.Value)
}
