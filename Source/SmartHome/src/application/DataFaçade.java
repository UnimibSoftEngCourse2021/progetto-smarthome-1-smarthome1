package application;

import java.util.ArrayList;
import java.util.List;
import domain.Room;
import domain.Scenario;
import domain.Config;
import domain.ConflictHandler;
import domain.Object;
import domain.ScenariosHandler;

public class DataFašade {
	
	private Room room;
	private ScenariosHandler scenariosHandler;
	

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
		List<String> objectsIDsInScenario = scenariosHandler.getObjectsInScenario(scenarioName);
		List<String[]> objectsInScenario = new ArrayList<String[]>();
		String name = "";
		String id = "";
		List<Object> objects = ConflictHandler.getInstance().getObjects();
		for(String objectID: objectsIDsInScenario) {
			for(Object object: objects) {
				if(object.getObjectID().equals(objectID)) {
					name = object.getName();
					id = object.getObjectID();
					break;
				}
			}
			String[] couple = {name, id};
			objectsInScenario.add(couple);
		}
		return objectsInScenario;
	}

	public List<String> getScenarios() {
		List<Scenario> scenarios = new ArrayList<Scenario>();
		scenarios = scenariosHandler.getScenarios();
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
