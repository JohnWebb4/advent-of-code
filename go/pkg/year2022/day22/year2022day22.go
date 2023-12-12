package year2022day22

import (
	"fmt"
	"regexp"
	"strconv"
	"strings"
)

var instructionRegex = regexp.MustCompile(`(\d+|\w)`)

func GetFinalPassword(input string) (int, error) {
	monkeyMap, instructions, err := readInput(input)

	if err != nil {
		return 0, err
	}

	player, err := getPlayer(monkeyMap)

	if err != nil {
		return 0, err
	}

	monkeyMap.teleportMap = getTeleportMap(monkeyMap)

	for _, instruction := range instructions {
		player = Move(monkeyMap, player, instruction)
	}

	return 1_000*(player.Y+1) + 4*(player.X+1) + int(player.Direction), nil
}

func GetFinalPasswordCube(input string) (int, error) {
	monkeyMap, instructions, err := readInput(input)

	if err != nil {
		return 0, err
	}

	player, err := getPlayer(monkeyMap)

	if err != nil {
		return 0, err
	}

	monkeyMap.teleportMap, err = getTeleportMapCube(monkeyMap)

	if err != nil {
		return 0, err
	}

	for _, instruction := range instructions {
		player = Move(monkeyMap, player, instruction)
	}

	return 1_000*(player.Y+1) + 4*(player.X+1) + int(player.Direction), nil
}

func SPrintMap(monkeyMap *MonkeyMap, player *Player) string {
	sb := strings.Builder{}

	for y := monkeyMap.yMin - 1; y <= monkeyMap.yMax+1; y++ {
		for x := monkeyMap.xMin - 1; x <= monkeyMap.xMax+1; x++ {
			if player.X == x && player.Y == y {
				if player.Direction == UpDirection {
					sb.WriteRune('^')
				} else if player.Direction == RigthDirection {
					sb.WriteRune('>')
				} else if player.Direction == DownDirection {
					sb.WriteRune('V')
				} else if player.Direction == LeftDirection {
					sb.WriteRune('<')
				}
			} else if monkeyMap.teleportMap[getTeleportKey(x, y, UpDirection)] != nil {
				sb.WriteRune('U')
			} else if monkeyMap.teleportMap[getTeleportKey(x, y, RigthDirection)] != nil {
				sb.WriteRune('R')
			} else if monkeyMap.teleportMap[getTeleportKey(x, y, DownDirection)] != nil {
				sb.WriteRune('D')
			} else if monkeyMap.teleportMap[getTeleportKey(x, y, LeftDirection)] != nil {
				sb.WriteRune('L')
			} else if monkeyMap.GetCell(x, y) == 0 {
				sb.WriteRune(' ')
			} else {
				sb.WriteRune(rune(monkeyMap.GetCell(x, y)))
			}
		}

		sb.WriteRune('\n')
	}

	return sb.String()

}

func readInput(input string) (*MonkeyMap, []*Instruction, error) {
	parts := strings.Split(input, "\n\n")
	monkeyMap := readMonkeyMap(parts[0])
	instructions, err := readInstructions(parts[1])

	if err != nil {
		return nil, nil, err
	}

	return monkeyMap, instructions, nil
}

func getTeleportMap(monkeyMap *MonkeyMap) map[string]*Teleport {
	teleportMap := make(map[string]*Teleport)

	// Iterate through map
	for y := monkeyMap.yMin - 1; y <= monkeyMap.yMax+1; y++ {
		for x := monkeyMap.xMin - 1; x <= monkeyMap.yMax+1; x++ {
			cell := monkeyMap.GetCell(x, y)

			if cell == VoidCell {
				// Ordered so index corresponds to direction cuz lazy
				diffDirections := []Vector{
					*NewVector(-1, 0), // When moving right to void
					*NewVector(0, -1), // When moving down to void
					*NewVector(1, 0),  // When moving left to void
					*NewVector(0, 1),  // When moving up to void
				}

				for diffDirectionI, diffDirection := range diffDirections {
					direction := Direction(diffDirectionI)
					xDiff := diffDirection.X
					yDiff := diffDirection.Y

					if monkeyMap.GetCell(x+xDiff, y+yDiff) != VoidCell {
						nextX := x + xDiff
						nextY := y + yDiff

						for nextX >= monkeyMap.xMin && nextX <= monkeyMap.xMax && nextY >= monkeyMap.yMin && nextY <= monkeyMap.yMax && monkeyMap.GetCell(nextX+xDiff, nextY+yDiff) != VoidCell {
							nextX += xDiff
							nextY += yDiff
						}

						if monkeyMap.GetCell(nextX, nextY) == AirCell {
							// Set teleport
							teleport := newTeleport(nextX, nextY, 0)

							teleportMap[getTeleportKey(x, y, direction)] = teleport
						}
					}

				}

			}
		}
	}

	return teleportMap
}

