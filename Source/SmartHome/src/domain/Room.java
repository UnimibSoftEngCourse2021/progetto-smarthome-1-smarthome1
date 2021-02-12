package domain;

import java.util.ArrayList;
import java.util.List;

import domain.Object.ObjectType;

public class Room {

	private String name;
	private short floor;
	private short lightsNum;
	private short doorsNum;
	private short HeatersNum;
	private short windowsNum;
	private boolean lightControlled = false;
	private boolean airControlled = false;
	private ArrayList<Object> objectList = null;
	/*
	 * esistono due liste di oggetti(??)
	 * objects e' quello creato dall'associazione quindi credo
	 * si possa togliere quello
	 * -d.barzio
	 */
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
	public void instatiateSensor(String sensorCategory) {
		///crea sensore associati a stanza 
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param objectType
	 */
	// provare con iterator
	public ArrayList<Object> getObjectList(String objectType) {
			for(int i = 0; i < objects.size(); i++) 
				if(objects.get(i).getObjectType().toString().equals(objectType) && objects.get(i).getReferencedRoom().equals(name))
					objectList.add(objects.get(i));	
			return objectList;
	}

}