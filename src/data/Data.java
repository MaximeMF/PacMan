package data;

import java.io.FileReader;
import java.io.IOException;
import java.util.EnumMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Class loading game data from a json config file.
 * @author Robin Algier - Maxime Mathis--Fumel - Yassin Ourkia
 */
public class Data implements IData {
	
	
	
	private final String CONFIG = "main";
	
	private JSONObject json;
	
	
	
	/**
	 * Constructs an instance of Data.
	 */
	public Data() {
		this.initialise();
	}
	
	
	
	/**
	 * Gets 
	 */
	private void initialise() {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader("data/"+CONFIG+".json"));
			this.json = (JSONObject) obj;			
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	
	
	private int parseInt(String key) {
		long value = (long) this.json.get(key);
		return Math.toIntExact(value);
	}
	
	

    /**
     * {@inheritDoc }
     */
	@Override
	public int getBoardHeight() {
		int boardHeight = parseInt("boardHeight");
		return boardHeight;
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



    /**
     * {@inheritDoc }
     */
	@Override
	public int getPowerTime() {
		// TODO Auto-generated method stub
		return 0;
	}

}
