package domain;

import domain.Object.ObjectType;
import domain.Sensor.SensorCategory;

public class Window extends Object {

	private Shader shader;
	private Sensor sensor;
	
	public Window(String name, String objectID, String referencedRoomID) {
		super(name, objectID, referencedRoomID, ObjectType.WINDOW);
		sensor = new Sensor("sensorOf: " + objectID, SensorCategory.WINDOW, null);
		shader = new Shader("ShaderOf: " + name, "SHADER_" + objectID,referencedRoomID, this);
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