package domain;

import domain.Sensor.SensorCategory;

public class Shader extends Obj {
	
	private Window window; 
	private Sensor sensor;

	public Shader(String name, String referencedRoomID, Window window, Room room) {
		super(name, ObjType.SHADER, room);
		sensor = new Sensor("sensorOf: ", SensorCategory.SHADER, room);
		AutomaticControl.getInstance().addSensor(sensor);
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
