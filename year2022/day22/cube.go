package year2022day22

import (
	"fmt"
)

type Cube struct {
	Top      *Face
	Bottom   *Face
	Left     *Face
	Right    *Face
	Forward  *Face
	Backward *Face
}

func NewCube(top *Face, bottom *Face, left *Face, right *Face, forward *Face, backward *Face) *Cube {
	return &Cube{
		Top:      top,
		Bottom:   bottom,
		Left:     left,
		Right:    right,
		Forward:  forward,
		Backward: backward,
	}
}

func (cube Cube) String() string {
	return fmt.Sprintf("Cube{t:%s, bott:%s, l:%s, r:%s, f:%s, back:%s}", cube.Top, cube.Bottom, cube.Left, cube.Right, cube.Forward, cube.Backward)
}

func (cube Cube) Copy() *Cube {
	topFace := cube.Top.Copy()
	bottomFace := cube.Bottom.Copy()
	leftFace := cube.Left.Copy()
	rightFace := cube.Right.Copy()
	forwardFace := cube.Forward.Copy()
	backwardFace := cube.Backward.Copy()

	faceMap := make(map[string]*Face)
	faces := []*Face{topFace, bottomFace, leftFace, rightFace, forwardFace, backwardFace}

	for i := 0; i < len(faces); i++ {
		faceMap[faces[i].Id] = faces[i]
	}

	for i := 0; i < len(faces); i++ {
		if faces[i].Top != nil {
			faces[i].Top = faceMap[faces[i].Top.Id]
		}
		if faces[i].Right != nil {
			faces[i].Right = faceMap[faces[i].Right.Id]
		}

		if faces[i].Bottom != nil {
			faces[i].Bottom = faceMap[faces[i].Bottom.Id]
		}

		if faces[i].Left != nil {
			faces[i].Left = faceMap[faces[i].Left.Id]
		}
	}

	newCube := NewCube(topFace, bottomFace, leftFace, rightFace, forwardFace, backwardFace)

	return newCube
}

func (cube Cube) GetFaces() []*Face {
	return []*Face{cube.Top, cube.Bottom, cube.Left, cube.Right, cube.Forward, cube.Backward}
}

