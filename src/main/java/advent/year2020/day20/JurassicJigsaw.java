/* Licensed under Apache-2.0 */
package advent.year2020.day20;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class JurassicJigsaw {
	public static class Block {
		public final static Pattern grabId = Pattern.compile("Tile (\\d*):");

		public final int id;
		public final String characters;

		public Block(int id, String characters) {
			this.id = id;
			this.characters = characters;
		}

		public static Block parseBlock(String input) {
			String[] parts = input.split("\n");
			Matcher idMatch = Block.grabId.matcher(parts[0]);

			idMatch.find();

			int id = Integer.parseInt(idMatch.group(1));
			String characters = String.join("\n", Arrays.copyOfRange(parts, 1, parts.length));


			return new Block(id, characters);
		}

		public static Stream<Block> getAllRotations(Block block) {
			List<Block> blocks = new LinkedList<>();

			for (int i = 0; i < 4; i++) {
				blocks.add(block.rotate(i));
			}

			Block flipped = block.flip();

			for (int i = 0; i < 4; i++) {
				blocks.add(flipped.rotate(i));
			}

			return blocks.stream();
		}

		public Block rotate(int n) {
			String[][] characters = Arrays.stream(this.characters.split("\n")).map((s) -> s.split("")).toArray(String[][]::new);

			for (int i = 0; i < n % 4; i++) {
				String[][] newCharacters = new String[characters.length][];

				for (int j = 0; j < newCharacters.length; j++) {
					newCharacters[j] = new String[characters[j].length];
				}

				for (int j = 0; j < newCharacters.length; j++) {
					for (int k = 0; k < characters[j].length; k++) {
						newCharacters[j][k] = characters[newCharacters.length - k - 1][j];
					}
				}

				characters = newCharacters;
			}

			return new Block(this.id, String.join("\n", Arrays.stream(characters).map((a) -> String.join("", a)).toArray(String[]::new)));
		}

		public Block flip() {
			String[] flipped = Arrays.stream(this.characters.split("\n")).map((s) -> new StringBuilder(s).reverse().toString()).toArray(String[]::new);

			return new Block(this.id, String.join("\n", flipped));
		}
	}

	public static long solveAndProductOfCorners(String input) {
		String[] blockParts = input.split("\n\n");
		Block[] blocks = Arrays.stream(blockParts).map(Block::parseBlock).flatMap(Block::getAllRotations).toArray(Block[]::new);
		Map<Block, Block[]> toRight = new HashMap<>();
		Map<Block, Block[]> toBottom = new HashMap<>();

		for (Block block : blocks) {
			String[] rows = block.characters.split("\n");
			String bottom = rows[rows.length - 1];
			String right = String.join("", Arrays.stream(rows).map((s) -> s.substring(s.length() - 1)).toArray(String[]::new));
			List<Block> mapMatchingBottom = new LinkedList<>();
			List<Block> mapMatchingRight = new LinkedList<>();

			for (Block matchingBlock : blocks) {
				if (block.id != matchingBlock.id) {
					String[] matchingRows = matchingBlock.characters.split("\n");
					String matchingTop = matchingRows[0];
					String matchingLeft = String.join("", Arrays.stream(matchingRows).map((s) -> s.substring(0, 1)).toArray(String[]::new));

					if (bottom.equals(matchingTop)) {
						mapMatchingBottom.add(matchingBlock);
					}

					if (right.equals(matchingLeft)) {
						mapMatchingRight.add(matchingBlock);
					}
				}
			}

			toBottom.put(block, mapMatchingBottom.toArray(Block[]::new));
			toRight.put(block, mapMatchingRight.toArray(Block[]::new));
		}

		Stack<Block[]> toCheck = new Stack<>();

		for (Block block : blocks) {
			toCheck.add(new Block[]{block});
		}

		int blockSize = blocks.length / 8;
		int rowSize = (int) Math.sqrt(blockSize);

		while (toCheck.size() > 0) {
			Block[] picture = toCheck.pop();
			Block leftBlock = picture[picture.length - 1];
			if (picture.length % rowSize == 0) {
				leftBlock = null;
			}

			Block aboveBlock = null;
			if (picture.length >= rowSize) {
				aboveBlock = picture[picture.length - rowSize];
			}

			Block[] belowBlocks = toBottom.get(aboveBlock);
			Integer[] ids = Arrays.stream(picture).map((b) -> b.id).toArray(Integer[]::new);
			Block[] possibleBlocks = toRight.get(leftBlock);
			if (leftBlock == null) {
				possibleBlocks = Arrays.stream(blocks).filter((b) -> Arrays.stream(ids).allMatch((id) -> b.id != id)).toArray(Block[]::new);
			}

			if (belowBlocks != null) {
				possibleBlocks = Arrays.stream(possibleBlocks).filter((Block b) -> Arrays.asList(belowBlocks).contains(b)).toArray(Block[]::new);
			}

			if (possibleBlocks != null && possibleBlocks.length > 0) {
				for (Block nextBlock : possibleBlocks) {
					Block[] nextPicture = Arrays.copyOfRange(picture, 0, picture.length + 1);
					nextPicture[nextPicture.length - 1] = nextBlock;

					if (nextPicture.length == blockSize) {
						// Found solution
						long cornerProduct = nextPicture[0].id;
						cornerProduct *= nextPicture[rowSize - 1].id;
						cornerProduct *= nextPicture[blockSize - rowSize].id;
						cornerProduct *= nextPicture[blockSize - 1].id;

						return cornerProduct;
					}

					toCheck.add(nextPicture);
				}
			}
		}

		return 0;
	}

	public static int calculateRoughWaters(String input) {
		String[] blockParts = input.split("\n\n");
		Block[] blocks = Arrays.stream(blockParts).map(Block::parseBlock).flatMap(Block::getAllRotations).toArray(Block[]::new);
		Map<Block, Block[]> toRight = new HashMap<>();
		Map<Block, Block[]> toBottom = new HashMap<>();

		for (Block block : blocks) {
			String[] rows = block.characters.split("\n");
			String bottom = rows[rows.length - 1];
			String right = String.join("", Arrays.stream(rows).map((s) -> s.substring(s.length() - 1)).toArray(String[]::new));
			List<Block> mapMatchingBottom = new LinkedList<>();
			List<Block> mapMatchingRight = new LinkedList<>();

			for (Block matchingBlock : blocks) {
				if (block.id != matchingBlock.id) {
					String[] matchingRows = matchingBlock.characters.split("\n");
					String matchingTop = matchingRows[0];
					String matchingLeft = String.join("", Arrays.stream(matchingRows).map((s) -> s.substring(0, 1)).toArray(String[]::new));

					if (bottom.equals(matchingTop)) {
						mapMatchingBottom.add(matchingBlock);
					}

					if (right.equals(matchingLeft)) {
						mapMatchingRight.add(matchingBlock);
					}
				}
			}

			toBottom.put(block, mapMatchingBottom.toArray(Block[]::new));
			toRight.put(block, mapMatchingRight.toArray(Block[]::new));
		}

		Stack<Block[]> toCheck = new Stack<>();

		for (Block block : blocks) {
			toCheck.add(new Block[]{block});
		}

		int blockSize = blocks.length / 8;
		int rowSize = (int) Math.sqrt(blockSize);

		Block[] solvedPicture = null;

		while (toCheck.size() > 0) {
			if (solvedPicture != null) {
				break;
			}

			Block[] picture = toCheck.pop();
			Block leftBlock = picture[picture.length - 1];
			if (picture.length % rowSize == 0) {
				leftBlock = null;
			}

			Block aboveBlock = null;
			if (picture.length >= rowSize) {
				aboveBlock = picture[picture.length - rowSize];
			}

			Block[] belowBlocks = toBottom.get(aboveBlock);
			Integer[] ids = Arrays.stream(picture).map((b) -> b.id).toArray(Integer[]::new);
			Block[] possibleBlocks = toRight.get(leftBlock);
			if (leftBlock == null) {
				possibleBlocks = Arrays.stream(blocks).filter((b) -> Arrays.stream(ids).allMatch((id) -> b.id != id)).toArray(Block[]::new);
			}

			if (belowBlocks != null) {
				possibleBlocks = Arrays.stream(possibleBlocks).filter((Block b) -> Arrays.asList(belowBlocks).contains(b)).toArray(Block[]::new);
			}

			if (possibleBlocks != null && possibleBlocks.length > 0) {
				for (Block nextBlock : possibleBlocks) {
					Block[] nextPicture = Arrays.copyOfRange(picture, 0, picture.length + 1);
					nextPicture[nextPicture.length - 1] = nextBlock;

					if (nextPicture.length == blockSize) {
						// Found solution

						solvedPicture = nextPicture;
					} else {
						toCheck.add(nextPicture);
					}
				}
			}
		}

		if (solvedPicture == null) {
			return 0;
		}

		StringBuilder seaBuilder = new StringBuilder();
		for (int rowIndex = 0; rowIndex < rowSize; rowIndex++) {
			String[] trimmedRows = new String[solvedPicture[rowIndex * rowSize].characters.split("\n").length - 2];

			for (int cellIndex = 0; cellIndex < rowSize; cellIndex++) {
				String[] newRows = solvedPicture[rowSize * rowIndex + cellIndex].characters.split("\n");

				for (int i = 0; i < trimmedRows.length; i++) {
					trimmedRows[i] = (trimmedRows[i] != null ? trimmedRows[i] : "") + newRows[i + 1].substring(1, newRows[i + 1].length() - 1);
				}
			}

			seaBuilder.append(String.join("\n", trimmedRows));

			if (rowIndex < rowSize - 1) {
				seaBuilder.append("\n");
			}
		}

		return calculateRoughWatersFromSea(seaBuilder.toString());
	}

	public static int calculateRoughWatersFromSea(String seaString) {
		String[][] sea = Arrays.stream(seaString.split("\n")).map((s) -> s.split("")).toArray(String[][]::new);

		// Find sea monster orientation
		boolean hasFoundMatch = false;
		for (int flipIndex = 0; flipIndex < 2; flipIndex++) {
			if (hasFoundMatch) {
				break;
			}

			if (flipIndex == 1) {
				String[] rows = Arrays.stream(sea).map((String[] s) -> String.join("", s)).toArray(String[]::new);
				sea = Arrays.stream(rows).map((String s) -> new StringBuilder(s).reverse().toString().split("")).toArray(String[][]::new);
			}


			for (int rotateIndex = 0; rotateIndex < 3; rotateIndex++) {
				if (hasFoundMatch) {
					break;
				}

				String[][] newSea = new String[sea.length][];

				for (int j = 0; j < newSea.length; j++) {
					newSea[j] = new String[sea[j].length];
				}

				for (int j = 0; j < newSea.length; j++) {
					for (int k = 0; k < newSea[j].length; k++) {
						newSea[j][k] = sea[newSea.length - k - 1][j];
					}
				}

				sea = newSea;

				for (int i = 2; i < sea.length - 2; i++) {
					for (int j = 1; j < sea[i].length - 19; j++) {


						if (!sea[i][j].equals("#")) {
							continue;
						} else if (!sea[i + 1][j + 1].equals("#")) {
							continue;
						} else if (!sea[i + 1][j + 4].equals("#")) {
							continue;
						} else if (!sea[i][j + 5].equals("#")) {
							continue;
						} else if (!sea[i][j + 6].equals("#")) {
							continue;
						} else if (!sea[i + 1][j + 7].equals("#")) {
							continue;
						} else if (!sea[i + 1][j + 10].equals("#")) {
							continue;
						} else if (!sea[i][j + 11].equals("#")) {
							continue;
						} else if (!sea[i][j + 12].equals("#")) {
							continue;
						} else if (!sea[i + 1][j + 13].equals("#")) {
							continue;
						} else if (!sea[i + 1][j + 16].equals("#")) {
							continue;
						} else if (!sea[i][j + 17].equals("#")) {
							continue;
						} else if (!sea[i][j + 18].equals("#")) {
							continue;
						} else if (!sea[i - 1][j + 18].equals("#")) {
							continue;
						} else if (!sea[i][j + 19].equals("#")) {
							continue;
						}

						hasFoundMatch = true;
					}
				}
			}
		}

		for (int i = 2; i < sea.length - 2; i++) {
			for (int j = 1; j < sea[i].length - 19; j++) {
				if (!sea[i][j].equals("#")) {
					continue;
				} else if (!sea[i + 1][j + 1].equals("#")) {
					continue;
				} else if (!sea[i + 1][j + 4].equals("#")) {
					continue;
				} else if (!sea[i][j + 5].equals("#")) {
					continue;
				} else if (!sea[i][j + 6].equals("#")) {
					continue;
				} else if (!sea[i + 1][j + 7].equals("#")) {
					continue;
				} else if (!sea[i + 1][j + 10].equals("#")) {
					continue;
				} else if (!sea[i][j + 11].equals("#")) {
					continue;
				} else if (!sea[i][j + 12].equals("#")) {
					continue;
				} else if (!sea[i + 1][j + 13].equals("#")) {
					continue;
				} else if (!sea[i + 1][j + 16].equals("#")) {
					continue;
				} else if (!sea[i][j + 17].equals("#")) {
					continue;
				} else if (!sea[i][j + 18].equals("#")) {
					continue;
				} else if (!sea[i - 1][j + 18].equals("#")) {
					continue;
				} else if (!sea[i][j + 19].equals("#")) {
					continue;
				}

				sea[i][j] = "O";
				sea[i + 1][j + 1] = "O";
				sea[i + 1][j + 4] = "O";
				sea[i][j + 5] = "O";
				sea[i][j + 6] = "O";
				sea[i + 1][j + 7] = "O";
				sea[i + 1][j + 10] = "O";
				sea[i][j + 11] = "O";
				sea[i][j + 12] = "O";
				sea[i + 1][j + 13] = "O";
				sea[i + 1][j + 16] = "O";
				sea[i][j + 17] = "O";
				sea[i][j + 18] = "O";
				sea[i - 1][j + 18] = "O";
				sea[i][j + 19] = "O";
			}
		}

		int count = 0;
		for (String[] row : sea) {
			for (String cell : row) {
				if (cell.equals("#")) {
					count++;
				}
			}
		}

		return count;
	}
}
