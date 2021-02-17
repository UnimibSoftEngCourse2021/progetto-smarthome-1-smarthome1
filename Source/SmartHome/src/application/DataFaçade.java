package application;

import java.util.List;
import domain.Room;
import domain.Scenario;
import domain.Config;
import domain.Object;
import domain.ScenariosHandler;

public class DataFaçade {
	
	Room room;

	public List<String[]> getObjectsInRoom(String roomName) {
		List<Object> objects = room.getObjectList();
		List<String[]> objectsInRoom;
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
		List<Object> objects = scenariosHandler.getObjects();
		List<String[]> allObjects;
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
		List<Object> objectsInScenario = scenarioHandler.getObjectsInScenario(scenarioName);
		List<String[]> allObjectsInScenario;
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
		List<Scenario> scenarios = scenarioHandler.getScenarios();
		List<String> allScenarios;
		for(Scenario scenario: scenarios) {
			allScenarios.add(scenario.getNameID());
		}
		return allScenarios;
	}

	public List<String> getRooms() {
		// cicla sulle stanze
		List<Room> rooms = Config.getInstance().getRooms();
		List<String> allRooms;
		for(Room room: rooms) {
			allRooms.add(room.getRoomID());
		}
		return allRooms;
	}

	public List<String[]> getObjectsInRoomInScenario(String roomName, String scenarioName) {
		List<String[]> allObjectsInScenario = getObjectsInScenario(scenarioName);
		List<String[]> objectsInRoom = getObjectsInRoom(roomName); 
		List<String[]> allObjectsInRoomInScenario = null;
		for(String[] object: objectsInRoom) {
			if(allObjectsInScenario.contains(object))
				allObjectsInRoomInScenario.add(object);
		}
 		return allObjectsInRoomInScenario;
	}
	
}
