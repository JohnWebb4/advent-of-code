import unittest
from enum import Enum

class Cell(Enum):
    EMPTY = " "

    def parse(s: str):
        if s == " ":
            return Cell.EMPTY


class Map:
    cells: dict[str, Cell]

    @staticmethod
    def parse(map_string: str):
        rows = [list(row) for row in map_string.split("\n")]
        cells: dict[str, Cell] = {}

        for y, row in enumerate(rows):
            for x, c in enumerate(row):
                key = Map.get_key(x, y)
                cells[key] = c

        m = Map(cells)
        
        print(m)

        return m

    def __init__(self, cells: dict[str, Cell]):
        self.cells = cells
        pass

    def __str__(self):
        return f"Map:{self.cells}"        

    def get_key(x: int, y: int) -> str:
        return f"{x},{y}"

    def get(self, x: int, y: int) -> str:
        key = Map.get_key(x, y)
        return self.cells[key]
    
    def set(self, x: int, y: int, cell: Cell):
        key = Map.get_key(x, y)
        self.cells[key] = cell

def get_path_letters(map_string: str) -> str:
    m = Map.parse(map_string)
    

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
