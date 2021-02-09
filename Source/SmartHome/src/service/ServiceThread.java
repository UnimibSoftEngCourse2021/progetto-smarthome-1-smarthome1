package service;


import domain.TimerOP;

public class ServiceThread extends Thread{
	
	private static final int TIME = 300;
	private TimerOP timer;
	
	public void run() {
		try {
			Thread.sleep(TIME * 1000);
		} catch (InterruptedException e) {
			/*boh */
		}
		timer.executeOperations();
	}
}
