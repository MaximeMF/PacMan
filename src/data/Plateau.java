package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
			int xmax = 0, ymax = 0;
			if(sc.hasNextInt()) 
				xmax = sc.nextInt(); 
			else 
				throw new IOException();
			if(sc.hasNextInt()) 
				ymax = sc.nextInt();
			else 
				throw new IOException();
			board = new int[ymax][xmax];
			for(int j=0; j<ymax; j++) {
				for (int i=0; i<xmax; i++) {
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
		// TO DO
	}

}
