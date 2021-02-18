package main.java.application;

import java.util.List;

import main.java.domain.ScenariosHandler;

public class ScenarioFaçade {

	private ScenariosHandler handler;
	
	public ScenarioFaçade(ScenariosHandler handler) {
		this.handler = handler;
	}
	public void manageActivateScenario(String scenarioID) {
		handler.activateScenario(scenarioID);
	}

	public void manageDeleteScenario(String scenarioID) {
		handler.deleteScenario(scenarioID);
	}

	public void manageCreateScenario(String scenarioID, String dateTime, List<List<String[]>> rooms, List<String> days) {
		handler.createScenario(scenarioID, dateTime, days, rooms);
	}

	public void manageAddRoomsToScenario(String scenarioID, List<List<String[]>> rooms) {
		handler.addRoomToScenario(scenarioID, rooms);
	}
	
	public void setDateTime (String scenarioID, String time, List<String> days) {
		handler.setDateTime(scenarioID, time, days);
	}
	
	public void manageChangeAction(String scenarioID, String objectID, String newState) {
		handler.changeAction(scenarioID, objectID, newState);
	}

	public void manageDeleteAction(String scenarioID, String objectID) {
		handler.deleteAction(scenarioID, objectID);
		
	}
}