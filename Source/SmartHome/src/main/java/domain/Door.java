package domain;

import domain.Sensor.SensorCategory;

public class Door extends Obj {

	private String code;
	private Sensor sensor;

	public Door(String name, Room room) {
		super(name, ObjType.DOOR, room);
		sensor = new Sensor("sensorOf: ", SensorCategory.DOOR, room);
		AutomaticControl.getInstance().addSensor(sensor);
		if(Alarm.isCreated())
			Alarm.getInstance().setSensor(sensor);
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