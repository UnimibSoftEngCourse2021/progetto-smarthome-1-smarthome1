package domain;

import java.util.ArrayList;

public class Scenario {

	private String nameID;
	private int dateTime;
	private boolean state = false;
	private ArrayList actionLIst;
	private String[] roomsIDs;
	
	private ConflictHandler handler;
	private AutomaticControl automaticControl;

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
		// TODO - implement Scenario.activateScenario
		throw new UnsupportedOperationException();
	}

}