package domain;

import domain.Object.ObjectType;
import domain.Sensor.SensorCategory;

public class Light extends Object {

	private Sensor sensor;
	
	public Light(String name, String referencedRoomID) {
		super(name, referencedRoomID, ObjectType.LIGHT);
		sensor = new Sensor("sensorOf: ", referencedRoomID, SensorCategory.LIGHT);
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}
}