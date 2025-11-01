import unittest


def parse_or_get_register(value: str, registers: dict[str, int]) -> int:
    try:
        return int(value)
    except ValueError:
        try:
            return registers[value]
        except KeyError:
            return 0


def set(rest: list[str], registers: dict[str, int]) -> int:
    value = parse_or_get_register(rest[1], registers)
    return value


def add(rest: list[str], registers: dict[str, int]) -> int:
    value = parse_or_get_register(rest[1], registers)
    return registers.get(rest[0], 0) + value


def mul(rest: list[str], registers: dict[str, int]) -> int:
    register2_value = parse_or_get_register(rest[1], registers)
    return registers.get(rest[0], 0) * register2_value


def mod(rest: list[str], registers: dict[str, int]) -> int:
    modulo = parse_or_get_register(rest[1], registers)
    return registers.get(rest[0], 0) % modulo


def get_recovered_frequency(instructions_string: str) -> int:
    registers: dict[str, int] = {}
    frequency = 0

    instructions = instructions_string.split("\n")

    instruction_index = 0
    while instruction_index < len(instructions):
        instruction = instructions[instruction_index]
        cmd, *rest = instruction.split(" ")

        if cmd == "set":
            registers[rest[0]] = set(rest, registers)
        elif cmd == "add":
            registers[rest[0]] = add(rest, registers)
        elif cmd == "mul":
            registers[rest[0]] = mul(rest, registers)
        elif cmd == "mod":
            registers[rest[0]] = mod(rest, registers)
        elif cmd == "snd":
            register = rest[0]
            frequency = registers[register]
        elif cmd == "rcv":
            register = rest[0]
            if registers[register] != 0:
                return frequency
        elif cmd == "jgz":
            value = parse_or_get_register(rest[0], registers)
            offset = parse_or_get_register(rest[1], registers)
            if value > 0:
                instruction_index += offset
                continue
        else:
            raise Exception(f"Unknown command {cmd}")

        instruction_index += 1

    return 0


class Program:
    id: int
    registers: dict[str, int]
    queue: list[int]
    instruction_index: int
    count_values_received: int

    def __init__(self, id: int):
        self.id = id
        self.registers = {"p": id}
        self.queue = []
        self.instruction_index = 0
        self.count_values_received = 0

    def __str__(self):
        return f"Program {self.id}: {self.instruction_index}, {self.count_values_received}, {self.queue}, {self.registers}"

    def enqueue(self, value: int):
        self.queue.append(value)
        self.count_values_received += 1


def update_program(program: Program, target: Program, instructions: list[str]):
    instruction = instructions[program.instruction_index]
    cmd, *rest = instruction.split(" ")

    if cmd == "set":
        program.registers[rest[0]] = set(rest, program.registers)
    elif cmd == "add":
        program.registers[rest[0]] = add(rest, program.registers)
    elif cmd == "mul":
        program.registers[rest[0]] = mul(rest, program.registers)
    elif cmd == "mod":
        program.registers[rest[0]] = mod(rest, program.registers)
    elif cmd == "snd":
        value = parse_or_get_register(rest[0], program.registers)
        target.enqueue(value)
    elif cmd == "rcv":
        if len(program.queue) > 0:
            value = program.queue.pop(0)
            register = rest[0]
            program.registers[register] = value
        else:
            # Hold for value
            return
    elif cmd == "jgz":
        value = parse_or_get_register(rest[0], program.registers)
        if value > 0:
            offset = parse_or_get_register(rest[1], program.registers)
            program.instruction_index = (program.instruction_index + offset) % len(
                instructions
            )
            return
    else:
        raise Exception(f"Unknown command {cmd}")

    program.instruction_index = (program.instruction_index + 1) % len(instructions)


def get_times_program_1_sent_value(instructions_string: str) -> int:
    instructions = instructions_string.split("\n")

    program_0 = Program(0)
    program_1 = Program(1)

    while True:
        if (
            instructions[program_0.instruction_index].startswith("rcv")
            and len(program_0.queue) == 0
        ) and (
            instructions[program_1.instruction_index].startswith("rcv")
            and len(program_1.queue) == 0
        ):
            return program_0.count_values_received

        update_program(program_0, program_1, instructions)
        update_program(program_1, program_0, instructions)


class TestDay18(unittest.TestCase):
    test_case_1 = """set a 1
add a 2
mul a a
mod a 5
snd a
set a 0
rcv a
jgz a -1
set a 1
jgz a -2"""

    test_case_2 = """snd 1
snd 2
snd p
rcv a
rcv b
rcv c
rcv d"""

    def test_part_1(self):
        self.assertEqual(get_recovered_frequency(self.test_case_1), 4, "Part 1 Test 1")

        with open("./input.txt") as f:
            self.assertEqual(get_recovered_frequency(f.read()), 8600, "Part 1 Solution")

    def test_part_2(self):
        self.assertEqual(
            get_times_program_1_sent_value(self.test_case_2), 3, "Part 2 Test 1"
        )

        with open("./input.txt") as f:
            self.assertEqual(
                get_times_program_1_sent_value(f.read()), 7239, "Part 2 Solution"
            )


if __name__ == "__main__":
    unittest.main()
