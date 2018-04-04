package data;

/**
 * Interface of Plateau class to use in Logic package.
 * @author Robin Algier - Maxime Mathis--Fumel - Yassin Ourkia
 */
public interface IPlateau {
	
	/**
	 * Gets the score.
	 * @return an integer representing the score
	 */
	public int getScore();
	
	/**
	 * Increases the score.
	 * @param score an integer representing the value to add to the score
	 */
	public void increaseScore(int score);
	
	/**
	 * Gets the level.
	 * @return an integer representing the level
	 */
	public int getLevel();
	
	/**
	 * Increments the level.
	 */
	public void incrementsLevel();
	
	/**
	 * Gets the speed.
	 * @return an integer representing the speed
	 */
	public int getSpeed();
	
	/**
	 * Sets the speed.
	 * @param speed an integer representing the new speed
	 */
	public void setSpeed(int speed);
	
	/**
	 * Gets PacMan.
	 * @return PacMan
	 */
	public Pacman getPlayer();
	
	/**
	 * Gets a Ghost.
	 * @param id an id representing the Ghost to get
	 * @return the Ghost corresponding to the id
	 */
	public Ghost getGhost(int id);
	
	/**
	 * Changes a cell value.
	 * @param x the abscissa of the cell
	 * @param y the ordinate of the cell
	 * @param value the new value of the cell
	 */
	public void changeCell(int x, int y, int value);

}
