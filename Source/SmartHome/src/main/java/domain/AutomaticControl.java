package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import domain.Obj.ObjType;
import domain.Sensor.AirState;
import domain.Sensor.SensorCategory;
import domain.TimerOP.Type;

public class AutomaticControl {

	private static AutomaticControl automaticControl = null; //singleton instance
	private static double[][] userMatrix = new double[7][26];
	private double[][] standardMatrix = new double[7][26];
	public enum ChoosenMatrix {STANDARD, USER} // flag per selezionare matrice standard o user defined (0 standard, 1 user)
	private ChoosenMatrix choosenMatrix = ChoosenMatrix.STANDARD;
	private static int startDayMode;
	private static int stopDayMode;
	private List<Sensor> sensors;
	
	/*
	 * alla creazione dell'oggetto viene anche inizializzata la matrice standard relativa alle
	 * temperature per i caloriferi
	 */
	private AutomaticControl() {
		initStandardMatrix();
		sensors = new ArrayList<>();
	}
	
	/*
	 * metodo che restituisce l'istanza dell'oggetto, se necessario viene anche creato l'oggetto
	 * questo metodo è l'applicazione del pattern singleton
	 */
	public static AutomaticControl getInstance() {
		if(automaticControl == null)
			automaticControl = new AutomaticControl();
		return automaticControl;
	}

	public static void initUserMatrix(int i , int j, double value) {
		userMatrix[i][j] = value;
	}
	
	/*
	 * i valori delle colonne da 0 a 23 corrispondono alla scelta della temperatura attiva/passiva per ogni ora del giorno
	 * le ultime due caselle sono i valori delle temperature attive/passive
	 * le righe corrispondono ai sette giorni della settimana
	 */
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
	/*
	 * metodo per verificare la necessità o meno di accendere i caloriferi
	 * a seconda dell'oraio e della temperatura correnti
	 */
	public void checkTempTresholds(double currentTemp, List<Obj> publisherList) {
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
					ConflictHandler.getInstance().doAction(publisherList.get(k).getObjID(), true);
			} else {
				for(int k = 0; k < publisherList.size(); k++) 
					ConflictHandler.getInstance().doAction(publisherList.get(k).getObjID(), false);
			}	
		} else {
			if (standardMatrix[i][treshold] > currentTemp) {
				for(int k = 0; k < publisherList.size(); k++) 
					ConflictHandler.getInstance().doAction(publisherList.get(k).getObjID(), true);
			} else {
				for(int k = 0; k < publisherList.size(); k++) 
					ConflictHandler.getInstance().doAction(publisherList.get(k).getObjID(), false);
			}
		}
	}

	/*
	 * metodo per controllare se l'allarme deve suonare in caso sia armato e uno dei sensori
	 * interessati si attivi
	 */
	public void checkAlarm() {
		if (Alarm.isCreated() && Alarm.getInstance().isArmed()) 
			for(Sensor sensor: sensors) 
				if((sensor.getCategory().equals(SensorCategory.MOVEMENT) 
						|| sensor.getCategory().equals(SensorCategory.DOOR) 
						|| sensor.getCategory().equals(SensorCategory.WINDOW))
						&& sensor.getValue() == 1.00) {
					ConflictHandler.getInstance().doAction(Alarm.getInstance().getObjID(), true);
					break;
				}
	}
	
	/*
	 * metodo per controllare se si debba attivare il ri-circolo dell'aria in seguito ad un eccessivo
	 * inquinamento interno, oppure ad una fuga di gas
	 * nel primo caso vengono aperte le finestre se non sono accesi i caloriferi per evitare sprechi
	 * altrimenti si chiede l'intervento dell'utente tramite una notifica
	 * nel secondo caso vengono spente le luci per evitare esplosioni ed aperte le finestre qualunque
	 * sia lo stato degli oggetti in quant osi tratta di una situazione critica
	 * i valori relativi all'inquinamento e alla fuga di gas sono dei valori forfait assegnati da noi
	 */
	public void checkAirPollution(double currentPollutionValue, Room room, AirState airState) {
		List<Obj> windows = room.getObjs(ObjType.WINDOW);
		TimerOP timer = room.getTimer();
		if(currentPollutionValue > 50.00 && airState.equals(AirState.POLLUTION)) {
			for(int i = 0; i < room.getWindowsNum(); i++)
				ConflictHandler.getInstance().doAction(room.getObjs(ObjType.WINDOW).get(i).getObjID(), airState, true);
			timer.startTimer(Type.AIR, 300);				
		} else if(airState.equals(AirState.POLLUTION) && (timer.isCreated(Type.AIR))) {					
				for(int j = 0; j < room.getWindowsNum(); j++) 
					if(windows.get(j).isActive()) 
						ConflictHandler.getInstance().doAction(windows.get(j).getObjID(), airState, false);
				timer.resetTimer(Type.AIR);		
		}
		if (currentPollutionValue > 30.00 && airState.equals(AirState.GAS))
			for(int i = 0; i < room.getWindowsNum(); i++)
				ConflictHandler.getInstance().doAction(room.getObjs(ObjType.WINDOW).get(i).getObjID(), airState, true);
	}

	/*
	 * metodo per accendere le luci qualora l'utente entri nella stanza interessata oppure
	 * spegnerle qualora sia uscito da cinque minuti
	 */
	public void checkLight(double movementValue, Room room) {
		List<Obj> lights = room.getObjs(ObjType.LIGHT);
		TimerOP timer = room.getTimer();
		if(movementValue == 1.00) {
			for(int i = 0; i < room.getLightsNum(); i++) 
				if(!lights.get(i).isActive()) 
					ConflictHandler.getInstance().doAction(lights.get(i).getObjID(), isDayMode(), true);
			if(timer.isCreated(Type.LIGHT))
				timer.resetTimer(Type.LIGHT);	
		} else {
			if(!timer.isCreated(Type.LIGHT)) 
				timer.startTimer(Type.LIGHT, 300);
			if(timer.getElapsedTimers()[0])
				for(int j = 0; j < room.getLightsNum(); j++) 
					if(lights.get(j).isActive()) 
						ConflictHandler.getInstance().doAction(lights.get(j).getObjID(), isDayMode(), false);		
		}
	}
	/*
	 * verifica se la modalità attuale è quella giorno, a seconda dell'orario
	 */
	public boolean isDayMode() {		
		return(LocalDateTime.now().getHour()*60 + LocalDateTime.now().getMinute() >= startDayMode &&
				LocalDateTime.now().getHour()*60 + LocalDateTime.now().getMinute() < stopDayMode);
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

	public  void setChoosenMatrix(ChoosenMatrix choosenMatrix) {
		this.choosenMatrix = choosenMatrix;
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
	/*
	 * metodo utilizzato durante i test per pulire l'istanza utilizzata
	 */
	public static void clean() {
		automaticControl = null;
	}
}