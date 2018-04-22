package data;

import java.io.*;
import java.util.EnumMap;

import org.json.simple.*;
import org.json.simple.parser.*;

import enums.Entity;
import enums.GhostType;

/**
 * Class loading game data from a json config file.
 * @author Robin Algier - Maxime Mathis--Fumel - Yassin Ourkia
 */
public class DataLoader implements IDataLoader {
	
	
	
	private final String CONFIG = "main";
	
	private JSONObject gameData;
	
	
	
	/**
	 * Constructs an instance of DataLoader.
	 */
	public DataLoader() {
		this.initialise();
	}
	
	
	
	/**
	 * Gets the gameData config file.
	 * @post gameData != null
	 */
	private void initialise() {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader("config/"+CONFIG+".json"));
			this.gameData = (JSONObject) obj;			
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		assert this.gameData != null : "postcondition violated : no gameData configuration file";
	}
	
	
	
	/**
	 * Parses the gameData object.
	 * @param key the element's name whose value is sought.
	 * @return the int value associated to the key
	 * @pre gameData.containsKey(key) == true
	 */
	private int parseInt(JSONObject loc, String key) {
		assert loc.containsKey(key) : "precondition violated : no "+key+" key in json configuration file";
		long value = (long) loc.get(key);
		return Math.toIntExact(value);
	}
	
	/**
	 * Parses a level object.
	 * @param lvl the level sought
	 * @return the object associated to the level
	 * @pre gameData.containsKey(key) == true
	 */
	private JSONObject parseLevel(int lvl) {
		assert this.gameData.containsKey("lvl"+lvl) : "precondition violated : no lvl"+lvl+" key in json configuration file";
		JSONObject level = (JSONObject) this.gameData.get("lvl"+lvl);
		return level;
	}
	
	

    /**
     * {@inheritDoc }
     */
	@Override
	public int getBoardHeight(int lvl) {
		JSONObject level = parseLevel(lvl);
		int boardHeight = parseInt(level, "boardHeight");
		assert boardHeight >= 5 : "postcondition violated : specified boardHeight in json configuration file doesn't meet the specifications";
		return boardHeight;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public int getBoardWidth(int lvl) {
		JSONObject level = parseLevel(lvl);
		int boardWidth = parseInt(level, "boardWidth");
		assert boardWidth >= 5 : "postcondition violated : specified boardWidth in json configuration file doesn't meet the specifications";
		return boardWidth;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public Entity[][] getBoard(int lvl) {
		int boardHeight = getBoardHeight(lvl);
		int boardWidth = getBoardWidth(lvl);
		JSONObject level = parseLevel(lvl);
		JSONArray board = (JSONArray) level.get("board");
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
	public int[] getPacmanPosition(int lvl) {
		JSONObject level = parseLevel(lvl);
		JSONArray position = (JSONArray) level.get("posPacman");
		int[] posArray = new int[position.size()];
        for(int i=0; i<position.size(); i++) {
        	posArray[i] = Math.toIntExact((long)position.get(i));
        }
        assert posArray.length == 2 && posArray[0] >= 0 && posArray[0]<getBoardWidth(lvl) && posArray[1] >= 0 && posArray[1]<getBoardHeight(lvl) : "postcondition violated : specified posPacman in json configuration file doesn't meet the specifications";
        return posArray;
	}
	
    /**
     * {@inheritDoc }
     */
	@Override
	public int[] getExitPosition(int lvl) {
		JSONObject level = parseLevel(lvl);
		JSONArray position = (JSONArray) level.get("exitPosition");
		int[] posArray = new int[position.size()];
		for(int i=0; i<position.size(); i++) {
        	posArray[i] = Math.toIntExact((long)position.get(i));
        }
        assert posArray.length == 2 && posArray[0] >= 0 && posArray[0]<getBoardWidth(lvl) && posArray[1] >= 0 && posArray[1]<getBoardHeight(lvl) : "postcondition violated : specified posPacman in json configuration file doesn't meet the specifications";
        return posArray;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public EnumMap<GhostType, int[]> getGhostsPosition(int lvl) {
		EnumMap<GhostType, int[]> posMap = new EnumMap<>(GhostType.class);
		JSONObject level = parseLevel(lvl);
		JSONObject posGhosts = (JSONObject) level.get("posGhosts");
		for(GhostType type : GhostType.values()) {
			JSONArray pos = (JSONArray) posGhosts.get(type.name().toLowerCase());
			int[] posArray = new int[pos.size()];
	        for(int i=0; i<pos.size(); i++) {
	        	posArray[i] = Math.toIntExact((long)pos.get(i));
	        	assert posArray.length == 2 && posArray[0] >= 0 && posArray[0]<getBoardWidth(lvl) && posArray[1] >= 0 && posArray[1]<getBoardHeight(lvl) : "postcondition violated : specified posGhosts in json configuration file doesn't meet the specifications";	        
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
		JSONObject pointsEntities = (JSONObject) this.gameData.get("points");
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
		String filename = (String) this.gameData.get("bestScore");
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
		String filename = (String) this.gameData.get("bestScore");
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
		int nbLevels = parseInt(this.gameData, "nbLevels");
		assert nbLevels >= 1 :  "postcondition violated : specified nbLevels in json configuration file doesn't meet the specifications";
		return nbLevels;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public int getGommes(int lvl) {
		JSONObject level = parseLevel(lvl);
		int nbGommes = parseInt(level, "nbGommes");
		assert nbGommes >= 1 && nbGommes <= getBoardWidth(lvl)*getBoardHeight(lvl) :  "postcondition violated : specified nbGommes in json configuration file doesn't meet the specifications";
		return nbGommes;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public int getLives() {
		int nbLives = parseInt(this.gameData, "nbLives");
		assert nbLives >= 1 :  "postcondition violated : specified nbLives in json configuration file doesn't meet the specifications";
		return nbLives;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public int getSpeed(int lvl) {
		JSONObject level = parseLevel(lvl);
		int speed = parseInt(level, "speedPercent");
		assert speed >= 50 && speed <= 200 :  "postcondition violated : specified speedPercent in json configuration file doesn't meet the specifications";
		return speed;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public int getPowerTime() {
		int powerTime = parseInt(this.gameData, "powerTime");
		assert powerTime >= 0 :  "postcondition violated : specified powerTime in json configuration file doesn't meet the specifications";
		return powerTime;
	}
	
	/**
	 * {@inheritDoc }
	 */
	@Override
	public int[] getModeTime() {
		JSONArray mode = (JSONArray) this.gameData.get("modeTime");
		int[] posArray = new int[mode.size()];
        for(int i=0; i<mode.size(); i++) {
        	posArray[i] = Math.toIntExact((long)mode.get(i));
        	assert posArray[i] > 0 : "postcondition violated : specified modeTime in json configuration file doesn't meet the specifications";
        }
        return posArray;
	}

}
