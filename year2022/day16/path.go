package year2022day16

type Path struct {
	Position              string
	OnSet                 map[string]bool
	TotalExpendedPressure int
	CurrentTime           int
}

func NewPath(position string, totalExpendedPressure int, currentTime int, onSet map[string]bool) *Path {
	return &Path{
		Position:              position,
		TotalExpendedPressure: totalExpendedPressure,
		CurrentTime:           currentTime,
		OnSet:                 onSet,
	}
}

func (path Path) GetPriority() int {
	return path.TotalExpendedPressure
}
