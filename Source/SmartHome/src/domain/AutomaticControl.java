package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import domain.Sensor.AirState;
import domain.Sensor.Category;

public class AutomaticControl {

	private double[][] userMatrix = new double[8][24];
	private double[][] standardMatrix = new double[8][24];
	private enum ChoosenMatrix {STANDARD, USER} // flag per selezionare matrice standard o user defined (0 standard, 1 user)
	private ChoosenMatrix choosenMatrix = ChoosenMatrix.STANDARD;
	private boolean atHome = true;
	private boolean activeLightControl = false;
	private boolean activeAirControl = false;
	public LocalDateTime currentTime = LocalDateTime.now(); // forse dovrebbe stare in una classe "generale" perchè potrebbe servire a chiunque
	
	private List<Scenario> scenarios;
	private List<Sensor> sensors;
	private ConflictHandler handler;
	private Config config;
	private TimerOP[] timers;

	public void userMatrixInitialize(double[][] userMatrix) {
		for(int i = 1; i <= 7; i++) {
			for(int j = 0; j <= 23; j++) {
				// inizializzare matrice con valori presi da utente
			}
		}
	}
	
	public double[][] getUserMatrix() {
		return userMatrix;
	}

	public void setUserMatrix(double[][] userMatrix) {
		this.userMatrix = userMatrix;
	}

	public double[][] getStandardMatrix() {
		return standardMatrix;
	}

	public void setStandardMatrix(double[][] standardMatrix) {
		this.standardMatrix = standardMatrix;
	}

	public ChoosenMatrix getChoosenMatrix() {
		return choosenMatrix;
	}

	public void setChoosenMatrix(ChoosenMatrix choosenMatrix) {
		this.choosenMatrix = choosenMatrix;
	}

	public boolean isAtHome() {
		return atHome;
	}

	public void setAtHome(boolean atHome) {
		this.atHome = atHome;
	}

	public boolean isActiveLightControl() {
		return activeLightControl;
	}

	public void setActiveLightControl(boolean activeLightControl) {
		this.activeLightControl = activeLightControl;
	}

	public boolean isActiveAirControl() {
		return activeAirControl;
	}

	public void setActiveAirControl(boolean activeAirControl) {
		this.activeAirControl = activeAirControl;
	}

	public void standardMatrixInitialize(double[][] standardMatrix) {
		for(int i = 1; i <= 7; i++) {
			for(int j = 0; j <= 7; j++) {
				standardMatrix[i][j] = 18.00;
			}
			for(int j = 8; j <= 20; j++) {
				standardMatrix[i][j] = 20.00;
			}
			for(int j = 21; j <= 23; j++) {
				standardMatrix[i][j] = 18.00;
			}
		}
	}
	
	/**
	 * 
	 * @param currentTemp
	 * @param publisherList
	 */
	public void checkTempTresholds(double currentTemp, Heater[] publisherList) {
		/*
		 *  forse non è necessario passare la publisherList perchè il metodo viene
		 *  chiamato automaticamente per ogni calorifero in ascolto sul sensore di temperatura
		 */
		int i = currentTime.getDayOfWeek().getValue(); // giorno della settimana
		int j = currentTime.getHour(); // ora attuale
	
		if(choosenMatrix.equals(ChoosenMatrix.USER)) {
			if (userMatrix[i][j] > currentTemp) {
				for(int k = 0; k < publisherList.length; k++) {
					handler.doAction(publisherList[k].getObjectID(), true);
				}
			}
			else {
				for(int k = 0; k < publisherList.length; k++) {
					handler.doAction(publisherList[k].getObjectID(), false);
				}
			}
		}
		else {
			if (standardMatrix[i][j] > currentTemp) {
				for(int k = 0; k < publisherList.length; k++) {
					handler.doAction(publisherList[k].getObjectID(), true);
				}
			}
			else {
				for(int k = 0; k < publisherList.length; k++) {
					handler.doAction(publisherList[k].getObjectID(), false);
				}
			}
		}
	}

	/**
	 * 
	 * @param returnValue
	 * @param alarm
	 */
	public void checkAlarm(boolean returnValue, Alarm alarm) {
		if (alarm.isArmed() == true) {
			for(int i = 0; i < sensors.size(); i++) {
				if(sensors.get(i).getCategory().equals(Category.MOVEMENT) || sensors.get(i).getCategory().equals(Category.DOOR) || sensors.get(i).getCategory().equals(Category.WINDOW)) {
						handler.doAction(alarm.getObjectID(), true);
				}
			}
		}
	}

	/**
	 * 
	 * @param alarm
	 */
	public void isAway(Alarm alarm) {
		atHome = false;
		alarm.setArmed(true);
	}

	/**
	 * 
	 * @param code
	 * @param alarm
	 */
	public void isHome(String code, Alarm alarm, Door door) { // ho dovuto aggiungere l'oggetto door come attributo per avere il codice (controllare)
		int i = 0;
		do {
			if (code.equals(door.getCode())) {
				atHome = true;
				alarm.setArmed(false);
			}
			else {
				//richiedere codice all'utente
				i++; // contatore degli accessi errati
			}
		} while (i < 5);
		if (i >= 5) {
			System.out.println("Numero massimo di tentativi superato. Chiamare l'assistenza");
			// bloccare la richiesta di codice
		}
	}

	/**
	 * 
	 * @param currentPollutionValue
	 * @param stanza
	 * @param airState
	 */
	public void checkAirPollution(double currentPollutionValue, Room room, String airState) {
		if(currentPollutionValue > 50.00 && airState.equals(AirState.POLLUTION.toString()))
			for(int i = 0; i < room.getWindowsNum(); i++)
				handler.doAction(room.getObjectList("window").get(i).getObjectID(), airState);
		else if (currentPollutionValue > 30.00)
			for(int i = 0; i < room.getWindowsNum(); i++)
				handler.doAction(room.getObjectList("window").get(i).getObjectID(), airState);
	}

	/**
	 * 
	 * @param movementValue
	 * @param stanza
	 */
	public void checkLight(boolean movementValue, Room room, boolean elapsedTimer) {
		ArrayList<Object> lights = room.getObjectList("light");
		TimerOP timer = new TimerOP();
		if(movementValue == true) {
			for(int i = 0; i < room.getLightsNum(); i++) {
				if(lights.get(i).isActive() == false) 
					handler.doAction(lights.get(i).getObjectID(), true);
			}
			for(int i = 0; i < timers.length; i++) {
				if(timers[i].getRoom().equals(room)) 
					timers[i].resetTimer();	
			}
		} else  {/* dire in qualche modo che il sensore di movimento non è attivo da tot minuti*/
			for(int i = 0; i < timers.length; i++) 
				if(timers[i].getRoom().equals(room))
					timer = timers[i];
			if(!timer.isWorking() && !elapsedTimer) 
				timer.startTimer(room);
			else if(!timer.isWorking() && elapsedTimer) //forse la prima cond non serve
				for(int j = 0; j < room.getLightsNum(); j++) {
					if(lights.get(j).isActive() == true) 
						handler.doAction(lights.get(j).getObjectID(), false);
				}
		}
	}
	
	public void handleDateEvent() {
		// TODO - implement AutomaticControl.handleDateEvent
		throw new UnsupportedOperationException();
	}

}