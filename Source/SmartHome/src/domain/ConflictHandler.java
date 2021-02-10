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
	public void doAction(String objectID, String airState) { //controllare metodo per vedere se è necessario aggiungere parametro changeState anche qua
		// TODO - implement ConflictHandler.doAction
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