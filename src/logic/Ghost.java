package logic;

import java.util.ArrayList;
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
	private Dijkstra dijkstra;
	private int mode = 1; //1:chase ; 2:scatter; 3:frightened
	private int currentMode = 1;
	private int[] timings;
	private int cursor = 0;
	private boolean scatterSwitch = false; //true for the first move after switching to scatter mode to allow ghosts to turn around

	/**
	 * Constructs an instance of Ghost
	 * @param pos
	 * @param points
	 * @param type
	 * @param respawnTime
	 * @param exitPosition
	 */
	public Ghost(int[] pos, int points, GhostType type, long respawnTime, int[] exitPosition, int speed, int[] timings) {
		this.position = pos.clone();
		this.respawnPosition = pos.clone();
		this.points = points;
		this.type = type;
		this.respawnTime = respawnTime;
		this.exitPosition = exitPosition;
		this.speed = speed;
		this.baseSpeed = speed;
		this.dijkstra = new Dijkstra();
		this.timings = timings;
		TimerTask tt = new TimerTask() {
			@Override
			public void run() {
				cursor += 1;
				if(currentMode == 1) {
					currentMode = 2;
					if(mode != 3) {
						mode = 2;
						scatterSwitch = true;
					}
				}
				else {
					currentMode = 1;
					if(mode != 3)
						mode = 1;
				}
				(new Timer()).schedule(this, timings[cursor]);
			}
		};
		if(type == GhostType.RED) {
			this.movable = true;
			this.position = exitPosition.clone();
			(new Timer()).schedule(tt, this.timings[this.cursor]);
		}

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
			if(this.currentDirection != null && (Direction.opposite(dir) == this.currentDirection || this.scatterSwitch))
				return false;
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

	/**
	 * Moves the ghost in a random direction.
	 */
	public void randomMove() {
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
		} while(!this.canMove(dir));
		this.move(dir);
		this.currentDirection = dir;
	}

	/**
	 * Moves the Pacman according to Direction given in parameters
	 * @param dir
	 */
	private void move(Direction dir) {
		this.scatterSwitch = false;
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
			if(Game.INSTANCE.getPlayer().getLevel() > 1)
				this.speed -= 35;
			else
				this.speed -= 40;
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

	/**
	 * {@inheritDoc}
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canBeEaten() {
		return this.canBeEaten;
	}

	/**
	 * Change the state of the ghost: can be eaten or not
	 */
	public void changeState() {
		this.canBeEaten = !this.canBeEaten;
		if(this.canBeEaten) {
			this.mode = 3;
			this.state = 2;
			if(Game.INSTANCE.getPlayer().getLevel() > 1)
				this.speed = this.baseSpeed - 25;
			else
				this.speed = this.baseSpeed - 30;
			(new Timer()).schedule(new TimerTask() {

				@Override
				public void run() {
					if(state == 2) {
						state = 3;
					}
				}
			}, (long)(Math.floor(Game.INSTANCE.getPowerTime()*0.70)));
		}
		else {
			this.state = 1;
			this.speed = this.baseSpeed;
			this.mode = this.currentMode;
		}
	}

	/**
	 * {@inheritDoc}
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
		this.currentDirection = null;
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() {
		this.respawn(false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSpeed() {
		return this.speed;
	}

	/**
	 * Gets the shortest path between the source and the destination using the Dijkstra's algorithm
	 * @param from the source of the path
	 * @param to the destination of the path
	 * @return
	 */
	private Direction dijkstra(int from, int to) {
		int[][] matrix = Game.INSTANCE.getBoardMatrix();
		int x = this.position[0];
		int y = this.position[1];
		//int height = Game.INSTANCE.getBoardHeight();
		int width = Game.INSTANCE.getBoardWidth();
		if(this.currentDirection != null && !scatterSwitch)
			switch(this.currentDirection) { //a ghost cannot go back
			case UP:
				matrix[y*width+x][(y+1)*width+x] = 100;
				break;
			case DOWN:
				matrix[y*width+x][(y-1)*width+x] = 100;
				break;
			case LEFT:
				matrix[y*width+x][y*width+(x+1)] = 100;
				break;
			case RIGHT:
				matrix[y*width+x][y*width+(x-1)] = 100;
				break;
			}

		ArrayList<Integer> chemin = this.dijkstra.dijkstra(matrix, from, to);
		int nextPos;
		if(chemin.size() > 1)
			nextPos = chemin.get(1);
		else
			return this.currentDirection;
		if(nextPos == y*width+x - 1 || nextPos == y*width+x - width + 1) {
			return Direction.LEFT;
		}
		else if(nextPos ==  y*width+x + 1 || nextPos ==  y*width+x + width - 1) {
			return Direction.RIGHT;
		}
		else if(nextPos == y*width+x + width || nextPos == x) {
			return Direction.DOWN;
		}
		else {
			return Direction.UP;
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void move() {
		if (this.movable) {
			int target = 0;
			int width = Game.INSTANCE.getBoardWidth();
			int height = Game.INSTANCE.getBoardHeight();
			int from = this.position[0] + width*this.position[1];
			Direction newDir;
			switch(this.mode) {
			case 1: //chase
				int[] pos = Game.INSTANCE.player.getPosition();
				int pPos = pos[0] + pos[1]*width;
				switch(this.type) {
				case CYAN:
				case ORANGE:
				case PINK:
					target = specialTarget(from, pPos, width, height);
					break;
				case RED:
					target = pPos;
					break;
				default:

				}
				newDir = this.dijkstra(from, target);
				if(this.canMove(newDir)) {
					this.move(newDir);
					this.currentDirection = newDir;
				}
				else
					if(this.currentDirection != null && this.canMove(this.currentDirection))
						this.move(currentDirection);
					else
						this.randomMove(); //should not happen
				break;
			case 2: //scatter
				switch(this.type) {
				case CYAN:
					target = (height-2) * width+1;
					break;
				case ORANGE:
					target = (height-1) * width-2;
					break;
				case PINK:
					target = width+1;
					break;
				case RED:
					target = 2*width - 2;
					break;
				default:

				}
				newDir = this.dijkstra(from, target);
				this.move(newDir);
				if(this.canMove(newDir)) {
					this.move(newDir);
					this.currentDirection = newDir;
				}
				else
					if(this.currentDirection != null && this.canMove(this.currentDirection))
						this.move(currentDirection);
					else
						this.randomMove(); //should not happen
				break;
			case 3: //frightened
				this.randomMove();
				break;
			}
		}
	}

	/**
	 * Calculates the target cell of the ghost to use as a destination for the algorithm.
	 * @param gPos position of the ghost
	 * @param pPos position of pacman
	 * @param width width of the board
	 * @param height height of the board
	 * @return the target cell of the ghost
	 */
	private int specialTarget(int gPos, int pPos, int width, int height) {
		int target = 0;
		int[] targetPos = {0,0};
		PacMan pac = Game.INSTANCE.player;
		//int add = 0;
		switch(this.type) {
		case CYAN:
			switch(pac.getDirection()) {
			case UP:
				targetPos = new int[] {pac.getPosition()[0],pac.getPosition()[1] - 4}; //not pac.getPosition()[0]-4 because it's almost always a wall 
				break;
			case DOWN:
				targetPos = new int[] {pac.getPosition()[0],pac.getPosition()[1] + 4};
				break;
			case LEFT:
				targetPos = new int[] {pac.getPosition()[0]- 4,pac.getPosition()[1]};
				break;
			case RIGHT:
				targetPos = new int[] {pac.getPosition()[0] + 4,pac.getPosition()[1]};
				break;
			}
			targetPos[0] += targetPos[0] - this.position[0];
			targetPos[1] += targetPos[1] - this.position[1];
			targetPos[0] = (targetPos[0] < 0 || targetPos[0] >= width) ? pac.getPosition()[0] : targetPos[0];
			targetPos[1] = (targetPos[1] < 0 || targetPos[1] >= height) ? pac.getPosition()[1] : targetPos[1];
			target = (Game.INSTANCE.getBoard()[targetPos[1]][targetPos[0]] != Entity.MUR) &&
					(Game.INSTANCE.getBoard()[targetPos[1]][targetPos[0]] != Entity.VOID) ? targetPos[1]*width + targetPos[0]: pPos;
					//more time efficient than considering every nodes connected
					break;
		case ORANGE:
			int[] pacPos = Game.INSTANCE.player.getPosition();
			int dist = (int)Math.sqrt(Math.pow(this.position[0]-pacPos[0],2) + Math.pow(this.position[1]-pacPos[1],2));
			target = dist < 6 ? (height-1) * width-2 : pPos;
			break;
		case PINK:
			switch(pac.getDirection()) {
			case UP:
				targetPos = new int[] {pac.getPosition()[0],pac.getPosition()[1] - 4}; //not pac.getPosition()[0]-4 because it's almost always a wall
				break;
			case DOWN:
				targetPos = new int[] {pac.getPosition()[0],pac.getPosition()[1] + 4};
				break;
			case LEFT:
				targetPos = new int[] {pac.getPosition()[0] - 4,pac.getPosition()[1]};
				break;
			case RIGHT:
				targetPos = new int[] {pac.getPosition()[0] + 4,pac.getPosition()[1]};
				break;
			}
			targetPos[0] = (targetPos[0] < 0 || targetPos[0] >= width) ? pac.getPosition()[0] : targetPos[0];
			targetPos[1] = (targetPos[1] < 0 || targetPos[1] >= height) ? pac.getPosition()[1] : targetPos[1];
			target = (Game.INSTANCE.getBoard()[targetPos[1]][targetPos[0]] != Entity.MUR) &&
					(Game.INSTANCE.getBoard()[targetPos[1]][targetPos[0]] != Entity.VOID) ? targetPos[1]*width + targetPos[0]: pPos;
					//more time efficient than considering every nodes connected
		default:
			break;
		}
		return target;
	}
}