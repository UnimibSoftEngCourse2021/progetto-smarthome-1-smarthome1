package service;

import domain.Alarm;
import domain.Door;
import domain.Heater;
import domain.Light;
import domain.Object;
import domain.Shader;
import domain.Window;

public class ObjectCommunicationSystem {
	
	private SensorCommunicationSystem scs; 
	
	public ObjectCommunicationSystem() {
		scs = new SensorCommunicationSystem();
	}
	
	public void triggerAction(Object object, boolean actionValue) {
		switch(object.getObjectType()) {
		case ALARM:
			Alarm alarm = (Alarm)object;
			scs.notifySensor(alarm.getSensor(), actionValue);
			break;
		case DOOR:
			Door door = (Door)object;
			scs.notifySensor(door.getSensor(), actionValue);
			break;
		case HEATER:
			Heater heater = (Heater)object;
			scs.notifySensor(heater.getSensor(), actionValue);
			break;
		case LIGHT:
			Light light = (Light)object;
			scs.notifySensor(light.getSensor(), actionValue);
			break;
		case SHADER:
			Shader shader = (Shader)object;
			scs.notifySensor(shader.getSensor(), actionValue);
			break;
		case WINDOW:
			Window window = (Window)object;
			scs.notifySensor(window.getSensor(), actionValue);
			break;
		default:
			break;
		
		}
	}

}