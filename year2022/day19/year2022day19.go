package year2022day19

import (
	"container/heap"
	"fmt"
	"regexp"
	"strconv"
	"strings"
)

type Blueprint struct {
	id int

	oreRobotCostOre int

	clayRobotCostOre int

	obsidianRobotCostOre  int
	obsidianRobotCostClay int

	geodeRobotCostOre      int
	geodeRobotCostObsidian int
}

func (blueprint Blueprint) String() string {
	return fmt.Sprintf("Blueprint %d. Ore robot ore %d. Clay robot ore. %d obsidian robot ore %d clay %d. Geode robot ore %d obsidian %d.",
		blueprint.id,
		blueprint.oreRobotCostOre,
		blueprint.clayRobotCostOre,

		blueprint.obsidianRobotCostOre,
		blueprint.obsidianRobotCostClay,

		blueprint.geodeRobotCostOre,
		blueprint.geodeRobotCostObsidian,
	)
}

type RobotState struct {
	time int

	numOreRobots int
	numOre       int

	numClayRobots int
	numClay       int

	numObsidianRobots int
	numObsidian       int

	numGeodeRobots int
	numGeodes      int
}

func (robotState RobotState) String() string {
	return fmt.Sprintf("Robot State ore robots %d ore %d clay robots %d clay %d obsidian robots %d obsidian %d geode robots %d geode %d",
		robotState.numOreRobots,
		robotState.numOre,

		robotState.numClayRobots,
		robotState.numClay,

		robotState.numObsidianRobots,
		robotState.numObsidian,

		robotState.numGeodeRobots,
		robotState.numGeodes,
	)
}

func (robotState RobotState) GetPriority(blueprint Blueprint) int {
	return (robotState.numGeodes + robotState.numGeodeRobots) +
		(robotState.numObsidian+robotState.numObsidianRobots)/blueprint.geodeRobotCostObsidian +
		(robotState.numClay+robotState.numClayRobots)/(blueprint.geodeRobotCostObsidian*blueprint.obsidianRobotCostClay) +
		(robotState.numOre+robotState.numOreRobots)/(blueprint.geodeRobotCostObsidian*blueprint.obsidianRobotCostOre) -
		3*robotState.time
}

var BlueprintRegex = regexp.MustCompile(`Blueprint (\d+): Each ore robot costs (\d+) ore. Each clay robot costs (\d+) ore. Each obsidian robot costs (\d+) ore and (\d+) clay. Each geode robot costs (\d+) ore and (\d+) obsidian.`)

func GetSumBlueprintQualityLevel(time int, input string) (int, error) {
	blueprints, err := parseBlueprints(input)

	if err != nil {
		return -1, err
	}

	sumQualityLevel := 0
	for _, blueprint := range blueprints {
		qualityLevel := getBlueprintQualityLevel(time, blueprint)

		sumQualityLevel += qualityLevel
	}

	return sumQualityLevel, nil
}

func GetProductOfTopThreeBlueprintGeodes(time int, input string) (int, error) {
	blueprints, err := parseBlueprints(input)

	if err != nil {
		return -1, err
	}

	var topThree []Blueprint
	if len(blueprints) > 3 {
		topThree = blueprints[0:3]
	} else {
		topThree = blueprints
	}

	productMaxGeodes := 0
	for _, blueprint := range topThree {
		maxGeodes := getBlueprintMaxGeodes(time, blueprint)

		if productMaxGeodes == 0 {
			productMaxGeodes = maxGeodes
		} else {
			productMaxGeodes *= maxGeodes
		}
	}

	return productMaxGeodes, nil
}

func getBlueprintQualityLevel(time int, blueprint Blueprint) int {
	return blueprint.id * getBlueprintMaxGeodes(time, blueprint)
}

