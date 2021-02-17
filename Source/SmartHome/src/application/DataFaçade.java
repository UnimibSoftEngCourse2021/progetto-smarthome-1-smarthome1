package application;

import java.util.ArrayList;
import java.util.List;
import domain.Room;
import domain.Scenario;
import domain.Config;
import domain.Object;
import domain.ScenariosHandler;

public class DataFaçade {
	
	Room room;

	public List<String[]> getObjectsInRoom(String roomName) {
		List<Object> objects = new ArrayList<Object>();
		objects = room.getObjectList();
		List<String[]> objectsInRoom = new ArrayList<String[]>();;
		String name;
		String id;
		for(Object object: objects) {
			name = object.getName();
			id = object.getObjectID();
			String[] array = {name, id};
			objectsInRoom.add(array);
		}		
		return objectsInRoom;
	}

	public List<String[]> getAllObjects() {
		List<Object> objects = new ArrayList<Object>();
		objects = scenariosHandler.getObjects();
		List<String[]> allObjects = new ArrayList<String[]>();;
		String name;
		String id;
		for(Object object: objects) {
			name = object.getName();
			id = object.getObjectID();
			String[] array = {name, id};
			allObjects.add(array);
		}		
		return allObjects;
	}

	public List<String[]> getObjectsInScenario(String scenarioName) {		
		List<Object> objectsInScenario = new ArrayList<Object>();
		objectsInScenario = scenarioHandler.getObjectsInScenario(scenarioName);
		List<String[]> allObjectsInScenario = new ArrayList<String[]>();
		String name;
		String id;
		for(Object object: objectsInScenario) {
			name = object.getName();
			id = object.getObjectID();
			String[] array = {name, id};
			allObjectsInScenario.add(array);
		}
		return allObjectsInScenario;
	}

	public List<String> getScenarios() {
		List<Scenario> scenarios = new ArrayList<Scenario>();
		scenarios = scenarioHandler.getScenarios();
		List<String> allScenarios = new ArrayList<String>();;
		for(Scenario scenario: scenarios) {
			allScenarios.add(scenario.getNameID());
		}
		return allScenarios;
	}

	public List<String> getRooms() {
		// cicla sulle stanze
		List<Room> rooms = new ArrayList<Room>();
		rooms = Config.getInstance().getRooms();
		List<String> allRooms = new ArrayList<String>();;
		for(Room room: rooms) {
			allRooms.add(room.getRoomID());
		}
		return allRooms;
	}

	public List<String[]> getObjectsInRoomInScenario(String roomName, String scenarioName) {
		List<String[]> allObjectsInScenario = new ArrayList<String[]>();
		allObjectsInScenario = getObjectsInScenario(scenarioName);
		List<String[]> objectsInRoom = new ArrayList<String[]>();
		objectsInRoom = getObjectsInRoom(roomName); 
		List<String[]> allObjectsInRoomInScenario = new ArrayList<String[]>();;
		for(String[] object: objectsInRoom) {
			if(allObjectsInScenario.contains(object))
				allObjectsInRoomInScenario.add(object);
		}
 		return allObjectsInRoomInScenario;
	}
	
}
