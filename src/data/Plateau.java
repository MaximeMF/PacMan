package data;

import java.io.FileReader;
import java.io.IOException;

public class Plateau implements IPlateau {

	private int score, level, speed;
	private Pacman player;
	private int[][] board;
	
	public Plateau() {
		this.score = 0;
		this.level = 1;
		this.speed = 2;
		this.player = new Pacman();
		this.initPlateau();
	}
	
	private void initPlateau() {
		this.board = new int[28][31];
		
		String s = "";
		try (FileReader fr = new FileReader("data/board")) {
			boolean b = fr.ready();
			while(b) {
				if(fr.ready()) s += (char)fr.read();
				else b = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(s);
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
	public int getSpeed() {
		return this.speed;
	}

	@Override
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	@Override
	public Pacman getPlayer() {
		return player;
	}

	@Override
	public Ghost getGhost(int id) {
		return findGhost(id);
	}
	
	private Ghost findGhost(int id) {
		// TO DO
		return new Ghost();
	}

	@Override
	public void increasesScore(int score) {
		this.score += score;
	}

	@Override
	public void incrementsLevel() {
		this.level++;
	}

	@Override
	public void changeCell(int x, int y, int value) {
		
	}

}
