package year2022day17

import (
	"fmt"
	"math"
	"strings"
)

type Vector2 struct {
	X int
	Y int
}

func (vector Vector2) String() string {
	return fmt.Sprintf("Vec{%d, %d}", vector.X, vector.Y)
}

func NewVector2(x int, y int) *Vector2 {
	return &Vector2{
		X: x,
		Y: y,
	}
}

type Shape struct {
	Points []*Vector2
	MaxX   int
	MinX   int

	MaxY int
	MinY int
}

func NewShape(points []*Vector2) *Shape {
	maxX := 0
	minX := math.MaxInt32

	maxY := 0
	minY := math.MaxInt32

	for _, point := range points {
		if point.X > maxX {
			maxX = point.X
		} else if point.X < minX {
			minX = point.X
		}

		if point.Y > maxY {
			maxY = point.Y
		} else if point.Y < minY {
			minY = point.Y
		}
	}

	return &Shape{
		Points: points,

		MaxX: maxX,
		MinX: minX,

		MaxY: maxY,
		MinY: minY,
	}
}

func (shape Shape) String() string {
	pointSb := strings.Builder{}
	for i := 0; i < len(shape.Points); i++ {
		pointSb.WriteString(shape.Points[i].String())

		if i == len(shape.Points)-1 {
			pointSb.WriteString(", ")
		}
	}

	return fmt.Sprintf("maxX:%d, minX:%d, maxY: %d, minY: %d, points: %s", shape.MaxX, shape.MinX, shape.MaxY, shape.MinY, pointSb.String())

}

type Rock struct {
	Position *Vector2
	Shape    *Shape
}

func NewRock(position *Vector2, points []*Vector2) *Rock {
	return &Rock{
		Position: position,
		Shape:    NewShape(points),
	}
}

func (rock Rock) String() string {
	return fmt.Sprintf("%s, %s", rock.Position.String(), rock.Shape.String())
}

const CHAMBER_WIDTH = 7
const ROCK_START_WIDTH = 2
const ROCK_START_HEIGHT = 3
const MIN_PATTERN_SIZE = 4
const MIN_NUMBER_REPEATS = 4

func HowTallIsTowerOfRocks(input string, numRocks int) int {
	jetDirections := strings.Split(input, "")
	defaultRocks := getDefaultRockShapes()

	chamberMap := make(map[string]bool)

	towerHeight := 0
	towerHeightChanges := []int{}
	jetDirectionIndex := 0
	for i := 0; i < numRocks; i++ {
		nextRock := NewRock(
			NewVector2(ROCK_START_WIDTH, towerHeight+ROCK_START_HEIGHT+1),
			defaultRocks[i%len(defaultRocks)],
		)

		for !isCollision(chamberMap, CHAMBER_WIDTH, nextRock.Position.X, nextRock.Position.Y, nextRock.Shape) {
			jetDirection := jetDirections[jetDirectionIndex%len(jetDirections)]

			if jetDirection == "<" {
				if !isCollision(chamberMap, CHAMBER_WIDTH, nextRock.Position.X-1, nextRock.Position.Y, nextRock.Shape) {
					nextRock.Position.X -= 1
				}
			} else if jetDirection == ">" {
				if !isCollision(chamberMap, CHAMBER_WIDTH, nextRock.Position.X+1, nextRock.Position.Y, nextRock.Shape) {
					nextRock.Position.X += 1
				}
			}

			jetDirectionIndex++

			// Attempt to move down
			if !isCollision(chamberMap, CHAMBER_WIDTH, nextRock.Position.X, nextRock.Position.Y-1, nextRock.Shape) {
				nextRock.Position.Y -= 1
			} else {
				break
			}
		}

		nextTowerHeight := towerHeight
		for _, point := range nextRock.Shape.Points {
			x := point.X + nextRock.Position.X
			y := point.Y + nextRock.Position.Y

			chamberMap[getCoordinatesKey(x, y)] = true

			if y > nextTowerHeight {
				nextTowerHeight = y
			}
		}

		towerHeightChanges = append(towerHeightChanges, nextTowerHeight-towerHeight)
		towerHeight = nextTowerHeight

		patternSize := getPatternSize(towerHeightChanges, MIN_PATTERN_SIZE, MIN_NUMBER_REPEATS)
		if patternSize != -1 {
			patternSumTowerHeightChange := 0
			for patternI := 0; patternI < patternSize; patternI++ {
				patternSumTowerHeightChange += towerHeightChanges[len(towerHeightChanges)-1-patternI]
			}

			patternIterations := (numRocks - i) / patternSize

			i += patternSize * patternIterations
			towerHeight += patternSumTowerHeightChange * patternIterations

			for ; i < numRocks; i++ {
				patternIndex := i % patternSize
				towerHeight += towerHeightChanges[len(towerHeightChanges)-patternSize+patternIndex]
			}

			break
		}
	}

	return towerHeight
}

