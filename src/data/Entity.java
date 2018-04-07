package data;

/**
 * Enumeration representing the various entities of the game.
 * @author Robin ALGIER - Maxime MATHIS--FUMEL - Yassin OURKIA
 */
public enum Entity {
	
	MUR(0,false),
	CHEMIN(1,false),
	GOMME(2,true),
	SUPERGOMME(3,true),
	TUNNEL(4,false),
	FRUIT(5,true),
	PACMAN(6,false),
	GHOST(7,true);
	
	private int id;
	private boolean givePoints;
	
	/**
	 * Associates an id to an entity.
	 * @param id the id associated to the entity
	 */
	private Entity(int id, boolean givePoints) {
		this.id = id;
		this.givePoints = givePoints;
	}
	
	/**
	 * Gets the id.
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Gets an entity by his id.
	 * @param id the id of the entity to get
	 * @return the entity associated to the id
	 */
	public static Entity getEntityById(int id) {
		Entity ret = null;
		for(Entity ent : Entity.values()) {
			if(ent.id == id)
				ret = ent;
		}
		return ret;
	}
	
	/**
	 * Checks if the entity can give points.
	 * @return true if the entity can give points, false otherwise
	 */
	public boolean isGivingPoints() {
		return this.givePoints;
	}

}
