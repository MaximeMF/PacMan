package logic;


public interface IGhost {
	/**
	 * Gets the position of a given ghost.
	 * @param type the type of the ghost to get the position from
	 * @return the position of the ghost
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
}
