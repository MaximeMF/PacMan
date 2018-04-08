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
	 * Gets the board.
	 * @return the matrix of the board
	 * @post {@code board[x][y] != null for any x,y < boardWidth, boardHeight}
	 */
	public Entity[][] getBoard();
	
	/**
	 * Gets the position of Pacman on the board.
	 * @return the Pacman position
	 * @post {@code pacmanPosition.length = 2 && 0 <= pacmanPosition[0] < boardWidth && 0 <= pacmanPosition[1] < boardHeight}
	 */
	public int[] getPacmanPosition();
	
	/**
	 * Gets the position of the ghosts on the board.
	 * @return the ghosts position
	 * @post {@code int[].length() = 2 && 0 <= int[0] < boardWidth && 0 <= int[1] < boardHeight}
	 */
	public EnumMap<GhostType, int[]> getGhostsPosition();
	
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
	 * Gets the number of levels.
	 * @return the levels number
	 * @post {@code levels >= 1}
	 */
	public int getLevels();
	
	/**
	 * Gets the number of gommes in each level.
	 * @return the gommes number
	 * @post {@code boardWidth*boardHeight >= gommes >= 1}
	 */
	public int getGommes();
	
	/**
	 * Gets the number of Pacman lives.
	 * @return the lives number
	 * @post {@code lives >= 0}
	 */
	public int getLives();
	
	/**
	 * Gets the speed percent.
	 * @return the speed percent
	 * @post {@code 200 >= speed >= 50}
	 */
	public int getSpeed();
	
	/**
	 * Gets the power time.
	 * @return the power time
	 * @post {@code powerTime >= 0}
	 */
	public int getPowerTime();

}
