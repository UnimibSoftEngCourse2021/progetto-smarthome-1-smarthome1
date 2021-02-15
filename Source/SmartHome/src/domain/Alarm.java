package domain;

import java.util.List;

public class Alarm extends Object {



	private static boolean armed;
	private static List<Sensor> sensors;
	
	public Alarm(String name) {
		super(name, ObjectType.ALARM, null);
		Alarm.armed = false;
		setObjectID(name);
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