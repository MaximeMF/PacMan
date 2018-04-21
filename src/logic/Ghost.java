package logic;

import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import enums.Direction;
import enums.Entity;
import enums.GhostType;

/**
 * Class Controlling the Ghost's movements and functionalities  
 * @author Robin ALGIER - Maxime MATHIS--FUMEL - Yassin OURKIA
 *
 */
public class Ghost implements IGhost{

	private int[] position;
	private GhostType type;
	private int[] respawnPosition;
	private int[] exitPosition;
	private int points;
	private boolean canBeEaten = false;
	private boolean movable = false; //false if in the "house"
	private Direction currentDirection;
	private long respawnTime;
	private int state = 1;
	private int speed;
	private int baseSpeed;

	/**
	 * Constructs an instance of Ghost
	 * @param pos
	 * @param points
	 * @param type
	 * @param respawnTime
	 * @param exitPosition
	 */
	public Ghost(int[] pos, int points, GhostType type, long respawnTime, int[] exitPosition, int speed) {
		this.position = pos.clone();
		this.respawnPosition = pos.clone();
		this.points = points;
		this.type = type;
		this.respawnTime = respawnTime;
		this.exitPosition = exitPosition;
		if(type == GhostType.RED) {
			this.movable = true;
			this.position = exitPosition.clone();
		}
		this.speed = speed;
		this.baseSpeed = speed;
	}

	/**
	 * @return true if the ghost is movable
	 */
	public boolean isMovable() {
		return this.movable;
	}


	/**
	 * Tests whether the ghost can move in the given direction
	 * @param dir the direction the ghost should move towards
	 * @return true if the ghost can move in the given direction, false otherwise
	 */
	
	public boolean canMove(Direction dir) {
		Entity[][] board = Game.INSTANCE.getBoard();
		if(this.movable) {
			int x = this.position[0];
			int y = this.position[1];
			switch(dir) {
			case LEFT:
				if(x == 0 && board[y][x] == Entity.TUNNEL)
					return true;
				else if(x > 0 && board[y][x-1] != Entity.MUR)
					return true;
				break;
			case RIGHT:
				if(x == Game.INSTANCE.getBoardWidth()-1 && board[y][x] == Entity.TUNNEL)
					return true;
				else if(x < Game.INSTANCE.getBoardWidth()-1 && board[y][x+1] != Entity.MUR)
					return true;
				break;
			case UP:
				if(y == 0 && board[y][x] == Entity.TUNNEL)
					return true;
				else if(y > 0 && board[y-1][x] != Entity.MUR)
					return true;
				break;
			case DOWN:
				if(y == Game.INSTANCE.getBoardHeight()-1 && board[y][x] == Entity.TUNNEL)
					return true;
				else if(x < Game.INSTANCE.getBoardHeight()-1 && board[y+1][x] != Entity.MUR)
					return true;
				break;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see logic.IGhost#move()
	 */
	@Override
	public void move() {
		if(this.isMovable()) {
			int max = Direction.values().length;
			Direction dir;
			Direction[] val = Direction.values();
			Random rd = new Random();
			do {
				int rd_int = rd.nextInt(max);
				dir = val[rd_int];
				Direction temp = val[max-1];
				val[max-1] = dir;
				val[rd_int] = temp;
				max--;
			} while(Direction.opposite(dir) == this.currentDirection || !this.canMove(dir));
			this.move(dir);
			this.currentDirection = dir;
		}
	}

	/**
	 * Move the Pacman according to Direction giving in paramaters
	 * @param dir
	 */
	private void move(Direction dir) {
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
		if(board[this.position[1]][this.position[0]] == Entity.TUNNEL) {
			this.speed -= 35;
			(new Timer()).schedule(new TimerTask() {
				
				@Override
				public void run() {
					speed = baseSpeed;
				}
			}, 500);
		}
		if(this.isDead())
			this.respawn(true);
		if(Game.INSTANCE.player.isDead()) {
			Game.INSTANCE.player.respawn(true);
		}
	}

	/**
	 * Tests if a Ghost is dead
	 * @return true if the Ghost is eaten by The Pacman
	 */
	public boolean isDead() {
		if(this.canBeEaten) {
			Game game = Game.INSTANCE;
			if(Arrays.equals(game.player.getPosition(), this.position)) {
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see logic.IGhost#getPosition()
	 */
	@Override
	public int[] getPosition() {
		return position;
	}

	/**
	 * Gets the Type of ghost
	 * @return the Ghost Type
	 */
	
	public GhostType getType() {
		return this.type;
	}

	/* (non-Javadoc)
	 * @see logic.IGhost#canBeEaten()
	 */
	@Override
	public boolean canBeEaten() {
		return this.canBeEaten;
	}

	/**
	 * Change the state of an Ghost either be eaten or not
	 */
	public void changeState() {
		this.canBeEaten = !this.canBeEaten;
		if(this.canBeEaten) {
			this.state = 2;
			this.speed = this.baseSpeed - 25;
			(new Timer()).schedule(new TimerTask() {
				
				@Override
				public void run() {
					if(state == 2)
						state = 3;
				}
			}, (long)(Math.floor(Game.INSTANCE.getPowerTime()*0.70)));
		}
		else {
			this.state = 1;
			this.speed = this.baseSpeed;
		}
	}
	
	/* (non-Javadoc)
	 * @see logic.IGhost#getState()
	 */
	@Override
	public int getState() {
		return this.state;
	}

	/**
	 * Resets the ghost status the initial one
	 * @param isDead
	 */
	public void respawn(boolean isDead) {
		this.canBeEaten = false;
		this.state = 1;
		if(isDead)
			Game.INSTANCE.player.addScore(this.points);
		this.position = this.respawnPosition.clone();
		this.movable = false;
		Timer release = new Timer();
		release.schedule(new TimerTask() {
			
			@Override
			public void run() {
				movable = true;
				position = exitPosition.clone();
			}
		}, this.respawnTime);;
	}
	
	public void init() {
		this.respawn(false);
	}

	@Override
	public int getSpeed() {
		return this.speed;
	}

}