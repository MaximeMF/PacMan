package logic;

import data.IDataLoader;
import enums.Entity;
import enums.GhostType;

import java.util.Timer;
import java.util.TimerTask;

import data.DataLoader;

/**
 * Class Controlling the entire elements of the game (Ghost, Pacman, Scores,Lives...)
 * @author Robin ALGIER - Maxime MATHIS--FUMEL - Yassin OURKIA
 *
 */
public class Game implements IGame{

	private Entity[][] board;
	private IDataLoader loader;
	private int speed;
	public PacMan player;
	public Ghost[] ghosts;
	private int gommes;
	private Timer powerTimer = new Timer();
	private boolean won = false;
	private boolean lost = false;
	private int[][] boardMatrix;

	/**
	 * Construct an instance of Game using singleton pattern
	 */
	private Game() {
		this.loader = new DataLoader();
		this.board = this.loader.getBoard(1);
		this.speed = this.loader.getSpeed(1);
		this.gommes = this.loader.getGommes(1);
		this.player = new PacMan(this.loader.getPacmanPosition(1), this.loader.getLives(), this.loader.getSpeed(1));
		this.boardMatrix = this.initBoardMatrix();
		Ghost red =  new Ghost(this.loader.getGhostsPosition(1).get(GhostType.RED), this.loader.getEntityPoints().get(Entity.GHOST),GhostType.RED, 5000, this.loader.getExitPosition(1), this.loader.getSpeed(1)-5, this.loader.getModeTime());
		Ghost cyan =  new Ghost(this.loader.getGhostsPosition(1).get(GhostType.CYAN), this.loader.getEntityPoints().get(Entity.GHOST),GhostType.CYAN, 5000, this.loader.getExitPosition(1), this.loader.getSpeed(1)-5, this.loader.getModeTime());
		Ghost pink =  new Ghost(this.loader.getGhostsPosition(1).get(GhostType.PINK), this.loader.getEntityPoints().get(Entity.GHOST),GhostType.PINK, 5000, this.loader.getExitPosition(1), this.loader.getSpeed(1)-5, this.loader.getModeTime());
		Ghost orange =  new Ghost(this.loader.getGhostsPosition(1).get(GhostType.ORANGE), this.loader.getEntityPoints().get(Entity.GHOST),GhostType.ORANGE, 5000, this.loader.getExitPosition(1), this.loader.getSpeed(1)-5, this.loader.getModeTime());
		this.ghosts = new Ghost[] {red, cyan, pink, orange};
	}


	static Game INSTANCE = new Game();

