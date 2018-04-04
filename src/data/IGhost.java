package data;

/**
 * Interface of Ghost class to use in Logic package.
 * @author Robin Algier - Maxime Mathis--Fumel - Yassin Ourkia
 */
public interface IGhost {
	
	/**
	 * Gets the points.
	 * @return an integer representing the points
	 */
	public int getPoints();
	
	/**
	 * Gets the id.
	 * @return an integer representing the id
	 */
	public GhostType getType();
	
	/**
	 * Gets the state.
	 * @return an integer representing the state
	 */
	public int getState();
	
	/**
	 * Sets the state.
	 * @param state an integer representing the new state
	 */
	public void setState(int state);
}
