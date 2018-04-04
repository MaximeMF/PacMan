package data;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

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
		try (Scanner sc = new Scanner(new File("data/board"))) {
			board = new int[WIDTH][HEIGHT];
			for(int j=0; j<HEIGHT; j++) {
				for (int i=0; i<WIDTH; i++) {
					if(sc.hasNextInt()) 
						board[j][i] = sc.nextInt();
					else
						throw new IOException();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		return null;
		// TODO
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
		// TODO
	}



	@Override
	public int getCell(int x, int y) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public int previous(int x, int y) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public int[] getPos(int id) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public int nbGommes() {
		// TODO Auto-generated method stub
		return 0;
	}


}
