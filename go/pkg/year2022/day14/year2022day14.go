package year2022day14

import (
	"math"
	"strconv"
	"strings"
)

func GetNumSandBeforeRest(input string) (int, error) {
	sandMap, err := ReadSandMap(input)
	if err != nil {
		return 0, err
	}

	sandCount := 0
	for sandCount < 1_000 {
		xSand, ySand, path := SettleSand(500, 0, sandMap)

		if ySand >= sandMap.Bounds.Y+sandMap.Bounds.Height {
			sandMapString := sandMap.String()

			// log.Printf("Break! %d %d", xSand, ySand)

			for _, v := range path {
				index := (v.X - sandMap.Bounds.X + 1) + (v.Y)*(sandMap.Bounds.Width+4)

				if (v.X-sandMap.Bounds.X) >= -1 && index > 0 && index < len(sandMapString) {
					sandMapString = sandMapString[:index] + "~" + sandMapString[index+1:]
				}
			}

			// log.Printf("Sand %d\n%s\n", sandCount, sandMapString)
			break
		} else {
			sandMap.SetCell(xSand, ySand, SandCell)
		}

		sandCount++
	}

	return sandCount, nil
}

func GetNumSandBeforeClogSource(input string) (int, error) {
	sandMap, err := ReadSandMap(input)
	if err != nil {
		return 0, err
	}

	// Add floor
	for x := sandMap.Bounds.X - 20_000; x < sandMap.Bounds.X+sandMap.Bounds.Width+20_000; x++ {
		sandMap.SetCell(x, sandMap.Bounds.Y+sandMap.Bounds.Height+2, RockCell)
	}

	sandCount := 0
	for sandCount < 100_000 {
		xSand, ySand, path := SettleSand(500, 0, sandMap)

		if xSand == 500 && ySand == 0 {
			sandMapString := sandMap.String()

			// log.Printf("Break! %d %d", xSand, ySand)

			for _, v := range path {
				index := (v.X - sandMap.Bounds.X + 1) + (v.Y)*(sandMap.Bounds.Width+4)

				if (v.X-sandMap.Bounds.X) >= -1 && index > 0 && index < len(sandMapString) {
					sandMapString = sandMapString[:index] + "~" + sandMapString[index+1:]
				}
			}

			// log.Printf("Sand %d\n%s\n", sandCount, sandMapString)
			sandCount++
			break
		} else {
			sandMap.SetCell(xSand, ySand, SandCell)
		}

		sandCount++
	}

	return sandCount, nil
}

func SettleSand(xStart int, yStart int, sandMap *SandMap) (int, int, []*Vector) {
	x := xStart
	y := yStart
	path := []*Vector{}

	for y <= sandMap.Bounds.Y+sandMap.Bounds.Height+3 {
		if sandMap.GetCell(x, y+1) == AirCell {
			y++

			path = append(path, NewVector(x, y))
		} else if sandMap.GetCell(x-1, y+1) == AirCell {
			x--
			y++

			path = append(path, NewVector(x, y))
		} else if sandMap.GetCell(x+1, y+1) == AirCell {
			x++
			y++

			path = append(path, NewVector(x, y))
		} else {
			// Settled
			break
		}
	}

	return x, y, path
}

func ReadSandMap(input string) (*SandMap, error) {
	innerSandMap := make(map[string]Cell)
	xMax := math.MinInt32
	xMin := math.MaxInt32

	yMax := math.MinInt32
	yMin := math.MaxInt32

	instructions := strings.Split(input, "\n")

	for _, instruction := range instructions {
		coordinates := strings.Split(instruction, " -> ")

		startVector, err := readVector(coordinates[0])
		if err != nil {
			return nil, err
		}

		for i := 1; i < len(coordinates); i++ {
			currentVector, err := readVector(coordinates[i])
			if err != nil {
				return nil, err
			}

			if currentVector.X != startVector.X {
				if currentVector.Y > yMax {
					yMax = currentVector.Y
				}
				if currentVector.Y < yMin {
					yMin = currentVector.Y
				}

				magnitude := 1
				if currentVector.X < startVector.X {
					magnitude = -1
				}

				for x := startVector.X; x != currentVector.X+magnitude; x += magnitude {
					if x > xMax {
						xMax = x
					}
					if x < xMin {
						xMin = x
					}

					innerSandMap[getSandMapKey(x, startVector.Y)] = RockCell
				}
			} else {
				if currentVector.X > xMax {
					xMax = currentVector.X
				}
				if currentVector.X < xMin {
					xMin = currentVector.X
				}

				magnitude := 1
				if currentVector.Y < startVector.Y {
					magnitude = -1
				}

				for y := startVector.Y; y != currentVector.Y+magnitude; y += magnitude {
					if y > yMax {
						yMax = y
					}
					if y < yMin {
						yMin = y
					}

					innerSandMap[getSandMapKey(startVector.X, y)] = RockCell
				}
			}

			startVector = currentVector
		}
	}

	bounds := NewRectangle(xMin, yMin, xMax-xMin, yMax-yMin)

	sandMap := NewSandMap(innerSandMap, bounds)

	return sandMap, nil
}

func readVector(input string) (*Vector, error) {
	coordinateStrings := strings.Split(input, ",")

	x, err := strconv.Atoi(coordinateStrings[0])
	if err != nil {
		return nil, err
	}

	y, err := strconv.Atoi(coordinateStrings[1])
	if err != nil {
		return nil, err
	}

	return NewVector(x, y), nil
}
