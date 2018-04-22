package data;

import java.util.EnumMap;

import enums.Entity;
import enums.GhostType;

/**
 * Class Testing Getting Data 
 * @author Robin Algier - Maxime Mathis--Fumel - Yassin Ourkia
 */
public class Test {

	public static void main(String[] args) {
		
		int lvl = 1;

		DataLoader dataLoader = new DataLoader();
		
		int boardHeight = dataLoader.getBoardHeight(lvl);
		int boardWidth = dataLoader.getBoardWidth(lvl);
		int bestScore = dataLoader.getBestScore();
		int gommes = dataLoader.getGommes(lvl);
		int levels = dataLoader.getLevels();
		int lives = dataLoader.getLives();
		int powerTime = dataLoader.getPowerTime();
		int speed = dataLoader.getSpeed(lvl);
		int[] pacmanPosition = dataLoader.getPacmanPosition(lvl);
		int[] exitPosition = dataLoader.getExitPosition(lvl);
		Entity[][] board = dataLoader.getBoard(lvl);
		EnumMap<GhostType, int[]> ghostsPosition = dataLoader.getGhostsPosition(lvl);
		EnumMap<Entity, Integer> points = dataLoader.getEntityPoints();
		
		System.out.println("Level : "+lvl);
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
		for(int i=0; i<exitPosition.length; i++) {
			System.out.println("ExitPosition["+i+"] : "+exitPosition[i]);
		}
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[0].length; j++) {
				System.out.print(board[i][j].name()+" ");
			}
			System.out.println("");
		}
		for(GhostType type : GhostType.values()) {
			System.out.println(type.toString());
			for(int k : ghostsPosition.get(type)) {
				System.out.println(k);
			}
		}
		for(Entity type : Entity.values()) {
			System.out.println(type.toString());
			if(type.isGivingPoints())
				System.out.println(points.get(type));
		}
	}

}
