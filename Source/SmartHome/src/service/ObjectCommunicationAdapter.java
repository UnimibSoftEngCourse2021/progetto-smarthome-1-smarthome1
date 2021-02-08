package service;

public interface ObjectCommunicationAdapter {

	/**
	 * 
	 * @param roomID
	 * @param objectID
	 * @param actionValue
	 */
	void triggerAction(String roomID, String objectID, boolean actionValue);

}