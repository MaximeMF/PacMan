package data;

public class Ghost implements IGhost {
	
	private int id = 3, points, state;
	
	public Ghost() {
		// TO DO
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
	public int getId() {
		return this.id;
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
