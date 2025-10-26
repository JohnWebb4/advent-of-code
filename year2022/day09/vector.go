package year2022day09

import (
	"fmt"
	"math"
)

type Vector struct {
	x int
	y int
}

func NewVector(x int, y int) Vector {
	return Vector{
		x: x,
		y: y,
	}
}

func (vector Vector) Equals(newVector Vector) bool {
	return (vector.x == newVector.x) && (vector.y == newVector.y)
}

func (vector Vector) Add(newVector Vector) Vector {
	return NewVector(
		vector.x+newVector.x,
		vector.y+newVector.y,
	)
}

func (vector Vector) Subtract(newVector Vector) Vector {
	return NewVector(
		vector.x-newVector.x,
		vector.y-newVector.y,
	)
}

func (vector Vector) Length() float64 {
	return math.Sqrt(float64(vector.x*vector.x) + float64(vector.y*vector.y))
}

func (vector Vector) ToString() string {
	return fmt.Sprintf("x=%d,y=%d", vector.x, vector.y)
}
