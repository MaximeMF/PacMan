package logic;

import java.util.Arrays;
import java.util.Random;

import data.Entity;
import data.GhostType;

public class Ghost implements IGhost{

	private int[] position;
	private GhostType type;
	private int[] respawnPosition;
	private int points;
	private boolean canBeEaten = false;
	private boolean movable = false; //false if in the "house"
	private Direction currentDirection;

	public Ghost(int[] pos, int points, GhostType type) {
		this.position = pos;
		this.respawnPosition = pos;
		this.points = points;
		this.type = type;
		if(type == GhostType.RED)
			this.movable = true;
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
				if(x > 0 && board[x-1][y] != Entity.MUR)
					return true;
				break;
			case RIGHT:
				if(x < Game.INSTANCE.getBoardWidth()-1 && board[x+1][y] != Entity.MUR)
					return true;
				break;
			case UP:
				if(y > 0 && board[x][y-1] != Entity.MUR)
					return true;
				break;
			case DOWN:
				if(x < Game.INSTANCE.getBoardHeight()-1 && board[x][y+1] != Entity.MUR)
					return true;
				break;
			}
		}
		return false;
	}

	@Override
	public void move() {
		int max = Direction.values().length;
		Direction dir;
		Random rd = new Random();
		do {
			dir = Direction.values()[rd.nextInt(max)];
		} while(Direction.opposite(dir) == this.currentDirection || !this.canMove(dir));
		this.move(dir);
		this.currentDirection = dir;
		if(this.isDead()) {
			this.canBeEaten = false;
			Game.INSTANCE.player.addScore(this.points);
			this.position = this.respawnPosition;
			this.movable = false;
		}
	}

	private void move(Direction dir) {
		this.movable = true;
		Entity[][] board = Game.INSTANCE.getBoard();
		int x = this.position[0];
		int y = this.position[1];
		switch(dir) {
		case LEFT:
			if(board[x][y] == Entity.TUNNEL)
				this.position[0] = Game.INSTANCE.getBoardWidth() - 1;
			else
				this.position[0]--;
			break;
		case RIGHT:
			if(board[x][y] == Entity.TUNNEL)
				this.position[0] = 0;
			else
				this.position[0]++;
			break;
		case UP:
			if(board[x][y] == Entity.TUNNEL)
				this.position[1] = Game.INSTANCE.getBoardHeight() - 1;
			else
				this.position[1]--;
			break;
		case DOWN:
			if(board[x][y] == Entity.TUNNEL)
				this.position[1] = 0;
			else
				this.position[1]++;
			break;
		}
	}

	@Override
	public boolean isDead() {
		if(this.canBeEaten) {
			Game game = Game.INSTANCE;
			if(Arrays.equals(game.player.getPosition(), this.position)) {
				return true;
			}
		}
		return false;
	}

	@Override
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

}