import unittest
from enum import Enum

class Cell(Enum):
    EMPTY = " "

class Map:
    cells: dict[str, Cell]

    def get_key(x: int, y: int) -> str:
        return f"{x},{y}"

    def get(self, x: int, y: int) -> str:
        key = Map.get_key(x, y)
        return self.cells[key]
    
    def set(self, x: int, y: int, cell: Cell):
        key = Map.get_key(x, y)
        self.cells[key] = cell

def parse_map(map_string: str) -> Map:
    map = [list(row) for row in map_string.split("\n")]
    m.c

    return m

def get_path_letters(map_string: str) -> str:
    m = parse_map(map_string)
    

    print(map)
    return ""


class TestDay19(unittest.TestCase):
    test_1 = """     |          
     |  +--+    
     A  |  C    
 F---|----E|--+ 
     |  |  |  D 
     +B-+  +--+ 

"""

    def test_part_one(self):
        self.assertEqual(get_path_letters(TestDay19.test_1), "ABCDEF", "Part 1 Test 1")


if __name__ == "__main__":
    unittest.main()
