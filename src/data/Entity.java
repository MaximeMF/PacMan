package data;

/**
 * Enumeration representing the various entities of the game.
 * @author Robin ALGIER - Maxime MATHIS--FUMEL - Yassin OURKIA
 */
public enum Entity {
	
	MUR(0),
	CHEMIN(1),
	GOMME(2),
	SUPERGOMME(3),
	TUNNEL(4),
	FRUIT(5),
	PACMAN(6),
	GHOST(7);
	
	private int id;
	
	/**
	 * Associates an id to an entity.
	 * @param id the id associated to the entity
	 */
	private Entity(int id) {
		this.id = id;
	}
	
	/**
	 * Gets the id.
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}

}
