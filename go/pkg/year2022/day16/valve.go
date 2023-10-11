package year2022day16

type Valve struct {
	Name string
	Pressure int
	Paths []string
}

func NewValve(name string, pressure int, paths []string) *Valve {
	return &Valve {
		Name: name,
		Pressure: pressure,
		Paths: paths,
	}
}