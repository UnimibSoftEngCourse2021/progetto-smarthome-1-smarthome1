package domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import service.TimeScheduleThread;

public class Scenario {

	private String nameID;
	private LocalDateTime startTime;
	private boolean active = false;
	private List<String> objectIDs;
	private List<Boolean> actions;
	private List<DayOfWeek> days;
	
	private List<String> roomsIDs;
	private TimeScheduleThread thread;
	
	public Scenario(String nameID, LocalDateTime startTime, List<String> objectIDs, List<Boolean> actions, List<DayOfWeek> days, List<String> roomsIDs) {
		this.nameID = nameID;
		this.startTime = startTime;
		this.objectIDs = objectIDs;
		this.actions = actions;
		this.days = days;
		this.roomsIDs = roomsIDs;
		if(startTime != null)
			handleDateEvent();
	}

	public void deleteScenario() {
		objectIDs.clear();
		actions.clear();
		thread.interrupt();
	}

	public void modifyScenario() {
		//probabilmente non esiste 
		//sara' modifyScenario di ScenariosHandler che chiama i vari setter dello specifico scenario
	}

	public void activateScenario() {
		 if(active == false) {
			 active = true;
			 for(int i = 0; i < objectIDs.size(); i++) {
				 ConflictHandler.getInstance().doAction(objectIDs.get(i), (boolean)actions.get(i));
			 }
		 }
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

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
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
}