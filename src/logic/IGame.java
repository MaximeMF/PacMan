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
	 * Gets PacMan object.
	 * @return PacMan object
	 */
	public IPacMan getPlayer();
	
	/**
	 * Gets Ghost object.
	 * @param type the type of the ghost
	 * @return Ghost object of type type
	 */
	public IGhost getGhost(GhostType type);
}