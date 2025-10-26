package year2022day15

type Sensor struct {
	X       int
	Y       int
	Dist    int
	BeaconX int
	BeaconY int
}

func NewSensor(x int, y int, beaconX int, beaconY int) *Sensor {
	dist := absInt(beaconX-x) + absInt(beaconY-y)

	return &Sensor{
		X:       x,
		Y:       y,
		Dist:    dist,
		BeaconX: beaconX,
		BeaconY: beaconY,
	}
}

func (sensor Sensor) hasPoint(x int, y int) bool {
	dist := absInt(x-sensor.X) + absInt(y-sensor.Y)

	return dist <= sensor.Dist
}
