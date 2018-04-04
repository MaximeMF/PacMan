package data;

public class Pacman implements IPacman {
	
	private int lives;
	
	public Pacman() {
		this.lives = 3;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public int getLives() {
		return this.lives;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public void die() {
		
	}

}
