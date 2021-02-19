package controller;

import static org.junit.Assert.*;

import org.junit.Test;

import application.DataFacade;
import application.GenericFacade;
import application.HandlerFacade;
import application.ScenarioFacade;
import domain.Alarm;
import domain.Config;
import domain.ConflictHandler;
import domain.DataDescription;
import domain.Light;
import domain.Object.ObjectType;
import view.Menu;
import domain.Room;
import domain.ScenariosHandler;

public class HandlerFaçadeTest {
	
	@Test
	public void isHomeTest() {
		ConflictHandler.getInstance().setAtHome(false);
		HandlerFacade hf = new HandlerFacade();
		Alarm.getInstance("alarm1");
		String code = "1234";
		String state = "home";
		
		Room room = new Room("room1", (short)1);		
		room.instantiateObject(ObjectType.DOOR, "door1");
		room.setDoorCode("1234");
		String doorID = room.getObjects(ObjectType.DOOR).get(0).getObjectID();
		
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
		room.instantiateObject(ObjectType.DOOR, "door2");		
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
		room.instantiateObject(ObjectType.LIGHT, "light1");
		HandlerFacade hf = new HandlerFacade();
		hf.manageManualAction(room.getObjects(ObjectType.LIGHT).get(0).getObjectID());
		assertEquals(true, room.getObjects(ObjectType.LIGHT).get(0).isActive());
		
		room.getObjects(ObjectType.LIGHT).get(0).setActive(true);
		hf.manageManualAction(room.getObjects(ObjectType.LIGHT).get(0).getObjectID());
		assertEquals(false, room.getObjects(ObjectType.LIGHT).get(0).isActive());
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
		room.instantiateObject(ObjectType.DOOR, "door1");
		Config.getInstance().getRooms().add(room);
		room.setDoorCode(code);
		String doorID = room.getObjects(ObjectType.DOOR).get(0).getObjectID();
		
		
		hf.manageHomeFlagSettings(state, wrongCode, doorID);
		assertEquals(true, ConflictHandler.getInstance().isAtHome());
		
	}*/
	
	
}
