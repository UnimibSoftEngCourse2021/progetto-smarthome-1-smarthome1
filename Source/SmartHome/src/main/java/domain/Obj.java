package domain;

public abstract class Obj {

	private String name;
	private boolean active;
	private String objID;
	private Room room;
	public enum ObjType {ALARM, DOOR, HEATER, LIGHT, WINDOW, SHADER}
	private ObjType objType;

	public Obj(String name, ObjType objType, Room room) {
		this.name = name;
		this.active = false;
		this.objType = objType;
		this.room = room;
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

	public String getObjID() {
		return objID;
	}

	public void setObjID(String objID) {
		this.objID = objID;
	}
	
	public ObjType getObjType() {
		return objType;
	}

	public void setObjType(ObjType objType) {
		this.objType = objType;
	}

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