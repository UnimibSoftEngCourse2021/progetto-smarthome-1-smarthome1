package domain;

import domain.Object.ObjectType;
import domain.Sensor.SensorCategory;

public class Shader extends Object {
	
	private Window window; 
	private Sensor sensor;

	public Shader(String name, String objectID, String referencedRoomID, Window window) {
		super(name, objectID, referencedRoomID, ObjectType.SHADER);
		sensor = new Sensor("sensorOf: " + objectID, SensorCategory.SHADER, null);
		this.window = window;
	}
	
	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}

}
