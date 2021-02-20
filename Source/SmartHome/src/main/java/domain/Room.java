package domain;

import java.util.ArrayList;
import java.util.List;

import domain.Obj.ObjType;
import domain.Sensor.SensorCategory;

public class Room {

	private String roomID;
	private short floor;
	private short lightsNum;
	private short doorsNum;
	private short heatersNum;
	private short windowsNum;
	private List<Sensor> sensors;
	private List<Obj> objs;
	private TimerOP timer;
	
	public Room(String roomID, Short floor) {
		this.roomID = roomID;
		this.floor = floor;
		sensors = new ArrayList<>();
		objs = new ArrayList<>();
	}

	public Room() {
		sensors = new ArrayList<>();
		objs = new ArrayList<>();
	}
	public void createTimer() {
		timer = new TimerOP(this);
	}
	
	/*
	 * aggiunta del sensore passato alla lista e creazione del suo id
	 */
	public void instantiateSensor(SensorCategory category, String name) {
		Sensor sensor = new Sensor(name, category, this);
		sensors.add(sensor);
		AutomaticControl.getInstance().addSensor(sensor);
		sensor.setSensorID(category.toString() + "_" + roomID.toUpperCase() + "_" + floor);
	}
	
	/*
	 * aggiunta dei vari tipi di oggetti alla lista e creazione del loro id
	 */
	public void instantiateObj(ObjType type, String name) {
		switch(type) {
		case DOOR:
			Door door= new Door(name, this);
			door.getSensor().attach(door);
			objs.add(door);
			door.setObjID("DOOR_" + roomID.toUpperCase() + "_" + floor + "_" + getObjs(ObjType.DOOR).indexOf(door));
			door.getSensor().concatName(door.getObjID());
			door.getSensor().setSensorID("DOOR_" + door.getObjID());
			ConflictHandler.getInstance().addObj(door);
			break;
		case WINDOW:
			Window window = new Window(name, this);
			window.getSensor().attach(window);
			objs.add(window);
			window.setObjID("WINDOW_" + roomID.toUpperCase() + "_" + floor + "_" + getObjs(ObjType.WINDOW).indexOf(window));
			window.getSensor().concatName(window.getObjID());
			window.getSensor().setSensorID("WINDOW_" + window.getObjID());
			Shader shader = window.getShader();
			objs.add(shader);
			shader.setObjID("SHADER_" + window.getObjID());
			shader.getSensor().setSensorID("SHADER_" + shader.getObjID());
			shader.getSensor().attach(shader);
			ConflictHandler.getInstance().addObj(window);
			ConflictHandler.getInstance().addObj(shader);
			break;
		case LIGHT:
			Light light= new Light(name, this);
			light.getSensor().attach(light);
			objs.add(light);
			light.setObjID("LIGHT_" + roomID.toUpperCase() + "_" + floor + "_" + getObjs(ObjType.LIGHT).indexOf(light));
			light.getSensor().concatName(light.getObjID());
			light.getSensor().setSensorID("LIGHT_" + light.getObjID());
			ConflictHandler.getInstance().addObj(light);
			break;
		case HEATER:
			Heater heater= new Heater(name, this);
			heater.getSensor().attach(heater);
			objs.add(heater);
			heater.setObjID("HEATER_" + roomID.toUpperCase() + "_" + floor + "_" + getObjs(ObjType.HEATER).indexOf(heater));
			heater.getSensor().concatName(heater.getObjID());
			heater.getSensor().setSensorID("HEATER_" + heater.getObjID());
			ConflictHandler.getInstance().addObj(heater);
			break;
		default:
			break;
		}
		
	}
	
	public void setDoorCode(String code) {
		Door door = (Door)getObjs(ObjType.DOOR).get(0);
		door.setCode(code);
	}
	
	public List<Obj> getObjList(){
		return objs;
	}
	
	public List<Obj> getObjs(ObjType type) {
		List<Obj> objList = new ArrayList<>();	
		for(Obj obj: objs) {
			if(obj.getObjType().equals(type))
				objList.add(obj);
		}
		return objList;
	}

	public List<Sensor> getSensors(SensorCategory category){
		List<Sensor> sensorList = new ArrayList<>();
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
		for(Obj obj: objs) 
			if(obj.getObjType().equals(ObjType.LIGHT))
				r++;
		lightsNum = r;
	}

	public short getDoorsNum() {
		return doorsNum;
	}

	public void setDoorsNum() {
		short r = 0;
		for(Obj obj: objs) 
			if(obj.getObjType().equals(ObjType.DOOR))
				r++;
		doorsNum = r;
	}

	public short getHeatersNum() {
		return heatersNum;
	}

	public void setHeatersNum() {
		short r = 0;
		for(Obj obj: objs) 
			if(obj.getObjType().equals(ObjType.HEATER))
				r++;
		heatersNum = r;
	}

	public short getWindowsNum() {
		return windowsNum;
	}

	public void setWindowsNum() {
		short r = 0;
		for(Obj obj: objs) 
			if(obj.getObjType().equals(ObjType.WINDOW))
				r++;
		windowsNum = r;
	}
	
}