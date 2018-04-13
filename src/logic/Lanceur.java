package logic;

import data.Entity;
import data.GhostType;

public class Lanceur {
	
	public static void main(String args[]) {
		IGame game = Game.getInstance();
		printBoard(game.getBoard());
	}
	
	public static void printBoard(Entity[][] board) {
		for(Entity[] i : board) {
			for(Entity j : i) {
				switch(j) {
				case MUR:
					System.out.print("█");
					break;
				case GOMME:
					System.out.print("o");
					break;
				case SUPERGOMME:
					System.out.print("O");
					break;
				case CHEMIN:
					System.out.print(" ");
					break;
				case TUNNEL:
					System.out.print(" ");
					break;
				case FRUIT:
					System.out.print("♪");
					break;
				}
			}
			System.out.println();
		}
	}
	
}
