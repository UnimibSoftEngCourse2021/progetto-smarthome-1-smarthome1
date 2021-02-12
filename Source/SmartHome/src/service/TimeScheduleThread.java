package service;

import java.time.LocalDateTime;

import domain.Scenario;

public class TimeScheduleThread extends Thread {
	
	private long sleepTime;
	private long waitTime;
	
	private Scenario scenario;
	
	public void run() {
		while(true) {
			if(!(scenario.getDays().contains(LocalDateTime.now().getDayOfWeek()))) {
				try {
					TimeScheduleThread.sleep((24*3600*1000) - this.getMilliseconds(LocalDateTime.now()));
				} catch (InterruptedException e) {
					break;
				}
			}
			else {
				try {
					TimeScheduleThread.sleep(sleepTime);
				} catch (InterruptedException e) {
					break;
				}
				scenario.activateScenario();
				try {
					TimeScheduleThread.sleep(waitTime);
				} catch (InterruptedException e) {
					break;
				}
			}
		}
	}
	
	public void init(LocalDateTime startTime) {
		sleepTime = this.getMilliseconds(startTime);
		waitTime = (24*3600*1000) - sleepTime;
		this.start();
	}
	
	public long getMilliseconds (LocalDateTime time) {
		return (time.getHour()*3600 + time.getMinute()*60) * 1000;
	}
	
}
