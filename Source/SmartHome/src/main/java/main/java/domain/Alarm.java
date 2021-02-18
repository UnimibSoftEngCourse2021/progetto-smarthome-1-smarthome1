package main.java.domain;

import java.util.List;

public class Alarm extends Object {

	private static Alarm alarm = null;
	private boolean armed;
	private List<Sensor> sensors;
	
	private Alarm(String name) {
		super(null, ObjectType.ALARM, null);
		armed = false;
		setObjectID(name);
	}
	
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

}