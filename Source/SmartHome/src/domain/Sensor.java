package domain;

import java.util.List;

import service.DatabaseCommunicationSystem;
import service.SensorCommunicationAdapter;

public class Sensor {

	private String name;
	private double value;
	private String communicationType;
	private Object[] publisherList; //trasformare in arrayList ???
	public enum Category {MOVEMENT, AIR, LIGHT, WINDOW, DOOR, TEMPERATURE}
	private Category category;
	private enum SensorType {BOOLEAN, DOUBLE}
	private SensorType sensorType;
	
	private AutomaticControl automaticControl;
	private DatabaseCommunicationSystem database;
	private SensorCommunicationAdapter adapter;
	private Room room;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public SensorType getSensorType() {
		return sensorType;
	}

	public void setSensorType(SensorType sensorType) {
		this.sensorType = sensorType;
	}

	public Object[] getPublisherList() {
		return publisherList;
	}

	public void setPublisherList(Object[] publisherList) {
		this.publisherList = publisherList;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

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