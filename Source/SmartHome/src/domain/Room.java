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