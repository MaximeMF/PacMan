package logic;

import data.Entity;
import data.GhostType;
import data.IDataLoader;
import data.DataLoader;

public class Game implements IGame{

	private Entity[][] board;
	private IDataLoader loader;
	private int speed;
	public PacMan player;
	public Ghost[] ghosts;
	private int gommes;

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
	public IPacMan getPlayer() {
		return this.player;
	}

	@Override
	public IGhost getGhost(GhostType type) {
		int i = 0;
		Ghost ghost;
		do {
			ghost = this.ghosts[i];
			i++;
		} while(ghost.getType() == type);
		return null;
	}

}