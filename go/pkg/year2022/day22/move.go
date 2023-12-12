package year2022day22

func Move(monkeyMap *MonkeyMap, player *Player, instruction *Instruction) *Player {
	x := player.X
	y := player.Y
	direction := player.Direction

	if instruction.InstructionType == TurnInstructionType {
		direction += Direction(instruction.Amount)

		// Handle negative numbers
		direction = (4 - (-direction % 4)) % 4
	} else if instruction.InstructionType == MoveINstructionType {
		for i := 0; i < instruction.Amount; i++ {
			xDiff := 0
			yDiff := 0

			if direction == UpDirection {
				xDiff = 0
				yDiff = -1
			} else if direction == RigthDirection {
				xDiff = 1
				yDiff = 0
			} else if direction == DownDirection {
				xDiff = 0
				yDiff = 1
			} else if direction == LeftDirection {
				xDiff = -1
				yDiff = 0
			}

			nextX := x + xDiff
			nextY := y + yDiff
			nextCell := monkeyMap.GetCell(nextX, nextY)

			if nextCell == RockCell {
				// Do not move
				break
			} else if nextCell == AirCell {
				x = nextX
				y = nextY
			} else if nextCell == VoidCell {
				// Check telport
				teleport := monkeyMap.teleportMap[getTeleportKey(nextX, nextY, direction)]

				if teleport != nil {
					// Teleport
					x = teleport.X
					y = teleport.Y
					direction += Direction(teleport.TurnAmount)
					direction = (4 - (-direction % 4)) % 4
				}
			}
		}
	}

	return NewPlayer(x, y, direction)
}
