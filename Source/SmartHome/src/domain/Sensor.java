package domain;

import java.util.List;

import service.DatabaseCommunicationSystem;
import service.SensorCommunicationAdapter;

public class Sensor {

	private String name;
	private double value;
	private String communicationType;
	private Object[] publisherList;
	public enum Category {MOVEMENT, AIR, LIGHT, WINDOW, DOOR, TEMPERATURE}
	private Category catergory;
	private enum Type {BOOLEAN, DOUBLE}
	private Type type;
	
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

	public Category getCatergory() {
		return catergory;
	}

	public void setCatergory(Category catergory) {
		this.catergory = catergory;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
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