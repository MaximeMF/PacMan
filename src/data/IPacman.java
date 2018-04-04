package data;

/**
 * Interface of Pacman class to use in Logic package.
 * @author Robin Algier - Maxime Mathis--Fumel - Yassin Ourkia
 */
public interface IPacman {
	
	/**
	 * Gets Pacman lives.
	 * @return an integer representing the lives left
	 */
	public int getLives();
	
	/**
	 * Makes Pacman die.
	 */
	public void die();
}
