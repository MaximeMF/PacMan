package logic;

import java.util.Arrays;

import data.Entity;

public class PacMan implements IPacMan{

	private boolean powered = false;
	private int[] position;
	private int score = 0;
	private int lives;
	private int level = 1;

	public PacMan(int[] pos, int lives) {
		this.position = pos;
		this.lives = lives;
	}

	public void addScore(int value) {
		this.score += value;
	}

	@Override
	public boolean canMove(Direction dir) {
		Entity[][] board = Game.INSTANCE.getBoard();
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
		return false;
	}

	@Override
	public void move(Direction dir) {
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
		switch(board[x][y]) {
		case GOMME:
			board[x][y] = Entity.CHEMIN;
			this.addScore(Game.INSTANCE.getEntityPoints(Entity.GOMME));
			Game.INSTANCE.decreaseGommes();
			break;
		case FRUIT:
			board[x][y] = Entity.CHEMIN;
			this.addScore(Game.INSTANCE.getEntityPoints(Entity.FRUIT));
			break;
		case SUPERGOMME:
			board[x][y] = Entity.CHEMIN;
			this.addScore(Game.INSTANCE.getEntityPoints(Entity.SUPERGOMME));
			Game.INSTANCE.decreaseGommes();
			for(Ghost ghost : Game.INSTANCE.ghosts) {
				ghost.changeState();
			}
			break;
		default:

		}
	}

	@Override
	public boolean isDead() {
		Game game = Game.INSTANCE;
		for(Ghost ghost : game.ghosts) {
			if(Arrays.equals(ghost.getPosition(), this.position))
				if(!ghost.canBeEaten()) {
					this.lives--;
					return true;
				}
		}
		return false;
	}

	@Override
	public boolean isPowered() {
		return this.powered;
	}

	@Override
	public int[] getPosition() {
		return this.position;
	}

	@Override
	public int getScore() {
		return this.score;
	}

	@Override
	public int getLevel() {
		return this.level;
	}

	@Override
	public int getLives() {
		return this.lives;
	}

}