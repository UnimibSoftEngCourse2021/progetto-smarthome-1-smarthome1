package domain;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ScenariosHandler {
	
	private List<Scenario> scenarios;
	//private List<Obj> objs; //non e' necessario (quindi neanche l'associazione nel modello di prog) 
	//tanto lo scenario conosce gli objID che poi passa al ConflictHandler
	//cancellare quindi anceh getter e setter per objs
	
	public ScenariosHandler() {
		scenarios = new ArrayList<Scenario>();
	}
	
	public void createScenario(String scenarioID, String dateTime, List<String> days, List<List<String[]>> rooms) {
		
		List<DayOfWeek> daysOfWeek = new ArrayList<DayOfWeek>();
		if(days != null) {
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
		
		List<String> objIDs = new ArrayList<String>();
		List<Boolean> actions = new ArrayList<Boolean>();
		List<String> roomsIDs = new ArrayList<String>();
		for(List<String[]> list: rooms) {
			for(String[] couple: list) {
				if(list.indexOf(couple) == 0) 
					roomsIDs.add(couple[0]);
				else {
					objIDs.add(couple[0]);
					actions.add(Boolean.parseBoolean(couple[1]));
				}	
			}
		}
		Scenario scenario = new Scenario(scenarioID, time, objIDs, actions, daysOfWeek, roomsIDs);
		addScenario(scenario);
		
	}

	public void changeAction(String scenarioID, String objID, String action) {
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
				for(String id: scenario.getObjIDs()) {
					if(id.equals(objID)) {
						scenario.getActions().set(scenario.getObjIDs().indexOf(id), actn);
						isNotIterated = true;
						break;
					}
				}
				if(!isNotIterated) {
					scenario.getObjIDs().add(objID);
					scenario.getActions().add(actn);
				}
				break;
			}
		}
	}

	public void deleteAction(String scenarioID, String objID) {
		Scenario scenario = new Scenario();
		for(Scenario s: scenarios) {
			if(s.getNameID().equals(scenarioID)) {
				scenario = s;
				break;
			}
		}
		for(String id: scenario.getObjIDs()) {
			if(id.equals(objID)) {
				scenario.getActions().remove(scenario.getObjIDs().indexOf(objID));
				scenario.getObjIDs().remove(objID);
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
				scenario.setDays(daysOfWeek);
				scenario.handleDateEvent();
				break;
			}
			
			
			
		}
		
		
	}
	public void addRoomToScenario(String scenarioID, List<List<String[]>> rooms) {

		Scenario scenario = new Scenario();
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
					scenario.getObjIDs().add(couple[0]);
					scenario.getActions().add(Boolean.parseBoolean(couple[1]));						
				}
			}
		}
	}
	
	public void deleteScenario(String nameID) {
		for(Scenario scenario: scenarios) {
			if(scenario.getNameID().equals(nameID)) {
				scenario.getObjIDs().clear();
				scenario.getActions().clear();
				if(scenario.getStartTime() != null)
					scenario.getThread().interrupt();
				scenarios.remove(scenario);
				break;
			}
		}
	}
	
	public void activateScenario(String nameID) {
		for(Scenario scenario: scenarios) {
			if(scenario.getNameID().equals(nameID))			
				 for(int i = 0; i < scenario.getObjIDs().size(); i++) 
					 ConflictHandler.getInstance().doAction(scenario.getObjIDs().get(i), (boolean)scenario.getActions().get(i)); 
		}
		
	}

	public List<String> getObjsInScenario(String scenarioID) {
		Scenario s = new Scenario();
		for(Scenario scenario: scenarios) 
			if(scenario.getNameID().equals(scenarioID))
				s = scenario;
		return s.getObjIDs();
	}
	
	public List<Scenario> getScenarios() {
		return scenarios;
	}

	public void addScenario(Scenario scenario) {
		scenarios.add(scenario);
	}
	
	public List<Boolean> getActionsInScenario(String scenarioID) {
		Scenario s = new Scenario();
		for(Scenario scenario: scenarios) 
			if(scenario.getNameID().equals(scenarioID))
				s = scenario;
		return s.getActions();
	}
	
}
