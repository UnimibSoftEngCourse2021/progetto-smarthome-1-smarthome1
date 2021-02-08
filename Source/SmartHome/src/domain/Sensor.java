package domain;

import service.DatabaseCommunicationSystem;
import service.SensorCommunicationAdapter;

public class Sensor {

	private String name;
	private double value;
	private String communicationType;
	private Object[] publisherList;
	private enum Category {MOVEMENT, AIR, LIGHT, WINDOW, DOOR, TEMPERATURE}
	private enum Type {BOOLEAN, DOUBLE}
	
	private AutomaticControl automaticControl;
	private DatabaseCommunicationSystem database;
	private SensorCommunicationAdapter adapter;
	private Room room;
	
	/**
	 * 
	 * @param object
	 */
	public void attach(Object object) {
		// TODO - implement Sensor.attach
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param object
	 */
	public void deattach(Object object) {
		// TODO - implement Sensor.deattach
		throw new UnsupportedOperationException();
	}

	public void notifies() {
		// TODO - implement Sensor.notify
		throw new UnsupportedOperationException();
	}

}