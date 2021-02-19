package domain;

import domain.Sensor.SensorCategory;

public class Heater extends Obj {

	private Sensor sensor;
	
	public Heater(String name, Room room) {
		super(name, ObjType.HEATER, room);
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