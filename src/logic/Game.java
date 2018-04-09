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

	@Override
	public boolean canMovePlayer(Direction dir) {
		return this.player.canMove(dir);
	}

	@Override
	public void movePlayer(Direction dir) {
		this.player.move(dir);
		if(this.player.isDead())
			this.player.respawn(true);
	}

	private Ghost getGhost(GhostType type) {
		int i = 0;
		Ghost ghost;
		do {
			ghost = this.ghosts[i];
			i++;
		} while(ghost.getType() != type);
		return ghost;
	}
	
	@Override
	public void moveGhost(GhostType type) {
		this.getGhost(type).move();
	}

	@Override
	public int[] getPlayerPosition() {
		return this.player.getPosition();
	}

	@Override
	public int[] getGhostPosition(GhostType type) {
		return this.getGhost(type).getPosition();
	}
	
	private void resetPower() {
		for(Ghost ghost : this.ghosts) {
			if(ghost.canBeEaten())
				ghost.changeState();
		}
	}
	
	public void startPowerTimer() {
		this.powerTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				resetPower();
				player.unpower();
			}
		}, this.loader.getPowerTime()*1000);
	}

	@Override
	public boolean canGhostBeEaten(GhostType type) {
		return this.getGhost(type).canBeEaten();
	}

	@Override
	public boolean isPowerActive() {
		return this.player.isPowered();
	}

	@Override
	public int getScore() {
		return this.player.getScore();
	}

	@Override
	public int getLives() {
		return this.player.getLives();
	}

	@Override
	public int getLevel() {
		return this.player.getLevel();
	}
	
}