func getTeleportMapCube(monkeyMap *MonkeyMap) (map[string]*Teleport, error) {
	faces, err := findAllFaces(monkeyMap)

	if err != nil {
		return nil, err
	}

	cube, err := solveCube(faces)

	if err != nil {
		return nil, err
	}

	teleportMap := make(map[string]*Teleport)
	cubeFaces := cube.GetFaces()
	faceMap := make(map[string]*Face)

	for _, face := range cubeFaces {
		faceMap[face.Id] = face
	}

	for _, face := range cubeFaces {
		teleportMap = setTeleportMapFace(monkeyMap, faceMap, face, teleportMap)
	}

	return teleportMap, nil
}

func SPrintCube(monkeyMap *MonkeyMap, cube *Cube) string {
	sb := strings.Builder{}

	cellMap := make(map[string]rune)

	faces := [][]interface{}{
		{cube.Top, 'T'},
		{cube.Bottom, 'B'},
		{cube.Left, 'L'},
		{cube.Right, 'R'},
		{cube.Forward, 'F'},
		{cube.Backward, 'A'},
	}

	for _, entry := range faces {
		face := entry[0].(*Face)
		value := entry[1].(rune)

		for y := face.Y; y < face.Y+face.Size; y++ {
			for x := face.X; x < face.X+face.Size; x++ {
				cellMap[getMapKey(x, y)] = value
			}
		}
	}

	for y := monkeyMap.yMin - 1; y <= monkeyMap.yMax+1; y++ {
		for x := monkeyMap.xMin - 1; x <= monkeyMap.xMax+1; x++ {
			cell := cellMap[getMapKey(x, y)]

			if cell == 0 {
				sb.WriteRune(' ')
			} else {
				sb.WriteRune(cell)
			}
		}

		sb.WriteRune('\n')
	}

	return sb.String()
}

func setTeleportMapFace(monkeyMap *MonkeyMap, faceMap map[string]*Face, face *Face, teleportMap map[string]*Teleport) map[string]*Teleport {
	for sizeI := 0; sizeI < face.Size; sizeI++ {
		xTop := face.X + sizeI
		yTop := face.Y - 1

		teleportMap = setTeleportEdge(monkeyMap, faceMap, face, UpDirection, xTop, yTop, sizeI, teleportMap)

		xRight := face.X + face.Size
		yRight := face.Y + sizeI

		teleportMap = setTeleportEdge(monkeyMap, faceMap, face, RigthDirection, xRight, yRight, sizeI, teleportMap)

		xBott := face.X + sizeI
		yBott := face.Y + face.Size

		teleportMap = setTeleportEdge(monkeyMap, faceMap, face, DownDirection, xBott, yBott, sizeI, teleportMap)

		xLeft := face.X - 1
		yLeft := face.Y + sizeI

		teleportMap = setTeleportEdge(monkeyMap, faceMap, face, LeftDirection, xLeft, yLeft, sizeI, teleportMap)
	}

	return teleportMap
}

