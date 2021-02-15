package domain;

import domain.Object.ObjectType;
import domain.Sensor.SensorCategory;

public class Light extends Object {

	private Sensor sensor;
	
	public Light(String name, Room room) {
		super(name, ObjectType.LIGHT, room);
		sensor = new Sensor("sensorOf: ", SensorCategory.LIGHT, room);
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}
}