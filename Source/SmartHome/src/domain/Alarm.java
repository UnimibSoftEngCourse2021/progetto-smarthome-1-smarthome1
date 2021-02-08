package domain;

public class Alarm extends Object {

	private boolean armed;
	private boolean mode; // considerare cambio nome
	
	public boolean isArmed() {
		return armed;
	}
	public void setArmed(boolean armed) {
		this.armed = armed;
	}
	public boolean isMode() {
		return mode;
	}
	public void setMode(boolean mode) {
		this.mode = mode;
	}
	
}