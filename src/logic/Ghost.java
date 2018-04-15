package logic;

import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import data.Entity;
import data.GhostType;

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

	public Ghost(int[] pos, int points, GhostType type, long respawnTime, int[] exitPosition) {
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
	}

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
		if(this.isDead())
			this.respawn(true);
		if(Game.INSTANCE.player.isDead()) {
			Game.INSTANCE.player.respawn(true);
		}
	}

	public boolean isDead() {
		if(this.canBeEaten) {
			Game game = Game.INSTANCE;
			if(Arrays.equals(game.player.getPosition(), this.position)) {
				return true;
			}
		}
		return false;
	}

	public int[] getPosition() {
		return position;
	}

	public GhostType getType() {
		return this.type;
	}

	public boolean canBeEaten() {
		return this.canBeEaten;
	}

	public void changeState() {
		this.canBeEaten = !this.canBeEaten;
	}

	public void respawn(boolean isDead) {
		this.canBeEaten = false;
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

}