func getBlueprintMaxGeodes(time int, blueprint Blueprint) int {
	pq := make(PriorityQueue, 1)
	pq[0] = &Item{
		value: RobotState{
			time:              0,
			numOreRobots:      1,
			numOre:            0,
			numClayRobots:     0,
			numClay:           0,
			numObsidianRobots: 0,
			numObsidian:       0,
			numGeodeRobots:    0,
			numGeodes:         0,
		},
		priority: 0,
	}

	heap.Init(&pq)

	maxGeodes := 0
	seenState := make(map[string]int)
	count := 0
	for len(pq) > 0 && count < 6_000_000 {
		item := heap.Pop(&pq).(*Item)
		robotState := item.value.(RobotState)

		nextOre := robotState.numOre + robotState.numOreRobots
		nextClay := robotState.numClay + robotState.numClayRobots
		nextObsidian := robotState.numObsidian + robotState.numObsidianRobots
		nextGeode := robotState.numGeodes + robotState.numGeodeRobots

		if nextGeode > maxGeodes {
			maxGeodes = nextGeode
		}

		if robotState.time < time-1 {
			nextRobotState := RobotState{
				time:              robotState.time + 1,
				numOreRobots:      robotState.numOreRobots,
				numOre:            nextOre,
				numClayRobots:     robotState.numClayRobots,
				numClay:           nextClay,
				numObsidianRobots: robotState.numObsidianRobots,
				numObsidian:       nextObsidian,
				numGeodeRobots:    robotState.numGeodeRobots,
				numGeodes:         nextGeode,
			}
			if seenState[nextRobotState.String()] == 0 || seenState[nextRobotState.String()] > nextRobotState.time {
				heap.Push(&pq, &Item{
					value:    nextRobotState,
					priority: nextRobotState.GetPriority(blueprint),
				})
				seenState[nextRobotState.String()] = nextRobotState.time

				if robotState.numOre >= blueprint.oreRobotCostOre {
					nextRobotState := RobotState{
						time:              robotState.time + 1,
						numOreRobots:      robotState.numOreRobots + 1,
						numOre:            nextOre - blueprint.oreRobotCostOre,
						numClayRobots:     robotState.numClayRobots,
						numClay:           nextClay,
						numObsidianRobots: robotState.numObsidianRobots,
						numObsidian:       nextObsidian,
						numGeodeRobots:    robotState.numGeodeRobots,
						numGeodes:         nextGeode,
					}

					if seenState[nextRobotState.String()] == 0 || seenState[nextRobotState.String()] > nextRobotState.time {
						heap.Push(&pq, &Item{
							value:    nextRobotState,
							priority: nextRobotState.GetPriority(blueprint),
						})
						seenState[nextRobotState.String()] = nextRobotState.time
					}
				}

				if robotState.numOre >= blueprint.clayRobotCostOre {
					nextRobotState := RobotState{
						time:              robotState.time + 1,
						numOreRobots:      robotState.numOreRobots,
						numOre:            nextOre - blueprint.clayRobotCostOre,
						numClayRobots:     robotState.numClayRobots + 1,
						numClay:           nextClay,
						numObsidianRobots: robotState.numObsidianRobots,
						numObsidian:       nextObsidian,
						numGeodeRobots:    robotState.numGeodeRobots,
						numGeodes:         nextGeode,
					}

					if seenState[nextRobotState.String()] == 0 || seenState[nextRobotState.String()] > nextRobotState.time {
						heap.Push(&pq, &Item{
							value:    nextRobotState,
							priority: nextRobotState.GetPriority(blueprint),
						})
						seenState[nextRobotState.String()] = nextRobotState.time
					}
				}

				if robotState.numOre >= blueprint.obsidianRobotCostOre && robotState.numClay >= blueprint.obsidianRobotCostClay {
					nextRobotState := RobotState{
						time:              robotState.time + 1,
						numOreRobots:      robotState.numOreRobots,
						numOre:            nextOre - blueprint.obsidianRobotCostOre,
						numClayRobots:     robotState.numClayRobots,
						numClay:           nextClay - blueprint.obsidianRobotCostClay,
						numObsidianRobots: robotState.numObsidianRobots + 1,
						numObsidian:       nextObsidian,
						numGeodeRobots:    robotState.numGeodeRobots,
						numGeodes:         nextGeode,
					}

					if seenState[nextRobotState.String()] == 0 || seenState[nextRobotState.String()] > nextRobotState.time {
						heap.Push(&pq, &Item{
							value:    nextRobotState,
							priority: nextRobotState.GetPriority(blueprint),
						})
						seenState[nextRobotState.String()] = nextRobotState.time
					}
				}

				if robotState.numOre >= blueprint.geodeRobotCostOre && robotState.numObsidian >= blueprint.geodeRobotCostObsidian {
					nextRobotState := RobotState{
						time:              robotState.time + 1,
						numOreRobots:      robotState.numOreRobots,
						numOre:            nextOre - blueprint.geodeRobotCostOre,
						numClayRobots:     robotState.numClayRobots,
						numClay:           nextClay,
						numObsidianRobots: robotState.numObsidianRobots,
						numObsidian:       nextObsidian - blueprint.geodeRobotCostObsidian,
						numGeodeRobots:    robotState.numGeodeRobots + 1,
						numGeodes:         nextGeode,
					}

					if seenState[nextRobotState.String()] == 0 || seenState[nextRobotState.String()] > nextRobotState.time {
						heap.Push(&pq, &Item{
							value:    nextRobotState,
							priority: nextRobotState.GetPriority(blueprint),
						})
						seenState[nextRobotState.String()] = nextRobotState.time
					}
				}
			}
		}

		count++
	}

	return maxGeodes
}

func parseBlueprints(input string) ([]Blueprint, error) {
	blueprints := []Blueprint{}

	blueprintStrings := strings.Split(input, "\n")

	for _, blueprintString := range blueprintStrings {
		blueprint, err := parseBlueprint(blueprintString)

		if err != nil {
			return []Blueprint{}, err
		}

		blueprints = append(blueprints, blueprint)
	}

	return blueprints, nil
}

func parseBlueprint(blueprintString string) (Blueprint, error) {
	groupsString := BlueprintRegex.FindStringSubmatch(blueprintString)
	groups := make([]int, len(groupsString)-1)

	for i := 1; i < len(groupsString); i++ {
		value, err := strconv.Atoi(groupsString[i])

		if err != nil {
			return Blueprint{}, err
		}

		groups[i-1] = value
	}

	return Blueprint{
		id: groups[0],

		oreRobotCostOre: groups[1],

		clayRobotCostOre: groups[2],

		obsidianRobotCostOre:  groups[3],
		obsidianRobotCostClay: groups[4],

		geodeRobotCostOre:      groups[5],
		geodeRobotCostObsidian: groups[6],
	}, nil
}
