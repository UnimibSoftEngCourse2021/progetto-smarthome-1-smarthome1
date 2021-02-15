package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import domain.Sensor.AirState;
import domain.Sensor.Category;
import domain.TimerOP.Type;

public class AutomaticControl {

	private int[][] userMatrix = new int[7][26];
	private int[][] standardMatrix = new int[7][26];
	private enum ChoosenMatrix {STANDARD, USER} // flag per selezionare matrice standard o user defined (0 standard, 1 user)
	private ChoosenMatrix choosenMatrix = ChoosenMatrix.STANDARD;
	//chosen con una o'? -d.barzio
	private boolean activeLightControl = false;
	private boolean activeAirControl = false;
	private int startDayMode;
	private int stopDayMode;
	
	private List<Sensor> sensors;
	private ConflictHandler handler;
	private Config config;
	//private TimerOP[] timers; non credo serva -> il timer viene preso dalla stanza che viene passata a parametro di checkLight -d.barzio
	
	public int[][] getUserMatrix() {
		return userMatrix;
	}

	public void setUserMatrix(int[][] userMatrix) {
		this.userMatrix = userMatrix;
	}

	public int[][] getStandardMatrix() {
		return standardMatrix;
	}

	public void setStandardMatrix(int[][] standardMatrix) {
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
		int treshold = 0;
	
		if(choosenMatrix.equals(ChoosenMatrix.USER)) {
			if(userMatrix[i][j] == 0) 
				treshold = 24;
			else	
				treshold = 25;
		} else {
			if(standardMatrix[i][j] == 0)
				treshold = 24;
			else
				treshold = 25;
		}
		if(choosenMatrix.equals(ChoosenMatrix.USER)) {
			if(userMatrix[i][treshold] > currentTemp) {
				for(int k = 0; k < publisherList.size(); k++) 
					handler.doAction(publisherList.get(k).getObjectID(), true);
			} else {
				for(int k = 0; k < publisherList.size(); k++) 
					handler.doAction(publisherList.get(k).getObjectID(), false);
			}	
		} else {
			if (standardMatrix[i][treshold] > currentTemp) {
				for(int k = 0; k < publisherList.size(); k++) 
					handler.doAction(publisherList.get(k).getObjectID(), true);
			} else {
				for(int k = 0; k < publisherList.size(); k++) 
					handler.doAction(publisherList.get(k).getObjectID(), false);
			}
		}
	}
	/**
	 * 
	 * @param alarm
	 */
	public void checkAlarm(Alarm alarm) {
		if (Alarm.isArmed() == true) 
			for(Sensor sensor: sensors) 
				if((sensor.getCategory().equals(Category.MOVEMENT) 
						|| sensor.getCategory().equals(Category.DOOR) 
						|| sensor.getCategory().equals(Category.WINDOW))
						&& sensor.getValue() == 1.00) {
					handler.doAction(alarm.getObjectID(), true);
					break;
				}
	}
	
	/**
	 * 
	 * @param currentPollutionValue
	 * @param stanza
	 * @param airState
	 */
	/*
	 * ha senso chiudere le finestre se il value non supera la soglia? -d.barzio  
	 */
	public void checkAirPollution(double currentPollutionValue, Room room, String airState) {
		ArrayList<Object> windows = room.getObjectList("window");
		TimerOP timer = room.getTimer();
		if(currentPollutionValue > 50.00 && airState.equals(AirState.POLLUTION.toString())) {
			for(int i = 0; i < room.getWindowsNum(); i++)
				handler.doAction(room.getObjectList("window").get(i).getObjectID(), airState, true);
			timer.resetTimer(Type.AIR);
		} else {
			if(!timer.isWorking(Type.LIGHT) && !timer.getElapsedTimers()[0]) 
				timer.startTimer(Type.LIGHT, room, 300);
			else if(!timer.isWorking(Type.LIGHT) && timer.getElapsedTimers()[0]) //forse la seconda cond non serve
				for(int j = 0; j < room.getWindowsNum(); j++) 
					if(windows.get(j).isActive() == true) 
						handler.doAction(windows.get(j).getObjectID(), airState, true);
		}
		if (currentPollutionValue > 30.00 && airState.equals(AirState.GAS.toString()))
			for(int i = 0; i < room.getWindowsNum(); i++)
				handler.doAction(room.getObjectList("window").get(i).getObjectID(), airState, true);

	}

	/**
	 * 
	 * @param movementValue
	 * @param stanza
	 */
	public void checkLight(double movementValue, Room room) {
		ArrayList<Object> lights = room.getObjectList("light");
		TimerOP timer = room.getTimer();
		if(movementValue == 1.00) {
			for(int i = 0; i < room.getLightsNum(); i++) 
				if(lights.get(i).isActive() == false) 
					handler.doAction(lights.get(i).getObjectID(), isDayMode(), true);
			timer.resetTimer(Type.LIGHT);	
		} else {
			if(!timer.isWorking(Type.LIGHT) && !timer.getElapsedTimers()[0]) 
				timer.startTimer(Type.LIGHT, room, 300);
			else if(!timer.isWorking(Type.LIGHT) && timer.getElapsedTimers()[0]) //forse la seconda cond non serve
				for(int j = 0; j < room.getLightsNum(); j++) 
					if(lights.get(j).isActive() == true) 
						handler.doAction(lights.get(j).getObjectID(), isDayMode(), false);
		}
	}
		public boolean isDayMode() {
			if(LocalDateTime.now().getHour()*60 + LocalDateTime.now().getMinute() >= startDayMode &&
					LocalDateTime.now().getHour()*60 + LocalDateTime.now().getMinute() < stopDayMode) 
				return true;
			else
				return false;
		}

}