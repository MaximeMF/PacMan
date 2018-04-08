package logic;

import data.GhostType;

public interface IGhost {
	
	/**
	 * Tests whether the ghost can move in the given direction
	 * @param dir the direction the ghost should move towards
	 * @return true if the ghost can move in the given direction, false otherwise
	 */
	public boolean canMove(Direction dir);
	
	/**
	 * Moves the ghost.
	 * @param dir the direction the ghost should move towards
	 * @post {@code 0 <= ghost.x < boardWidth && 0 <= ghost.y < boardHeight}
	 */
	public void move(Direction dir);
	
	/**
	 * Tests whether the ghost has been eaten
	 * @return true if the ghost has been eaten, false otherwise
	 */
	public boolean isDead();
	
	/**
	 * Gets the position of the ghost on the board.
	 * @return the ghost position
	 * @post {@code int[].length() = 2 && 0 <= int[0] < boardWidth && 0 <= int[1] < boardHeight}
	 */
	public int[] getPosition();
}
