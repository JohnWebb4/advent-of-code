import unittest


def get_spinlock_value_after_last(step_size: int) -> int:
    spinlock = {0: 0}
    current_point = 0

    for i in range(1, 2017 + 1):
        next_point = current_point
        for _ in range(step_size):
            next_point = spinlock[next_point]
        swap_point = spinlock[next_point]
        spinlock[next_point] = i
        spinlock[i] = swap_point

        current_point = i

    return spinlock[current_point]


def get_spinlock_value_after_0(step_size: int, num_points=2017) -> int:
    # TLDR: Just keep track of the the value after zero
    # and the current index for inserting new elements
    value_after_0 = 0
    spinlock_size = 1
    current_index = 0

    for i in range(1, num_points + 1):
        current_index = (current_index + step_size) % spinlock_size

        if current_index == 0:
            value_after_0 = i

        current_index += 1
        spinlock_size += 1

    return value_after_0


class TestDay17(unittest.TestCase):
    def test_part_1(self):
        self.assertEqual(get_spinlock_value_after_last(3), 638)
        self.assertEqual(get_spinlock_value_after_last(380), 204)

    def test_part_2(self):
        self.assertEqual(get_spinlock_value_after_0(3), 1226)
        self.assertEqual(get_spinlock_value_after_0(380), 665)

        # Takes about 5 seconds
        self.assertEqual(get_spinlock_value_after_0(380, 50_000_000), 28_954_211)


if __name__ == "__main__":
    unittest.main()
