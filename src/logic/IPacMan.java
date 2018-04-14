package logic;

public interface IPacMan {
	/**
	 * Gets the current score.
	 * @return the current score
	 * @post {@code score >= 0}
	 */
	public int getScore();

	/**
	 * Gets the remaining lives.
	 * @return the remaining lives
	 * @post {@code lives >= 0}
	 */
	public int getLives();

	/**
	 * Gets the current level.
	 * @return the current level
	 * @post {@code 1 <= level <= 5}
	 */
	public int getLevel();
	
	/**
	 * Tests whether power is currently active.
	 * @return true if power is currently active, false otherwise
	 */
	public boolean isPowered();
	
	/**
	 * Gets the position of PacMan.
	 * @return the position of PacMan
	 * @post {@code pacmanPosition.length = 2 && 0 <= pacmanPosition[0] < boardWidth && 0 <= pacmanPosition[1] < boardHeight}
	 */
	public int[] getPosition();
	
	/**
	 * Moves PacMan in the desired direction.
	 * @param dir the direction PacMan should move towards
	 */
	public void move(Direction dir);
	
	/**
	 * Tests whether PacMan can move in the desired direction
	 * @param dir the direction PacMan should move towards
	 */
	public boolean canMove(Direction dir);
}
