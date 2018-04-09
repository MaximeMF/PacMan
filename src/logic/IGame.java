package logic;

import data.*;

/**
 * Interface of Logic class to use in View package.
 * @author Robin Algier - Maxime Mathis--Fumel - Yassin Ourkia
 */
public interface IGame {

	/**
	 * Starts a new game.
	 */
	public void newGame();

	/**
	 * Gets the board.
	 * @return the matrix of the board
	 * @post {@code board[x][y] != null for any x,y < boardWidth, boardHeight}
	 */
	public Entity[][] getBoard();

	/**
	 * Gets the height of the board.
	 * @return the board height
	 * @post {@code boardHeight >= 5}
	 */
	public int getBoardHeight();

	/**
	 * Gets the width of the board.
	 * @return the board width
	 * @post {@code boardWidth >= 5}
	 */
	public int getBoardWidth();

	/**
	 * Gets the speed percent.
	 * @return the speed percent
	 * @post {@code 200 >= speed >= 50}
	 */
	public int getSpeed();

	/**
	 * Gets the best score.
	 * @return the best score
	 * @post {@code bestScore >= 0}
	 */
	public int getBestScore();

	/**
	 * Has PacMan won the level.
	 * @return true if PacMan ate all the dots, false otherwise
	 * @pre {@code gommes >= 0}
	 */
	public boolean hasWon();

	/**
	 * Has PacMan lost.
	 * @return true if PacMan lost all his lives, false otherwise
	 * @pre {@code pacman.lives >= 0}
	 */
	public boolean hasLost();
	
	/**
	 * Tests whether PacMan can move in the desired direction
	 * @param dir the direction PacMan should move towards
	 */
	public boolean canMovePlayer(Direction dir);
	
	/**
	 * Moves PacMan in the desired direction.
	 * @param dir the direction PacMan should move towards
	 */
	public void movePlayer(Direction dir);
	
	/**
	 * Moves the ghost.
	 * @param type the type of the ghost to move
	 */
	public void moveGhost(GhostType type);
	
	/**
	 * Gets the position of PacMan.
	 * @return the position of PacMan
	 * @post {@code pacmanPosition.length = 2 && 0 <= pacmanPosition[0] < boardWidth && 0 <= pacmanPosition[1] < boardHeight}
	 */
	public int[] getPlayerPosition();
	
	/**
	 * Gets the position of a given ghost.
	 * @param type the type of the ghost to get the position from
	 * @return the position of the ghost
	 */
	public int[] getGhostPosition(GhostType type);
	
	/**
	 * Tests whether the ghost can be eaten.
	 * @param type the type of the ghost
	 * @return true if the ghost can be eaten, false otherwise
	 */
	public boolean canGhostBeEaten(GhostType type);
	
	/**
	 * Tests whether power is currently active.
	 * @return true if power is currently active, false otherwise
	 */
	public boolean isPowerActive();
}