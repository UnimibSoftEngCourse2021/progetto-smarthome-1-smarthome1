package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import domain.Sensor.AirState;
import domain.Sensor.Category;

public class AutomaticControl {

	private double[][] userMatrix = new double[7][26];
	private double[][] standardMatrix = new double[7][26];
	private enum ChoosenMatrix {STANDARD, USER} // flag per selezionare matrice standard o user defined (0 standard, 1 user)
	private ChoosenMatrix choosenMatrix = ChoosenMatrix.STANDARD;
	
	private boolean activeLightControl = false;
	private boolean activeAirControl = false;
	private int startDayMode;
	private int stopDayMode;
	
	private List<Sensor> sensors;
	private ConflictHandler handler;
	private Config config;
	private TimerOP[] timers;
	
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

	public int getStartDayMode() {
		return startDayMode;
	}

	public void setStartDayMode(int startDayMode) {
		this.startDayMode = startDayMode;
	}

	public int getStopDayMode() {
		return stopDayMode;
	}

	public void setStopDayMode(int stopDayMode) {
		this.stopDayMode = stopDayMode;
	}

	/*
	 * se initUserMatrix prende gia una matrice allora 
	 * esiste gia il metodo setUserMatrix
	 * -d.barzio
	 */
	public void initUserMatrix(double[][] userMatrix) {
		for(int i = 0; i <= 6; i++) {
			for(int j = 0; j <= 23; j++) {
				// inizializzare matrice con valori presi da utente
			}
		}
	}
	
	/*
	 * anche qui vale la stessa cosa di initUserMatrix -d.barzio
	 */
	public void initStandardMatrix(double[][] standardMatrix) {

		for(int i = 0; i <= 6; i++) {
			standardMatrix[i][24] = 16.00;
			standardMatrix[i][25] = 20.00;
			for(int j = 0; j <= 7; j++) {
				standardMatrix[i][j] = 0.00;
			}
			for(int j = 8; j <= 20; j++) {
				standardMatrix[i][j] = 1.00;
			}
			for(int j = 21; j <= 23; j++) {
				standardMatrix[i][j] = 0.00;
			}
		}
	}
	
	/**
	 * 
	 * @param currentTemp
	 * @param publisherList
	 */
	public void checkTempTresholds(double currentTemp, List<Object> publisherList) {
		/*
		 *  forse non è necessario passare la publisherList perchè il metodo viene
		 *  chiamato automaticamente per ogni calorifero in ascolto sul sensore di temperatura
		 */
		int i = LocalDateTime.now().getDayOfWeek().getValue() - 1; // giorno della settimana
		int j = LocalDateTime.now().getHour(); // ora attuale
	
		if(choosenMatrix.equals(ChoosenMatrix.USER)) {
			if (userMatrix[i][j] > currentTemp) {
				for(int k = 0; k < publisherList.size(); k++) {
					handler.doAction(publisherList.get(k).getObjectID(), true);
				}
			}
			else {
				for(int k = 0; k < publisherList.size(); k++) {
					handler.doAction(publisherList.get(k).getObjectID(), false);
				}
			}
		}
		else {
			if (standardMatrix[i][j] > currentTemp) {
				for(int k = 0; k < publisherList.size(); k++) {
					handler.doAction(publisherList.get(k).getObjectID(), true);
				}
			}
			else {
				for(int k = 0; k < publisherList.size(); k++) {
					handler.doAction(publisherList.get(k).getObjectID(), false);
				}
			}
		}
	}

	/**
	 * 
	 * @param alarm
	 */
	public void checkAlarm(Alarm alarm) { // boolean returnValue
		if (Alarm.isArmed() == true) {
			for(Sensor sensor: sensors) {
				if((sensor.getCategory().equals(Category.MOVEMENT) 
						|| sensor.getCategory().equals(Category.DOOR) 
						|| sensor.getCategory().equals(Category.WINDOW))
						&& sensor.getValue() == 1.00) {
					handler.doAction(alarm.getObjectID(), true);
				}
			}
		}
	}
	/*
	 * il metodo viene chiamato solo se uno dei sensori interessati si attiva, 
	 * quindi se l'allarme è armato deve attivarsi sempre
	 * il fatto che viene passato l'oggetto alarm per ovviare alla mancata conoscenza di Object è giusto?
	 */

	/**
	 * 
	 * @param currentPollutionValue
	 * @param stanza
	 * @param airState
	 */
	public void checkAirPollution(double currentPollutionValue, Room room, String airState) {
		if(currentPollutionValue > 50.00 && airState.equals(AirState.POLLUTION.toString()))
			for(int i = 0; i < room.getWindowsNum(); i++)
				handler.doAction(room.getObjectList("window").get(i).getObjectID(), airState, true);
		else if (currentPollutionValue > 30.00)
			for(int i = 0; i < room.getWindowsNum(); i++)
				handler.doAction(room.getObjectList("window").get(i).getObjectID(), airState, true);
		else
			for(int i = 0; i < room.getWindowsNum(); i++)
				handler.doAction(room.getObjectList("window").get(i).getObjectID(), airState, false);
	}

	/**
	 * 
	 * @param movementValue
	 * @param stanza
	 */
	public void checkLight(double movementValue, Room room, boolean elapsedTimer) {
		ArrayList<Object> lights = room.getObjectList("light");
		TimerOP timer = new TimerOP();
		if(movementValue == 1.00) {
			for(int i = 0; i < room.getLightsNum(); i++) {
				if(lights.get(i).isActive() == false) 
					handler.doAction(lights.get(i).getObjectID(), isDayMode(), true);
			}
			for(int i = 0; i < timers.length; i++) {
				if(timers[i].getRoom().equals(room)) 
					timers[i].resetTimer();	
			}
		} 
		else {
			for(int i = 0; i < timers.length; i++) 
				if(timers[i].getRoom().equals(room))
					timer = timers[i];
			if(!timer.isWorking() && !elapsedTimer) 
				timer.startTimer(room);
			else if(!timer.isWorking() && elapsedTimer) //forse la prima cond non serve
				for(int j = 0; j < room.getLightsNum(); j++) {
					if(lights.get(j).isActive() == true) 
						handler.doAction(lights.get(j).getObjectID(), isDayMode(), false);
				}
		}
	}
		public boolean isDayMode() {
			if(LocalDateTime.now().getHour()*60 + LocalDateTime.now().getMinute() >= startDayMode &&
					LocalDateTime.now().getHour()*60 + LocalDateTime.now().getMinute() < stopDayMode) {
				return true;
			}
			else
				return false;
		}

}