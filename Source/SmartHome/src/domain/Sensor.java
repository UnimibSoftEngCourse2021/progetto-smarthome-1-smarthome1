package domain;

import java.util.ArrayList;
import java.util.List;

import service.DatabaseCommunicationSystem;
import service.SensorCommunicationAdapter;

public class Sensor {

	private String name;
	private double value;
	private String communicationType;
	private ArrayList<Object> publisherList;
	public enum Category {MOVEMENT, AIR, LIGHT, WINDOW, DOOR, TEMPERATURE}
	private Category category;
	private enum SensorType {BOOLEAN, DOUBLE}
	private SensorType sensorType;
	public enum AirState {POLLUTION, GAS}
	private AirState airState;
	
	private List<Object> objects;
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
	
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getCommunicationType() {
		return communicationType;
	}

	public void setCommunicationType(String communicationType) {
		this.communicationType = communicationType;
	}
	
	public ArrayList<Object> getPublisherList() {
		return publisherList;
	}

	public void setPublisherList(ArrayList<Object> publisherList) {
		this.publisherList = publisherList;
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
	
	public AirState getAirState() {
		return airState;
	}

	public void setAirState(AirState airState) {
		this.airState = airState;
	}

	/**
	 * 
	 * @param object
	 */
	public void attach(Object object) {
		publisherList.add(object);
	}
	
	/**
	 * 
	 * @param object
	 */
	public void deattach(Object object) {
		publisherList.remove(object);
	}

	public void notifies(double value) { // ho dovuto aggiungere come parametro active perchè deve comunicare all'oggetto in quale stato andare
		for(Object objects : publisherList)
			objects.update(value);
	}
}