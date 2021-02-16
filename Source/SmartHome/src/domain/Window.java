package domain;

import domain.Object.ObjectType;
import domain.Sensor.SensorCategory;

public class Window extends Object {

	private Shader shader;
	private Sensor sensor;
	
	public Window(String name, Room room) {
		super(name, ObjectType.WINDOW, room);
		sensor = new Sensor("sensorOf: ", SensorCategory.WINDOW, room);
		shader = new Shader("ShaderOf: " + name, room.getRoomID(), this, room);
		if(Alarm.isCreated())
			Alarm.setSensors(sensor);
	}
	
	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public Shader getShader() {
		return shader;
	}

	public void setShader(Shader shader) {
		this.shader = shader;
	}

}