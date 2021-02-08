package domain;

import java.util.List;

public class Room {

	private String name;
	private short floor;
	private short lightsNum;
	private short doorsNum;
	private short HeatersNum;
	private short windowsNum;
	private boolean lightControl = false;
	private boolean airControl = false;
	
	private List<Sensor> sensors;
	private List<Object> objects;
	private Config config;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public boolean isLightControl() {
		return lightControl;
	}

	public void setLightControl(boolean lightControl) {
		this.lightControl = lightControl;
	}

	public boolean isAirControl() {
		return airControl;
	}

	public void setAirControl(boolean airControl) {
		this.airControl = airControl;
	}

	public List<Sensor> getSensors() {
		return sensors;
	}

	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}

	public List<Object> getObjects() {
		return objects;
	}

	public void setObjects(List<Object> objects) {
		this.objects = objects;
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	/**
	 * 
	 * @param sensorCategory
	 */
	public void instatiateSensor(String sensorCategory) {
		// TODO - implement Room.instatiateSensor
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param objectType
	 */
	public Object[] getObjectList(String objectType) {
		// TODO - implement Room.getObjectList
		throw new UnsupportedOperationException();
	}

}