func solveCube(knownFaces []*Face) (*Cube, error) {
	if len(knownFaces) != 6 {
		return nil, fmt.Errorf("not enough faces to solve")
	}

	// Copy known faces
	faceMap := make(map[string]*Face)
	solvedFaces := make([]*Face, len(knownFaces))
	for i := 0; i < len(solvedFaces); i++ {
		faceCopy := knownFaces[i].Copy()
		solvedFaces[i] = faceCopy
		faceMap[faceCopy.Id] = faceCopy
	}

	// Copy edges
	for _, face := range solvedFaces {
		if face.Top != nil {
			face.Top = faceMap[face.Top.Id]
		}

		if face.Right != nil {
			face.Right = faceMap[face.Right.Id]
		}

		if face.Bottom != nil {
			face.Bottom = faceMap[face.Bottom.Id]
		}

		if face.Left != nil {
			face.Left = faceMap[face.Left.Id]
		}
	}

	// Four edges per face
	hasChange := true
	for hasChange {
		hasChange = false
		for _, face := range solvedFaces {
			if face.Top != nil {
				if face.Left == nil && face.Top.Left != nil && face != face.Top.Left && (face.Right == nil || face.Right != face.Top.Left) {
					face.Left = face.Top.Left
					hasChange = true
				} else if face.Right == nil && face.Top.Right != nil && face != face.Top.Right && (face.Left == nil || face.Left != face.Top.Right) {
					face.Right = face.Top.Right
					hasChange = true
				}
			}

			if face.Bottom != nil {
				if face.Left == nil && face.Bottom.Left != nil && face != face.Bottom.Left && (face.Right == nil || face.Right != face.Bottom.Left) {
					face.Left = face.Bottom.Left
					hasChange = true
				} else if face.Right == nil && face.Bottom.Right != nil && face != face.Bottom.Right && (face.Left == nil || face.Left != face.Bottom.Right) {
					face.Right = face.Bottom.Right
					hasChange = true
				}
			}

			if face.Right != nil {
				if face.Top == nil && face.Right.Top != nil && face != face.Right.Top && (face.Bottom == nil || face.Bottom != face.Right.Top) {
					face.Top = face.Right.Top
					hasChange = true
				} else if face.Bottom == nil && face.Right.Bottom != nil && face != face.Right.Bottom && (face.Top == nil || face.Top != face.Right.Bottom) {
					face.Bottom = face.Right.Bottom
					hasChange = true
				}
			}

			if face.Left != nil {
				if face.Top == nil && face.Left.Top != nil && face != face.Left.Top && (face.Bottom == nil || face.Bottom != face.Left.Top) {
					face.Top = face.Left.Top
					hasChange = true
				} else if face.Bottom == nil && face.Left.Bottom != nil && face != face.Left.Bottom && (face.Top == nil || face.Top != face.Left.Bottom) {
					face.Bottom = face.Left.Bottom
					hasChange = true
				}
			}
		}
	}

	topFace := solvedFaces[0]
	rightFace := topFace.Right
	leftFace := topFace.Left
	forwardFace := topFace.Bottom
	backwardFace := topFace.Top
	var bottomFace *Face

	unknownFaces := []*Face{}
	for _, face := range solvedFaces {
		if face != topFace && face != rightFace && face != leftFace && face != forwardFace && face != backwardFace {
			unknownFaces = append(unknownFaces, face)
		}
	}

	possibilities := make(map[string][]*Face)
	for _, unknownFace := range unknownFaces {
		edgeMap := make(map[string]bool)

		if unknownFace.Top != nil {
			edgeMap[unknownFace.Top.Id] = true
		}
		if unknownFace.Right != nil {
			edgeMap[unknownFace.Right.Id] = true
		}
		if unknownFace.Bottom != nil {
			edgeMap[unknownFace.Bottom.Id] = true
		}
		if unknownFace.Left != nil {
			edgeMap[unknownFace.Left.Id] = true
		}

		if rightFace == nil && leftFace != nil && !edgeMap[leftFace.Id] {
			possibleRight := possibilities["right"]
			if possibleRight == nil {
				possibleRight = []*Face{}
			}
			possibleRight = append(possibleRight, unknownFace)
			possibilities["right"] = possibleRight
		} else if leftFace == nil && rightFace != nil && !edgeMap[rightFace.Id] {
			possibleLeft := possibilities["left"]
			if possibleLeft == nil {
				possibleLeft = []*Face{}
			}
			possibleLeft = append(possibleLeft, unknownFace)
			possibilities["left"] = possibleLeft
		} else if forwardFace == nil && backwardFace != nil && !edgeMap[backwardFace.Id] {
			possibleForward := possibilities["forward"]
			if possibleForward == nil {
				possibleForward = []*Face{}
			}
			possibleForward = append(possibleForward, unknownFace)
			possibilities["forward"] = possibleForward
		} else if backwardFace == nil && forwardFace != nil && !edgeMap[forwardFace.Id] {
			possibleBackward := possibilities["backward"]
			if possibleBackward == nil {
				possibleBackward = []*Face{}
			}
			possibleBackward = append(possibleBackward, unknownFace)
			possibilities["backward"] = possibleBackward
		} else if bottomFace == nil && topFace != nil && !edgeMap[topFace.Id] {
			possibleBottom := possibilities["bottom"]
			if possibleBottom == nil {
				possibleBottom = []*Face{}
			}
			possibleBottom = append(possibleBottom, unknownFace)
			possibilities["bottom"] = possibleBottom
		}
	}

	for i := 0; i < 6 && (rightFace == nil || leftFace == nil || forwardFace == nil || backwardFace == nil || bottomFace == nil); i++ {
		// TODO: What if there is more than one match?
		if rightFace == nil && len(possibilities["right"]) == 1 {
			rightFace = possibilities["right"][0]
		}
		if leftFace == nil && len(possibilities["left"]) == 1 {
			leftFace = possibilities["left"][0]
		}
		if forwardFace == nil && len(possibilities["forward"]) == 1 {
			forwardFace = possibilities["forward"][0]
		}
		if backwardFace == nil && len(possibilities["backward"]) == 1 {
			backwardFace = possibilities["backward"][0]
		}
		if bottomFace == nil && len(possibilities["bottom"]) == 1 {
			bottomFace = possibilities["bottom"][0]
		}
	}

	if topFace != nil && rightFace != nil && leftFace != nil && forwardFace != nil && backwardFace != nil && bottomFace != nil {
		// So we know all the cube sides
		// Know we gotta finish all the edges

		cube := NewCube(topFace, bottomFace, leftFace, rightFace, forwardFace, backwardFace)

		solvedCube, err := solveCubeEdges(cube)

		if err != nil {
			return nil, err
		}

		return solvedCube, nil
	} else {
		return nil, fmt.Errorf("could not solve cube %v", knownFaces)
	}
}

