import unittest
from enum import Enum

class ParseCellTypeError(ValueError):
    title = "Failed to parse value to cell type"
    message = ""

    def __init__(self, *args):
        super().__init__(*args)
        self.message = args

class CellType(Enum):
    EMPTY = " "
    VERTICAL = "|"
    HORIZONTAL = "-"
    CROSS = "+"
    LETTER = "A"

    @staticmethod
    def parse(s: str):
        if s == " ":
            return CellType.EMPTY
        elif s == "|":
            return CellType.VERTICAL
        elif s == "-":
            return CellType.HORIZONTAL
        elif s == "+":
            return CellType.CROSS
        elif str.isalpha(s):
            return CellType.LETTER
        else:
            raise ParseCellTypeError(f"Parsing: {s}")
        

class Cell:
    type: CellType = CellType.EMPTY
    data: dict[str, str] = {}

    def __init__(self, type: CellType, data: dict[str, str]):
        self.type = type
        self.data = data

    def __str__(self):
        return f"Cell:{self.type},{self.data}"

    @staticmethod
    def parse(s: str):
        type = CellType.parse(s)

        if type == CellType.LETTER:
            return Cell(type, {"letter": s})
        
        return Cell(type, {})


class Map:
    cells: dict[str, Cell]
    x_max: int
    y_max: int

    @staticmethod
    def parse(map_string: str):
        rows = [list(row) for row in map_string.split("\n")]
        cells: dict[str, Cell] = {}
        x_max = 0
        y_max = 0

        for y, row in enumerate(rows):
            if y > y_max:
                y_max = y

            for x, c in enumerate(row):
                if x > x_max:
                    x_max = x

                key: str = Map.get_key(x, y)
                cells[key] = Cell.parse(c)

        m = Map(cells, x_max, y_max)

        return m

    def __init__(self, cells: dict[str, Cell], x_max: int, y_max: int):
        self.cells = cells
        self.x_max = x_max
        self.y_max = y_max
        pass

    def __str__(self):
        return f"Map:{self.x_max},{self.y_max}:{self.cells}"        

    def get_key(x: int, y: int) -> str:
        return f"{x},{y}"

    def get(self, x: int, y: int) -> Cell:
        key = Map.get_key(x, y)
        return self.cells[key]
    
    def set(self, x: int, y: int, cell: Cell):
        key = Map.get_key(x, y)
        self.cells[key] = cell

def get_path_letters(map_string: str) -> str:
    m = Map.parse(map_string)

    print(m)

    # start point is a vertical line on the top row
    start_x = 0
    for x in range(m.x_max+1):
        if m.get(x, 0).type == CellType.VERTICAL:
            start_x = x
            break

    letters = []

    pos: tuple[int, int] = (start_x, 0)
    dir: tuple[int, int] = (0, 1)

    next: tuple[int, int] = (pos[0] + dir[0], pos[1] + dir[1])

    while
    
    return ''.join(letters)


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
