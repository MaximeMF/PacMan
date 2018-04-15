package logic;

/**
 * Enumeration representing the directions.
 * @author Robin ALGIER - Maxime MATHIS--FUMEL - Yassin OURKIA
 */
public enum Direction {

	UP,
	LEFT,
	RIGHT,
	DOWN;

	/**
	 * @param dir
	 * @return Direction
	 */
	public static Direction opposite(Direction dir) {
		switch(dir) {
		case UP:
			return DOWN;
		case DOWN:
			return UP;
		case LEFT:
			return RIGHT;
		case RIGHT:
			return LEFT;
		}
		return null;
	}

}