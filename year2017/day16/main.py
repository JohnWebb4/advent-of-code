import unittest

# Notes
# 16 programs a:0, b:1, ... p:15


def spin(programs: str, times: int) -> str:
    for i in range(0, times):
        program = programs[-1]
        programs = program + programs[:-1]
    return programs


def exchange(programs: str, position_1: int, position_2: int) -> str:
    if position_1 == position_2:
        return programs

    if position_2 < position_1:
        position_swap = position_1
        position_1 = position_2
        position_2 = position_swap

    program_1 = programs[position_1]
    program_2 = programs[position_2]

    a = (
        programs[:position_1]
        + program_2
        + programs[position_1 + 1 : position_2]
        + program_1
        + programs[position_2 + 1 :]
    )

    return a


def parter(programs: str, program_1: str, program_2: str) -> str:
    position_1 = programs.index(program_1)
    position_2 = programs.index(program_2)

    return exchange(programs, position_1, position_2)


def get_program_order(programs: str, commands: str) -> str:
    for command in commands.split(","):
        if command.startswith("s"):
            # Spin
            spin_times = int(command[1:])
            programs = spin(programs, spin_times)
        elif command.startswith("x"):
            # Exchange
            [position_1, position_2] = [int(x) for x in command[1:].split("/")]

            programs = exchange(programs, position_1, position_2)
        elif command.startswith("p"):
            # Partner
            [program_1, program_2] = command[1:].split("/")
            programs = parter(programs, program_1, program_2)

    return programs


ONE_BILLION = 1_000_000_000


def get_program_order_billion(programs: str, commands: str) -> str:
    original_programs = programs

    # Find loop
    loop = ONE_BILLION
    for i in range(ONE_BILLION):
        programs = get_program_order(programs, commands)

        if programs == original_programs:
            loop = i + 1
            break

    remaining = ONE_BILLION % loop

    programs = original_programs
    for i in range(0, remaining):
        programs = get_program_order(programs, commands)

    return programs


class Test2024Day16(unittest.TestCase):
    def test_spin(self):
        self.assertEqual(get_program_order("abcde", "s1"), "eabcd")
        self.assertEqual(get_program_order("abcde", "s3"), "cdeab")

    def test_exchange(self):
        self.assertEqual(get_program_order("eabcd", "x3/4"), "eabdc")

    def test_partner(self):
        self.assertEqual(get_program_order("eabdc", "pe/b"), "baedc")

    def test_part_1_test_1(self):
        self.assertEqual(get_program_order("abcde", "s1,x3/4,pe/b"), "baedc")

    def test_part_1(self):
        with open("./input.txt") as f:
            self.assertEqual(
                get_program_order("abcdefghijklmnop", f.read()), "kpfonjglcibaedhm"
            )

    def test_part_2_test_1(self):
        self.assertEqual(get_program_order_billion("abcde", "s1,x3/4,pe/b"), "abcde")

    def test_part_2(self):
        with open("./input.txt") as f:
            self.assertEqual(
                get_program_order_billion("abcdefghijklmnop", f.read()),
                "odiabmplhfgjcekn",
            )


if __name__ == "__main__":
    unittest.main()
