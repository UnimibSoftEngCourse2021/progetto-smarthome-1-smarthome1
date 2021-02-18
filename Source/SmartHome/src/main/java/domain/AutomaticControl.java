package main.java.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import main.java.domain.Object.ObjectType;
import main.java.domain.Sensor.AirState;
import main.java.domain.Sensor.SensorCategory;
import main.java.domain.TimerOP.Type;

public class AutomaticControl {

	private static AutomaticControl automaticControl = null; //singleton instance
	private static double[][] userMatrix = new double[7][26];
	private double[][] standardMatrix = new double[7][26];
	public enum ChoosenMatrix {STANDARD, USER} // flag per selezionare matrice standard o user defined (0 standard, 1 user)
	private static ChoosenMatrix choosenMatrix = ChoosenMatrix.STANDARD;
	//private boolean activeLightControl = false;
	//private boolean activeAirControl = false;
	private static int startDayMode;
	private static int stopDayMode;
	private List<Sensor> sensors = new ArrayList<Sensor>();
	
	private AutomaticControl() {
		initStandardMatrix();
	}
	
	public static AutomaticControl getInstance() {
		if(automaticControl == null)
			automaticControl = new AutomaticControl();
		return automaticControl;
	}

	public static void initUserMatrix(int i , int j, double value) {
		userMatrix[i][j] = value;
	}
	
	private void initStandardMatrix() {

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
	
	public void checkTempTresholds(double currentTemp, List<Object> publisherList) {
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
					ConflictHandler.getInstance().doAction(publisherList.get(k).getObjectID(), true);
			} else {
				for(int k = 0; k < publisherList.size(); k++) 
					ConflictHandler.getInstance().doAction(publisherList.get(k).getObjectID(), false);
			}	
		} else {
			if (standardMatrix[i][treshold] > currentTemp) {
				for(int k = 0; k < publisherList.size(); k++) 
					ConflictHandler.getInstance().doAction(publisherList.get(k).getObjectID(), true);
			} else {
				for(int k = 0; k < publisherList.size(); k++) 
					ConflictHandler.getInstance().doAction(publisherList.get(k).getObjectID(), false);
			}
		}
	}

	public void checkAlarm() {
		if (Alarm.isCreated() && Alarm.getInstance().isArmed()) 
			for(Sensor sensor: sensors) 
				if((sensor.getCategory().equals(SensorCategory.MOVEMENT) 
						|| sensor.getCategory().equals(SensorCategory.DOOR) 
						|| sensor.getCategory().equals(SensorCategory.WINDOW))
						&& sensor.getValue() == 1.00) {
					ConflictHandler.getInstance().doAction(Alarm.getInstance().getObjectID(), true);
					break;
				}
	}
	
	//controllare la logica del timer
	public void checkAirPollution(double currentPollutionValue, Room room, AirState airState) {
		List<Object> windows = room.getObjects(ObjectType.WINDOW);
		TimerOP timer = room.getTimer();
		if(currentPollutionValue > 50.00 && airState.equals(AirState.POLLUTION)) {
			for(int i = 0; i < room.getWindowsNum(); i++)
				ConflictHandler.getInstance().doAction(room.getObjects(ObjectType.WINDOW).get(i).getObjectID(), airState, true);
			timer.resetTimer(Type.AIR);
		} else {
			if(!timer.isWorking(Type.LIGHT) && !timer.getElapsedTimers()[0]) 
				timer.startTimer(Type.LIGHT, 300);
			else if(!timer.isWorking(Type.LIGHT))
				for(int j = 0; j < room.getWindowsNum(); j++) 
					if(windows.get(j).isActive() == true) 
						ConflictHandler.getInstance().doAction(windows.get(j).getObjectID(), airState, true);
		}
		if (currentPollutionValue > 30.00 && airState.equals(AirState.GAS))
			for(int i = 0; i < room.getWindowsNum(); i++)
				ConflictHandler.getInstance().doAction(room.getObjects(ObjectType.WINDOW).get(i).getObjectID(), airState, true);

	}

	public void checkLight(double movementValue, Room room) {
		List<Object> lights = room.getObjects(ObjectType.LIGHT);
		TimerOP timer = room.getTimer();
		if(movementValue == 1.00) {
			for(int i = 0; i < room.getLightsNum(); i++) 
				if(lights.get(i).isActive() == false) 
					ConflictHandler.getInstance().doAction(lights.get(i).getObjectID(), isDayMode(), true);
			timer.resetTimer(Type.LIGHT);	
		} else {
			if(!timer.isWorking(Type.LIGHT) && !timer.getElapsedTimers()[0]) 
				timer.startTimer(Type.LIGHT, 300);
			else if(!timer.isWorking(Type.LIGHT))
				for(int j = 0; j < room.getLightsNum(); j++) 
					if(lights.get(j).isActive() == true) 
						ConflictHandler.getInstance().doAction(lights.get(j).getObjectID(), isDayMode(), false);
		}
	}
	
	public boolean isDayMode() {
		if(LocalDateTime.now().getHour()*60 + LocalDateTime.now().getMinute() >= startDayMode &&
				LocalDateTime.now().getHour()*60 + LocalDateTime.now().getMinute() < stopDayMode) 
			return true;
		else
			return false;
	}
	
	public double[][] getUserMatrix() {
		return userMatrix;
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

	public static void setChoosenMatrix(ChoosenMatrix choosenMatrix) {
		AutomaticControl.choosenMatrix = choosenMatrix;
	}

	public int getStartDayMode() {
		return startDayMode;
	}

	public static void setStartDayMode(String startDayMode) {
		AutomaticControl.startDayMode = Integer.parseInt(startDayMode.substring(0, 2))*60 + Integer.parseInt(startDayMode.substring(3));
				
	}

	public int getStopDayMode() {
		return stopDayMode;
	}

	public static void setStopDayMode(String stopDayMode) {
		AutomaticControl.stopDayMode = Integer.parseInt(stopDayMode.substring(0, 2))*60 + Integer.parseInt(stopDayMode.substring(3));
	}

	public List<Sensor> getSensors() {
		return sensors;
	}

	public void addSensor(Sensor sensor) {
		sensors.add(sensor);
	}
}