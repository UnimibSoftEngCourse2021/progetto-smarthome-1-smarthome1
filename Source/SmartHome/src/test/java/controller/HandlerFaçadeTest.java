package controller;

import static org.junit.Assert.*;

import org.junit.Test;

import application.HandlerFacade;
import domain.ConflictHandler;
import domain.Object.ObjectType;
import domain.Room;

public class HandlerFaçadeTest {
	
	@Test
	public void isHomeTest() {
		Room room = new Room("room1", (short)1);		
		room.instantiateObject(ObjectType.DOOR, "door1");
		room.setDoorCode("code");
		HandlerFacade hf = new HandlerFacade();
		String state = "home";
		String doorID = "door1";		
		hf.manageHomeFlagSettings(state, "code", doorID);
		assertEquals(true, ConflictHandler.getInstance().isAtHome());
	}
}
