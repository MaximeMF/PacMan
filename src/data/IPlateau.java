package data;

/**
 * Interface of Plateau class to use in Logic package.
 * @author Robin Algier - Maxime Mathis--Fumel - Yassin Ourkia
 */
public interface IPlateau {
	
	
	
	public final int MUR = 0;
	public final int VIDE = 1;
	public final int GOMME = 2;
	public final int FRUIT = 3;
	public final int TELEPORT = 4;
	public final int PACMAN = 5;
	public final int GHOST = 6;
	
	public final int HEIGHT = 31;
	public final int WIDTH = 28;
		
	
	
	/**
	 * Gets the score.
	 * @return an integer representing the score
	 */
	public int getScore();
	
	
	
	/**
	 * Increases the score.
	 * @param score an integer representing the value to add to the score
	 */
	public void increasesScore(int score);
	
	
	
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
	 * Gets a cell.
	 * @param x the abscissa of the cell
	 * @param y the ordinate of the cell
	 * @return the value of the cell
	 */
	public int getCell(int x, int y);
	
	
	
	/**
	 * Changes a cell value.
	 * @param x the abscissa of the cell
	 * @param y the ordinate of the cell
	 * @param value the new value of the cell
	 */
	public void changeCell(int x, int y, int value);
	
	
	
	/**
	 * Gets the previous value of a cell.
	 * @param x the abscissa of the cell
	 * @param y the ordinate of the cell
	 * @return the previous value of the cell
	 */
	public int previous(int x, int y);
	
	
	
	/**
	 * Gets the position of a specific element.
	 * @param id the id of the element
	 * @return an integer array representing the abscissa and the ordinate of the element
	 */
	public int[] getPos(int id);
	
	
	
	/**
	 * Gets the number of Gomme.
	 * @return an integer representing the number of Gomme
	 */
	public int nbGommes();


}
