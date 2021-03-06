package service;

import domain.TimerOP;
import domain.TimerOP.Type;

public class TimerThread extends Thread{
	
	private  int time;
	private TimerOP timer;
	private boolean interrupted;
	private Type timerType;
	
	public TimerThread(int time, Type timerType) {
		this.time = time;
		this.timerType = timerType;
	}
	
	@Override
	public boolean isInterrupted() {
		return interrupted;
	}

	public void setInterrupted(boolean interrupted) {
		this.interrupted = interrupted;
	}
	
	public Type getTimerType() {
		return timerType;
	}
	
	public void setTimerType(Type timerType) {
		this.timerType = timerType;
	}

	@Override
	public void run() {
		try {
			Thread.sleep((long)time * 1000);
		} catch (InterruptedException e) {
			interrupted = true;
			this.interrupt();
		}
		if(!interrupted)
			timer.executeOperations(timerType);
	}
}