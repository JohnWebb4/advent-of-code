package year2022day24

import (
	"fmt"

	"github.com/emirpasic/gods/utils"
)

type Path struct {
	Position    *Vector
	EndPosition *Vector
	Minutes     int
	priority    int

	tempPath string
}

func NewPath(position *Vector, endPosition *Vector, minutes int) *Path {
	return &Path{
		Position:    position,
		EndPosition: endPosition,
		Minutes:     minutes,
		priority:    getPriority(position, endPosition, minutes),
	}
}

func (path Path) String() string {
	return fmt.Sprintf("Path{Position:%s, End %s, Minutes %d, Priority: %d, tempPath: '%s'}",
		path.Position,
		path.EndPosition,
		path.Minutes,
		path.priority,
		path.tempPath)
}

func (path Path) Key() string {
	return fmt.Sprintf("%s,%d", path.Position.String(), path.Minutes)
}

func getPriority(position *Vector, endPosition *Vector, minutes int) int {
	taxiDistance := AbsInt(endPosition.X-position.X) + AbsInt(endPosition.Y-position.Y)

	// Multipliers are arbitrary tuning
	return 0 - 3*taxiDistance - minutes
}

func byPriority(a interface{}, b interface{}) int {
	priorityA := a.(*Path).priority
	priorityB := b.(*Path).priority

	return -utils.IntComparator(priorityA, priorityB)
}