func setTeleportEdge(monkeyMap *MonkeyMap, faceMap map[string]*Face, face *Face, currentDirection Direction, x int, y int, sizeI int, teleportMap map[string]*Teleport) map[string]*Teleport {
	cell := monkeyMap.GetCell(x, y)

	if cell == VoidCell {
		var nextFace *Face

		if currentDirection == UpDirection {
			nextFace = faceMap[face.Top.Id]
		} else if currentDirection == RigthDirection {
			nextFace = faceMap[face.Right.Id]
		} else if currentDirection == DownDirection {
			nextFace = faceMap[face.Bottom.Id]
		} else if currentDirection == LeftDirection {
			nextFace = faceMap[face.Left.Id]
		}

		if nextFace.Top.Id == face.Id {
			nextFaceX := nextFace.X + sizeI
			if currentDirection == RigthDirection || currentDirection == UpDirection {
				nextFaceX = nextFace.X + nextFace.Size - sizeI - 1
			}

			nextFaceY := nextFace.Y

			teleportMap = setTeleportCell(monkeyMap, x, y, nextFaceX, nextFaceY, currentDirection, DownDirection, teleportMap)
		} else if nextFace.Right.Id == face.Id {
			nextFaceX := nextFace.X + nextFace.Size - 1

			nextFaceY := nextFace.Y + sizeI
			if currentDirection == UpDirection || currentDirection == RigthDirection {
				nextFaceY = nextFace.Y + nextFace.Size - sizeI - 1
			}

			teleportMap = setTeleportCell(monkeyMap, x, y, nextFaceX, nextFaceY, currentDirection, LeftDirection, teleportMap)
		} else if nextFace.Bottom.Id == face.Id {
			nextFaceX := nextFace.X + sizeI
			if currentDirection == DownDirection || currentDirection == LeftDirection {
				nextFaceX = nextFace.X + nextFace.Size - sizeI - 1
			}

			nextFaceY := nextFace.Y + nextFace.Size - 1

			teleportMap = setTeleportCell(monkeyMap, x, y, nextFaceX, nextFaceY, currentDirection, UpDirection, teleportMap)
		} else if nextFace.Left.Id == face.Id {
			nextFaceX := nextFace.X

			nextFaceY := nextFace.Y + sizeI
			if currentDirection == LeftDirection || currentDirection == DownDirection {
				nextFaceY = nextFace.Y + nextFace.Size - sizeI - 1
			}

			teleportMap = setTeleportCell(monkeyMap, x, y, nextFaceX, nextFaceY, currentDirection, RigthDirection, teleportMap)
		}
	}

	return teleportMap
}

func setTeleportCell(monkeyMap *MonkeyMap, x int, y int, nextFaceX int, nextFaceY int, currentDirection Direction, nextDirection Direction, teleportMap map[string]*Teleport) map[string]*Teleport {
	nextCell := monkeyMap.GetCell(nextFaceX, nextFaceY)

	if nextCell == AirCell {
		turnAmount := int(nextDirection) - int(currentDirection)
		turnAmount = (4 - (-turnAmount % 4)) % 4

		teleportMap[getTeleportKey(x, y, currentDirection)] = newTeleport(nextFaceX, nextFaceY, turnAmount)
	}

	return teleportMap
}

func getPlayer(monkeyMap *MonkeyMap) (*Player, error) {
	for y := monkeyMap.yMin; y <= monkeyMap.yMax; y++ {
		for x := monkeyMap.xMin; x <= monkeyMap.xMax; x++ {
			if monkeyMap.GetCell(x, y) == AirCell {
				return NewPlayer(x, y, RigthDirection), nil
			}
		}
	}

	return nil, fmt.Errorf("failed to find starting position")
}

func readMonkeyMap(input string) *MonkeyMap {
	mapRows := strings.Split(input, "\n")
	monkeyMap := NewMonkeyMap()

	for rowI, mapRow := range mapRows {
		for columnI, cellRune := range mapRow {
			if cellRune != ' ' {
				monkeyMap.SetCell(columnI, rowI, Cell(cellRune))
			}
		}
	}

	return monkeyMap
}

func readInstructions(input string) ([]*Instruction, error) {
	instructionGroups := instructionRegex.FindAllStringSubmatch(input, -1)

	instructions := make([]*Instruction, len(instructionGroups))
	for instructionI, instructionString := range instructionGroups {
		if instructionString[0] == "R" {
			instructions[instructionI] = NewInstruction(TurnInstructionType, 1)
		} else if instructionString[0] == "L" {
			instructions[instructionI] = NewInstruction(TurnInstructionType, -1)
		} else {
			value, err := strconv.Atoi(instructionString[0])

			if err != nil {
				return nil, err
			}

			instructions[instructionI] = NewInstruction(MoveINstructionType, value)
		}
	}

	return instructions, nil
}
