/* Licensed under Apache-2.0 */
package advent.year2020.day24;

import java.util.*;

public class LobbyLayout {
	public static int getFlippedTileCount(String input) {
		String[] instructions = input.split("\n");

		Set<String> flippedTileSet = new HashSet<>();

		for (String instruction : instructions) {
			String[] chars = instruction.split("");
			StringBuilder command = new StringBuilder();

			float x = 0;
			float y = 0;

			for (String character : chars) {
				command.append(character);

				switch (command.toString()) {
					case "ne":
						x += 0.5;
						y++;
						command = new StringBuilder();
						break;
					case "e":
						x++;
						command = new StringBuilder();
						break;
					case "se":
						x += 0.5;
						y--;
						command = new StringBuilder();
						break;
					case "nw":
						x -= 0.5;
						y++;
						command = new StringBuilder();
						break;
					case "w":
						x--;
						command = new StringBuilder();
						break;
					case "sw":
						x -= 0.5;
						y--;
						command = new StringBuilder();
						break;
					default:
						break;
				}
			}

			String positionKey = String.format("%s,%s", x, y);

			if (flippedTileSet.contains(positionKey)) {
				flippedTileSet.remove(positionKey);
			} else {
				flippedTileSet.add(positionKey);
			}
		}

		return flippedTileSet.size();
	}

	public static int getFlippedTileCountAfterXDays(int days, String input) {
		String[] instructions = input.split("\n");

		Set<String> flippedTileSet = new HashSet<>();

		for (String instruction : instructions) {
			String[] chars = instruction.split("");
			StringBuilder command = new StringBuilder();

			float x = 0;
			float y = 0;

			for (String character : chars) {
				command.append(character);

				switch (command.toString()) {
					case "ne":
						x += 0.5;
						y++;
						command = new StringBuilder();
						break;
					case "e":
						x++;
						command = new StringBuilder();
						break;
					case "se":
						x += 0.5;
						y--;
						command = new StringBuilder();
						break;
					case "nw":
						x -= 0.5;
						y++;
						command = new StringBuilder();
						break;
					case "w":
						x--;
						command = new StringBuilder();
						break;
					case "sw":
						x -= 0.5;
						y--;
						command = new StringBuilder();
						break;
					default:
						break;
				}
			}

			String positionKey = String.format("%s,%s", x, y);

			if (flippedTileSet.contains(positionKey)) {
				flippedTileSet.remove(positionKey);
			} else {
				flippedTileSet.add(positionKey);
			}
		}

		for (int i = 0; i < days; i++) {
			Map<String, Integer> adjacentFlippedCountMap = new HashMap<>();

			for (String flippedTile : flippedTileSet) {
				String[] coordinates = flippedTile.split(",");
				float x = Float.parseFloat(coordinates[0]);
				float y = Float.parseFloat(coordinates[1]);

				List<String> adjacentTiles = new LinkedList<>();
				adjacentTiles.add(String.format("%s,%s", x + 0.5, y + 1));
				adjacentTiles.add(String.format("%s,%s", x + 1, y));
				adjacentTiles.add(String.format("%s,%s", x + 0.5, y - 1));
				adjacentTiles.add(String.format("%s,%s", x - 0.5, y + 1));
				adjacentTiles.add(String.format("%s,%s", x - 1, y));
				adjacentTiles.add(String.format("%s,%s", x - 0.5, y - 1));

				for (String adjacentTile : adjacentTiles) {
					if (adjacentFlippedCountMap.containsKey(adjacentTile)) {
						adjacentFlippedCountMap.put(adjacentTile, adjacentFlippedCountMap.get(adjacentTile) + 1);
					} else {
						adjacentFlippedCountMap.put(adjacentTile, 1);
					}
				}
			}

			Set<String> newFlippedTiles = new HashSet<>();

			for (Map.Entry<String, Integer> adjacentFlippedEntry : adjacentFlippedCountMap.entrySet()) {
				if (flippedTileSet.contains(adjacentFlippedEntry.getKey())) {
					// Currently flipped
					if (adjacentFlippedEntry.getValue() == 1 || adjacentFlippedEntry.getValue() == 2) {
						newFlippedTiles.add(adjacentFlippedEntry.getKey());
					}
				} else {
					if (adjacentFlippedEntry.getValue() == 2) {
						newFlippedTiles.add(adjacentFlippedEntry.getKey());
					}
				}
			}

			flippedTileSet = newFlippedTiles;
		}

		return flippedTileSet.size();
	}
}
