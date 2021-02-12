package domain;

public class Alarm extends Object {

	private boolean armed;
	private boolean active; // cambiato nome da mode per incongruenza con getter
	//esiste gia un attributo active in object -d.barzio
	public boolean isArmed() {
		return armed;
	}
	public void setArmed(boolean armed) {
		this.armed = armed;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean mode) {
		this.active = mode;
	}
	
}