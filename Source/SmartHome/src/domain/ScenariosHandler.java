package domain;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ScenariosHandler {
	
	private List<Scenario> scenarios;
	private List<Object> objects; //non e' necessario (quindi neanche l'associazione nel modello di prog) tanto lo scenario conosce gli objectID che poi passa al ConflictHandler
	//cancellare quindi anceh getter e setter per objects
	
	//definire un costruttore, deve esistere una singola istanza di questa classe ma non e necessario usare il singleton
	
	public List<Object> getObjects(){
		return objects;
	}
	
	public void addObject(Object object) {
		objects.add(object);
	}
	
	public void createScenario(String scenarioID, String dateTime, List<String> days, List<List<String[]>> rooms) {
		
		List<DayOfWeek> daysOfWeek = new ArrayList<DayOfWeek>();
		if(!days.equals(null)) {
			for(String day: days) {
				if(day.equalsIgnoreCase("LUNEDI"))
					daysOfWeek.add(DayOfWeek.MONDAY);
				if(day.equalsIgnoreCase("MARTEDI"))
					daysOfWeek.add(DayOfWeek.TUESDAY);
				if(day.equalsIgnoreCase("MERCOLEDI"))
					daysOfWeek.add(DayOfWeek.WEDNESDAY);
				if(day.equalsIgnoreCase("GIOVEDI"))
					daysOfWeek.add(DayOfWeek.THURSDAY);
				if(day.equalsIgnoreCase("VENERDI"))
					daysOfWeek.add(DayOfWeek.FRIDAY);
				if(day.equalsIgnoreCase("SABATO"))
					daysOfWeek.add(DayOfWeek.SATURDAY);
				if(day.equalsIgnoreCase("DOMENICA"))
					daysOfWeek.add(DayOfWeek.SUNDAY);
			}
		} else
			daysOfWeek = null;
		
		LocalTime time = null;
		if(!dateTime.equalsIgnoreCase("null")) {
			int hour = Integer.parseInt(dateTime.substring(0, 2));
			int minute = Integer.parseInt(dateTime.substring(3));
			time = LocalTime.of(hour, minute);	
		}
		
		List<String> objectIDs = new ArrayList<String>();
		List<Boolean> actions = new ArrayList<Boolean>();
		List<String> roomsIDs = new ArrayList<String>();
		for(List<String[]> list: rooms) {
			for(String[] couple: list) {
				if(list.indexOf(couple) == 0) 
					roomsIDs.add(couple[0]);
				else {
					objectIDs.add(couple[0]);
					actions.add(Boolean.parseBoolean(couple[1]));
				}	
			}
		}
		Scenario scenario = new Scenario(scenarioID, time, objectIDs, actions, daysOfWeek, roomsIDs);
		addScenario(scenario);
		
	}

	public void changeAction(String scenarioID, String objectID, String action) {
		boolean isNotIterated = false;
		boolean actn = false;
		if(action.equalsIgnoreCase("true")
				|| action.equalsIgnoreCase("apri")
				|| action.equalsIgnoreCase("accendi")
				|| action.equalsIgnoreCase("vero"))
			actn = true;
		else
			actn = false;
		for(Scenario scenario: scenarios) {
			if(scenario.getNameID().equals(scenarioID)) {
				for(String id: scenario.getObjectIDs()) {
					if(id.equals(objectID)) {
						scenario.getActions().set(scenario.getObjectIDs().indexOf(id), actn);
						isNotIterated = true;
						break;
					}
				}
				if(!isNotIterated) {
					scenario.getObjectIDs().add(objectID);
					scenario.getActions().add(actn);
				}
				break;
			}
		}
	}

	public void deleteAction(String scenarioID, String objectID) {
		Scenario scenario = null;
		for(Scenario s: scenarios) {
			if(s.getNameID().equals(scenarioID)) {
				scenario = s;
				break;
			}
		}
		for(String id: scenario.getObjectIDs()) {
			if(id.equals(objectID)) {
				scenario.getActions().remove(scenario.getObjectIDs().indexOf(objectID));
				scenario.getObjectIDs().remove(objectID);
				break;
			}
		}
	}

	public void setDateTime(String scenarioID, String time, List<String> days) {
		int hour = Integer.parseInt(time.substring(0, 2));
		int minute = Integer.parseInt(time.substring(3));
		LocalTime localTime = LocalTime.of(hour, minute);	
		List<DayOfWeek> daysOfWeek = new ArrayList<DayOfWeek>();
		
		for(Scenario scenario: scenarios) {
			if(scenario.getNameID().equals(scenarioID)) {
				scenario.setStartTime(localTime);
				for(String day: days) {
					if(day.equalsIgnoreCase("LUNEDI"))
						daysOfWeek.add(DayOfWeek.MONDAY);
					if(day.equalsIgnoreCase("MARTEDI"))
						daysOfWeek.add(DayOfWeek.TUESDAY);
					if(day.equalsIgnoreCase("MERCOLEDI"))
						daysOfWeek.add(DayOfWeek.WEDNESDAY);
					if(day.equalsIgnoreCase("GIOVEDI"))
						daysOfWeek.add(DayOfWeek.THURSDAY);
					if(day.equalsIgnoreCase("VENERDI"))
						daysOfWeek.add(DayOfWeek.FRIDAY);
					if(day.equalsIgnoreCase("SABATO"))
						daysOfWeek.add(DayOfWeek.SATURDAY);
					if(day.equalsIgnoreCase("DOMENICA"))
						daysOfWeek.add(DayOfWeek.SUNDAY);
				}
				break;
			}
		}
		
	}
	public void addRoomToScenario(String scenarioID, List<List<String[]>> rooms) {

		Scenario scenario = null;
		for(Scenario s: scenarios) {
			if(s.getNameID().equals(scenarioID)) {
				scenario = s;
				break;
			}
		}
		for(List<String[]> list: rooms) {
			for(String[] couple: list) {
				if(list.indexOf(couple) == 0) 
					scenario.getRoomsIDs().add(couple[0]);
				else {
					scenario.getObjectIDs().add(couple[0]);
					scenario.getActions().add(Boolean.parseBoolean(couple[1]));						
				}
			}
		}
	}
	
	public void deleteScenario(String nameID) {
		for(Scenario scenario: scenarios) {
			if(scenario.getNameID().equals(nameID)) {
				scenario.getObjectIDs().clear();
				scenario.getActions().clear();
				scenario.getThread().interrupt();
			}
		}
	}
	
	public void activateScenario(String nameID) {
		for(Scenario scenario: scenarios) {
			if(scenario.getNameID().equals(nameID))
				if(scenario.isActive() == false) {
					 scenario.setActive(false); 
					 for(int i = 0; i < scenario.getObjectIDs().size(); i++) {
						 ConflictHandler.getInstance().doAction(scenario.getObjectIDs().get(i), (boolean)scenario.getActions().get(i));
					 }
				 }
		}
		
	}

	public List<String> getObjectsInScenario(String scenarioID) {
		Scenario s = null;
		for(Scenario scenario: scenarios) 
			if(scenario.getNameID().equals(scenarioID))
				s = scenario;
		return s.getObjectIDs();
	}
	
	public List<Scenario> getScenarios() {
		return scenarios;
	}

	public void addScenario(Scenario scenario) {
		scenarios.add(scenario);
	}

	public void setObjects(List<Object> objects) {
		this.objects = objects;
	}
	
}
