import unittest


def get_recovered_frequency(instructions_string: str) -> int:
    registers: dict[str, int] = {}
    frequency = 0

    instructions = instructions_string.split("\n")

    def parse_or_get_register(value: str):
        try:
            return int(value)
        except ValueError:
            try:
                return registers[value]
            except KeyError:
                return 0

    instruction_index = 0
    while instruction_index < len(instructions):
        instruction = instructions[instruction_index]
        cmd, *rest = instruction.split(" ")

        if cmd == "set":
            register = rest[0]
            value = parse_or_get_register(rest[1])
            registers[register] = value
        elif cmd == "add":
            register = rest[0]
            value = parse_or_get_register(rest[1])
            registers[register] = registers.get(register, 0) + value
        elif cmd == "mul":
            register = rest[0]
            register2_value = parse_or_get_register(rest[1])
            registers[register] = registers.get(register, 0) * register2_value
        elif cmd == "mod":
            register = rest[0]
            modulo = parse_or_get_register(rest[1])
            registers[register] = registers.get(register, 0) % modulo
        elif cmd == "snd":
            register = rest[0]
            frequency = registers[register]
        elif cmd == "rcv":
            register = rest[0]
            if registers[register] != 0:
                return frequency
        elif cmd == "jgz":
            register = rest[0]
            offset = parse_or_get_register(rest[1])
            if registers[register] != 0:
                instruction_index += offset
                continue
        else:
            raise Exception(f"Unknown command {cmd}")

        instruction_index += 1

    return 0


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

    def test_part_1(self):
        self.assertEqual(get_recovered_frequency(self.test_case_1), 4, "Part 1 Test 1")

        with open("./input.txt") as f:
            self.assertEqual(get_recovered_frequency(f.read()), 8600, "Part 1 Solution")


if __name__ == "__main__":
    unittest.main()
