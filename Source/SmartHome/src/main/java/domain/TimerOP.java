package domain;

import domain.Sensor.AirState;
import service.TimerThread;

public class TimerOP{

	private Room room;
	private TimerThread airThread;
	private TimerThread lightThread;
	public enum Type {LIGHT, AIR}
	private boolean[] elapsedTimers = {false, false};
	
	public TimerOP(Room room) {
		this.room = room;
	}
	
	public void startTimer(Type timerType, int time) {
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
	
	public boolean isCreated(Type type) {
		if(type.equals(Type.AIR)) {
			if(airThread != null)
				return true;
			return false;
		} else {
			if((lightThread != null))
				return true;
			return false;
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
			AutomaticControl.getInstance().checkLight(0.00, room);
		} else 
			AutomaticControl.getInstance().checkAirPollution(0.00, room, AirState.POLLUTION);
	}
	
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

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

}