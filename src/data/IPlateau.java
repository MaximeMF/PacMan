package data;

public interface IPlateau {
	public int getScore();
	
	public void setScore(int score);
	
	public int getLevel();
	
	public void setLevel(int level);
	
	public int getSpeed();
	
	public void setSpeed(int speed);
	
	public Pacman getPlayer();
	
	public Ghost getGhost(int id);
	
	public int[][] board = new int[0][0];
}
