package year2022day15

import (
	"fmt"
	"math"
	"regexp"
	"strconv"
	"strings"
)

var sensorReg = regexp.MustCompile(`Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)`)

func GetTuningFrequencyOfBeacon(input string, max int) (int, error) {
	sensors, err := readGrid(input)
	if err != nil {
		return 0, err
	}

	possibleBeacons := getPossibleSignalBeacons(sensors)

	beaconRanges := make([]BeaconRange, 4*len(possibleBeacons))

	for beaconIndex, beacon := range possibleBeacons {
		yMin := beacon.Y - beacon.Dist
		yMax := beacon.Y + beacon.Dist

		xMin := beacon.X - beacon.Dist
		xMax := beacon.X + beacon.Dist

		beaconRanges[4*beaconIndex] = BeaconRange{
			x1: xMin, x2: beacon.X - 1, m: -1, b: beacon.Y + xMin,
		}
		beaconRanges[4*beaconIndex+1] = BeaconRange{
			x1: beacon.X, x2: xMax - 1, m: 1, b: yMin - beacon.X,
		}
		beaconRanges[4*beaconIndex+2] = BeaconRange{
			x1: xMin + 1, x2: beacon.X, m: 1, b: beacon.Y - xMin,
		}
		beaconRanges[4*beaconIndex+3] = BeaconRange{
			x1: beacon.X + 1, x2: xMax, m: -1, b: yMax + beacon.X,
		}
	}

	for i := 0; i < len(beaconRanges); i++ {
		// Because the next three are the same beacon
		for j := i + 4; j < len(beaconRanges); j++ {
			x, y, err := findIntersection(beaconRanges[i], beaconRanges[j])
			if err == nil && x >= 0 && x <= max && y >= 0 && y <= max {
				if !isInAnySensor(x, y, sensors) {
					return getTuningFrequency(x, y), nil
				}

			}
		}
	}

	return 0, nil
}

func GetNumPositionsWithoutBeacon(input string, y int) (int, error) {
	sensors, err := readGrid(input)
	if err != nil {
		return 0, err
	}

	return countNoBeacons(y, sensors), nil
}

func readGrid(input string) ([]Sensor, error) {
	lines := strings.Split(input, "\n")
	sensors := make([]Sensor, len(lines))

	for i, line := range lines {
		values := sensorReg.FindStringSubmatch(line)
		sensorX, err := strconv.Atoi(values[1])
		if err != nil {
			return nil, err
		}

		sensorY, err := strconv.Atoi(values[2])
		if err != nil {
			return nil, err
		}

		beaconX, err := strconv.Atoi(values[3])
		if err != nil {
			return nil, err
		}

		beaconY, err := strconv.Atoi(values[4])
		if err != nil {
			return nil, err
		}

		sensors[i] = *NewSensor(sensorX, sensorY, beaconX, beaconY)
	}

	return sensors, nil
}

func countNoBeacons(yRow int, sensors []Sensor) int {
	numNoBeacon := 0

	minX := math.MaxInt64
	maxX := math.MinInt64

	for _, sensor := range sensors {
		sensorMinX := sensor.X - sensor.Dist
		sensorMaxX := sensor.X + sensor.Dist

		if sensorMinX < minX {
			minX = sensorMinX
		}

		if sensorMaxX > maxX {
			maxX = sensorMaxX
		}
	}

	for x := minX; x <= maxX; x++ {
		hasPoint := false

		for _, sensor := range sensors {
			if sensor.hasPoint(x, yRow) && (sensor.X != x || sensor.Y != yRow) && (sensor.BeaconX != x || sensor.BeaconY != yRow) {
				hasPoint = true
				break
			}
		}

		if hasPoint {
			numNoBeacon++
		}
	}

	return numNoBeacon
}

func isInAnySensor(x int, y int, sensors []Sensor) bool {
	for _, sensor := range sensors {
		if sensor.hasPoint(x, y) {
			return true
		}
	}

	return false
}

func findIntersection(iBeaconRange BeaconRange, jBeaconRange BeaconRange) (int, int, error) {
	if iBeaconRange.m != jBeaconRange.m {
		xIntersect := (iBeaconRange.b - jBeaconRange.b) / (jBeaconRange.m - iBeaconRange.m)

		if iBeaconRange.x1 <= xIntersect && iBeaconRange.x2 >= xIntersect && jBeaconRange.x1 <= xIntersect && jBeaconRange.x2 >= xIntersect {
			yIntersect := iBeaconRange.m*xIntersect + iBeaconRange.b

			return xIntersect, yIntersect, nil
		}
	}

	return 0, 0, fmt.Errorf("no intersection")
}

func getPossibleSignalBeacons(sensors []Sensor) []Sensor {
	signalBeasons := make([]Sensor, len(sensors))

	for i, sensor := range sensors {
		signalBeasons[i] = *NewSensor(sensor.X, sensor.Y, sensor.BeaconX, sensor.BeaconY)
		signalBeasons[i].Dist += 1
	}

	return signalBeasons
}

func getTuningFrequency(x int, y int) int {
	return x*4000000 + y
}

func absInt(a int) int {
	if a >= 0 {
		return a
	} else {
		return -a
	}
}
