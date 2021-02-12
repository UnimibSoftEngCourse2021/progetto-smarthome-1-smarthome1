package domain;

import service.TimerThread;

public class TimerOP{

	private AutomaticControl automaticControl;
	private Room room;
	private TimerThread thread;
	
	public void startTimer(Room room) {
		this.setRoom(room); //forse non serve perche la stanza quando viene creata crea a sua volta il timer
		thread.setInterrupted(false);
		thread.start();
	}

	public void resetTimer() {
		thread.interrupt();
	}

	public void executeOperations() {
		automaticControl.checkLight(0.00, room, true);
	}
	
	public boolean isWorking() {
		return thread.isAlive();
	}
	
	public boolean isFinished() {
		if(thread.equals(null))
			return true;
		return false;
	}
	
	public AutomaticControl getAutomaticControl() {
		return automaticControl;
	}

	public void setAutomaticControl(AutomaticControl automaticControl) {
		this.automaticControl = automaticControl;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

}