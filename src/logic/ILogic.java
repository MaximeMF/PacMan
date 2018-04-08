package logic;

import java.util.EnumMap;

import data.*;

/**
 * Interface of Logic class to use in View package.
 * @author Robin Algier - Maxime Mathis--Fumel - Yassin Ourkia
 */
public interface ILogic {
	
	/**
	 * Tests whether the ghost can move in the given direction
	 * @param type the type of the ghost
	 * @param dir the direction the ghost should move towards
	 * @return true if the ghost can move in the given direction, false otherwise
	 */
	public boolean canMoveGhost(GhostType type, Direction dir);
	
	/**
	 * Tests whether PacMan can move in the given direction
	 * @param dir the direction PacMan should move towards
	 * @return true if PacMan can move in the given direction, false otherwise
	 */
	public boolean canMovePlayer(Direction dir);
	
	/**
	 * Moves a Ghost
	 * @param type the type of the ghost to move
	 * @param dir the direction the ghost should move towards
	 * @post 0 <= ghost.x < boardWidth && 0 <= ghost.y < boardHeight
	 */
	public void moveGhost(GhostType type, Direction dir);
	
	/**
	 * Moves PacMan
	 * @param dir the direction the ghost should move towards
	 * @post 0 <= pacman.x < boardWidth && 0 <= pacman.y < boardHeight
	 */
	public void movePlayer(Direction dir);
	
	/**
	 * Gets the board.
	 * @return the matrix of the board
	 * @post board[x][y] != null for any x,y < boardWidth, boardHeight
	 */
	public Entity[][] getBoard();
	
	/**
	 * Gets the height of the board.
	 * @return the board height
	 * @post boardHeight >= 5
	 */
	public int getBoardHeight();
	
	/**
	 * Gets the width of the board.
	 * @return the board width
	 * @post boardWidth >= 5
	 */
	public int getBoardWidth();
	
	/**
	 * Gets the position of Pacman on the board.
	 * @return the Pacman position
	 * @post pacmanPosition.length = 2 && 0 <= pacmanPosition[0] < boardWidth && 0 <= pacmanPosition[1] < boardHeight
	 */
	public int[] getPacmanPosition();
	
	/**
	 * Gets the position of the ghosts on the board.
	 * @return the ghosts position
	 * @post int[].length() = 2 && 0 <= int[0] < boardWidth && 0 <= int[1] < boardHeight
	 */
	public EnumMap<GhostType, int[]> getGhostsPosition();
	
	/**
	 * Gets the speed percent.
	 * @return the speed percent
	 * @post 200 >= speed >= 50
	 */
	public int getSpeed();
	
	/**
	 * Gets the best score.
	 * @return the best score
	 * @post bestScore >= 0
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
