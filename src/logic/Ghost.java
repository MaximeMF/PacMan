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
		this.dijkstra = new Dijkstra();
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
			if(this.currentDirection != null && Direction.opposite(dir) == this.currentDirection)
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

	public void init() {
		this.respawn(false);
	}

	@Override
	public int getSpeed() {
		return this.speed;
	}


	private Direction dijkstra(int from, int to) {
		int[][] matrix = Game.INSTANCE.getBoardMatrix();
		int x = this.position[0];
		int y = this.position[1];
		//int height = Game.INSTANCE.getBoardHeight();
		int width = Game.INSTANCE.getBoardWidth();
		if(this.currentDirection != null)
			switch(this.currentDirection) {
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

		/*this.dijkstra.createGraph(matrix);
		int nextPos = this.dijkstra.launch(from, to);
		System.out.println(nextPos);*/
		ArrayList<Integer> chemin = this.dijkstra.dijkstra(matrix, from, to);
		System.out.println(chemin);
		int nextPos = chemin.get(1);
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



	@Override
	public void move() {
		if (this.movable) {
			int target;
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
					target = 0;
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
						this.randomMove();
				break;
			case 2: //scatter
				switch(this.type) {
				case CYAN:
					target = height * width - 1;
					break;
				case ORANGE:
					target = (height-1) * width;
					break;
				case PINK:
					target = 0;
					break;
				case RED:
					target = width - 1;
					break;
				default:
					target = 0;
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
						this.randomMove();
				break;
			case 3: //frightened
				this.randomMove();
				break;
			}
		}
	}

	private int specialTarget(int gPos, int pPos, int width, int height) {
		int target = 0;
		int[] targetPos = {0,0};
		PacMan pac = Game.INSTANCE.player;
		//int add = 0;
		switch(this.type) {
		case CYAN:
			switch(pac.getDirection()) {
			case UP:
				targetPos = new int[] {pac.getPosition()[0] - 2,pac.getPosition()[1] - 2};
				//add = (pPos + (-4)*width - 4) > 0 ? ((-4)*width - 4) : 0;
				break;
			case DOWN:
				targetPos = new int[] {pac.getPosition()[0],pac.getPosition()[1] + 2};
				//add = (pPos + 4*width) < (height*width) ? 4*width : 0;
				break;
			case LEFT:
				targetPos = new int[] {pac.getPosition()[0] - 2,pac.getPosition()[1]};
				//add = (pPos%width) > 3 ? -4 : 0;
				break;
			case RIGHT:
				targetPos = new int[] {pac.getPosition()[0],pac.getPosition()[1] + 2};
				//add = (pPos%width) < width-4 ? 4 : 0;
				break;
			}
			/*int dx = (pPos+add)%width - this.position[0];
			int dy = (pPos+add)/width - this.position[1];
			add = (pPos+add+dx < 0 || pPos+add+dx >= width*height) ? add : add+dx;
			add =  (pPos+add+dy*width < 0 || pPos+add+dx*width >= width*height) ? add : add+dy*width;
			target = pPos + add;*/
			targetPos[0] += targetPos[0] - this.position[0];
			targetPos[1] += targetPos[1] - this.position[1];
			targetPos[0] = (targetPos[0] < 0 || targetPos[0] >= width) ? pac.getPosition()[0] : targetPos[0];
			targetPos[1] = (targetPos[1] < 0 || targetPos[1] >= height) ? pac.getPosition()[1] : targetPos[1];
			target = targetPos[1]*width + targetPos[0];
			break;
		case ORANGE:
			int[] pacPos = Game.INSTANCE.player.getPosition();
			int dist = (int)Math.sqrt(Math.pow(this.position[0]-pacPos[0],2) + Math.pow(this.position[1]-pacPos[1],2));
			System.out.println(dist);
			target = dist < 4 ? (height-1)*width : pPos;
			break;
		case PINK:
			switch(pac.getDirection()) {
			case UP:
				targetPos = new int[] {pac.getPosition()[0] - 2,pac.getPosition()[1] - 2};
				//add = (pPos + (-4)*width - 4) > 0 ? ((-4)*width - 4) : 0;
				break;
			case DOWN:
				targetPos = new int[] {pac.getPosition()[0],pac.getPosition()[1] + 2};
				//add = (pPos + 4*width) < (height*width) ? 4*width : 0;
				break;
			case LEFT:
				targetPos = new int[] {pac.getPosition()[0] - 2,pac.getPosition()[1]};
				//add = (pPos%width) > 3 ? -4 : 0;
				break;
			case RIGHT:
				targetPos[1] = (targetPos[1] < 0 || targetPos[1] >= height) ? pac.getPosition()[1] : targetPos[1];
				//add = (pPos%width) < width-4 ? 4 : 0;
				break;
			}
			//target = pPos + add;
			targetPos[0] = (targetPos[0] < 0 || targetPos[0] >= width) ? pac.getPosition()[0] : targetPos[0];
			targetPos[1] = (targetPos[1] < 0 || targetPos[1] >= height) ? pac.getPosition()[1] : targetPos[1];
			target = targetPos[1]*width + targetPos[0];
		default:
			break;
		}
		return target;
	}
}