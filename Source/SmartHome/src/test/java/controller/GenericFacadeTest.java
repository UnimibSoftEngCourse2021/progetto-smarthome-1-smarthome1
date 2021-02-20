package controller;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import application.GenericFacade;
import domain.Alarm;
import domain.AutomaticControl;
import domain.Config;
import domain.ConflictHandler;
import domain.DataDescription;
import domain.Heater;
import domain.Room;
import domain.Sensor;
import domain.Sensor.SensorCategory;

public class GenericFacadeTest {

	@Test
	public void manageWriteOnHCFileTest() {
		DataDescription dd = new DataDescription();
		GenericFacade gf = new GenericFacade(dd);
		Config.getInstance().setDataDescription(dd);
		gf.manageWriteOnHCFile("Alarm", "alarm1");
		gf.manageWriteOnHCFile("startDayMode", "01:30");
		gf.manageWriteOnHCFile("stopDayMode", "10:00");
		gf.manageWriteOnHCFile("room", "room1");
		gf.manageWriteOnHCFile("floor", "1");
		gf.manageWriteOnHCFile("light", "light1");
		gf.manageWriteOnHCFile("door", "door1");
		gf.manageWriteOnHCFile("code", "1234");
		gf.manageWriteOnHCFile("heater", "heater2");
		gf.manageWriteOnHCFile("window", "window1");
		gf.manageWriteOnHCFile("heater", "heater1");
		gf.manageWriteOnHCFile("air", "air1");
		gf.manageWriteOnHCFile("movement", "movement1");
		gf.manageWriteOnHCFile("temperature", "temp1");
		gf.manageWriteOnHCFile("roomNameSensor", "room1");
		gf.manageWriteOnHCFile("heaterID", "HEATER_ROOM1_1_0");
		gf.manageWriteOnHCFile("heaterID", "HEATER_ROOM1_1_1");
		gf.manageWriteOnHCFile("heaterID", "");
		gf.manageWriteOnHCFile("end", null);
		assertEquals("alarm1", Alarm.getInstance().getName());
		Room room = Config.getInstance().getRooms().get(0);
		assertEquals("room1", room.getRoomID());
		assertEquals((short)1, room.getFloor());
		assertEquals(90, AutomaticControl.getInstance().getStartDayMode());
		assertEquals(600, AutomaticControl.getInstance().getStopDayMode());
		assertEquals("light1", ConflictHandler.getInstance().getObjs().get(1).getName());
		Sensor sTemp = room.getSensors(SensorCategory.TEMPERATURE).get(0);
		Object[] heaters = sTemp.getPublisherList().toArray();
		String[] heatersIDs = new String[2];
		Heater heater = (Heater)heaters[0];
		for(int i = 0; i < heaters.length; i++) {
			heater = (Heater)heaters[i];
			heatersIDs[i] = heater.getObjID();
		}
		String[] ioHeatersIDs = {"HEATER_ROOM1_1_0", "HEATER_ROOM1_1_1"};
		assertEquals("temp1", sTemp.getName());
		assertEquals("room1", sTemp.getRoom().getRoomID());
		assertArrayEquals(ioHeatersIDs, heatersIDs);
		assertNotNull(dd.getFileHC());
		
		Alarm.getInstance().clean();
		
	}
	
	@Test
	public void manageWriteOnHSCFileTest() {
		DataDescription dd = new DataDescription();
		GenericFacade gf = new GenericFacade(dd);
		Config.getInstance().setDataDescription(dd);
		double[] monday = {18.00, 21.00, 0.00, 0.00, 1.00, 1.00, 1.00, 0.00, 0.00, 1.00, 0.00, 1.00, 1.00, 1.00, 1.00, 0.00, 0.00, 0.00, 1.00, 1.00, 1.00, 1.00, 1.00, 0.00, 0.00, 0.00};
		double[] sunday = {18.00, 21.00, 0.00, 0.00, 1.00, 1.00, 1.00, 0.00, 0.00, 1.00, 0.00, 1.00, 1.00, 1.00, 1.00, 0.00, 0.00, 0.00, 1.00, 1.00, 1.00, 1.00, 1.00, 0.00, 0.00, 0.00};
		double[] friday = {18.00, 21.00, 0.00, 0.00, 1.00, 1.00, 1.00, 0.00, 0.00, 1.00, 0.00, 1.00, 1.00, 1.00, 1.00, 0.00, 0.00, 0.00, 1.00, 1.00, 1.00, 1.00, 1.00, 0.00, 0.00, 0.00};
		double[]  other = {18.00, 21.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00};
		gf.manageWriteOnHSCFile("monday", monday);
		gf.manageWriteOnHSCFile("Tuesday", other);
		gf.manageWriteOnHSCFile("wednesday", other);
		gf.manageWriteOnHSCFile("thursday", other);
		gf.manageWriteOnHSCFile("saturday", other);
		gf.manageWriteOnHSCFile("sunday", sunday);
		gf.manageWriteOnHSCFile("friday", friday);
		gf.manageWriteOnHSCFile("end", null);
		double[][] expectedMatrix = {
				{0.00, 0.00, 1.00, 1.00, 1.00, 0.00, 0.00, 1.00, 0.00, 1.00, 1.00, 1.00, 1.00, 0.00, 0.00, 0.00, 1.00, 1.00, 1.00, 1.00, 1.00, 0.00, 0.00, 0.00, 18.00, 21.00},
				{0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 18.00, 21.00},
				{0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 18.00, 21.00},
				{0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 18.00, 21.00},
				{0.00, 0.00, 1.00, 1.00, 1.00, 0.00, 0.00, 1.00, 0.00, 1.00, 1.00, 1.00, 1.00, 0.00, 0.00, 0.00, 1.00, 1.00, 1.00, 1.00, 1.00, 0.00, 0.00, 0.00, 18.00, 21.00},
				{0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 18.00, 21.00},
				{0.00, 0.00, 1.00, 1.00, 1.00, 0.00, 0.00, 1.00, 0.00, 1.00, 1.00, 1.00, 1.00, 0.00, 0.00, 0.00, 1.00, 1.00, 1.00, 1.00, 1.00, 0.00, 0.00, 0.00, 18.00, 21.00}
				
		};
		
		assertArrayEquals(expectedMatrix, AutomaticControl.getInstance().getUserMatrix());
		
	}
		
	@After
	public void clean() {
		Config.getInstance().clean();
		AutomaticControl.getInstance().clean();
		ConflictHandler.getInstance().clean();
	}

}
