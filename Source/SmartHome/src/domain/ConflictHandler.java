package domain;

import java.util.List;
import service.ObjectCommunicationAdapter;

public class ConflictHandler {
	
	private AutomaticControl automaticControl;
	private List<Object> objects;
	private List<Scenario> scenario;
	private ObjectCommunicationAdapter adapter;

	/**
	 * 
	 * @param objectID
	 */
	public void doAction(String objectID, boolean changeState) {
		// TODO - implement ConflictHandler.doAction
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param objectID
	 * @param airState
	 */
	public void doAction(String objectID, String airState) { //non serve il parametro changeState perch� viene chiamato solo in modo automatico
		// TODO - implement ConflictHandler.doAction
		// a seconda di airState azione ha priorit� diversa
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param objectID
	 */
	public void doManualAction(String objectID) {
		// TODO - implement ConflictHandler.doManualAction
		throw new UnsupportedOperationException();
	}

}