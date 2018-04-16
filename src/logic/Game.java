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

	/**
	 * Construct an instance of Game using singleton pattern
	 */
	private Game() {
		this.loader = new DataLoader();
		this.board = loader.getBoard();
		this.speed = loader.getSpeed();
		this.gommes = loader.getGommes();
		this.player = new PacMan(loader.getPacmanPosition(), loader.getLives());
		Ghost red =  new Ghost(loader.getGhostsPosition().get(GhostType.RED), loader.getEntityPoints().get(Entity.GHOST),GhostType.RED, 5000, loader.getExitPosition());
		Ghost cyan =  new Ghost(loader.getGhostsPosition().get(GhostType.CYAN), loader.getEntityPoints().get(Entity.GHOST),GhostType.CYAN, 5000, loader.getExitPosition());
		Ghost pink =  new Ghost(loader.getGhostsPosition().get(GhostType.PINK), loader.getEntityPoints().get(Entity.GHOST),GhostType.PINK, 5000, loader.getExitPosition());
		Ghost orange =  new Ghost(loader.getGhostsPosition().get(GhostType.ORANGE), loader.getEntityPoints().get(Entity.GHOST),GhostType.ORANGE, 5000, loader.getExitPosition());
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

	/* (non-Javadoc)
	 * @see logic.IGame#newGame()
	 */
	public void newGame() {
		INSTANCE = new Game();
	}

	/* (non-Javadoc)
	 * @see logic.IGame#getBoard()
	 */
	@Override
	public Entity[][] getBoard() {
		return this.board;
	}

	/* (non-Javadoc)
	 * @see logic.IGame#getBoardHeight()
	 */
	@Override
	public int getBoardHeight() {
		return this.loader.getBoardHeight();
	}

	/* (non-Javadoc)
	 * @see logic.IGame#getBoardWidth()
	 */
	@Override
	public int getBoardWidth() {
		return this.loader.getBoardWidth();
	}

	/**
	 * Gets the Entity points
	 * @param entity
	 * @return Points from an entity passing in param
	 */
	public int getEntityPoints(Entity entity) {
		return this.loader.getEntityPoints().get(entity);
	}

	/* (non-Javadoc)
	 * @see logic.IGame#getSpeed()
	 */
	@Override
	public int getSpeed() {
		return this.speed;
	}

	/* (non-Javadoc)
	 * @see logic.IGame#getBestScore()
	 */
	@Override
	public int getBestScore() {
		return loader.getBestScore();
	}

	/**
	 * Decraeses Number the gums eaten by the PacMan
	 */
	public void decreaseGommes() {
		this.gommes--;
		if(this.hasWon()) {
			this.player.respawn(false);
			this.board = this.loader.getBoard();
		}
	}

	/* (non-Javadoc)
	 * @see logic.IGame#hasWon()
	 */
	@Override
	public boolean hasWon() {
		return this.won ;
	}

	/* (non-Javadoc)
	 * @see logic.IGame#hasLost()
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
	}
	
	/**
	 * Has the PacMan lost
	 */
	public void lost() {
		this.lost = true;
	}

	/* (non-Javadoc)
	 * @see logic.IGame#getGhost(data.GhostType)
	 */
	public IGhost getGhost(GhostType type) {
		int i = 0;
		Ghost ghost;
		do {
			ghost = this.ghosts[i];
			i++;
		} while(ghost.getType() != type);
		return ghost;
	}

	/* (non-Javadoc)
	 * @see logic.IGame#getPlayer()
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
	public void nextLevel() {
		this.board = loader.getBoard();
		this.gommes = loader.getGommes();
		this.speed = loader.getSpeed();
		Ghost red =  new Ghost(loader.getGhostsPosition().get(GhostType.RED), loader.getEntityPoints().get(Entity.GHOST),GhostType.RED, 5000, loader.getExitPosition());
		Ghost cyan =  new Ghost(loader.getGhostsPosition().get(GhostType.CYAN), loader.getEntityPoints().get(Entity.GHOST),GhostType.CYAN, 5000, loader.getExitPosition());
		Ghost pink =  new Ghost(loader.getGhostsPosition().get(GhostType.PINK), loader.getEntityPoints().get(Entity.GHOST),GhostType.PINK, 5000, loader.getExitPosition());
		Ghost orange =  new Ghost(loader.getGhostsPosition().get(GhostType.ORANGE), loader.getEntityPoints().get(Entity.GHOST),GhostType.ORANGE, 5000, loader.getExitPosition());
		this.ghosts = new Ghost[] {red, cyan, pink, orange};
		cyan.init();
		pink.init();
		orange.init();
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
}