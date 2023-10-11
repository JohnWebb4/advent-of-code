package year2022day16

import (
	"container/heap"
	"fmt"
	"log"
	"math"
	"regexp"
	"strconv"
	"strings"
)

var valveInputRegex = regexp.MustCompile(`^Valve (\w+) has flow rate=(\d+); tunnels? leads? to valves? ((?:\w+,? ?)+)`)

func GetMostPressure(input string, maxTime int) (int, error) {
	valveMap, err := getValveMap(input)
	if err != nil {
		return 0, err
	}

	allValveNames := make([]string, len(valveMap))
	valveIndex := 0
	for k := range valveMap {
		allValveNames[valveIndex] = k
		valveIndex++
	}

	costToAllValvesMap := getCostToAllValvesMap(allValveNames, valveMap, maxTime)

	pq := make(PriorityQueue, 1)
	pq[0] = NewItem(NewPath("AA", 0, 0, make(map[string]bool)), 0)
	heap.Init(&pq)

	maxPressure := math.MinInt32

	for len(pq) > 0 {
		item := heap.Pop(&pq).(*Item)
		path := item.Value.(*Path)

		if path.CurrentTime <= maxTime && path.TotalExpendedPressure > maxPressure {
			maxPressure = path.TotalExpendedPressure
		}

		if path.CurrentTime < maxTime {
			// Find next valve to turn on
			for _, nextValveName := range allValveNames {
				if !path.OnSet[nextValveName] {
					// Move to next valve and turn on

					// Do you have time to turn it on and does it have pressure
					pathKey := fmt.Sprintf("%s:%s", path.Position, nextValveName)
					movementCost := costToAllValvesMap[pathKey]
					nextValve := valveMap[nextValveName]
					nextTime := path.CurrentTime + movementCost + 1 // + 1 to turn on
					if nextTime <= maxTime && nextValve.Pressure > 0 {
						nextOnSet := make(map[string]bool)
						for onValve, isOn := range path.OnSet {
							nextOnSet[onValve] = isOn
						}
						nextOnSet[nextValveName] = true

						nextTotalExpendedPressure := path.TotalExpendedPressure + nextValve.Pressure*(maxTime-nextTime)
						nextPath := NewPath(nextValveName,
							nextTotalExpendedPressure,
							nextTime,
							nextOnSet,
						)
						nextItem := NewItem(nextPath, nextPath.GetPriority())

						heap.Push(&pq, nextItem)
					}
				}
			}
		}

	}

	return maxPressure, nil
}

