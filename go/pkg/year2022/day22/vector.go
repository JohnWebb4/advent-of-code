package year2022day22

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

func (vec Vector) String() string {
	return fmt.Sprintf("Vector{x:%d, y:%d}", vec.X, vec.Y)
}
