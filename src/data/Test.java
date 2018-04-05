package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Test {

	public static void main(String[] args) {

		Data data = new Data();
		
		int boardHeight = data.getBoardHeight();
		int boardWidth = data.getBoardWidth();
		int bestScore = data.getBestScore();
		int gommes = data.getGommes();
		int levels = data.getLevels();
		int lives = data.getLives();
		int powerTime = data.getPowerTime();
		int speed = data.getSpeed();
		int[] pacmanPosition = data.getPacmanPosition();
		int[][] board = data.getBoard();
		
		System.out.println("BoardHeight : "+boardHeight);
		System.out.println("BoardWidth : "+boardWidth);
		System.out.println("BestScore : "+bestScore);
		System.out.println("Gommes : "+gommes);
		System.out.println("Levels : "+levels);
		System.out.println("Lives : "+lives);
		System.out.println("PowerTime : "+powerTime);
		System.out.println("Speed : "+speed);
		for(int i=0; i<pacmanPosition.length; i++) {
			System.out.println("PacmanPosition["+i+"] : "+pacmanPosition[i]);
		}
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[0].length; j++) {
				System.out.print(board[i][j]);
			}
			System.out.println("");
		}

	}

}
