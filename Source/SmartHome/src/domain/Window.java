package domain;

import domain.Object.ObjectType;
import domain.Sensor.SensorCategory;

public class Window extends Object {

	private Shader shader;
	private Sensor sensor;
	
	public Window(String name, String referencedRoomID) {
		super(name, referencedRoomID, ObjectType.WINDOW);
		sensor = new Sensor("sensorOf: ", referencedRoomID, SensorCategory.WINDOW);
		shader = new Shader("ShaderOf: " + name, referencedRoomID, this);
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