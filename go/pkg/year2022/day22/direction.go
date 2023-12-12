package year2022day22

type Direction int

const RigthDirection Direction = 0
const DownDirection Direction = 1
const LeftDirection Direction = 2
const UpDirection Direction = 3

func (direction Direction) String() string {
	if direction == UpDirection {
		return "Up"
	} else if direction == RigthDirection {
		return "Right"
	} else if direction == DownDirection {
		return "Down"
	} else if direction == LeftDirection {
		return "Left"
	}

	return "Unknown"
}
