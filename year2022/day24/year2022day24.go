package year2022day24

import (
	"log"
	"math"
	"strings"

	"github.com/emirpasic/gods/queues/priorityqueue"
)

func GetFewestMinutesToAvoidBlizzard(input string) int {
	startBlizzMap, startLocation, endLocation := getInput(input)

	fewestSteps, _ := FindFewestMinutesToTravel(startLocation, endLocation, startBlizzMap)

	return fewestSteps
}

func GetFewestMinutesToAvoidBlizzardThereAndBack(input string) int {
	startBlizzMap, startLocation, endLocation := getInput(input)

	fewestStepsThere, backBlizzMap := FindFewestMinutesToTravel(startLocation, endLocation, startBlizzMap)
	fewestStepsBack, returnBlizzMap := FindFewestMinutesToTravel(endLocation, startLocation, backBlizzMap)
	fewestStepsReturn, _ := FindFewestMinutesToTravel(startLocation, endLocation, returnBlizzMap)

	return fewestStepsThere + fewestStepsBack + fewestStepsReturn
}

func FindFewestMinutesToTravel(startLocation *Vector, endLocation *Vector, startBlizzMap *Map) (int, *Map) {
	blizzMapAtTime := []*Map{startBlizzMap}
	initialPath := NewPath(startLocation, endLocation, 0)
	minPathQueue := priorityqueue.NewWith(byPriority)
	minPathQueue.Enqueue(initialPath)
	fewestMinutes := math.MaxInt32
	hasSeen := make(map[string]bool)

	count := 0
	for !minPathQueue.Empty() {
		item, ok := minPathQueue.Dequeue()
		if ok {
			path := item.(*Path)

			if path.Minutes < fewestMinutes {
				count++

				if path.Position.Equals(path.EndPosition) {
					// PrintTimeline(fmt.Sprintf("Min path there in %d minutes after %d iterations", path.Minutes, tempCount), path, blizzMapAtTime, startLocation)

					fewestMinutes = path.Minutes
				} else if path.Minutes < fewestMinutes-2 {
					// Consider all possibilities that can do better
					for len(blizzMapAtTime) <= path.Minutes+1 {
						blizzMapAtTime = append(blizzMapAtTime, UpdateMap(blizzMapAtTime[len(blizzMapAtTime)-1]))
					}
					nextBlizzMap := blizzMapAtTime[path.Minutes+1]

					// Wait
					{
						waitPosition := path.Position.Copy()

						if !isCollision(nextBlizzMap, waitPosition) {
							waitPath := NewPath(waitPosition, path.EndPosition.Copy(), path.Minutes+1)

							// TEMP
							waitPath.tempPath = path.tempPath + "W"

							if !hasSeen[waitPath.Key()] {
								minPathQueue.Enqueue(waitPath)
								hasSeen[waitPath.Key()] = true
							}
						}
					}

					{
						upPosition := NewVector(path.Position.X, path.Position.Y-1)
						if !isCollision(nextBlizzMap, upPosition) {
							upPath := NewPath(upPosition, path.EndPosition.Copy(), path.Minutes+1)

							// TEMP
							upPath.tempPath = path.tempPath + "U"

							if !hasSeen[upPath.Key()] {
								minPathQueue.Enqueue(upPath)
								hasSeen[upPath.Key()] = true
							}
						}
					}

					{
						rightPosition := NewVector(path.Position.X+1, path.Position.Y)
						if !isCollision(nextBlizzMap, rightPosition) {
							rightPath := NewPath(rightPosition, path.EndPosition.Copy(), path.Minutes+1)

							// TEMP
							rightPath.tempPath = path.tempPath + "R"

							if !hasSeen[rightPath.Key()] {
								minPathQueue.Enqueue(rightPath)
								hasSeen[rightPath.Key()] = true
							}
						}
					}

					{
						downPosition := NewVector(path.Position.X, path.Position.Y+1)
						if !isCollision(nextBlizzMap, downPosition) {
							downPath := NewPath(downPosition, path.EndPosition.Copy(), path.Minutes+1)

							// TEMP
							downPath.tempPath = path.tempPath + "D"

							if !hasSeen[downPath.Key()] {
								minPathQueue.Enqueue(downPath)
								hasSeen[downPath.Key()] = true
							}
						}
					}

					{
						leftPosition := NewVector(path.Position.X-1, path.Position.Y)
						if !isCollision(nextBlizzMap, leftPosition) {
							leftPath := NewPath(leftPosition, path.EndPosition.Copy(), path.Minutes+1)

							// TEMP
							leftPath.tempPath = path.tempPath + "L"

							if !hasSeen[leftPath.Key()] {
								minPathQueue.Enqueue(leftPath)
								hasSeen[leftPath.Key()] = true
							}
						}
					}
				}
			}
		}
	}

	return fewestMinutes, blizzMapAtTime[fewestMinutes]
}

