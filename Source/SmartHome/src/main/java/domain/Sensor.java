package domain;

import java.util.ArrayList;
import java.util.List;

import domain.Object.ObjectType;

public class Sensor {

	private String name;
	private String sensorID;
	private double value;
	private Room room;
	private List<Object> publisherList;
	public enum SensorCategory {MOVEMENT, AIR, LIGHT, WINDOW, DOOR, TEMPERATURE, HEATER, ALARM, SHADER}
	private SensorCategory category;
	public enum AirState {POLLUTION, GAS}
	private AirState airState;
	
	public Sensor(String name, SensorCategory category, Room room) {
		this.name = name;
		this.value = 0.00;
		this.category = category;
		this.room = room;
		this.airState = null;
		publisherList = new ArrayList<Object>();
	}

	public void attach(Object object) {
		publisherList.add(object);
	}

	public void deattach(Object object) {
		publisherList.remove(object);
	}

	public void notifies(double newValue) { 
		value = newValue;
		switch (category) {
		case MOVEMENT:
			if(newValue == 1.00) {
				for (int i = 0; i < publisherList.size(); i++) {
					if(publisherList.get(i).getObjectType().equals(ObjectType.ALARM))
						AutomaticControl.getInstance().checkAlarm();
					if(publisherList.get(i).getObjectType().equals(ObjectType.LIGHT))
						AutomaticControl.getInstance().checkLight(newValue, room);
				}
			}
			else
				for (int i = 0; i < publisherList.size(); i++)
					if(publisherList.get(i).getObjectType().equals(ObjectType.LIGHT))
						AutomaticControl.getInstance().checkLight(newValue, room);
			break;
		case AIR:
			AutomaticControl.getInstance().checkAirPollution(newValue, room, airState);
			break;
		case LIGHT:
		case WINDOW:
		case DOOR:
		case HEATER:
		case ALARM:
		case SHADER:
			publisherList.get(0).update(newValue);
			break;
		case TEMPERATURE:
			AutomaticControl.getInstance().checkTempTresholds(newValue, publisherList);
			break;
		default:
			break;
		}
	}
	
	public void concatName(String stringToBeAttached) {
		setName(name + stringToBeAttached);
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSensorID() {
		return sensorID;
	}

	public void setSensorID(String sensorID) {
		this.sensorID = sensorID;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	public List<Object> getPublisherList() {
		return publisherList;
	}

	public void setPublisherList(List<Object> publisherList) {
		this.publisherList = publisherList;
	}
	
	public SensorCategory getCategory() {
		return category;
	}

	public void setCategory(SensorCategory category) {
		this.category = category;
	}

	public AirState getAirState() {
		return airState;
	}

	public void setAirState(AirState airState) {
		this.airState = airState;
	}
}