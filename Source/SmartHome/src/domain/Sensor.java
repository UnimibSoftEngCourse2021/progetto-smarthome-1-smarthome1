package domain;

import java.util.List;

import domain.Object.ObjectType;
import service.DatabaseCommunicationSystem;
import service.SensorCommunicationAdapter;

public class Sensor {

	private String name;
	private double value;
	private String communicationType;//BOH
	private List<Object> publisherList;
	public enum SensorCategory {MOVEMENT, AIR, LIGHT, WINDOW, DOOR, TEMPERATURE, HEATER, ALARM, SHADER}
	private SensorCategory category;
	public enum AirState {POLLUTION, GAS}
	private AirState airState;
	
	private AutomaticControl automaticControl;
	private DatabaseCommunicationSystem database;
	private SensorCommunicationAdapter adapter;
	private Room room;

	public Sensor(String name, SensorCategory category, AirState airState) {
		this.name = name;
		this.value = 0.00;
		this.category = category;
		if(category.equals(SensorCategory.AIR))
			this.airState = airState;
		else
			this.airState = null;
		if(category.equals(SensorCategory.MOVEMENT))
			Alarm.setSensors(this);
	}
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
		switch (category) {
		case MOVEMENT:
			if(value == 1.00) {
				for (int i = 0; i < publisherList.size(); i++) {
					if(publisherList.get(i).getObjectType().equals(ObjectType.ALARM))
						automaticControl.checkAlarm((Alarm)publisherList.get(i));
					if(publisherList.get(i).getObjectType().equals(ObjectType.LIGHT))
						automaticControl.checkLight(value, room);
				}
			}
			else
				for (int i = 0; i < publisherList.size(); i++)
					if(publisherList.get(i).getObjectType().equals(ObjectType.LIGHT))
						automaticControl.checkLight(value, room);
			break;
		case AIR:
			automaticControl.checkAirPollution(value, room, airState.toString());
			break;
		case LIGHT:
		case WINDOW:
		case DOOR:
		case HEATER:
		case ALARM:
			publisherList.get(0).update(value);
			break;
		case TEMPERATURE:
			automaticControl.checkTempTresholds(value, publisherList);
			break;
		// definire un case di default??
		}
	}
}