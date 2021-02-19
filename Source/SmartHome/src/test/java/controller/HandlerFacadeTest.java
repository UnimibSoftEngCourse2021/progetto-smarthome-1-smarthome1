package controller;

import static org.junit.Assert.*;

import org.junit.Test;
import application.HandlerFacade;
import domain.Alarm;
import domain.ConflictHandler;
import domain.Obj.ObjType;
import domain.Room;


public class HandlerFacadeTest {
	
	@Test
	public void isHomeTest() {
		ConflictHandler.getInstance().setAtHome(false);
		HandlerFacade hf = new HandlerFacade();
		Alarm.getInstance("alarm1");
		String code = "1234";
		String state = "home";
		
		Room room = new Room("room1", (short)1);		
		room.instantiateObj(ObjType.DOOR, "door1");
		room.setDoorCode("1234");
		String doorID = room.getObjs(ObjType.DOOR).get(0).getObjID();
		
		hf.manageHomeFlagSettings(state, code, doorID);
		assertEquals(true, ConflictHandler.getInstance().isAtHome());
		assertEquals(false, Alarm.getInstance().isArmed());
	}
	
	@Test
	public void isAwayTest() {
		/*
		 * forse non serve creare stanza e finestra 
		 */
		ConflictHandler.getInstance().setAtHome(true);
		Room room = new Room("room2", (short)1);		
		room.instantiateObj(ObjType.DOOR, "door2");		
		HandlerFacade hf = new HandlerFacade();
		String state = "away";
		String doorID = "door2";		
		hf.manageHomeFlagSettings(state, "code", doorID);
		Alarm.getInstance("alarm2");
		assertEquals(false, ConflictHandler.getInstance().isAtHome());
		assertEquals(true, Alarm.getInstance().isArmed());
		
	}
	
	@Test
	public void manageManualActionTest() {
		Room room = new Room("room3", (short)1);
		room.instantiateObj(ObjType.LIGHT, "light1");
		HandlerFacade hf = new HandlerFacade();
		hf.manageManualAction(room.getObjs(ObjType.LIGHT).get(0).getObjID());
		assertEquals(true, room.getObjs(ObjType.LIGHT).get(0).isActive());
		
		room.getObjs(ObjType.LIGHT).get(0).setActive(true);
		hf.manageManualAction(room.getObjs(ObjType.LIGHT).get(0).getObjID());
		assertEquals(false, room.getObjs(ObjType.LIGHT).get(0).isActive());
	}

	/*@Test
	public void manageWrongCodeTest() {
		DataDescription dd = new DataDescription();
		ScenariosHandler sh = new ScenariosHandler();
		Config.getInstance().setDataDescription(dd);
		//inizializzazione oggetti facade
		DataFacade df = new DataFacade(sh);
		GenericFacade gf = new GenericFacade(dd);
		ScenarioFacade sf = new ScenarioFacade(sh);
		HandlerFacade hf = new HandlerFacade();
		Menu menu = new Menu(df, gf, sf, hf);
		ConflictHandler.getInstance().setAtHome(false);
		//HandlerFacade hf = new HandlerFacade();
		//ConflictHandler.getInstance().setHandlerFacade(hf);
		Alarm.getInstance("alarm1");
		String code = "1234";
		String state = "home";
		String wrongCode = "4321";
		Room room = new Room("room1", (short)1);		
		room.instantiateObj(ObjType.DOOR, "door1");
		Config.getInstance().getRooms().add(room);
		room.setDoorCode(code);
		String doorID = room.getObjs(ObjType.DOOR).get(0).getObjID();
		
		
		hf.manageHomeFlagSettings(state, wrongCode, doorID);
		assertEquals(true, ConflictHandler.getInstance().isAtHome());
		
	}*/
	
	
}
