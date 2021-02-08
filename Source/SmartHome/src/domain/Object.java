package domain;

import java.util.List;

public abstract class Object {

	private String name;
	private boolean state; //considerare cambio nome
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

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
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