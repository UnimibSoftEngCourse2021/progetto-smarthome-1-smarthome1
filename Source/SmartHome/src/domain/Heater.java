package domain;

public class Heater extends Object {



	private Sensor sensor;
	
	public Heater(String name, String referencedRoomID) {
		super(name, referencedRoomID, ObjectType.HEATER);
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}
}