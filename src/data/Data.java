package data;

import java.io.FileReader;
import java.io.IOException;
import java.util.EnumMap;

import org.json.simple.JSONArray;
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
	 * Gets the json config file.
	 */
	private void initialise() {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader("config/"+CONFIG+".json"));
			this.json = (JSONObject) obj;			
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Parse the json object.
	 * @param key the element's name whose value is sought.
	 * @return the int value associated to the key
	 */
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
		int boardWidth = parseInt("boardWidth");
		return boardWidth;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public int[][] getBoard() {
		int boardHeight = getBoardHeight();
		int boardWidth = getBoardWidth();
		JSONArray board = (JSONArray) json.get("board");
		int[][] boardMatrix = new int[boardHeight][boardWidth];
		for(int i=0; i<boardHeight; i++) {
			JSONArray row = (JSONArray) board.get(i);
	       	for(int j=0; j<boardWidth; j++) {
	       		boardMatrix[i][j] = Math.toIntExact((long)row.get(j));
	       	}
	    }
		return boardMatrix;
	}	

    /**
     * {@inheritDoc }
     */
	@Override
	public int[] getPacmanPosition() {
		JSONArray position = (JSONArray) json.get("posPacman");
		int[] posArray = new int[position.size()];
        for(int i=0; i<position.size(); i++) {
        	posArray[i] = Math.toIntExact((long)position.get(i));
        }
        return posArray;
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
		int bestScore = parseInt("bestScore");
		return bestScore;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public int getLevels() {
		int nbLevels = parseInt("nbLevels");
		return nbLevels;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public int getGommes() {
		int nbGommes = parseInt("nbGommes");
		return nbGommes;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public int getLives() {
		int nbLives = parseInt("nbLives");
		return nbLives;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public int getSpeed() {
		int speed = parseInt("speed");
		return speed;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public int getPowerTime() {
		int powerTime = parseInt("powerTime");
		return powerTime;
	}

}
