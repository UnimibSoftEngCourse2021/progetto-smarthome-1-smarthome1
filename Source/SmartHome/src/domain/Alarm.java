package domain;

import java.util.List;

public class Alarm extends Object {


	private static Alarm alarm = null;
	private static boolean armed;
	private static List<Sensor> sensors;
	
	private Alarm(String name) {
		super(name, ObjectType.ALARM, null);
		Alarm.armed = false;
		setObjectID(name);
	}
	
	public static Alarm getInstance(String name) {
		if(alarm == null)
			alarm = new Alarm(name);
		return alarm;
			
	}

	public static boolean isCreated() {
		if(alarm == null)
			return false;
		return true;
	}
	public static boolean isArmed() {
		return armed;
	}
	
	public void setArmed(boolean armed) {
		Alarm.armed = armed;
	}

	public List<Sensor> getSensors() {
		return sensors;
	}

	public static void setSensors(Sensor sensor) {
		Alarm.sensors.add(sensor);
	}

}