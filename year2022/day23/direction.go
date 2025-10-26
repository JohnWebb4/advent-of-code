package year2022day23

import "fmt"

type Direction int
const NorthDirection Direction = 0
const SouthDirection Direction = 1
const EastDirection Direction = 2
const WestDirection Direction = 3

func (direction Direction)String() string {
	if direction == 0 {
		return "North"
	} else if direction == 1 {
		return "South"
	} else if direction == 2 {
		return "West"
	} else if direction == 3 {
		return "East"
	} else {
		return fmt.Sprint(int(direction))
	}
}