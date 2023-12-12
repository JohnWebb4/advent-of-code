package year2022day22

import "fmt"

type InstructionType string

const TurnInstructionType InstructionType = "Turn"
const MoveINstructionType InstructionType = "Move"

type Instruction struct {
	InstructionType InstructionType
	Amount          int
}

func NewInstruction(instructionType InstructionType, amount int) *Instruction {
	return &Instruction{
		InstructionType: instructionType,
		Amount:          amount,
	}
}

func (instruction Instruction) String() string {
	return fmt.Sprintf("Instruction{ %s %d }", instruction.InstructionType, instruction.Amount)
}
