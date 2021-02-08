package domain;

import java.util.List;

public abstract class Object {

	private String name;
	private boolean state;
	private enum Type {ALARM, DOOR, HEATER, LIGHT, WINDOW}
	
	private List<Sensor> sensors;
	private ConflictHandler handler;
	private Config config;

	/**
	 * 
	 * @param newState
	 */
	public void update(boolean newState) {
		// TODO - implement Object.update
		throw new UnsupportedOperationException();
	}

}