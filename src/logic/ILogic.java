package logic;

import data.*;

/**
 * Interface of Logic class to use in View package.
 * @author Robin Algier - Maxime Mathis--Fumel - Yassin Ourkia
 */
public interface ILogic {
	
	public final int DIR_LEFT = 1, DIR_RIGHT = 2, DIR_UP = 0, DIR_DOWN = 3;
	
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
	public IPlateau getPlateau();
	
	/**
	 * @see IPlateau#getPlayer()
	 */
	public IPacman getPlayer();
	
	/**
	 * @see IPlateau#getGhost(int)
	 */
	public IGhost getGhost(int id);
}
