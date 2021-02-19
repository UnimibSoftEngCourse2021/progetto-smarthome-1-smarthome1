package controller;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import application.DataFacade;
import domain.Obj.ObjType;
import domain.Config;
import domain.Room;
import domain.ScenariosHandler;

public class DataFacadeTest {

	@Test
	public void getObjsInRoomTest() {
		ScenariosHandler sh = new ScenariosHandler();
		DataFacade df = new DataFacade(sh);
		Room room1 = new Room("room1", (short)1);
		Config.getInstance().setRooms(room1);
		room1.instantiateObj(ObjType.LIGHT, "light1");
		
		List<String[]> objs = df.getObjsInRoom("room1");
		assertEquals("light1", objs.get(0)[0]);
	}

	@Test
	public void getAllObjsTest() {
		ScenariosHandler sh = new ScenariosHandler();
		DataFacade df = new DataFacade(sh);
		Room room2 = new Room("room2", (short)1);
		Config.getInstance().setRooms(room2);
		room2.instantiateObj(ObjType.LIGHT, "light2");
		room2.instantiateObj(ObjType.HEATER, "heater1");
		
		Room room3 = new Room("room3", (short)1);
		Config.getInstance().setRooms(room3);
		room3.instantiateObj(ObjType.DOOR, "door1");
		room3.instantiateObj(ObjType.WINDOW, "window1");
		
		for(String obj: objs) {
		System.out.println(obj);
		}
		List<String[]> objs = df.getAllObjs();
		assertEquals("light2", objs.get(1)[0]);
		assertEquals("heater1", objs.get(2)[0]);
		assertEquals("door1", objs.get(3)[0]);
		assertEquals("window1", objs.get(4)[0]);
	}
	
	@Test
	public void getObjsType() {
		ScenariosHandler sh = new ScenariosHandler();
		DataFacade df = new DataFacade(sh);
		Room room4 = new Room("room4", (short)1);
		Config.getInstance().setRooms(room4);
		room4.instantiateObj(ObjType.LIGHT, "light2");
		room4.instantiateObj(ObjType.LIGHT, "light3");
		room4.instantiateObj(ObjType.LIGHT, "light4");
		List<String> objs = df.getObjsTypeID("LIGHT");
		/*for(String obj: objs) {
			System.out.println(obj);
		}*/
		assertEquals("LIGHT_ROOM4_1_0", objs.get(0));
		assertEquals("LIGHT_ROOM4_1_1", objs.get(1));
		assertEquals("LIGHT_ROOM4_1_2", objs.get(2));
	}
}
