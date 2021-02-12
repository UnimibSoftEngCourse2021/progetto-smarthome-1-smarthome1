package service;

import domain.TimerOP;

public class TimerThread extends Thread{
	
	private static final int TIME = 300;
	private TimerOP timer;
	private boolean interrupted;
	
	public boolean isInterrupted() {
		return interrupted;
	}

	public void setInterrupted(boolean interrupted) {
		this.interrupted = interrupted;
	}

	public void run() {
		try {
			Thread.sleep(TIME * 1000);
		} catch (InterruptedException e) {
			interrupted = true;
		}
		if(!interrupted)
			timer.executeOperations();
	}
	
}