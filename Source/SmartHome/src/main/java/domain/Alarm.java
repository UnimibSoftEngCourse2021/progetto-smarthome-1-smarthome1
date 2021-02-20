package domain;

import java.util.ArrayList;
import java.util.List;

import domain.Sensor.SensorCategory;

public class Alarm extends Obj {

	private static Alarm alarm = null;
	private boolean armed;
	private List<Sensor> sensors = new ArrayList<>();
	private Sensor sensor;
	
	/*
	 * costruttore della classe:
	 * a partire dal nome passato per parametro crea un oggetto allarme
	 * inizialmente l'allarme non viene armato
	 * lo stato armato si riferisce al fatto che sia in "ascolto", aspettando possibili cambiamenti da parte dei sensori interessati
	 * se è impostato a false non si attiverà al cambiamento di uno dei sensori
	 */
	private Alarm(String name) {
		super(name, ObjType.ALARM, null);
		armed = false;
		setObjID(name);
		sensor = new Sensor("sensorOf: ", SensorCategory.ALARM, null);
	}
	/*
	 * metodi che restituiscono l'istanza dell'oggetto, se necessario viene anche creato l'oggetto
	 * questi metodi sono l'applicazione del pattern singleton
	 */
	public static Alarm getInstance(String name) {
		if(alarm == null)
			alarm = new Alarm(name);
		return alarm;	
	}
	
	public static Alarm getInstance() {
		return alarm;
	}
	
	public static boolean isCreated() {
		if(alarm == null)
			return false;
		return true;
	}

	public boolean isArmed() {
		return armed;
	}
	
	public void setArmed(boolean armed) {
		this.armed = armed;
	}

	public List<Sensor> getSensors() {
		return sensors;
	}

	public void setSensor(Sensor sensor) {
		sensors.add(sensor);
	}

	public Sensor getSensor() {
		return sensor;
	}
	
	public static void clean() {
			alarm = null;
	}

}