package application;

import java.util.List;

import domain.Scenario;
import domain.ScenarioCreationHandler;

public class ScenarioFa�ade {
	
	private ScenarioCreationHandler handler;
	private Scenario scenario;



	/**
	 * 
	 * @param ScenarioID
	 */
	public void manageActivateScenario(String ScenarioID) {
		// TODO - implement HomeFa�ade.manageActivateScenario
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param scenarioID
	 */
	public void manageDeleteScenario(String scenarioID) {
		// TODO - implement HomeFa�ade.manageDeleteScenario
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
		// TODO - implement HomeFa�ade.manageModifyScenario
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
		// TODO - implement HomeFa�ade.manageCreateScenario
		throw new UnsupportedOperationException();
	}

}