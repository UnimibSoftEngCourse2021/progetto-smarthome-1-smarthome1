package application;

import java.util.List;

import domain.Scenario;
import domain.ScenarioCreationHandler;

public class ScenarioFaçade {
	
	private ScenarioCreationHandler handler;
	private Scenario scenario;



	/**
	 * 
	 * @param ScenarioID
	 */
	public void manageActivateScenario(String ScenarioID) {
		// TODO - implement HomeFaçade.manageActivateScenario
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param scenarioID
	 */
	public void manageDeleteScenario(String scenarioID) {
		// TODO - implement HomeFaçade.manageDeleteScenario
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param roomID
	 * @param objectID
	 * @param actionType
	 * @param actionLIstIndex
	 */
	public void manageModifyScenario(String roomID, String objectID, boolean actionType, short actionLIstIndex) {
		// TODO - implement HomeFaçade.manageModifyScenario
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param scenarioID
	 * @param dateTime
	 * @param roomsIDs
	 * @param actionList
	 */
	public void manageCreateScenario(String scenarioID, int dateTime, List<List<String[]>> rooms) {
		// TODO - implement HomeFaçade.manageCreateScenario
		throw new UnsupportedOperationException();
	}

}