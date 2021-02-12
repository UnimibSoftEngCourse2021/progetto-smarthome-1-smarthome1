package domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import service.TimeScheduleThread;

public class Scenario {

	private String nameID;
	private LocalDateTime startTime;// prendere data e ora da currentTime in automaticControl?
	private boolean active = false;
	private List<String> objectIDList;
	private List<Boolean> actionList;
	private List<DayOfWeek> days;
	/*
	 * private HashMap<String, Boolean> actionList = new HashMap<String, Boolean>();
	 * ?? action list contiene gli IDdegli oggetti?, anche le azioni (boolean per casi semplici) 
	 * il problema della matrice è che sappiamo il numero di colonne ma non il numero di righe
	 * sarebbe necessaria una struttura dati flessibile che contenga due tipi differenti di valori
	 * String per l'objectID e boolean per lo stato "desiderato", cioè lo stato in cui si desidera sia l'oggetto una volta eseguita l'azione
	 * si potrebbero usare due liste, una per l'ID e una per lo stato obiettivo, legate tra loro tramite l'indice
	 * non è una soluzione molto elegante però è l'unica che mi viene in mente
	 * oppure considerare l'indice della lista come l'ID e il suo valore come lo stato (partendo per esempio da numeri alti per differenziare
	 * maggiormente gli ID
	*/
	
	private String[] roomsIDs;
	private ConflictHandler handler;
	private TimeScheduleThread thread;
	
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

	public List<String> getObjectIDList() {
		return objectIDList;
	}

	public void setObjectIDList(List<String> objectIDList) {
		this.objectIDList = objectIDList;
	}

	public List<Boolean> getActionList() {
		return actionList;
	}

	public void setActionList(List<Boolean> actionList) {
		this.actionList = actionList;
	}

	public String[] getRoomsIDs() {
		return roomsIDs;
	}

	public void setRoomsIDs(String[] roomsIDs) {
		this.roomsIDs = roomsIDs;
	}
	
	public List<DayOfWeek> getDays() {
		return days;
	}

	public void setDays(List<DayOfWeek> days) {
		this.days = days;
	}

	public void deleteScenario() {
		objectIDList.clear();
		actionList.clear();
		thread.interrupt();
	}

	/**
	 * 
	 * @param parametersToBeModified
	 */
	public void modifyScenario(int parametersToBeModified) {
		/*
		 * modifica tramite interfaccia
		 */
	}

	public void activateScenario() {
		 if(active == false) {
			 active = true;
			 for(int i = 0; i < objectIDList.size(); i++) {
				 handler.doAction(objectIDList.get(i), (boolean)actionList.get(i));
			 }
		 }
	}
	
	public void handleDateEvent() {
		thread = new TimeScheduleThread();
		thread.init(startTime);
	}

}