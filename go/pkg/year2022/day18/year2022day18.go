package year2022day18

import (
	"fmt"
	"math"
	"strconv"
	"strings"
)

type Droplet struct {
	X int
	Y int
	Z int
}

func makeDroplet(x int, y int, z int) Droplet {
	return Droplet{X: x, Y: y, Z: z}
}

const EMPTY = ""
const STEAM = "s"
const LAVA = "l"

func GetSurfaceAreaOfLavaDroplet(input string) int {
	droplets := strings.Split(input, "\n")

	surfaceTension := 0
	surfaceMap := make(map[string]bool)

	for _, droplet := range droplets {
		coordinates := strings.Split(droplet, ",")

		x, err := strconv.Atoi(coordinates[0])
		if err != nil {
			return -1
		}
		y, err := strconv.Atoi(coordinates[1])
		if err != nil {
			return -1
		}
		z, err := strconv.Atoi(coordinates[2])
		if err != nil {
			return -1
		}

		surfaceMap[getSurfaceMapKey(x, y, z)] = true
		surfaceTension += 6
		if surfaceMap[getSurfaceMapKey(x+1, y, z)] {
			surfaceTension -= 2
		}
		if surfaceMap[getSurfaceMapKey(x-1, y, z)] {
			surfaceTension -= 2
		}
		if surfaceMap[getSurfaceMapKey(x, y+1, z)] {
			surfaceTension -= 2
		}
		if surfaceMap[getSurfaceMapKey(x, y-1, z)] {
			surfaceTension -= 2
		}
		if surfaceMap[getSurfaceMapKey(x, y, z+1)] {
			surfaceTension -= 2
		}
		if surfaceMap[getSurfaceMapKey(x, y, z-1)] {
			surfaceTension -= 2
		}
	}

	return surfaceTension
}

func GetSurfaceAreaWithoutPockets(input string) int {
	dropletString := strings.Split(input, "\n")
	droplets := []Droplet{}

	surfaceTension := 0
	surfaceMap := make(map[string]string)

	maxX := math.MinInt32
	minX := math.MaxInt32

	maxY := math.MinInt32
	minY := math.MaxInt32

	maxZ := math.MinInt32
	minZ := math.MaxInt32

	for _, dropletString := range dropletString {
		coordinates := strings.Split(dropletString, ",")

		x, err := strconv.Atoi(coordinates[0])
		if err != nil {
			return -1
		}
		y, err := strconv.Atoi(coordinates[1])
		if err != nil {
			return -1
		}
		z, err := strconv.Atoi(coordinates[2])
		if err != nil {
			return -1
		}

		droplets = append(droplets, makeDroplet(x, y, z))
		surfaceMap[getSurfaceMapKey(x, y, z)] = LAVA

		if x > maxX {
			maxX = x
		}
		if x < minX {
			minX = x
		}
		if y > maxY {
			maxY = y
		}
		if y < minY {
			minY = y
		}
		if z > maxZ {
			maxZ = z
		}
		if z < minZ {
			minZ = z
		}
	}

	surfaceMap[getSurfaceMapKey(minX-1, minY-1, minZ-1)] = STEAM
	surfaceMap[getSurfaceMapKey(maxX+1, minY-1, minZ-1)] = STEAM
	surfaceMap[getSurfaceMapKey(minX-1, maxY+1, minZ-1)] = STEAM
	surfaceMap[getSurfaceMapKey(maxX+1, maxY+1, minZ-1)] = STEAM
	surfaceMap[getSurfaceMapKey(minX-1, minY-1, maxZ+1)] = STEAM
	surfaceMap[getSurfaceMapKey(maxX+1, minY-1, maxZ+1)] = STEAM
	surfaceMap[getSurfaceMapKey(minX-1, maxY+1, maxZ+1)] = STEAM
	surfaceMap[getSurfaceMapKey(maxX+1, maxY+1, maxZ+1)] = STEAM

	directions := [][]int{
		{-1, 0, 0},
		{1, 0, 0},
		{0, -1, 0},
		{0, 1, 0},
		{0, 0, -1},
		{0, 0, 1},
	}

	hasExpandedSteam := true
	for hasExpandedSteam {
		hasExpandedSteam = false

		for x := minX - 2; x < maxX+2; x++ {
			for y := minY - 2; y < maxY+2; y++ {
				for z := minZ - 2; z < maxZ+2; z++ {
					if surfaceMap[getSurfaceMapKey(x, y, z)] == STEAM {
						for _, direction := range directions {
							newX := x + direction[0]
							newY := y + direction[1]
							newZ := z + direction[2]

							if surfaceMap[getSurfaceMapKey(newX, newY, newZ)] == EMPTY {
								surfaceMap[getSurfaceMapKey(newX, newY, newZ)] = STEAM
								hasExpandedSteam = true
							}
						}
					}
				}
			}
		}
	}

	for _, droplet := range droplets {
		for _, direction := range directions {
			newX := droplet.X + direction[0]
			newY := droplet.Y + direction[1]
			newZ := droplet.Z + direction[2]

			if surfaceMap[getSurfaceMapKey(newX, newY, newZ)] == STEAM {
				surfaceTension++
			}
		}

	}

	return surfaceTension
}

func getSurfaceMapKey(x int, y int, z int) string {
	return fmt.Sprintf("%d-%d-%d", x, y, z)
}
