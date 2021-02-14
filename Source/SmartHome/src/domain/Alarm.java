package domain;

public class Alarm extends Object {

	private static boolean armed;
	
	public static boolean isArmed() {
		return armed;
	}
	
	public void setArmed(boolean armed) {
		Alarm.armed = armed;
	}

}