func GetMostPressureWithElephant(input string, maxTime int) (int, error) {
	valveMap, err := getValveMap(input)
	if err != nil {
		return 0, err
	}

	allValveNames := make([]string, len(valveMap))
	valveIndex := 0
	for k := range valveMap {
		allValveNames[valveIndex] = k
		valveIndex++
	}

	costToAllValvesMap := getCostToAllValvesMap(allValveNames, valveMap, maxTime)

	type DualPath struct {
		PositionMe            string
		MovementTimeMe        int
		PositionElephant      string
		MovementTimeElephant  int
		OnSet                 map[string]bool
		TotalExpendedPressure int
		CurrentTime           int
	}

	pq := make(PriorityQueue, 1)
	pq[0] = NewItem(
		&DualPath{
			PositionMe:            "AA",
			MovementTimeMe:        0,
			PositionElephant:      "AA",
			MovementTimeElephant:  0,
			OnSet:                 make(map[string]bool),
			TotalExpendedPressure: 0,
			CurrentTime:           0,
		}, 0)
	heap.Init(&pq)

	maxPressure := math.MinInt32

	log.Printf("All valve names %v\n", allValveNames)

	count := 0
	for len(pq) > 0 {
		count++
		item := heap.Pop(&pq).(*Item)
		path := item.Value.(*DualPath)

		if path.CurrentTime <= maxTime && path.TotalExpendedPressure > maxPressure {
			log.Printf("New Max %v at iteration %v", path, count)
			maxPressure = path.TotalExpendedPressure
		}

		if path.CurrentTime <= maxTime {
			if path.MovementTimeMe <= 0 && path.MovementTimeElephant <= 0 {
				for _, nextValveNameMe := range allValveNames {
					if !path.OnSet[nextValveNameMe] && path.PositionMe != nextValveNameMe {
						// Move to next valve and turn on

						// Do you have time to turn it on and does it have pressure
						pathKeyMe := fmt.Sprintf("%s:%s", path.PositionMe, nextValveNameMe)
						movementCostMe := costToAllValvesMap[pathKeyMe] + 1 // + 1 to trun
						nextValveMe := valveMap[nextValveNameMe]
						nextTimeMe := path.CurrentTime + movementCostMe

						if nextTimeMe <= maxTime && nextValveMe.Pressure > 0 {
							for _, nextValveNameElephant := range allValveNames {
								if !path.OnSet[nextValveNameElephant] && path.PositionElephant != nextValveNameElephant && nextValveNameMe != nextValveNameElephant {
									// Move to next valve and turn on

									// Do you have time to turn it on and does it have pressure
									pathKeyElephant := fmt.Sprintf("%s:%s", path.PositionElephant, nextValveNameElephant)
									movementCostElephant := costToAllValvesMap[pathKeyElephant] + 1 // +1 to turn on
									nextValveElephant := valveMap[nextValveNameElephant]
									nextTimeElephant := path.CurrentTime + movementCostElephant

									if nextTimeElephant <= maxTime && nextValveElephant.Pressure > 0 {
										// I can move, but elephant is still moving
										nextOnSet := make(map[string]bool)
										for onValve, isOn := range path.OnSet {
											nextOnSet[onValve] = isOn
										}
										nextOnSet[nextValveNameMe] = true
										nextOnSet[nextValveNameElephant] = true

										nextTotalExpendedPressure := path.TotalExpendedPressure + nextValveMe.Pressure*(maxTime-nextTimeMe) + nextValveElephant.Pressure*(maxTime-nextTimeElephant)

										nextPath :=
											&DualPath{
												PositionMe:            nextValveNameMe,
												MovementTimeMe:        movementCostMe,
												PositionElephant:      nextValveNameElephant,
												MovementTimeElephant:  movementCostElephant,
												OnSet:                 nextOnSet,
												TotalExpendedPressure: nextTotalExpendedPressure,
												CurrentTime:           path.CurrentTime,
											}
										nextItem := NewItem(nextPath, nextPath.TotalExpendedPressure)

										heap.Push(&pq, nextItem)
									}
								}
							}
						}
					}
				}
			} else if path.MovementTimeMe <= 0 {
				// Find where I should move to next
				for _, nextValveName := range allValveNames {
					if !path.OnSet[nextValveName] && path.PositionMe != nextValveName {
						// Move to next valve and turn on

						// Do you have time to turn it on and does it have pressure
						pathKeyMe := fmt.Sprintf("%s:%s", path.PositionMe, nextValveName)
						movementCostMe := costToAllValvesMap[pathKeyMe] + 1 // + 1 to trun
						nextValveMe := valveMap[nextValveName]
						nextTimeMe := path.CurrentTime + movementCostMe

						if nextTimeMe <= maxTime && nextValveMe.Pressure > 0 {
							nextOnSet := make(map[string]bool)
							for onValve, isOn := range path.OnSet {
								nextOnSet[onValve] = isOn
							}
							nextOnSet[nextValveName] = true

							nextTotalExpendedPressure := path.TotalExpendedPressure + nextValveMe.Pressure*(maxTime-nextTimeMe)

							nextPath :=
								&DualPath{
									PositionMe:            nextValveName,
									MovementTimeMe:        movementCostMe,
									PositionElephant:      path.PositionElephant,
									MovementTimeElephant:  path.MovementTimeElephant,
									OnSet:                 nextOnSet,
									TotalExpendedPressure: nextTotalExpendedPressure,
									CurrentTime:           path.CurrentTime,
								}
							nextItem := NewItem(nextPath, nextPath.TotalExpendedPressure)

							heap.Push(&pq, nextItem)
						}
					}
				}
			} else if path.MovementTimeElephant <= 0 {
				// Find where elephant should move to next
				for _, nextValveName := range allValveNames {
					if !path.OnSet[nextValveName] && path.PositionElephant != nextValveName {
						// Move to next valve and turn on

						// Do you have time to turn it on and does it have pressure
						pathKeyElephant := fmt.Sprintf("%s:%s", path.PositionElephant, nextValveName)
						movementCostElephant := costToAllValvesMap[pathKeyElephant] + 1 // +1 to turn on
						nextValveElephant := valveMap[nextValveName]
						nextTimeElephant := path.CurrentTime + movementCostElephant

						if nextTimeElephant <= maxTime && nextValveElephant.Pressure > 0 {
							// I can move, but elephant is still moving
							nextOnSet := make(map[string]bool)
							for onValve, isOn := range path.OnSet {
								nextOnSet[onValve] = isOn
							}
							nextOnSet[nextValveName] = true

							nextTotalExpendedPressure := path.TotalExpendedPressure + nextValveElephant.Pressure*(maxTime-nextTimeElephant)

							nextPath :=
								&DualPath{
									PositionMe:            path.PositionMe,
									MovementTimeMe:        path.MovementTimeMe,
									PositionElephant:      nextValveName,
									MovementTimeElephant:  movementCostElephant,
									OnSet:                 nextOnSet,
									TotalExpendedPressure: nextTotalExpendedPressure,
									CurrentTime:           path.CurrentTime,
								}
							nextItem := NewItem(nextPath, nextPath.TotalExpendedPressure)

							heap.Push(&pq, nextItem)
						}
					}
				}
			} else {
				// Advance time
				copyOnSet := make(map[string]bool)
				for onValve, isOn := range path.OnSet {
					copyOnSet[onValve] = isOn
				}

				advanceTimeBy := MinInt(path.MovementTimeMe, path.MovementTimeElephant)

				nextPath := &DualPath{
					PositionMe:            path.PositionMe,
					MovementTimeMe:        path.MovementTimeMe - advanceTimeBy,
					PositionElephant:      path.PositionElephant,
					MovementTimeElephant:  path.MovementTimeElephant - advanceTimeBy,
					OnSet:                 copyOnSet,
					TotalExpendedPressure: path.TotalExpendedPressure,
					CurrentTime:           path.CurrentTime + advanceTimeBy,
				}
				nextItem := NewItem(nextPath, nextPath.TotalExpendedPressure)

				heap.Push(&pq, nextItem)
			}

		}
	}

	log.Printf("Found solution %v in %v iterations\n", maxPressure, count)

	return maxPressure, nil
}

