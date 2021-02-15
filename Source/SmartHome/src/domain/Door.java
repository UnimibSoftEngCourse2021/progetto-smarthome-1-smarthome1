package domain;

import domain.Object.ObjectType;
import domain.Sensor.SensorCategory;

public class Door extends Object {

	private String code;
	private Sensor sensor;

	public Door(String name, String objectID, String referencedRoomID) {
		super(name, objectID, referencedRoomID, ObjectType.DOOR);
		sensor = new Sensor("sensorOf: " + objectID, SensorCategory.DOOR, null);
		Alarm.setSensors(sensor);
	}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	public Sensor getSensor() {
		return sensor;
	}
	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

}