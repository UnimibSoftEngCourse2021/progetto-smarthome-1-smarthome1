package domain;

import java.util.List;

public abstract class Object {

	private String name;
	private boolean active; // nome cambiato da state per incongruenza con setter
	private String objectID;
	private Room room;
	public enum ObjectType {ALARM, DOOR, HEATER, LIGHT, WINDOW, SHADER}
	private ObjectType objectType;
	
	private ConflictHandler handler; //da togliere

	public Object(String name, ObjectType objectType, Room room) {
		this.name = name;
		this.active = false;
		this.objectType = objectType;
		this.room = room;
		handler = ConflictHandler.getInstance();
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
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
	
	public ObjectType getObjectType() {
		return objectType;
	}

	public void setObjectType(ObjectType objectType) {
		this.objectType = objectType;
	}
	/**
	 * 
	 * @param newState
	 */
	public void update(double sensorValue) {
		/*
		 * potrebbe essere necessario aggiungere una gestione
		 * delle eccezzioni per valori sballati dei sensori
		 */
			if(sensorValue == 0.00) 
				active = false;
			else if (sensorValue == 1.00)
				active = true;
	}

}