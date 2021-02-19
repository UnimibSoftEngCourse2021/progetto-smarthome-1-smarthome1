package service;

import domain.Alarm;
import domain.Door;
import domain.Heater;
import domain.Light;
import domain.Obj;
import domain.Shader;
import domain.Window;

public class ObjCommunicationSystem {
	
	private SensorCommunicationSystem scs; 
	
	public ObjCommunicationSystem() {
		scs = new SensorCommunicationSystem();
	}
	
	public void triggerAction(Obj obj, boolean actionValue) {
		switch(obj.getObjType()) {
		case ALARM:
			Alarm alarm = (Alarm)obj;
			scs.notifySensor(alarm.getSensor(), actionValue);
			break;
		case DOOR:
			Door door = (Door)obj;
			scs.notifySensor(door.getSensor(), actionValue);
			break;
		case HEATER:
			Heater heater = (Heater)obj;
			scs.notifySensor(heater.getSensor(), actionValue);
			break;
		case LIGHT:
			Light light = (Light)obj;
			scs.notifySensor(light.getSensor(), actionValue);
			break;
		case SHADER:
			Shader shader = (Shader)obj;
			scs.notifySensor(shader.getSensor(), actionValue);
			break;
		case WINDOW:
			Window window = (Window)obj;
			scs.notifySensor(window.getSensor(), actionValue);
			break;
		default:
			break;
		
		}
	}

}