package data;

public class Ghost implements IGhost {
	
	private int points, state;
	private GhostType type;
	
	public Ghost(GhostType type) {
		// TODO
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public int getPoints() {
		return this.points;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public GhostType getType() {
		return this.type;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public int getState() {
		return this.state;
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public void setState(int state) {
		this.state = state;
	}

}
