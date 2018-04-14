package logic;

import java.util.Arrays;

import data.Entity;
import data.GhostType;

public class Lanceur {

	public static void main(String args[]) {
		IGame game = Game.getInstance();
		printBoard(game.getBoard(), game);
		while(true) {
			System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			//System.out.println("\f");
			for(GhostType gt : GhostType.values())
				game.getGhost(gt).move();
			printBoard(game.getBoard(), game);
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

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
					brd += "☻";
				else if(Arrays.equals(cpos, new int[] {x,y}))
					brd += "☻";
				else if(Arrays.equals(ppos, new int[] {x,y}))
					brd += "☻";
				else if(Arrays.equals(opos, new int[] {x,y}))
					brd += "☻";
				else if(Arrays.equals(plpos, new int[] {x,y}))
					brd += "☺";
				else
					switch(j) {
					case MUR:
						brd += "█";
						break;
					case GOMME:
						brd += "◦";
						break;
					case SUPERGOMME:
						brd += "○";
						break;
					case CHEMIN:
						brd += " ";
						break;
					case TUNNEL:
						brd += " ";
						break;
					case FRUIT:
						brd += "♣";
						break;
					}
				y++;
			}
			brd += "\n";
			x++;
			y = 0;
		}
		System.out.println(brd);
	}

}
