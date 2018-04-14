package logic;

import data.Entity;
import data.GhostType;
import data.IDataLoader;

import java.util.Timer;
import java.util.TimerTask;

import data.DataLoader;

public class Game implements IGame{

	private Entity[][] board;
	private IDataLoader loader;
	private int speed;
	public PacMan player;
	public Ghost[] ghosts;
	private int gommes;
	private Timer powerTimer;

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

	public static IGame getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Game();
		}
		return INSTANCE;
	}

	public void newGame() {
		INSTANCE = new Game();
	}

	@Override
	public Entity[][] getBoard() {
		return this.board;
	}

	@Override
	public int getBoardHeight() {
		return this.loader.getBoardHeight();
	}

	@Override
	public int getBoardWidth() {
		return this.loader.getBoardWidth();
	}

	public int getEntityPoints(Entity entity) {
		return this.loader.getEntityPoints().get(entity);
	}

	@Override
	public int getSpeed() {
		return this.speed;
	}

	@Override
	public int getBestScore() {
		return loader.getBestScore();
	}

	public void decreaseGommes() {
		this.gommes--;
		if(this.hasWon()) {
			this.player.respawn(false);
			this.board = this.loader.getBoard();
		}
	}

	@Override
	public boolean hasWon() {
		return this.gommes == 0;
	}

	@Override
	public boolean hasLost() {
		return this.player.getLives() == 0;
	}

	public IGhost getGhost(GhostType type) {
		int i = 0;
		Ghost ghost;
		do {
			ghost = this.ghosts[i];
			i++;
		} while(ghost.getType() != type);
		return ghost;
	}

	@Override
	public IPacMan getPlayer() {
		return this.player;
	}
	
	private void resetPower() {
		for(Ghost ghost : this.ghosts) {
			if(ghost.canBeEaten())
				ghost.changeState();
		}
		this.player.unpower();
	}
	
	public void startPowerTimer() {
		this.powerTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				resetPower();
			}
		}, this.loader.getPowerTime()*1000);
	}	
}