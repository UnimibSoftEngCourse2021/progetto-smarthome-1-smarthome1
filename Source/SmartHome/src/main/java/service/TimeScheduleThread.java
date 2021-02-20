package service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import domain.Scenario;

public class TimeScheduleThread extends Thread {
	
	private long sleepTime;
	private long waitTime;
	
	private Scenario scenario;
	
	@Override
	public void run() {
		while(true) {
			if(!(scenario.getDays().contains(LocalDateTime.now().getDayOfWeek()))) {
				try {
					Thread.sleep((24*3600*1000) - getMilliseconds(LocalTime.now()));
				} catch (InterruptedException e) {
					this.interrupt();
					break;
				}
			}
			else {
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					this.interrupt();
					break;
				}
				scenario.activateScenario();
				sleepTime = getMilliseconds(scenario.getStartTime());
				try {
					Thread.sleep(waitTime);					
				} catch (InterruptedException e) {
					this.interrupt();
					break;
				}
			}
		}
	}
	
	public void init(LocalTime startTime) {
		waitTime = (24*3600*1000) - getMilliseconds(startTime);
		if(LocalTime.now().compareTo(startTime) < 0)
			sleepTime = getMilliseconds(startTime);
		else
			sleepTime = waitTime;
		
		
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