func getValveMap(input string) (map[string]Valve, error) {
	valveInputs := strings.Split(input, "\n")
	valveMap := make(map[string]Valve)

	for _, valveInput := range valveInputs {
		subMatches := valveInputRegex.FindAllStringSubmatch(valveInput, -1)

		name := subMatches[0][1]

		pressure, err := strconv.Atoi(subMatches[0][2])
		if err != nil {
			return nil, err
		}

		pathNames := strings.Split(subMatches[0][3], ", ")
		valve := NewValve(name, pressure, pathNames)

		valveMap[name] = *valve
	}

	return valveMap, nil
}

func getCostToAllValvesMap(allValveNames []string, valveMap map[string]Valve, maxTime int) map[string]int {
	costToAllValvesMap := make(map[string]int)

	// Path from a to b is not the same as path b to a
	for _, valveName := range allValveNames {
		for _, otherValveName := range allValveNames {
			if valveName != otherValveName {
				// Get min distance to go from a to b
				minDistance := maxTime // It can't cost more to move to a point than time you have

				type ValvePath struct {
					position string
					cost     int
				}

				pq := make(PriorityQueue, 1)
				pq[0] = NewItem(&ValvePath{
					position: valveName,
					cost:     0,
				}, 0)
				heap.Init(&pq)

				count := 0

				for len(pq) > 0 {
					item := heap.Pop(&pq).(*Item)
					valvePath := item.Value.(*ValvePath)

					// Cheating
					count++
					if count > 300 {
						break
					}

					if valvePath.cost < minDistance {
						if valvePath.position == otherValveName {
							minDistance = valvePath.cost
						}

						for _, nextValve := range valveMap[valvePath.position].Paths {
							nextValvePath := &ValvePath{
								position: nextValve,
								cost:     valvePath.cost + 1,
							}
							nextItem := NewItem(nextValvePath, -nextValvePath.cost)
							heap.Push(&pq, nextItem)
						}
					}
				}

				key := fmt.Sprintf("%s:%s", valveName, otherValveName)
				costToAllValvesMap[key] = minDistance
			}
		}
	}

	return costToAllValvesMap
}

func MinInt(a int, b int) int {
	if a <= b {
		return a
	} else {
		return b
	}
}
