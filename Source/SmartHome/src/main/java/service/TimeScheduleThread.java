package service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import domain.Scenario;

public class TimeScheduleThread extends Thread {
	
	private long sleepTime;
	private long waitTime;
	
	private Scenario scenario;
	
	public void run() {
		while(true) {
			if(!(scenario.getDays().contains(LocalDateTime.now().getDayOfWeek()))) {
				try {
					TimeScheduleThread.sleep((24*3600*1000) - getMilliseconds(LocalTime.now()));
				} catch (InterruptedException e) {
					this.interrupt();
					break;
				}
			}
			else {
				try {
					TimeScheduleThread.sleep(sleepTime);
				} catch (InterruptedException e) {
					this.interrupt();
					break;
				}
				scenario.activateScenario();
				try {
					TimeScheduleThread.sleep(waitTime);					
				} catch (InterruptedException e) {
					this.interrupt();
					break;
				}
			}
		}
	}
	
	public void init(LocalTime startTime) {
		sleepTime = getMilliseconds(startTime) - getMilliseconds(LocalTime.now());
		waitTime = (24*3600*1000) - sleepTime;
		
	}
	
	public long getMilliseconds (LocalTime time) {
		return (long)(time.getHour()*3600 + time.getMinute()*60) * 1000;
	}

	public Scenario getScenario() {
		return scenario;
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}
	

	
}