func UpdateMap(blizzMap *Map) *Map {
	nextBlizzards := make([]*Blizzard, len(blizzMap.Blizzards))
	for blizzardI, blizzard := range blizzMap.Blizzards {
		nextBlizzardX := blizzard.X + blizzard.XDiff
		nextBlizzardY := blizzard.Y + blizzard.YDiff

		if nextBlizzardX <= blizzMap.Bounds.X {
			nextBlizzardX = blizzMap.Bounds.X + blizzMap.Bounds.Width - 2
		}
		if nextBlizzardX >= blizzMap.Bounds.X+blizzMap.Bounds.Width-1 {
			nextBlizzardX = blizzMap.Bounds.X + 1
		}
		if nextBlizzardY <= blizzMap.Bounds.Y {
			nextBlizzardY = blizzMap.Bounds.Y + blizzMap.Bounds.Height - 2
		}
		if nextBlizzardY >= blizzMap.Bounds.Y+blizzMap.Bounds.Height-1 {
			nextBlizzardY = blizzMap.Bounds.Y + 1
		}

		nextBlizzard := NewBlizzard(nextBlizzardX, nextBlizzardY, blizzard.XDiff, blizzard.YDiff)

		nextBlizzards[blizzardI] = nextBlizzard
	}

	nextWalls := make([]*Vector, len(blizzMap.Walls))
	for wallI, wall := range blizzMap.Walls {
		nextWalls[wallI] = wall.Copy()
	}

	return NewMap(nextBlizzards, nextWalls)
}

func getInput(input string) (*Map, *Vector, *Vector) {
	rows := strings.Split(input, "\n")

	var startLocation *Vector
	walls := []*Vector{}
	blizzards := []*Blizzard{}

	for rowI, row := range rows {
		for columnI, cell := range row {
			if cell == '.' && startLocation == nil {
				startLocation = NewVector(columnI, rowI)
			} else if cell == '#' {
				walls = append(walls, NewVector(columnI, rowI))
			} else if cell == UpDirection {
				blizzards = append(blizzards, NewBlizzard(columnI, rowI, 0, -1))
			} else if cell == RightDirection {
				blizzards = append(blizzards, NewBlizzard(columnI, rowI, 1, 0))
			} else if cell == DownDirection {
				blizzards = append(blizzards, NewBlizzard(columnI, rowI, 0, 1))
			} else if cell == LeftDirection {
				blizzards = append(blizzards, NewBlizzard(columnI, rowI, -1, 0))
			}
		}
	}

	// Find end
	var endLocation *Vector
	endRow := len(rows) - 1
	for x := len(rows[endRow]) - 1; x >= 0; x-- {
		if rows[endRow][x] == '.' {
			endLocation = NewVector(x, endRow)
			break
		}
	}

	return NewMap(blizzards, walls), startLocation, endLocation
}

func isCollision(nextBlizzMap *Map, position *Vector) bool {
	for _, blizzard := range nextBlizzMap.Blizzards {
		if blizzard.X == position.X && blizzard.Y == position.Y {
			return true
		}
	}

	for _, wall := range nextBlizzMap.Walls {
		if wall.Equals(position) {
			return true
		}
	}

	return !nextBlizzMap.Bounds.Contains(position)
}

func PrintTimeline(name string, path *Path, blizzMapAtTime []*Map, startLocation *Vector) {
	blizzMapStrings := make([]string, len(blizzMapAtTime))
	tempPath := path.tempPath
	location := startLocation.Copy()
	for blizzMapI, blizzMap := range blizzMapAtTime {
		if blizzMapI != 0 && blizzMapI < len(tempPath) {
			direction := tempPath[blizzMapI-1]
			if direction == 'U' {
				location.Y--
			} else if direction == 'R' {
				location.X++
			} else if direction == 'D' {
				location.Y++
			} else if direction == 'L' {
				location.X--
			}
		}

		blizzString := blizzMap.String()
		charLocation := location.Y*(blizzMap.Bounds.Width+1) + location.X
		blizzString = blizzString[0:charLocation] + "E" + blizzString[charLocation+1:]
		blizzMapStrings[blizzMapI] = blizzString
	}

	log.Printf("Timeline %s\n%s", name, strings.Join(blizzMapStrings, "\n"))
}

func PrintPathAtTime(name string, path *Path, blizzMap *Map) {
	blizzString := blizzMap.String()
	charLocation := path.Position.Y*(blizzMap.Bounds.Width+1) + path.Position.X
	blizzString = blizzString[0:charLocation] + "E" + blizzString[charLocation+1:]

	log.Printf("Path at Time %s\n%s", name, blizzString)
}
