package logic;


public interface IGhost {
	/**
	 * Gets the position of the ghost.
	 * @return the coordinates (x,y) of the ghost
	 */
	public int[] getPosition();
	
	/**
	 * Tests whether the ghost can be eaten.
	 * @param type the type of the ghost
	 * @return true if the ghost can be eaten, false otherwise
	 */
	public boolean canBeEaten();
	
	/**
	 * Moves the ghost.
	 * @param type the type of the ghost to move
	 */
	public void move();
	
	/**
	 * Starts spawn timer.
	 */
	public void init();

	/**
	 * Gets the state of the ghost.
	 * @return integer representing state of the ghost
	 */
	public int getState();
}
