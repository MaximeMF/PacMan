package logic;

public interface IPacMan {
  /**
   * Tests whether PacMan can move in the given direction.
   * @param dir the direction PacMan should move towards
   * @return true if PacMan can move in the given direction, false otherwise
   */
  public boolean canMove(Direction dir);
  
  /**
   * Moves PacMan.
   * @param dir the direction the ghost should move towards
   * @post {@code 0 <= pacman.x < boardWidth && 0 <= pacman.y < boardHeight}
   */
  public void move(Direction dir);
  
  /**
   * Tests whether PacMan has been eaten by a ghost.
   * @return true if PacMan is on the same case as a ghost, false otherwise
   */
  public boolean isDead();
  
  /**
   * Tests whether PacMan can eat ghosts.
   * @return true if PacMan can eat ghosts
   */
  public boolean isPowered();
  
  /**
   * Gets the position of Pacman on the board.
   * @return the Pacman position
   * @post {@code pacmanPosition.length = 2 && 0 <= pacmanPosition[0] < boardWidth && 0 <= pacmanPosition[1] < boardHeight}
   */
  public int[] getPosition();
  
  /**
   * Gets the current score.
   * @return current score
   * @post {@code score >= 0}
   */
  public int getScore();
  
  /**
   * Gets the current level.
   * @return current level
   * @post {@code 5 >= level >= 1}
   */
  public int getLevel();
  
  /**
   * Gets the number of lives left.
   * @return the number of lives left
   * @post {@code lives >= 0}
   */
  public int getLives();
}