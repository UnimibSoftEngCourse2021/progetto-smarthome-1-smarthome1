package domain;

import java.util.ArrayList;
import java.util.List;

import domain.Object.ObjectType;
import domain.Sensor.SensorCategory;

public class Room {

	private String roomID;
	private short floor;
	private short lightsNum;
	private short doorsNum;
	private short HeatersNum;
	private short windowsNum;
	private boolean lightControlled = false;
	private boolean airControlled = false;
	
	/*
	 * esistono due liste di oggetti(??)
	 * objects e' quello creato dall'associazione quindi credo
	 * si possa togliere quello
	 * -d.barzio
	 */
	private List<Sensor> sensors;
	private List<Object> objects;
	private TimerOP timer;
	private Config config;

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

	public void setLightsNum(short lightsNum) {
		this.lightsNum = lightsNum;
	}

	public short getDoorsNum() {
		return doorsNum;
	}

	public void setDoorsNum(short doorsNum) {
		this.doorsNum = doorsNum;
	}

	public short getHeatersNum() {
		return HeatersNum;
	}

	public void setHeatersNum(short heatersNum) {
		HeatersNum = heatersNum;
	}

	public short getWindowsNum() {
		return windowsNum;
	}

	public void setWindowsNum(short windowsNum) {
		this.windowsNum = windowsNum;
	}

	public boolean isLightControlled() {
		return lightControlled;
	}

	public void setLightControlled(boolean lightControlled) {
		this.lightControlled = lightControlled;
	}

	public boolean isAirControl() {
		return airControlled;
	}

	public void setAirControlled(boolean airControlled) {
		this.airControlled = airControlled;
	}

	// non sono sicuro se vadano aggiunti getter/setter per objectList (il getter in teoria è già definito sotto)
	
	/**
	 * 
	 * @param sensorCategory
	 */
	public void instantiateSensor(SensorCategory category, String name) {
		Sensor sensor = new Sensor(name, roomID, category);
		sensors.add(sensor);
		sensor.setSensorID(category.toString() + "_" + roomID);
	}
	
	public void instantiateObject(ObjectType type, String name) {
		switch(type) {
		case DOOR:
			Door door= new Door(name, roomID);
			door.getSensor().attach(door);
			objects.add(door);
			door.setObjectID(type.toString() + "_" + roomID + "_" + String.valueOf(objects.indexOf(door)));
			door.getSensor().concatName(door.getObjectID());
			door.getSensor().setSensorID(door.getSensor().getCategory().toString() + "_" + door.getObjectID());
			break;
		case WINDOW:
			Window window = new Window(name, roomID);
			window.getSensor().attach(window);
			objects.add(window);
			window.setObjectID(type.toString() + "_" + roomID + "_" + String.valueOf(objects.indexOf(window)));
			window.getSensor().concatName(window.getObjectID());
			window.getSensor().setSensorID(window.getSensor().getCategory().toString() + "_" + window.getObjectID());
			Shader shader = window.getShader();
			objects.add(shader);
			shader.setObjectID("SHADER_" + window.getObjectID());
			shader.getSensor().setSensorID(shader.getSensor().getCategory().toString() + "_" + shader.getObjectID());
			break;
		case LIGHT:
			Light light= new Light(name, roomID);
			light.getSensor().attach(light);
			objects.add(light);
			light.setObjectID(type.toString() + "_" + roomID + "_" + String.valueOf(objects.indexOf(light)));
			light.getSensor().concatName(light.getObjectID());
			light.getSensor().setSensorID(light.getSensor().getCategory().toString() + "_" + light.getObjectID());
			break;
		case HEATER:
			Heater heater= new Heater(name, roomID);
			objects.add(heater);
			heater.setObjectID(type.toString() + "_" + roomID + "_" + String.valueOf(objects.indexOf(heater)));
			heater.getSensor().concatName(heater.getObjectID());
			heater.getSensor().setSensorID(heater.getSensor().getCategory().toString() + "_" + heater.getObjectID());
			break;
		default:
			break;
		}
		
	}

	/**
	 * 
	 * @param objectType
	 */
	
	public List<Object> getObjectList(ObjectType type) {
		List<Object> objectList = null;	
		for(Object object: objects) {
			if(object.getObjectType().equals(type))
				objectList.add(object);
		}
		return objectList;
	}

	public List<Sensor> getSensorList(SensorCategory category){
		List<Sensor> sensorList = null;
		for(Sensor sensor: sensors) {
			if(sensor.getCategory().equals(category))
				sensorList.add(sensor);
		}
		return sensorList;
	}
	public void bindHeater(Heater heater, Sensor temperature) {
		temperature.attach(heater);
	}
	
	public void setDoorCode(String code) {
		for(Object object: objects) {
			Door door;
			if(object.getObjectType().equals(ObjectType.DOOR)) {
				 door = (Door)object;
				 door.setCode(code);
			}
		}
	}
}