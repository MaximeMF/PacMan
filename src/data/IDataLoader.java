package data;

import java.util.EnumMap;

import enums.Entity;
import enums.GhostType;

/**
 * Interface providing methods that can be used by the Logic package.
 * @author Robin Algier - Maxime Mathis--Fumel - Yassin Ourkia
 */
public interface IDataLoader {
	
	/**
	 * Gets the height of the board.
	 * @param lvl the level of the board
	 * @return the board height
	 * @post {@code boardHeight >= 5}
	 */
	public int getBoardHeight(int lvl);
	
	/**
	 * Gets the width of the board.
	 * @param lvl the level of the board
	 * @return the board width
	 * @post {@code boardWidth >= 5}
	 */
	public int getBoardWidth(int lvl);
	
	/**
	 * Gets the board.
	 * @param lvl the level of the board
	 * @return the matrix of the board
	 * @post {@code board[x][y] != null for any x,y < boardWidth, boardHeight}
	 */
	public Entity[][] getBoard(int lvl);
	
	/**
	 * Gets the position of Pacman on the board.
	 * @param lvl the level of the board
	 * @return the Pacman position
	 * @post {@code pacmanPosition.length = 2 && 0 <= pacmanPosition[0] < boardWidth && 0 <= pacmanPosition[1] < boardHeight}
	 */
	public int[] getPacmanPosition(int lvl);
	
	/**
	 * Gets the exit position of the ghosts house.
	 * @param lvl the level of the board
	 * @return the exit position
	 * @post {@code exitPosition.length = 2 && 0 <= exitPosition[0] < boardWidth && 0 <= exitPosition[1] < boardHeight}
	 */
	public int[] getExitPosition(int lvl);
	
	/**
	 * Gets the position of the ghosts on the board.
	 * @param lvl the level of the board
	 * @return the ghosts position
	 * @post {@code int[].length() = 2 && 0 <= int[0] < boardWidth && 0 <= int[1] < boardHeight}
	 */
	public EnumMap<GhostType, int[]> getGhostsPosition(int lvl);
	
	/**
	 * Gets the points corresponding to the entities.
	 * @return the entities points
	 * @post {@code Integer > 0}
	 */
	public EnumMap<Entity, Integer> getEntityPoints();
	
	/**
	 * Gets the best score.
	 * @return the best score
	 * @post {@code bestScore >= 0}
	 */
	public int getBestScore();
	
	/**
	 * Sets the new best score.
	 * @param bs the new best score
	 * @pre {@code bs > 0}
	 */
	public void setBestScore(int bs);
	
	/**
	 * Gets the number of levels.
	 * @return the levels number
	 * @post {@code levels >= 1}
	 */
	public int getLevels();
	
	/**
	 * Gets the number of gommes in each level.
	 * @param lvl the level of the board
	 * @return the gommes number
	 * @post {@code boardWidth*boardHeight >= gommes >= 1}
	 */
	public int getGommes(int lvl);
	
	/**
	 * Gets the number of Pacman lives.
	 * @return the lives number
	 * @post {@code lives >= 0}
	 */
	public int getLives();
	
	/**
	 * Gets the speed percent.
	 * @param lvl the level of the board
	 * @return the speed percent
	 * @post {@code 200 >= speed >= 50}
	 */
	public int getSpeed(int lvl);
	
	/**
	 * Gets the power time.
	 * @return the power time
	 * @post {@code powerTime >= 0}
	 */
	public int getPowerTime();

}
