package io.akshay.wordSearchAPI.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class WordGridService {

	private enum Direction {
		HORIZONTAL, VERTICAL, DIAGONAL, RHORIZONTAL, RVERTICAL, RDIAGONAL
	}

	class Coordinate {
		private int x;
		private int y;

		public Coordinate(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	private Direction doesFit(String word, Coordinate coordinate, char[][] contents, int gridSize) {
		List<Direction> directions = Arrays.asList(Direction.values());
		Collections.shuffle(directions);
		for (Direction direction : directions) {
			if (doesFit(word, coordinate, direction, contents, gridSize))
				return direction;
		}
		return null;
	}

	private boolean doesFit(String word, Coordinate coordinate, Direction direction, char[][] contents, int gridSize) {
		int x = coordinate.x;
		int y = coordinate.y;
		switch (direction) {
		case HORIZONTAL:
			if (y + word.length() > gridSize)
				return false;

			for (int i = 0; i < word.length(); i++) {
				if (contents[x][y + i] != '_') {
					return false;
				}
			}

			break;
		case VERTICAL:

			if (x + word.length() > gridSize)
				return false;

			for (int i = 0; i < word.length(); i++) {
				if (contents[x + i][y] != '_') {
					return false;
				}
			}
			break;
		case DIAGONAL:

			if (x + word.length() > gridSize || y + word.length() > gridSize)
				return false;

			for (int i = 0; i < word.length(); i++) {
				if (contents[x + i][y + i] != '_') {
					return false;
				}
			}
			break;

		case RHORIZONTAL:
			if (y < word.length())
				return false;

			for (int i = 0; i < word.length(); i++) {
				if (contents[x][y - i] != '_') {
					return false;
				}
			}

			break;
		case RVERTICAL:

			if (x < word.length())
				return false;

			for (int i = 0; i < word.length(); i++) {
				if (contents[x - i][y] != '_') {
					return false;
				}
			}
			break;
		case RDIAGONAL:

			if (x < word.length() || y < word.length())
				return false;

			for (int i = 0; i < word.length(); i++) {
				if (contents[x - i][y - i] != '_') {
					return false;
				}
			}
			break;
		}
		return true;

	}

	private int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}

	private void randomFillGrid(char[][] contents, int gridSize) {
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				if (contents[i][j] == '_') {
					int random = getRandomNumber(0, 26);
					contents[i][j] = (char) (random + 65);
				}
			}
		}
	}

	public char[][] generateGrid(int gridSize, List<String> words) {
		List<Coordinate> coordinates = new ArrayList<>();

		char[][] contents = new char[gridSize][gridSize];

		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				coordinates.add(new Coordinate(i, j));
				contents[i][j] = '_';
			}
		}
		Collections.shuffle(coordinates);

		for (String word : words) {
			for (Coordinate coordinate : coordinates) {
				int x = coordinate.x;
				int y = coordinate.y;
				Direction d = doesFit(word, coordinate, contents, gridSize);
				if (d != null) {
					switch (d) {
					case HORIZONTAL:

						for (char c : word.toCharArray()) {
							contents[x][y++] = c;
						}
						break;
					case VERTICAL:

						for (char c : word.toCharArray()) {
							contents[x++][y] = c;
						}
						break;
					case DIAGONAL:

						for (char c : word.toCharArray()) {
							contents[x++][y++] = c;
						}
						break;
					case RHORIZONTAL:

						for (char c : word.toCharArray()) {
							contents[x][y--] = c;
						}
						break;
					case RVERTICAL:

						for (char c : word.toCharArray()) {
							contents[x--][y] = c;
						}
						break;
					case RDIAGONAL:

						for (char c : word.toCharArray()) {
							contents[x--][y--] = c;
						}
						break;
					}
					break;
				}
			}
		}
		randomFillGrid(contents, gridSize);
		return contents;
	}

}
