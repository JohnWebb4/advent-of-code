package year2022day12

import "fmt"

type Path struct {
	route []*Point
	cost  int
	index int
}

func NewPath(route []*Point) *Path {
	steps := len(route)

	remaining := 0
	if steps > 0 {
		lastPoint := route[steps-1]
		remaining = 27 - lastPoint.height
	}

	return &Path{
		route: route,
		cost:  5*steps + remaining,
	}
}

func (path Path) LastPoint() Point {
	steps := path.Steps()

	return *path.route[steps-1]
}

func (path Path) Steps() int {
	return len(path.route)
}

func (path Path) ToString() string {
	routeString := ""

	for _, point := range path.route {
		routeString += point.ToString()
	}

	return fmt.Sprintf("route:%s,cost:%d,index:%d", routeString, path.cost, path.index)
}
