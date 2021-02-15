package domain;

public class Heater extends Object {



	private Sensor sensor;
	
	public Heater(String name, String objectID, String referencedRoomID, ObjectType objectType) {
		super(name, objectID, referencedRoomID, ObjectType.HEATER);
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}
}