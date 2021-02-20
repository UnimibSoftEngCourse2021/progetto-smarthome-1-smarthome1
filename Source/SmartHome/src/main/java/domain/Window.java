package domain;

import domain.Sensor.SensorCategory;

public class Window extends Obj {

	private Shader shader;
	private Sensor sensor;
	
	public Window(String name, Room room) {
		super(name, ObjType.WINDOW, room);
		sensor = new Sensor("sensorOf: ", SensorCategory.WINDOW, room);
		shader = new Shader("ShaderOf: " + name, this, room);
		if(Alarm.isCreated())
			Alarm.getInstance().setSensor(sensor);
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