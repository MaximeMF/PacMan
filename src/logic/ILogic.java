package logic;

import data.Ghost;
import data.Pacman;
import data.Plateau;

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
	public Plateau getPlateau();
	
	/**
	 * Gets Pacman.
	 * @return Pacman
	 */
	public Pacman getPlayer();
	
	/**
	 * Gets a Ghost.
	 * @param id an integer representing the id of the ghost
	 * @return a Ghost
	 */
	public Ghost getGhost(int id);
}
