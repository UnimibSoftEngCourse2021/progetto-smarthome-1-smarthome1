package application;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import domain.Alarm;
import domain.ConflictHandler;
import domain.Door;
import domain.Light;
import domain.Object.ObjectType;
import domain.Room;
import view.IsAtHomeView;
import view.NotifiesView;


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