func solveCubeEdges(cube *Cube) (*Cube, error) {
	solvedCube := cube.Copy()

	// Being lazy. I would need to check all 16 edges
	normalTopEdges := []*Face{cube.Backward, cube.Right, cube.Forward, cube.Left}
	var err error
	solvedCube.Top, err = solveFace(solvedCube.Top, normalTopEdges)

	if err != nil {
		return nil, err
	}

	normalRightEdges := []*Face{cube.Top, cube.Backward, cube.Bottom, cube.Forward}
	solvedCube.Right, err = solveFace(solvedCube.Right, normalRightEdges)

	if err != nil {
		return nil, err
	}

	normalBottomEdges := []*Face{cube.Forward, cube.Right, cube.Backward, cube.Left}
	solvedCube.Bottom, err = solveFace(solvedCube.Bottom, normalBottomEdges)

	if err != nil {
		return nil, err
	}

	normalLeftEdges := []*Face{cube.Top, cube.Forward, cube.Bottom, cube.Backward}
	solvedCube.Left, err = solveFace(solvedCube.Left, normalLeftEdges)

	if err != nil {
		return nil, err
	}

	normalForwardEdges := []*Face{cube.Top, cube.Right, cube.Bottom, cube.Left}
	solvedCube.Forward, err = solveFace(solvedCube.Forward, normalForwardEdges)

	if err != nil {
		return nil, err
	}

	normalBackwardEdges := []*Face{cube.Top, cube.Left, cube.Bottom, cube.Right}
	solvedCube.Backward, err = solveFace(solvedCube.Backward, normalBackwardEdges)

	if err != nil {
		return nil, err
	}

	return solvedCube, nil
}

func solveFace(face *Face, normalEdges []*Face) (*Face, error) {
	solvedFace := face.Copy()
	edges := solvedFace.GetEdges()
	hasFilled := false

	for edgeI, edge := range edges {
		if edge != nil {
			for normalEdgeI, normalEdge := range normalEdges {
				if normalEdge != nil && edge.Id == normalEdge.Id {
					// Match
					// Check forward
					isForward := true
					for i := 0; i < len(edges); i++ {
						nextTopEdge := edges[(edgeI+i)%len(edges)]
						normalNextTopEdge := normalEdges[(normalEdgeI+i)%len(normalEdges)]

						if nextTopEdge != nil && normalNextTopEdge != nil && nextTopEdge.Id != normalNextTopEdge.Id {
							isForward = false
							break
						}
					}

					if isForward {
						for i := 0; i < len(edges); i++ {
							nextEdgeI := (edgeI + i) % len(edges)
							nextTopEdge := edges[nextEdgeI]
							normalNextTopEdge := normalEdges[(normalEdgeI+i)%len(normalEdges)]

							if nextTopEdge == nil {
								if nextEdgeI == 0 {
									solvedFace.Top = normalNextTopEdge
								} else if nextEdgeI == 1 {
									solvedFace.Right = normalNextTopEdge
								} else if nextEdgeI == 2 {
									solvedFace.Bottom = normalNextTopEdge
								} else if nextEdgeI == 3 {
									solvedFace.Left = normalNextTopEdge
								}
							}
						}
					} else {
						return nil, fmt.Errorf("cannot solve backward side")
					}

					hasFilled = true
					break
				}
			}
		}

		if hasFilled {
			break
		}
	}

	return solvedFace, nil
}
