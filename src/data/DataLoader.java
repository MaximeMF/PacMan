package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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
public class DataLoader implements IDataLoader {
	
	
	
	private final String CONFIG = "main";
	
	private JSONObject json;
	
	
	
	/**
	 * Constructs an instance of DataLoader.
	 */
	public DataLoader() {
		this.initialise();
	}
	
	
	
	/**
	 * Gets the json config file.
	 * @post json != null
	 */
	private void initialise() {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader("config/"+CONFIG+".json"));
			this.json = (JSONObject) obj;			
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		assert this.json != null : "postcondition violated : no json configuration file";
	}
	
	
	
	/**
	 * Parse the json object.
	 * @param key the element's name whose value is sought.
	 * @return the int value associated to the key
	 * @pre json.containsKey(key) == true
	 */
	private int parseInt(String key) {
		assert this.json.containsKey(key) : "precondition violated : no "+key+" key in json configuration file";
		long value = (long) this.json.get(key);
		return Math.toIntExact(value);
	}
	
	

    /**
     * {@inheritDoc }
     */
	@Override
	public int getBoardHeight() {
		int boardHeight = parseInt("boardHeight");
		assert boardHeight >= 5 : "postcondition violated : specified boardHeight in json configuration file doesn't meet the specifications";
		return boardHeight;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public int getBoardWidth() {
		int boardWidth = parseInt("boardWidth");
		assert boardWidth >= 5 : "postcondition violated : specified boardWidth in json configuration file doesn't meet the specifications";
		return boardWidth;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public Entity[][] getBoard() {
		int boardHeight = getBoardHeight();
		int boardWidth = getBoardWidth();
		JSONArray board = (JSONArray) this.json.get("board");
		Entity[][] boardMatrix = new Entity[boardHeight][boardWidth];
		for(int i=0; i<boardHeight; i++) {
			JSONArray row = (JSONArray) board.get(i);
	       	for(int j=0; j<boardWidth; j++) {
	       		boardMatrix[i][j] = Entity.getEntityById(Math.toIntExact((long)row.get(j)));
	       		assert boardMatrix[i][j] != null : "postcondition violated : specified board in json configuration file doesn't meet the specifications";
	       	}
	    }
		return boardMatrix;
	}	

    /**
     * {@inheritDoc }
     */
	@Override
	public int[] getPacmanPosition() {
		JSONArray position = (JSONArray) this.json.get("posPacman");
		int[] posArray = new int[position.size()];
        for(int i=0; i<position.size(); i++) {
        	posArray[i] = Math.toIntExact((long)position.get(i));
        }
        assert posArray.length == 2 && posArray[0] >= 0 && posArray[0]<getBoardWidth() && posArray[1] >= 0 && posArray[1]<getBoardHeight() : "postcondition violated : specified posPacman in json configuration file doesn't meet the specifications";
        return posArray;
	}
	
    /**
     * {@inheritDoc }
     */
	@Override
	public int[] getExitPosition() {
		JSONArray position = (JSONArray) this.json.get("exitPosition");
		int[] posArray = new int[position.size()];
		for(int i=0; i<position.size(); i++) {
        	posArray[i] = Math.toIntExact((long)position.get(i));
        }
        assert posArray.length == 2 && posArray[0] >= 0 && posArray[0]<getBoardWidth() && posArray[1] >= 0 && posArray[1]<getBoardHeight() : "postcondition violated : specified posPacman in json configuration file doesn't meet the specifications";
        return posArray;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public EnumMap<GhostType, int[]> getGhostsPosition() {
		EnumMap<GhostType, int[]> posMap = new EnumMap<>(GhostType.class);
		JSONObject posGhosts = (JSONObject) this.json.get("posGhosts");
		for(GhostType type : GhostType.values()) {
			JSONArray pos = (JSONArray) posGhosts.get(type.name().toLowerCase());
			int[] posArray = new int[pos.size()];
	        for(int i=0; i<pos.size(); i++) {
	        	posArray[i] = Math.toIntExact((long)pos.get(i));
	        	assert posArray.length == 2 && posArray[0] >= 0 && posArray[0]<getBoardWidth() && posArray[1] >= 0 && posArray[1]<getBoardHeight() : "postcondition violated : specified posGhosts in json configuration file doesn't meet the specifications";	        
	        }
			posMap.put(type, posArray);
		}
		return posMap;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public EnumMap<Entity, Integer> getEntityPoints() {
		EnumMap<Entity, Integer> pointsMap = new EnumMap<>(Entity.class);
		JSONObject pointsEntities = (JSONObject) this.json.get("points");
		for(Entity type : Entity.values()) {
			if(type.isGivingPoints()) {
				int points = Math.toIntExact((long)pointsEntities.get(type.name().toLowerCase()));
		        pointsMap.put(type, points);
		        assert points > 0 :  "postcondition violated : specified points in json configuration file doesn't meet the specifications";
			}
		}
		return pointsMap;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public int getBestScore() {
		int bestScore = -1;
		String filename = (String) this.json.get("bestScore");
		try (BufferedReader file = new BufferedReader(new FileReader(filename))) {
			bestScore = Integer.valueOf(file.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
		assert bestScore >= 0 : "postcondition violated : specified bestScore doesn't meet the specifications";
		return bestScore;
	}
	
    /**
     * {@inheritDoc }
     */
	@Override
	public void setBestScore(int bs) {
		assert bs > 0 : "precondition violated : new bestScore <= 0";
		String filename = (String) this.json.get("bestScore");
		try (BufferedWriter file = new BufferedWriter(new FileWriter(filename))) {
			file.write(String.valueOf(bs));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public int getLevels() {
		int nbLevels = parseInt("nbLevels");
		assert nbLevels >= 1 :  "postcondition violated : specified nbLevels in json configuration file doesn't meet the specifications";
		return nbLevels;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public int getGommes() {
		int nbGommes = parseInt("nbGommes");
		assert nbGommes >= 1 && nbGommes <= getBoardWidth()*getBoardHeight() :  "postcondition violated : specified nbGommes in json configuration file doesn't meet the specifications";
		return nbGommes;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public int getLives() {
		int nbLives = parseInt("nbLives");
		assert nbLives >= 1 :  "postcondition violated : specified nbLives in json configuration file doesn't meet the specifications";
		return nbLives;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public int getSpeed() {
		int speed = parseInt("speedPercent");
		assert speed >= 50 && speed <= 200 :  "postcondition violated : specified speedPercent in json configuration file doesn't meet the specifications";
		return speed;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public int getPowerTime() {
		int powerTime = parseInt("powerTime");
		assert powerTime >= 0 :  "postcondition violated : specified powerTime in json configuration file doesn't meet the specifications";
		return powerTime;
	}

}