func getPatternSize(towerHeightChanges []int, minPatternSize int, minNumberRepeats int) int {
	lenTowerHeightChanges := len(towerHeightChanges)

	if lenTowerHeightChanges < minPatternSize*(minNumberRepeats+3)+1 {
		return -1
	}

	for patternSize := lenTowerHeightChanges / (minNumberRepeats + 2); patternSize >= minPatternSize; patternSize-- {
		isPattern := true
		for patternI := 0; isPattern && patternI < patternSize; patternI++ {
			for repeatI := 0; isPattern && repeatI < minNumberRepeats; repeatI++ {
				if towerHeightChanges[lenTowerHeightChanges-patternSize+patternI] != towerHeightChanges[lenTowerHeightChanges-(repeatI+2)*patternSize+patternI] {
					isPattern = false
				}
			}
		}

		if isPattern {
			return patternSize
		}
	}

	return -1
}

func isCollision(chamberMap map[string]bool, chamberWidth int, x int, y int, shape *Shape) bool {

	// Convert to global coordinates
	maxX := shape.MaxX + x
	minX := shape.MinX + x
	minY := shape.MinY + y

	if maxX >= chamberWidth || minX < 0 {
		return true
	}

	if minY <= 0 {
		return true
	}

	for _, nextPoint := range shape.Points {
		nextX := nextPoint.X + x
		nextY := nextPoint.Y + y

		if chamberMap[getCoordinatesKey(nextX, nextY)] {
			return true
		}
	}

	return false
}

func getDefaultRockShapes() [][]*Vector2 {
	return [][]*Vector2{
		/* #### */
		{
			NewVector2(0, 0),
			NewVector2(1, 0),
			NewVector2(2, 0),
			NewVector2(3, 0),
		},
		/*
			.#.
			###
			.#.
		*/
		{
			NewVector2(1, 0),
			NewVector2(0, 1),
			NewVector2(1, 1),
			NewVector2(2, 1),
			NewVector2(1, 2),
		},
		/*
			..#
			..#
			###
		*/
		{
			NewVector2(0, 0),
			NewVector2(1, 0),
			NewVector2(2, 0),
			NewVector2(2, 1),
			NewVector2(2, 2),
		},
		/*
			#
			#
			#
			#
		*/
		{
			NewVector2(0, 0),
			NewVector2(0, 1),
			NewVector2(0, 2),
			NewVector2(0, 3),
		},
		/*
			##
			##
		*/
		{
			NewVector2(0, 0),
			NewVector2(1, 0),
			NewVector2(0, 1),
			NewVector2(1, 1),
		},
	}
}

func getCoordinatesKey(x int, y int) string {
	return fmt.Sprintf("%d,%d", x, y)
}

func MaxInt(a int, b int) int {
	if a < b {
		return b
	} else {
		return a
	}
}

func MinInt(a int, b int) int {
	if a > b {
		return b
	} else {
		return a
	}
}
