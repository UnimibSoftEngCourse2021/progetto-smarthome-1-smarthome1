package controller;

import static org.junit.Assert.*;

import org.junit.Test;

import application.HandlerFacade;
import domain.Alarm;
import domain.ConflictHandler;
import domain.Object.ObjectType;
import domain.Room;

public class HandlerFaçadeTest {
	
	@Test
	public void isHomeTest() {
		ConflictHandler.getInstance().setAtHome(false);
		Room room = new Room("room1", (short)1);		
		room.instantiateObject(ObjectType.DOOR, "door1");
		room.setDoorCode("code");
		HandlerFacade hf = new HandlerFacade();
		String state = "home";
		String doorID = "door1";
		Alarm.getInstance("alarm1");
		hf.manageHomeFlagSettings(state, "code", doorID);
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
		
	}

	@Test
	public void manageWrongCodeTest() {
		Room room = new Room("room1", (short)1);		
		room.instantiateObject(ObjectType.DOOR, "door1");
		room.setDoorCode("code");
		HandlerFacade hf = new HandlerFacade();
		hf.manageWrongCode();
	}
	
}
