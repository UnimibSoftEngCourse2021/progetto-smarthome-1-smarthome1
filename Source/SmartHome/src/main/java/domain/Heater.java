package domain;

import domain.Sensor.SensorCategory;

public class Heater extends Object {

	private Sensor sensor;
	
	public Heater(String name, Room room) {
		super(name, ObjectType.HEATER, room);
		sensor = new Sensor("sensorOf: ", SensorCategory.HEATER, room);
		AutomaticControl.getInstance().addSensor(sensor);
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}
}