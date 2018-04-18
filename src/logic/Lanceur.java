package logic;

import java.io.IOException;
import java.util.Arrays;

import enums.Entity;
import enums.GhostType;

/**
 * Class Launcher Testing the logic classes 
 * @author Robin Algier - Maxime Mathis--Fumel - Yassin Ourkia
 *
 */
public class Lanceur {

	public static void main(String args[]) {
		IGame game = Game.getInstance();
		printBoard(game.getBoard(), game);
		int i = 0;
		while(true) {
			//System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			//System.out.println("\f");
			try {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(GhostType gt : GhostType.values())
				game.getGhost(gt).move();
			printBoard(game.getBoard(), game);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(i);
			i++;
		}
	}

	/**
	 * Print board on console 
	 * @param board
	 * @param game
	 */
	public static void printBoard(Entity[][] board, IGame game) {
		String brd = "";
		int[] plpos = game.getPlayer().getPosition();
		int[] rpos = game.getGhost(GhostType.RED).getPosition();
		int[] cpos = game.getGhost(GhostType.CYAN).getPosition();
		int[] ppos = game.getGhost(GhostType.PINK).getPosition();
		int[] opos = game.getGhost(GhostType.ORANGE).getPosition();
		int x = 0;
		int y = 0;
		for(Entity[] i : board) {
			for(Entity j : i) {
				if(Arrays.equals(rpos, new int[] {x,y}))
					brd += "F";
				else if(Arrays.equals(cpos, new int[] {x,y}))
					brd += "F";
				else if(Arrays.equals(ppos, new int[] {x,y}))
					brd += "F";
				else if(Arrays.equals(opos, new int[] {x,y}))
					brd += "F";
				else if(Arrays.equals(plpos, new int[] {x,y}))
					brd += "P";
				else
					switch(j) {
					case MUR:
						brd += "█";
						break;
					case GOMME:
						brd += "o";
						break;
					case SUPERGOMME:
						brd += "O";
						break;
					case CHEMIN:
						brd += " ";
						break;
					case TUNNEL:
						brd += " ";
						break;
					case FRUIT:
						brd += ".";
						break;
					default:
						break;
					}
				x++;
			}
			brd += "\n";
			y++;
			x = 0;
		}
		System.out.println(brd);
	}

}
