package main.java.domain;

import java.util.ArrayList;
import java.util.List;

import main.java.domain.Object.ObjectType;
import main.java.domain.Sensor.SensorCategory;

public class Room {

	private String roomID;
	private short floor;
	private short lightsNum;
	private short doorsNum;
	private short heatersNum;
	private short windowsNum;
	//private boolean lightControlled = false;
	//private boolean airControlled = false;
	private List<Sensor> sensors;
	private List<Object> objects;
	private TimerOP timer;
	
	public Room(String roomID, Short floor) {
		this.roomID = roomID;
		this.floor = floor;
		timer = new TimerOP(this);
		timer.setRoom(this);
		sensors = new ArrayList<Sensor>();
		objects = new ArrayList<Object>();
	}

	public Room() {
		sensors = new ArrayList<Sensor>();
		objects = new ArrayList<Object>();
	}

	public void instantiateSensor(SensorCategory category, String name) {
		Sensor sensor = new Sensor(name, category, this);
		sensors.add(sensor);
		AutomaticControl.getInstance().addSensor(sensor);
		sensor.setSensorID(category.toString() + "_" + roomID + "_" + String.valueOf(floor));
		if(category.equals(SensorCategory.MOVEMENT))
			if(Alarm.isCreated())
				Alarm.getInstance().setSensor(sensor);
	}
	
	public void instantiateObject(ObjectType type, String name) {
		switch(type) {
		case DOOR:
			Door door= new Door(name, this);
			door.getSensor().attach(door);
			objects.add(door);
			door.setObjectID("DOOR_" + roomID.toUpperCase() + "_" + String.valueOf(floor) + "_" + String.valueOf(getObjects(ObjectType.DOOR).indexOf(door)));
			door.getSensor().concatName(door.getObjectID());
			door.getSensor().setSensorID("DOOR_" + door.getObjectID());
			ConflictHandler.getInstance().addObject(door);
			//ScenariosHandler.getInstance().addObject(door);
			break;
		case WINDOW:
			Window window = new Window(name, this);
			window.getSensor().attach(window);
			objects.add(window);
			window.setObjectID("WINDOW_" + roomID.toUpperCase() + "_" + String.valueOf(floor) + "_" + String.valueOf(getObjects(ObjectType.WINDOW).indexOf(window)));
			window.getSensor().concatName(window.getObjectID());
			window.getSensor().setSensorID("WINDOW_" + window.getObjectID());
			Shader shader = window.getShader();
			objects.add(shader);
			shader.setObjectID("SHADER_" + window.getObjectID());
			shader.getSensor().setSensorID("SHADER_" + shader.getObjectID());
			ConflictHandler.getInstance().addObject(window);
			ConflictHandler.getInstance().addObject(shader);
			//ScenariosHandler.getInstance().addObject(window);
			//ScenariosHandler.getInstance().addObject(shader);
			break;
		case LIGHT:
			Light light= new Light(name, this);
			light.getSensor().attach(light);
			objects.add(light);
			light.setObjectID("LIGHT_" + roomID.toUpperCase() + "_" + String.valueOf(floor) + "_" + String.valueOf(getObjects(ObjectType.LIGHT).indexOf(light)));
			light.getSensor().concatName(light.getObjectID());
			light.getSensor().setSensorID("LIGHT_" + light.getObjectID());
			ConflictHandler.getInstance().addObject(light);
			//ScenariosHandler.getInstance().addObject(light);
			break;
		case HEATER:
			Heater heater= new Heater(name, this);
			objects.add(heater);
			heater.setObjectID("HEATER_" + roomID.toUpperCase() + "_" + String.valueOf(floor) + "_" + String.valueOf(getObjects(ObjectType.HEATER).indexOf(heater)));
			ConflictHandler.getInstance().addObject(heater);
			//ScenariosHandler.getInstance().addObject(heater);
			break;
		default:
			break;
		}
		
	}
	
	public void setDoorCode(String code) {
		Door door = (Door)getObjects(ObjectType.DOOR).get(0);
		door.setCode(code);
	}
	
	public List<Object> getObjectList(){
		return objects;
	}
	
	public List<Object> getObjects(ObjectType type) {
		List<Object> objectList = new ArrayList<Object>();	
		for(Object object: objects) {
			if(object.getObjectType().equals(type))
				objectList.add(object);
		}
		return objectList;
	}

	public List<Sensor> getSensors(SensorCategory category){
		List<Sensor> sensorList = new ArrayList<Sensor>();
		for(Sensor sensor: sensors) {
			if(sensor.getCategory().equals(category))
				sensorList.add(sensor);
		}
		return sensorList;
	}
	
	public String getRoomID() {
		return roomID;
	}

	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}

	public TimerOP getTimer() {
		return timer;
	}

	public void setTimer(TimerOP timer) {
		this.timer = timer;
	}

	public short getFloor() {
		return floor;
	}

	public void setFloor(short floor) {
		this.floor = floor;
	}

	public short getLightsNum() {
		return lightsNum;
	}

	public void setLightsNum() {
		short r = 0;
		for(Object object: objects) 
			if(object.getObjectType().equals(ObjectType.LIGHT))
				r++;
		lightsNum = r;
	}

	public short getDoorsNum() {
		return doorsNum;
	}

	public void setDoorsNum() {
		short r = 0;
		for(Object object: objects) 
			if(object.getObjectType().equals(ObjectType.DOOR))
				r++;
		doorsNum = r;
	}

	public short getHeatersNum() {
		return heatersNum;
	}

	public void setHeatersNum() {
		short r = 0;
		for(Object object: objects) 
			if(object.getObjectType().equals(ObjectType.HEATER))
				r++;
		heatersNum = r;
	}

	public short getWindowsNum() {
		return windowsNum;
	}

	public void setWindowsNum() {
		short r = 0;
		for(Object object: objects) 
			if(object.getObjectType().equals(ObjectType.WINDOW))
				r++;
		windowsNum = r;
	}
}