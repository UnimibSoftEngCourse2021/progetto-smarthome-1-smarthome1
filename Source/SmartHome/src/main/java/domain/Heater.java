package domain;

public class Heater extends Object {



	private Sensor sensor;
	
	public Heater(String name, Room room) {
		super(name, ObjectType.HEATER, room);
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}
}