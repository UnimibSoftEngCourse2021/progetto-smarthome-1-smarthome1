package domain;

import java.util.List;

public abstract class Object {

	private String name;
	private boolean active; // nome cambiato da state per incongruenza con setter
	private String objectID;
	private String referencedRoom;
	public enum ObjectType {ALARM, DOOR, HEATER, LIGHT, WINDOW}
	private ObjectType objectType;
	
	private List<Sensor> sensors;
	private ConflictHandler handler;
	private Config config;
	private AutomaticControl automaticControl;

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

	public String getReferencedRoom() {
		return referencedRoom;
	}

	public void setReferencedRoom(String referencedRoom) {
		this.referencedRoom = referencedRoom;
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
		switch (objectType) {
		case ALARM:
			if(sensorValue == 0.00) 
				active = false;
			else if (sensorValue == 1.00)
				active = true;
			break;
		case DOOR:
			if(sensorValue == 0.00) 
				active = false;
			else if (sensorValue == 1.00)
				active = true;
			break;
		case HEATER:
			automaticControl.checkTempTresholds(sensorValue, null); // la pubList è necessaria? vedi dichiarazione di checkTempTreshholds
			break;
		case LIGHT:
			if(sensorValue == 0.00) 
				active = false;
			else if (sensorValue == 1.00)
				active = true;
			break;
		case WINDOW:
			if(sensorValue == 0.00) 
				active = false;
			else if (sensorValue == 1.00)
				active = true;
			break;
		}
	}

}