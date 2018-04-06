package logic;

import data.*;

/**
 * Interface of Logic class to use in View package.
 * @author Robin Algier - Maxime Mathis--Fumel - Yassin Ourkia
 */
public interface ILogic {
	/**
	 * Moves Pacman or a Ghost
	 * @param id an integer representing the id of the object to move
	 * @param dir an integer representing the direction
	 * @return true if the object succeeded to move, false otherwise
	 */
	public boolean move(int id, int dir);
	
	/**
	 * Gets Plateau.
	 * @return Plateau
	 */
	public int[][] getPlateau();
	
	/**
	 * Gets PacMan
	 * @return PacMan
	 */
	public PacMan getPlayer();
	
	/**
	 * Gets Ghost
	 * @return Ghost
	 */
	public Ghost getGhost(GhostType type);
	
	/**
	 * Has PacMan won the level
	 * @return true if PacMan ate all the dots, false otherwise
	 */
	public boolean hasWon();
}
