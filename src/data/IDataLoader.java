package data;

import java.util.EnumMap;

/**
 * Interface providing methods that can be used by the Logic package.
 * @author Robin Algier - Maxime Mathis--Fumel - Yassin Ourkia
 */
public interface IDataLoader {
	
	/**
	 * Gets the height of the board.
	 * @return the board height
	 */
	public int getBoardHeight();
	
	/**
	 * Gets the width of the board.
	 * @return the board width
	 */
	public int getBoardWidth();
	
	/**
	 * Gets the board.
	 * @return the matrix of the board
	 */
	public int[][] getBoard();
	
	/**
	 * Gets the position of Pacman on the board.
	 * @return the Pacman position
	 */
	public int[] getPacmanPosition();
	
	/**
	 * Gets the position of the ghosts on the board.
	 * @return the ghosts position
	 */
	public EnumMap<GhostType, int[]> getGhostsPosition();
	
	/**
	 * Gets the points corresponding to the entities.
	 * @return the entities points
	 */
	public EnumMap<Entity, Integer> getEntityPoints();
	
	/**
	 * Gets the best score.
	 * @return the best score
	 */
	public int getBestScore();
	
	/**
	 * Gets the number of levels.
	 * @return the levels number
	 */
	public int getLevels();
	
	/**
	 * Gets the number of gommes in each level.
	 * @return the gommes number
	 */
	public int getGommes();
	
	/**
	 * Gets the number of Pacman lives.
	 * @return the lives number
	 */
	public int getLives();
	
	/**
	 * Gets the speed //TODO
	 * @return the speed
	 */
	public int getSpeed();
	
	/**
	 * Gets the power time.
	 * @return the power time
	 */
	public int getPowerTime();

}
