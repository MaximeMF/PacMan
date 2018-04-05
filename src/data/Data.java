package data;

import java.util.EnumMap;

public class Data implements IData {

	@Override
	public int getBoardHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getBoardWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int[][] getBoard() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*private void initPlateau() {	
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
	}*/

	@Override
	public int[] getPacmanPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnumMap<GhostType, Integer[]> getGhostsPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnumMap<Entity, Integer> getEntityPoints() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getBestScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLevels() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getGommes() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLives() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

}
