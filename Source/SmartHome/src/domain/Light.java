package domain;

import domain.Object.ObjectType;
import domain.Sensor.SensorCategory;

public class Light extends Object {

	private Sensor sensor;
	
	public Light(String name, String objectID, String referencedRoomID) {
		super(name, objectID, referencedRoomID, ObjectType.LIGHT);
		sensor = new Sensor("sensorOf: " + objectID, SensorCategory.LIGHT, null);
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}
}