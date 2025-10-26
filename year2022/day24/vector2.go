package year2022day24

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

func (vector Vector) Copy() *Vector {
	return NewVector(vector.X, vector.Y)
}

func (vector Vector) String() string {
	return fmt.Sprintf("Vector{X:%d, Y:%d}", vector.X, vector.Y)
}

func (vector Vector) Equals(aVector *Vector) bool {
	return vector.X == aVector.X && vector.Y == aVector.Y
}
