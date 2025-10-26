package year2022day14

import "fmt"

type Vector struct {
	X int
	Y int
}

func NewVector(x int, y int) *Vector {
	return &Vector{
		X: x,
		Y: y,
	}
}

func (v Vector)String() string {
	return fmt.Sprintf("Vector{ %d,%d }", v.X, v.Y)
}