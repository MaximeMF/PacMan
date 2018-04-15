package logic;

import java.util.Arrays;

import data.Entity;

public class PacMan implements IPacMan{

	private boolean powered = false;
	private int[] position;
	private int score = 0;
	private int lives;
	private int level = 1;
	private int[] respawnPosition;

	public PacMan(int[] pos, int lives) {
		this.position = pos.clone();
		this.lives = lives;
		this.respawnPosition = pos.clone();
	}

	public void addScore(int value) {
		this.score += value;
	}

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
			if(x < Game.INSTANCE.getBoardHeight()-1 && board[y+1][x] != Entity.MUR)
				return true;
			break;
		}
		return false;
	}

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

	public boolean isDead() {
		Game game = Game.INSTANCE;
		for(Ghost ghost : game.ghosts) {
			if(Arrays.equals(ghost.getPosition(), this.position))
				if(!ghost.canBeEaten())
					return true;
		}
		return false;
	}

	public boolean isPowered() {
		return this.powered;
	}

	public int[] getPosition() {
		return this.position;
	}

	public int getScore() {
		return this.score;
	}

	public int getLevel() {
		return this.level;
	}

	public int getLives() {
		return this.lives;
	}

	public void respawn(boolean isDead) {
		if(this.lives == 0)
			Game.INSTANCE.lost();
		else {
			if(isDead)
				this.lives--;
			this.position = this.respawnPosition.clone();
		}
	}

	public void unpower() {
		this.powered = false;
	}

}