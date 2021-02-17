package domain;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import service.TimeScheduleThread;

public class Scenario {

	private String nameID;
	private LocalTime startTime;
	private boolean active = false;
	private List<String> objectIDs;
	private List<Boolean> actions;
	private List<DayOfWeek> days;
	
	private List<String> roomsIDs;
	private TimeScheduleThread thread;
	
	public Scenario(String nameID, LocalTime startTime, List<String> objectIDs, List<Boolean> actions, List<DayOfWeek> days, List<String> roomsIDs) {
		this.nameID = nameID;
		this.startTime = startTime;
		this.objectIDs = objectIDs;
		this.actions = actions;
		this.days = days;
		this.roomsIDs = roomsIDs;
		if(startTime != null)
			handleDateEvent();
	}

	public void modifyScenario() {
		//probabilmente non esiste (lo stesso per delete e activate)
		//sara' modifyScenario di ScenariosHandler che chiama i vari setter dello specifico scenario
	}

	public void handleDateEvent() {
		thread = new TimeScheduleThread();
		thread.init(startTime);
	}

	public String getNameID() {
		return nameID;
	}

	public void setNameID(String nameID) {
		this.nameID = nameID;
	}

	public List<DayOfWeek> getDays() {
		return days;
	}

	public void setDays(List<DayOfWeek> days) {
		this.days = days;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<String> getObjectIDs() {
		return objectIDs;
	}

	public void setObjectIDs(List<String> objectIDs) {
		this.objectIDs = objectIDs;
	}

	public List<Boolean> getActions() {
		return actions;
	}

	public void setActions(List<Boolean> actions) {
		this.actions = actions;
	}

	public TimeScheduleThread getThread() {
		return thread;
	}

	public void setThread(TimeScheduleThread thread) {
		this.thread = thread;
	}

	public List<String> getRoomsIDs() {
		return roomsIDs;
	}

	public void setRoomsIDs(List<String> roomsIDs) {
		this.roomsIDs = roomsIDs;
	}
}