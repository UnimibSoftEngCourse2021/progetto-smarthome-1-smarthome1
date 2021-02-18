package main.java.service;

public interface ObjectCommunicationAdapter {

	/**
	 * 
	 * @param roomID
	 * @param objectID
	 * @param actionValue
	 */
	void triggerAction(Object object, boolean actionValue);

}