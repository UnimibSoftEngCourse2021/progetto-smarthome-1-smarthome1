package test.java.controller;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import main.java.application.HandlerFaçade;
import main.java.domain.ConflictHandler;
import main.java.domain.Room;
import main.java.domain.Object.ObjectType;

class HandlerFaçadeTest {
	
	@Test
	public void isHomeTest() {
		Room room = new Room("room1", (short)1);		
		room.instantiateObject(ObjectType.DOOR, "door1");
		room.setDoorCode("code");
		System.out.println(room.getObjectList());
		HandlerFaçade hf = new HandlerFaçade();
		String state = "home";
		String doorID = "door1";		
		hf.manageHomeFlagSettings(state, "code", doorID);
		assertEquals(true, ConflictHandler.getInstance().isAtHome());
	}

	@Test
	public void isAwayTest() {
		
	}

}
