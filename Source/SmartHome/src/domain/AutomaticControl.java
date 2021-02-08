package domain;

import java.util.List;

import domain.Sensor.Category;

public class AutomaticControl {

	private List<double[]> userMatrix7x24; //mettere a posto per farle diventare matrici
	private List<double[]> standardMatrix7x24;
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
		int i; // elemento i-mo selezionato della matrice
		int j; // valore del j-mo sensore 
		currentTemp = sensors.get(j).getValue();
		if (userMatrix7x24.get(i) > currentTemp) { // capire come prendere il valore impostato nella matrice
			handler.doAction(sensors.get(i).getPublisherList()[i].getObjectID());
		}
	}

	/**
	 * 
	 * @param returnValue
	 * @param alarm
	 */
	public void checkAlarm(boolean returnValue, Alarm alarm) {
		// TODO - implement AutomaticControl.checkAlarm
		if (alarm.isArmed() == true) {
			for(int i = 0; i < sensors.size(); i++) {
				if(sensors.get(i).getCatergory() == (Category.MOVEMENT, Category.WINDOW, Category.DOOR)) { // capire come usare enum
					if(alarm.getType() == Type.ALARM & sensors.get(i).getValue() == 1) { // 1 è acceso 0 è spento
						handler.doAction(alarm.getObjectID());
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param alarm
	 */
	public void isAway(Alarm alarm) {
		// TODO - implement AutomaticControl.isAway
		alarm.setArmed(true);
	}

	/**
	 * 
	 * @param code
	 * @param alarm
	 */
	public void isHome(String code, Alarm alarm) {
		// TODO - implement AutomaticControl.isHome
		String insertCode;
		int i = 0;
		do {
			// inserire codice tramite UI
			if (insertCode.equals(code)) {
				alarm.setArmed(false);
			}
			else {
				//richiedere codice
				i++; // contatore degli accessi errati
			}
		} while (i < 5);
		if (i >= 5) {
			System.out.println("Numero massimo di tentativi superato. Chiamare l'assistenza");
		}
	}

	/**
	 * 
	 * @param currentPollutionValue
	 * @param stanza
	 * @param airState
	 */
	public void checkAirPollution(double currentPollutionValue, Room room, String airState) {
		// TODO - implement AutomaticControl.checkAirPollution
		if(currentPollutionValue > 50.00) {
			for(int i = 0; i < room.getWindowsNum(); i++) {
				handler.doAction(room.getObjectList(Type.WINDOW)[i]), airState); // capire come prendere gli oggetti di tipo window dalla lista
			}
		}
	}

	/**
	 * 
	 * @param movementValue
	 * @param stanza
	 */
	public void checkLight(boolean movementValue, Room room) {
		// TODO - implement AutomaticControl.checkLight
		for(int i = 0; i < room.getLightsNum(); i++) {
			if(room.getObjectList(Type.LIGHT).isState(true) && room.getObjectList(Type.MOVEMENT)) { // dire in qualche modo che il sensore di movimento non è attivo da tot minuti
				handler.doAction(room.getObjectList(Type.LIGHT));
			}
		}
	}

	public void handleDateEvent() {
		// TODO - implement AutomaticControl.handleDateEvent
		throw new UnsupportedOperationException();
	}

}