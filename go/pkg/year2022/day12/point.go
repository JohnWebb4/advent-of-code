package year2022day12

import "fmt"

type Point struct {
	x      int
	y      int
	height int
}

func NewPoint(x int, y int, height int) *Point {
	return &Point{
		x:      x,
		y:      y,
		height: height,
	}
}

func (p Point) Equals(point Point) bool {
	return p.x == point.x && p.y == point.y && p.height == point.height
}

func (p Point) ToString() string {
	return fmt.Sprintf("x:%d,y:%d,height:%d;", p.x, p.y, p.height)
}
