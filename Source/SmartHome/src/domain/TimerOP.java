package domain;


import service.TimerThread;

public class TimerOP{

	private AutomaticControl automaticControl;
	private Room room;
	private TimerThread airThread;
	private TimerThread lightThread;
	public enum Type {LIGHT, AIR};
	private boolean[] elapsedTimers = {false, false};
	
	public TimerThread getAirThread() {
		return airThread;
	}

	public void setAirThread(TimerThread airThread) {
		this.airThread = airThread;
	}

	public TimerThread getLightThread() {
		return lightThread;
	}

	public void setLightThread(TimerThread lightThread) {
		this.lightThread = lightThread;
	}

	public boolean[] getElapsedTimers() {
		return elapsedTimers;
	}

	public void setElapsedTimers(boolean[] elapsedTimers) {
		this.elapsedTimers = elapsedTimers;
	}

	public void startTimer(Type timerType, Room room, int time) {
		this.setRoom(room);//forse non serve perche la stanza quando viene creata crea a sua volta il timer
		if(timerType.equals(Type.AIR)) {
			elapsedTimers[1] = false;
			airThread = new TimerThread(time, Type.AIR);
			airThread.setInterrupted(false);
			airThread.start();
		} else {
			elapsedTimers[0] = false;
			lightThread = new TimerThread(time, Type.LIGHT);
			lightThread.setInterrupted(false);
			lightThread.start();
		}
	}

	public void resetTimer(Type timerType) {
		if(timerType.equals(Type.AIR)) {
			elapsedTimers[1] = false;
			airThread.interrupt();
		} else {
			elapsedTimers[0] = false;
			lightThread.interrupt();
		}
	}

	public void executeOperations(Type timerType) {
		if(timerType.equals(Type.LIGHT)) {
			elapsedTimers[0] = true;
			automaticControl.checkLight(0.00, room);
		} else {
			elapsedTimers[1] = true;
			automaticControl.checkAirPollution(0.00, room, "pollution");
		}
	}
	
	public boolean isWorking(Type timerType) {
		if(timerType.equals(Type.AIR))
			return airThread.isAlive();
		else
			return lightThread.isAlive();
	}
	
	public boolean isFinished(Type timerType) {
		if(timerType.equals(Type.AIR)) {
			if(airThread.equals(null)) 
				return true;
			return false;
		} else {
			if(lightThread.equals(null))
				return true;
			return false;
		}
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