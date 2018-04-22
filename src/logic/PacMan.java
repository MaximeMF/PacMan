package logic;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

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
	private int baseSpeed;
	private int speed;
	private Timer t = new Timer();
	private int liveUpScore = 10000;
	private Direction dir;

	/**
	 * Construct an instance of PacMan
	 * @param pos
	 * @param lives
	 */
	public PacMan(int[] pos, int lives, int speed) {
		this.position = pos.clone();
		this.lives = lives;
		this.respawnPosition = pos.clone();
		this.speed = speed;
		this.baseSpeed = speed;
	}

	/**
	 * Add to the Score a value passing in paramaters
	 * @param value of the score
	 */
	public void addScore(int value) {
		this.score += value;
		if(this.score > this.liveUpScore) {
			this.lives++;
			this.liveUpScore += 10000;
		}
	}

	/**
	 * {@inheritDoc}
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void move(Direction dir) {
		this.dir = dir;
		Entity[][] board = Game.INSTANCE.getBoard();
		int x = this.position[0];
		int y = this.position[1];
		switch(dir) { //move Pacman
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
		switch(board[y][x]) { //eat whatever is under Pacman
		case GOMME:
			board[y][x] = Entity.CHEMIN;
			this.addScore(Game.INSTANCE.getEntityPoints(Entity.GOMME));
			Game.INSTANCE.decreaseGommes();
			if(!this.powered)
				if(this.level > 1)
					this.newSpeed(this.baseSpeed - 9, 500);
				else
					this.newSpeed(this.baseSpeed - 11, 500);
			break;
		case FRUIT:
			board[y][x] = Entity.CHEMIN;
			this.addScore(Game.INSTANCE.getEntityPoints(Entity.FRUIT));
			break;
		case SUPERGOMME:
			board[y][x] = Entity.CHEMIN;
			this.addScore(Game.INSTANCE.getEntityPoints(Entity.SUPERGOMME));
			if(!this.powered)
				this.newSpeed(this.baseSpeed - 1, 500);
			Game.INSTANCE.decreaseGommes();
			this.powered = true;
			for(Ghost ghost : Game.INSTANCE.ghosts) { //ghosts can be eaten
				if (ghost.getState() == 1)
					ghost.changeState();
			}
			Game.INSTANCE.startPowerTimer();
			if(this.level > 1)
				this.newSpeed(this.baseSpeed + 5, Game.INSTANCE.getPowerTime()*1000);
			else
				this.newSpeed(this.baseSpeed + 10, Game.INSTANCE.getPowerTime()*1000);
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPowered() {
		return this.powered;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int[] getPosition() {
		return this.position;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getScore() {
		return this.score;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getLevel() {
		return this.level;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getLives() {
		return this.lives;
	}

	/**
	 * Decrements the lives of the player if Pacman has been eaten and reset its position.
	 * @param isDead
	 */
	public void respawn(boolean isDead) {
		if(this.lives == 0)
			Game.INSTANCE.lost();
		else {
			if(isDead) {
				this.lives--;
				for (Ghost ghost : Game.INSTANCE.ghosts) {
					ghost.init();
				}
			}
			this.position = this.respawnPosition.clone();
		}
	}

	/**
	 * Unpower the PacMan
	 */
	public void unpower() {
		this.powered = false;
		this.speed = this.baseSpeed;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSpeed() {
		return this.speed;
	}

	/**
	 * Changes the speed for a given duration.
	 * @param spd the new speed to apply
	 * @param duration the duration before reverting to normal speed
	 */
	private void newSpeed(int spd, int duration) {
		t.cancel();
		t = new Timer();
		this.speed = spd;
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				if(powered)
					speed = baseSpeed + 10;
				else
					speed = baseSpeed;
			}
		}, duration);
	}

	/**
	 * Gets the direction of Pacman.
	 * @return the current direction
	 */
	public Direction getDirection() {
		return this.dir;
	}
}