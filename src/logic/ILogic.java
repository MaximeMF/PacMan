package logic;

import java.util.EnumMap;

import data.*;

/**
 * Interface of Logic class to use in View package.
 * @author Robin Algier - Maxime Mathis--Fumel - Yassin Ourkia
 * @inv pacman.lives >= 0
 */
public interface ILogic {
	
	/**
	 * Moves a Ghost
	 * @param type the type of the ghost to move
	 * @param dir the direction the ghost should move towards
	 * @return true if the ghost succeeded to move, false otherwise
	 * @post 0 <= ghost.x < boardWidth && 0 <= ghost.y < boardHeight
	 */
	public boolean moveGhost(GhostType type, Direction dir);
	
	/**
	 * Moves PacMan
	 * @param dir the direction the ghost should move towards
	 * @return true if PacMan succeeded to move, false otherwise
	 * @post 0 <= pacman.x < boardWidth && 0 <= pacman.y < boardHeight
	 */
	public boolean movePlayer(Direction dir);
	
	/**
	 * @see IDataLoader#getBoard()
	 */
	public Entity[][] getBoard();
	
	/**
	 * @see IDataLoader#getBoardHeight()
	 */
	public int getBoardHeight();
	
	/**
	 * @see IDataLoader#getBoardWidth()
	 */
	public int getBoardWidth();
	
	/**
	 * @see IDataLoader#getPacmanPosition()
	 */
	public int[] getPacmanPosition();
	
	/**
	 * @see IDataLoader#getGhostsPosition()
	 */
	public EnumMap<GhostType, int[]> getGhostsPosition();
	
	/**
	 * @see IDataLoader#getSpeed()
	 */
	public int getSpeed();
	
	/**
	 * @see IDataLoader#getBestScore()
	 */
	public int getBestScore();
	
	/**
	 * Gets the current score.
	 * @return current score
	 * @post score >= 0
	 */
	public int getScore();
	
	/**
	 * Gets the current level.
	 * @return current level
	 * @post 5 >= level >= 1
	 */
	public int getLevel();
	
	/**
	 * Has PacMan won the level
	 * @return true if PacMan ate all the dots, false otherwise
	 * @pre gommes >= 0
	 */
	public boolean hasWon();
	
	/**
	 * Has PacMan lost
	 * @return true if PacMan lost all his lives, false otherwise
	 * @pre pacman.lives >= 0
	 */
	public boolean hasLost();
}
