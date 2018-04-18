package logic;

import java.util.Arrays;

import enums.Direction;
import enums.Entity;

/**
 * Class Controlling the PacMan's Movements 
 * @author Robin Algier - Maxime Mathis--Fumel - Yassin Ourkia
 *
 */
public class PacMan implements IPacMan{

	private boolean powered = false;
	private int[] position;
	private int score = 0;
	private int lives;
	private int level = 1;
	private int[] respawnPosition;

	/**
	 * Construct an instance of PacMan
	 * @param pos
	 * @param lives
	 */
	public PacMan(int[] pos, int lives) {
		this.position = pos.clone();
		this.lives = lives;
		this.respawnPosition = pos.clone();
	}

	/**
	 * Add to the Score a value passing in paramaters
	 * @param value of the score
	 */
	public void addScore(int value) {
		this.score += value;
	}

	/* (non-Javadoc)
	 * @see logic.IPacMan#canMove(logic.Direction)
	 */
	@Override
	public boolean canMove(Direction dir) {
		Entity[][] board = Game.INSTANCE.getBoard();
		int x = this.position[0];
		int y = this.position[1];
		switch(dir) {
		case LEFT:
			if(x == 0 && board[y][x] == Entity.TUNNEL)
				return true;
			if(x > 0 && board[y][x-1] != Entity.MUR)
				return true;
			break;
		case RIGHT:
			if(x == Game.INSTANCE.getBoardWidth()-1 && board[y][x] == Entity.TUNNEL)
				return true;
			if(x < Game.INSTANCE.getBoardWidth()-1 && board[y][x+1] != Entity.MUR)
				return true;
			break;
		case UP:
			if(y == 0 && board[y][x] == Entity.TUNNEL)
				return true;
			if(y > 0 && board[y-1][x] != Entity.MUR)
				return true;
			break;
		case DOWN:
			if(y == Game.INSTANCE.getBoardHeight()-1 && board[y][x] == Entity.TUNNEL)
				return true;
			if(y < Game.INSTANCE.getBoardHeight()-1 && board[y+1][x] != Entity.MUR)
				return true;
			break;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see logic.IPacMan#move(logic.Direction)
	 */
	@Override
	public void move(Direction dir) {
		Entity[][] board = Game.INSTANCE.getBoard();
		int x = this.position[0];
		int y = this.position[1];
		switch(dir) {
		case LEFT:
			if(board[y][x] == Entity.TUNNEL && x == 0)
				this.position[0] = Game.INSTANCE.getBoardWidth() - 1;
			else
				this.position[0]--;
			break;
		case RIGHT:
			if(board[y][x] == Entity.TUNNEL && x == Game.INSTANCE.getBoardWidth()-1)
				this.position[0] = 0;
			else
				this.position[0]++;
			break;
		case UP:
			if(board[y][x] == Entity.TUNNEL && y == 0)
				this.position[1] = Game.INSTANCE.getBoardHeight() - 1;
			else
				this.position[1]--;
			break;
		case DOWN:
			if(board[y][x] == Entity.TUNNEL && y == Game.INSTANCE.getBoardHeight()-1)
				this.position[1] = 0;
			else
				this.position[1]++;
			break;
		}
		x = this.position[0];
		y = this.position[1];
		switch(board[y][x]) {
		case GOMME:
			board[y][x] = Entity.CHEMIN;
			this.addScore(Game.INSTANCE.getEntityPoints(Entity.GOMME));
			Game.INSTANCE.decreaseGommes();
			break;
		case FRUIT:
			board[y][x] = Entity.CHEMIN;
			this.addScore(Game.INSTANCE.getEntityPoints(Entity.FRUIT));
			break;
		case SUPERGOMME:
			board[y][x] = Entity.CHEMIN;
			this.addScore(Game.INSTANCE.getEntityPoints(Entity.SUPERGOMME));
			Game.INSTANCE.decreaseGommes();
			this.powered = true;
			for(Ghost ghost : Game.INSTANCE.ghosts) {
				ghost.changeState();
			}
			Game.INSTANCE.startPowerTimer();
			break;
		default:
			break;
		}
		if(this.isDead())
			this.respawn(true);
		for(Ghost ghost : Game.INSTANCE.ghosts)
			if(ghost.isDead()) {
				ghost.respawn(true);
				this.addScore(Game.INSTANCE.getEntityPoints(Entity.GHOST));
			}
		if(Game.INSTANCE.getGommes() == 0) {
			if(this.level == Game.INSTANCE.getLevels())
				Game.INSTANCE.won();
			else {
				this.level++;
				this.respawn(false);
				Game.INSTANCE.nextLevel();
			}
		}
	}

	/**
	 * Tests if the PacMAn is dead
	 * @return true if the PacMan is dead
	 */
	public boolean isDead() {
		Game game = Game.INSTANCE;
		for(Ghost ghost : game.ghosts) {
			if(Arrays.equals(ghost.getPosition(), this.position))
				if(!ghost.canBeEaten())
					return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see logic.IPacMan#isPowered()
	 */
	@Override
	public boolean isPowered() {
		return this.powered;
	}

	/* (non-Javadoc)
	 * @see logic.IPacMan#getPosition()
	 */
	@Override
	public int[] getPosition() {
		return this.position;
	}

	/* (non-Javadoc)
	 * @see logic.IPacMan#getScore()
	 */
	@Override
	public int getScore() {
		return this.score;
	}

	/* (non-Javadoc)
	 * @see logic.IPacMan#getLevel()
	 */
	@Override
	public int getLevel() {
		return this.level;
	}

	/* (non-Javadoc)
	 * @see logic.IPacMan#getLives()
	 */
	@Override
	public int getLives() {
		return this.lives;
	}

	/**
	 * Decrements lives number of a player
	 * @param isDead
	 */
	public void respawn(boolean isDead) {
		if(this.lives == 0)
			Game.INSTANCE.lost();
		else {
			if(isDead)
				this.lives--;
			this.position = this.respawnPosition.clone();
		}
	}

	/**
	 * Unpower the PacMan
	 */
	public void unpower() {
		this.powered = false;
	}

}