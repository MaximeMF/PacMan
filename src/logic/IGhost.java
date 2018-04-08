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
	 * Gets the ghosts PacMan just ate.
	 * @return the type of the ghosts PacMan ate or null if none was eaten
	 */
	public GhostType[] isDead();
	
	/**
	 * Gets the position of the ghost on the board.
	 * @return the ghost position
	 * @post {@code int[].length() = 2 && 0 <= int[0] < boardWidth && 0 <= int[1] < boardHeight}
	 */
	public int[] getPosition();
}
