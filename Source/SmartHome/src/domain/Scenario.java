package domain;

import java.util.ArrayList;
import java.util.*;

public class Scenario {

	private String nameID;
	private Date dateTime;// da controllare il tipo
	private boolean active = false;
	private Object[][] actionList;//?? action list contiene gli IDdegli oggetti?, anche le azioni (boolean per casi semplici)
	private String[] roomsIDs;
	
	private ConflictHandler handler;
	private AutomaticControl automaticControl;
	
	

	public String getNameID() {
		return nameID;
	}

	public void setNameID(String nameID) {
		this.nameID = nameID;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Object[][] getActionList() {
		return actionList;
	}

	public void setActionList(Object[][] actionList) {
		this.actionList = actionList;
	}

	public String[] getRoomsIDs() {
		return roomsIDs;
	}

	public void setRoomsIDs(String[] roomsIDs) {
		this.roomsIDs = roomsIDs;
	}

	public void deleteScenario() {
		// TODO - implement Scenario.deleteScenario
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param parametersToBeModified
	 */
	public void modifyScenario(int parametersToBeModified) {
		// TODO - implement Scenario.modifyScenario
		throw new UnsupportedOperationException();
	}

	public void activateScenario() {
		 if(active == false) {
			 active = true;
			 for(int i = 0; i < actionList.length; i++) {
				 handler.doAction(actionList[i][0], actionList[i][1]);
			 }
		 }
			 
		throw new UnsupportedOperationException();
	}

}