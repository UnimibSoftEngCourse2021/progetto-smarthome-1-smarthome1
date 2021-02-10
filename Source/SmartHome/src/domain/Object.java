package domain;

import java.util.List;

public abstract class Object {

	private String name;
	private boolean active; // nome cambiato da state per incongruenza con setter
	private String objectID;
	private String referencedRoom;
	public enum ObjectType {ALARM, DOOR, HEATER, LIGHT, WINDOW}
	private ObjectType type;
	
	private List<Sensor> sensors;
	private ConflictHandler handler;
	
	public String getReferencedRoom() {
		return referencedRoom;
	}

	public void setReferencedRoom(String referencedRoom) {
		this.referencedRoom = referencedRoom;
	}

	private Config config;
	
	public ObjectType getType() {
		return type;
	}

	public void setType(ObjectType type) {
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