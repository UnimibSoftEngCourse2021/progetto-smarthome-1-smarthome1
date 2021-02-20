package domain;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import service.TimeScheduleThread;


public class Scenario {

	private String nameID;
	private LocalTime startTime;
	private List<String> objIDs;
	private List<Boolean> actions;
	private List<DayOfWeek> days;
	
	private List<String> roomsIDs;
	private TimeScheduleThread thread;
	
	public Scenario(String nameID, LocalTime startTime, List<String> objIDs, List<Boolean> actions, List<DayOfWeek> days, List<String> roomsIDs) {
		this.nameID = nameID;
		this.startTime = startTime;
		this.objIDs = objIDs;
		this.actions = actions;
		this.days = days;
		this.roomsIDs = roomsIDs;
		if(startTime != null)
			handleDateEvent();
	}
	
	public Scenario() {
		
	}

	public void activateScenario() {
		 for(int i = 0; i < getObjIDs().size(); i++) 
			 ConflictHandler.getInstance().doAction(getObjIDs().get(i), getActions().get(i));
	}

	public void handleDateEvent() {
		thread = new TimeScheduleThread();
		thread.setScenario(this);
		thread.init(startTime);
		thread.start();
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

	public List<String> getObjIDs() {
		return objIDs;
	}

	public void setObjIDs(List<String> objIDs) {
		this.objIDs = objIDs;
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