	/**
	 * return one and only instance of game (singleton)
	 * @return the instance of game 
	 */
	public static IGame getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Game();
		}
		return INSTANCE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void newGame() {
		INSTANCE = new Game();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Entity[][] getBoard() {
		return this.board;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getBoardHeight() {
		return this.loader.getBoardHeight(this.player.getLevel());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getBoardWidth() {
		return this.loader.getBoardWidth(this.player.getLevel());
	}

	/**
	 * Gets the Entity points
	 * @param entity
	 * @return Points from an entity passing in param
	 */
	public int getEntityPoints(Entity entity) {
		return this.loader.getEntityPoints().get(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSpeed() {
		return this.speed;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getBestScore() {
		return this.loader.getBestScore();
	}

	/**
	 * Decreases Number the gums eaten by the PacMan
	 */
	public void decreaseGommes() {
		this.gommes--;
		if(this.hasWon()) {
			this.player.respawn(false);
			this.board = this.loader.getBoard(this.player.getLevel());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasWon() {
		return this.won ;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasLost() {
		return this.lost;
	}

	/**
	 * Has the PacMan won
	 */
	public void won() {
		this.won = true;
		if(this.player.getScore() > this.getBestScore()) {
			this.setBestScore(this.player.getScore());
		}
	}

	/**
	 * Has the PacMan lost
	 */
	public void lost() {
		this.lost = true;
		if(this.player.getScore() > this.getBestScore()) {
			this.setBestScore(this.player.getScore());
		}
	}

	private void setBestScore(int score) {
		this.loader.setBestScore(score);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IGhost getGhost(GhostType type) {
		int i = 0;
		Ghost ghost;
		do {
			ghost = this.ghosts[i];
			i++;
		} while(ghost.getType() != type);
		return ghost;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IPacMan getPlayer() {
		return this.player;
	}

	/**
	 * Reset the Ghost status to the initial one
	 */
	private void resetPower() {
		for(Ghost ghost : this.ghosts) {
			if(ghost.canBeEaten())
				ghost.changeState();
		}
		this.player.unpower();
	}

	/**
	 * Start Power timer
	 */
	public void startPowerTimer() {
		this.powerTimer.cancel(); //ensure that the power will not reset too early due to another task scheduled
		this.powerTimer = new Timer();
		this.powerTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				resetPower();
			}
		}, this.loader.getPowerTime()*1000);
	}

	/**
	 * Gets Gums
	 * @return the gums 
	 */
	public int getGommes() {
		return this.gommes;
	}

	/**
	 * The Pacman start the next level
	 */
	public void nextLevel() { //reset the board and the ghosts
		this.board = this.loader.getBoard(this.player.getLevel());
		this.gommes = this.loader.getGommes(this.player.getLevel());
		this.speed = this.loader.getSpeed(this.player.getLevel());
		this.boardMatrix = this.initBoardMatrix();
		Ghost red =  new Ghost(this.loader.getGhostsPosition(this.player.getLevel()).get(GhostType.RED), this.loader.getEntityPoints().get(Entity.GHOST),GhostType.RED, 5000, this.loader.getExitPosition(this.player.getLevel()), this.loader.getSpeed(this.player.getLevel())-5, this.loader.getModeTime());
		Ghost cyan =  new Ghost(this.loader.getGhostsPosition(this.player.getLevel()).get(GhostType.CYAN), this.loader.getEntityPoints().get(Entity.GHOST),GhostType.CYAN, 5000, this.loader.getExitPosition(this.player.getLevel()), this.loader.getSpeed(this.player.getLevel())-5, this.loader.getModeTime());
		Ghost pink =  new Ghost(this.loader.getGhostsPosition(this.player.getLevel()).get(GhostType.PINK), this.loader.getEntityPoints().get(Entity.GHOST),GhostType.PINK, 5000, this.loader.getExitPosition(this.player.getLevel()), this.loader.getSpeed(this.player.getLevel())-5, this.loader.getModeTime());
		Ghost orange =  new Ghost(this.loader.getGhostsPosition(this.player.getLevel()).get(GhostType.ORANGE), this.loader.getEntityPoints().get(Entity.GHOST),GhostType.ORANGE, 5000, this.loader.getExitPosition(this.player.getLevel()), this.loader.getSpeed(this.player.getLevel())-5, this.loader.getModeTime());
		this.ghosts = new Ghost[] {red, cyan, pink, orange};
		cyan.init();
		pink.init();
		orange.init();
		//red is already out
	}

	/**
	 * @return the level number from the data loader 
	 */
	public int getLevels() {
		return this.loader.getLevels();
	}

	/**
	 * Get the Power Time
	 * @return the Power time
	 */
	public int getPowerTime() {
		return this.loader.getPowerTime()*1000;
	}

	/**
	 * Create the adjacency matrix of the board
	 * @return matrice[width*height][width*height]
	 */
	private int[][] initBoardMatrix() {
		int height = this.getBoardHeight();
		int width = this.getBoardWidth();
		int[][] matrix = new int[height*width][height*width]; //the value of the edge from pos1 to pos2 will be matrix[pos1][pos2]
		for(int i = 0; i < height*width; i++)
			for(int j = 0; j < height*width; j++)
				matrix[i][j] = 100; //edges with value 100 will be ignored by dijkstra's algorithm
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if(this.board[y][x] == Entity.TUNNEL) { 
					if(x==0) {
						matrix[y*width+x][y*width+(width-1)] = 1;
						matrix[y*width+x][y*width+x+1] = 1;
					}
					else if(x==width-1) {
						matrix[y*width+x][y*width] = 1;
						matrix[y*width+x][y*width+x-1] = 1;
					}
					else if(y==0) {
						matrix[y*width+x][(height-1)*width+x] = 1;
						matrix[y*width+x][(y+1)*width+x] = 1;
					}
					else if(y==height-1) {
						matrix[y*width+x][x] = 1;
						matrix[y*width+x][(y-1)*width+x] = 1;
					}
				}
				else if(this.board[y][x] != Entity.MUR && this.board[y][x] != Entity.VOID) {
					if(this.board[y-1][x] != Entity.MUR && this.board[y-1][x] != Entity.VOID) {
						matrix[y*width+x][(y-1)*width+x] = 1;
					}
					if(this.board[y+1][x] != Entity.MUR && this.board[y+1][x] != Entity.VOID) {
						matrix[y*width+x][(y+1)*width+x] = 1;
					}
					if(this.board[y][x-1] != Entity.MUR && this.board[y][x-1] != Entity.VOID) {
						matrix[y*width+x][y*width+(x-1)] = 1;
					}
					if(this.board[y][x+1] != Entity.MUR && this.board[y][x+1] != Entity.VOID) {
						matrix[y*width+x][y*width+(x+1)] = 1;
					}
				}
			}
		}
		return matrix;
	}
	
	/**
	 * Gets the adjacency matrix of the board.
	 * @return the adjacency matrix
	 */
	public int[][] getBoardMatrix() {
		int[][] clone = new int[this.boardMatrix.length][];
	    for (int r = 0; r < this.boardMatrix.length; r++) {
	    	clone[r] = this.boardMatrix[r].clone();
	    }
		return clone;
	}
}