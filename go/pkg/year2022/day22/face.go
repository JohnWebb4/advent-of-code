package year2022day22

import "fmt"

type Face struct {
	Id     string
	Top    *Face
	Right  *Face
	Bottom *Face
	Left   *Face
	X      int
	Y      int
	Size   int
}

func NewFace(x int, y int, size int, top *Face, right *Face, bottom *Face, left *Face) *Face {
	return &Face{
		Id:     getFaceId(x, y),
		Top:    top,
		Right:  right,
		Bottom: bottom,
		Left:   left,
		X:      x,
		Y:      y,
		Size:   size,
	}
}

func (face Face) String() string {
	topString := "nil"
	if face.Top != nil {
		topString = face.Top.Id
	}

	rightString := "nil"
	if face.Right != nil {
		rightString = face.Right.Id
	}

	bottomString := "nil"
	if face.Bottom != nil {
		bottomString = face.Bottom.Id
	}

	leftString := "nil"
	if face.Left != nil {
		leftString = face.Left.Id
	}

	return fmt.Sprintf("Face{id:%s x:%d, y:%d, size:%d, t:%v, r:%v, b:%v, l:%v}", face.Id, face.X, face.Y, face.Size, topString, rightString, bottomString, leftString)
}

func getFaceId(x int, y int) string {
	return fmt.Sprintf("x%dy%d", x, y)
}

func (face Face) Copy() *Face {
	return &Face{
		Id:     face.Id,
		X:      face.X,
		Y:      face.Y,
		Top:    face.Top,
		Right:  face.Right,
		Bottom: face.Bottom,
		Left:   face.Left,
		Size:   face.Size,
	}

}

func (face Face) GetEdges() []*Face {
	return []*Face{face.Top, face.Right, face.Bottom, face.Left}
}

func findAllFaces(monkeyMap *MonkeyMap) ([]*Face, error) {
	maxDimension := max(monkeyMap.xMax-monkeyMap.xMin, monkeyMap.yMax-monkeyMap.yMin)

	// Find all faces
	size := maxDimension / 3
	faceCount := 0
	for ; size > 0; size-- {
		faceCount = 0
		numFaces := maxDimension / size

		for yFace := 0; yFace <= numFaces; yFace++ {
			for xFace := 0; xFace <= numFaces; xFace++ {
				xStart := xFace * size
				yStart := yFace * size

				// Validate face
				isFace := true
				for y := 0; y < size; y++ {
					for x := 0; x < size; x++ {
						if monkeyMap.GetCell(x+xStart, y+yStart) == VoidCell {
							isFace = false
							break
						}
					}

					if !isFace {
						break
					}
				}

				if isFace {
					faceCount++
				}
			}
		}

		if faceCount == 6 {
			break
		}
	}

	if faceCount != 6 {
		return nil, fmt.Errorf("failed to find all faces in cube")
	}

	var faces []*Face
	for yFace := 0; yFace <= maxDimension/size; yFace++ {
		for xFace := 0; xFace <= maxDimension/size; xFace++ {
			xStart := xFace * size
			yStart := yFace * size

			// Validate face
			isFace := true
			for y := 0; y < size; y++ {
				for x := 0; x < size; x++ {
					if monkeyMap.GetCell(x+xStart, y+yStart) == VoidCell {
						isFace = false
						break
					}
				}

				if !isFace {
					break
				}
			}

			if isFace {
				face := NewFace(xStart, yStart, size, nil, nil, nil, nil)

				faces = append(faces, face)
			}
		}
	}

	// Find neighbors
	for _, face := range faces {
		top := NewVector(face.X, face.Y-size)
		right := NewVector(face.X+size, face.Y)
		bottom := NewVector(face.X, face.Y+size)
		left := NewVector(face.X-size, face.Y)

		for _, nextFace := range faces {
			if nextFace.X == top.X && nextFace.Y == top.Y {
				face.Top = nextFace
			} else if nextFace.X == right.X && nextFace.Y == right.Y {
				face.Right = nextFace
			} else if nextFace.X == bottom.X && nextFace.Y == bottom.Y {
				face.Bottom = nextFace
			} else if nextFace.X == left.X && nextFace.Y == left.Y {
				face.Left = nextFace
			}
		}
	}

	return faces, nil
}
