package data;

import java.util.EnumMap;

public class Data implements IData {
	
	
	
	public Data() {
		this.initialise();
	}
	
	
	
	public void initialise() {
		
	}
	
	

    /**
     * {@inheritDoc }
     */
	@Override
	public int getBoardHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public int getBoardWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

    /**
     * {@inheritDoc }
     */
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

    /**
     * {@inheritDoc }
     */
	@Override
	public int[] getPacmanPosition() {
		// TODO Auto-generated method stub
		return null;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public EnumMap<GhostType, Integer[]> getGhostsPosition() {
		// TODO Auto-generated method stub
		return null;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public EnumMap<Entity, Integer> getEntityPoints() {
		// TODO Auto-generated method stub
		return null;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public int getBestScore() {
		// TODO Auto-generated method stub
		return 0;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public int getLevels() {
		// TODO Auto-generated method stub
		return 0;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public int getGommes() {
		// TODO Auto-generated method stub
		return 0;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public int getLives() {
		// TODO Auto-generated method stub
		return 0;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

}
