package controller;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import application.DataFacade;
import application.ScenarioFacade;
import domain.Obj.ObjType;
import domain.AutomaticControl;
import domain.Config;
import domain.ConflictHandler;
import domain.Room;
import domain.ScenariosHandler;

public class DataFacadeTest {
	
	@Before
	public void init() {
		Room room1 = new Room("room1", (short)1);
		Config.getInstance().setRooms(room1);
		room1.instantiateObj(ObjType.LIGHT, "light1");
		Room room2 = new Room("room2", (short)1);
		Config.getInstance().setRooms(room2);
		room2.instantiateObj(ObjType.LIGHT, "light2");
		room2.instantiateObj(ObjType.HEATER, "heater1");
		room2.instantiateObj(ObjType.LIGHT, "light3");
	}

	@Test
	public void getObjsInRoomTest() {
		ScenariosHandler sh = new ScenariosHandler();
		DataFacade df = new DataFacade(sh);
		//init();
		
		assertEquals("light1", df.getObjsInRoom("room1").get(0)[0]);
	}

	@Test
	public void getAllObjsTest() {
		ScenariosHandler sh = new ScenariosHandler();
		DataFacade df = new DataFacade(sh);
		//init();
		
		assertEquals("light1", df.getAllObjs().get(0)[0]);
		assertEquals("light2", df.getAllObjs().get(1)[0]);
		assertEquals("heater1", df.getAllObjs().get(2)[0]);
	}
	
	@Test
	public void getObjsType() {
		ScenariosHandler sh = new ScenariosHandler();
		DataFacade df = new DataFacade(sh);
		//init();

		assertEquals("LIGHT_ROOM1_1_0", df.getObjsTypeID("LIGHT").get(0));
		assertEquals("LIGHT_ROOM2_1_0", df.getObjsTypeID("LIGHT").get(1));
		assertEquals("LIGHT_ROOM2_1_1", df.getObjsTypeID("LIGHT").get(2));
	}
	
	@Test
	public void getObjsInScenarioTest() {
		ScenariosHandler sh = new ScenariosHandler();
		DataFacade df = new DataFacade(sh);
		//init();
		
		// creazione scenario
		ScenarioFacade sf = new ScenarioFacade(sh);
		List<List<String[]>> rooms = new ArrayList<>();
		List<String[]> roomObjs = new ArrayList<String[]>();
		String[] roomName = {"room1", null};
		roomObjs.add(roomName);
		String[] obj1 = {"LIGHT_ROOM1_1_0", "true"};
		roomObjs.add(obj1);
		String[] obj2 = {"HEATER_ROOM2_1_0", "false"};
		roomObjs.add(obj2);
		rooms.add(roomObjs);		
		sf.manageCreateScenario("scenario1", "null", rooms, null);
		
		assertEquals("light1", df.getObjsInScenario("scenario1").get(0)[0]);
		assertEquals("heater1", df.getObjsInScenario("scenario1").get(1)[0]);
	}
	
	@Test
	public void getScenariosTest() {
		ScenariosHandler sh = new ScenariosHandler();
		DataFacade df = new DataFacade(sh);
		//init();
		
		// creazione scenario
		ScenarioFacade sf = new ScenarioFacade(sh);
		List<List<String[]>> rooms = new ArrayList<>();
		List<String[]> roomObjs = new ArrayList<String[]>();
		String[] roomName = {"room1", null};
		roomObjs.add(roomName);
		String[] obj1 = {"LIGHT_ROOM1_1_0", "true"};
		roomObjs.add(obj1);
		String[] obj2 = {"HEATER_ROOM2_1_0", "false"};
		roomObjs.add(obj2);
		rooms.add(roomObjs);		
		sf.manageCreateScenario("scenario1", "null", rooms, null);
		
		rooms.clear();
		roomObjs.clear();
		String[] roomName2 = {"room2", null};
		roomObjs.add(roomName2);
		String[] obj3 = {"LIGHT_ROOM2_1_0", "true"};
		roomObjs.add(obj3);
		String[] obj4 = {"LIGHT_ROOM2_1_1", "false"};
		roomObjs.add(obj4);
		rooms.add(roomObjs);		
		sf.manageCreateScenario("scenario2", "null", rooms, null);
		
		assertEquals("scenario1", df.getScenarios().get(0));
		assertEquals("scenario2", df.getScenarios().get(1));
	}
	
	@Test
	public void getRoomsTest() {
		ScenariosHandler sh = new ScenariosHandler();
		DataFacade df = new DataFacade(sh);
		//init();
		
		assertEquals("room1", df.getRooms().get(0));
		assertEquals("room2", df.getRooms().get(1));
		
		
	}
	
	@Test
	public void getObjsInRoomInScenario() {
		ScenariosHandler sh = new ScenariosHandler();
		DataFacade df = new DataFacade(sh);
		//init();
		
		// creazione scenario
		ScenarioFacade sf = new ScenarioFacade(sh);
		List<List<String[]>> rooms = new ArrayList<>();
		List<String[]> roomObjs = new ArrayList<String[]>();
		String[] roomName = {"room1", null};
		roomObjs.add(roomName);
		String[] obj1 = {"LIGHT_ROOM1_1_0", "true"};
		roomObjs.add(obj1);
		String[] obj2 = {"HEATER_ROOM2_1_0", "false"};
		roomObjs.add(obj2);
		rooms.add(roomObjs);		
		sf.manageCreateScenario("scenario1", "null", rooms, null);
		
		rooms.clear();
		roomObjs.clear();
		String[] roomName2 = {"room2", null};
		roomObjs.add(roomName2);
		String[] obj3 = {"LIGHT_ROOM2_1_0", "true"};
		roomObjs.add(obj3);
		String[] obj4 = {"LIGHT_ROOM2_1_1", "false"};
		roomObjs.add(obj4);
		rooms.add(roomObjs);		
		sf.manageCreateScenario("scenario2", "null", rooms, null);
		
		assertEquals("light1", df.getObjsInRoomInScenario("room1", "scenario1").get(0)[0]);
		assertEquals("heater1", df.getObjsInRoomInScenario("room2", "scenario1").get(1)[0]);
		assertEquals("light2", df.getObjsInRoomInScenario("room2", "scenario2").get(0)[0]);
		assertEquals("light3", df.getObjsInRoomInScenario("room2", "scenario2").get(2)[0]);
		
	}
	
	@After
	public void clean() {
		Config.getInstance().clean();
		AutomaticControl.getInstance().clean();
		ConflictHandler.getInstance().clean();
	}
}
