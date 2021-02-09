package domain;

import java.util.List;

public abstract class Object {

	private String name;
	private boolean active; // nome cambiato da state per incongruenza con setter
	private String objectID;
	private enum Type {ALARM, DOOR, HEATER, LIGHT, WINDOW}
	private Type type;
	
	private List<Sensor> sensors;
	private ConflictHandler handler;
	private Config config;
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean state) {
		this.active = state;
	}

	public String getObjectID() {
		return objectID;
	}

	public void setObjectID(String objectID) {
		this.objectID = objectID;
	}

	/**
	 * 
	 * @param newState
	 */
	public void update(boolean newState) {
		// TODO - implement Object.update
		throw new UnsupportedOperationException();
	}

}