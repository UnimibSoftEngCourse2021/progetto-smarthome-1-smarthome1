package domain;

import java.util.List;

public class AutomaticControl {

	private int userMatrix7x24;
	private int standardMatrix7x24;
	private boolean matrixFlag = false;
	private boolean atHome = true;
	private boolean homeLightControl = false;
	private boolean homeAirControl = false;
	
	private List<Scenario> scenarios;
	private List<Sensor> sensors;
	private ConflictHandler handler;
	private Config config;

	/**
	 * 
	 * @param currentTemp
	 * @param publisherList
	 */
	public void checkTempTresholds(double currentTemp, Heater[] publisherList) {
		// TODO - implement AutomaticControl.checkTempTresholds
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param returnValue
	 * @param alarm
	 */
	public void checkAlarm(boolean returnValue, Alarm alarm) {
		// TODO - implement AutomaticControl.checkAlarm
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param alarm
	 */
	public void isAway(Alarm alarm) {
		// TODO - implement AutomaticControl.isAway
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param code
	 * @param alarm
	 */
	public void isHome(String code, Alarm alarm) {
		// TODO - implement AutomaticControl.isHome
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param currentPollutionValue
	 * @param stanza
	 * @param airState
	 */
	public void checkAirPollution(double currentPollutionValue, Room stanza, String airState) {
		// TODO - implement AutomaticControl.checkAirPollution
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param movementValue
	 * @param stanza
	 */
	public void checkLight(boolean movementValue, Room stanza) {
		// TODO - implement AutomaticControl.checkLight
		throw new UnsupportedOperationException();
	}

	public void handleDateEvent() {
		// TODO - implement AutomaticControl.handleDateEvent
		throw new UnsupportedOperationException();
	}

}