package application;

import java.util.ArrayList;
import java.util.List;

import domain.Config;
import domain.ConflictHandler;
import domain.ScenariosHandler;
import domain.Obj;
import domain.Obj.ObjType;
import domain.Room;
import domain.Scenario;

public class DataFacade {
	
	private ScenariosHandler scenariosHandler;
	
	public DataFacade(ScenariosHandler scenariosHandler) {
		this.scenariosHandler = scenariosHandler;
	}
	
	public List<String[]> getObjsInRoom(String roomName) {
		List<Obj> objs = new ArrayList<>();
		List<String[]> objsInRoom = new ArrayList<>();
		for(Room r: Config.getInstance().getRooms()) {
			if(roomName.equals(r.getRoomID())) {
				objs = r.getObjList();
			}
		}
		String name;
		String id;
		for(Obj obj: objs) {
			name = obj.getName();
			id = obj.getObjID();
			String[] array = {name, id};
			objsInRoom.add(array);
		}		
		return objsInRoom;
	}

	public List<String[]> getAllObjs() {
		List<Obj> objs = ConflictHandler.getInstance().getObjs();
		List<String[]> allObjs = new ArrayList<>();
		String name;
		String id;
		for(Obj obj: objs) {
			name = obj.getName();
			id = obj.getObjID();
			String[] array = {name, id};
			allObjs.add(array);
		}		
		return allObjs;
	}
	
	public List<String> getObjsTypeID(String objType) {
		List<String> allObjs = new ArrayList<>();
		List<Room> rooms = Config.getInstance().getRooms();
		for(Room room: rooms) {
			for(Obj obj: room.getObjs(ObjType.valueOf(objType))) {
				allObjs.add(obj.getObjID());
			}
		}
		return allObjs;
	}

	public List<String[]> getObjsInScenario(String scenarioName) {		
		List<String> objsIDsInScenario = scenariosHandler.getObjsInScenario(scenarioName);
		List<String[]> objsInScenario = new ArrayList<>();
		String name = "";
		String id = "";
		List<Obj> objs = ConflictHandler.getInstance().getObjs();
		for(String objID: objsIDsInScenario) {
			for(Obj obj: objs) {
				if(obj.getObjID().equals(objID)) {
					name = obj.getName();
					id = obj.getObjID();
					break;
				}
			}
			String[] couple = {name, id};
			objsInScenario.add(couple);
		}
		return objsInScenario;
	}

	public List<String> getScenarios() {
		List<String> allScenarios = new ArrayList<>();
		for(Scenario scenario: scenariosHandler.getScenarios()) {
			allScenarios.add(scenario.getNameID());
		}
		return allScenarios;
	}

	public List<String> getRooms() {
		// cicla sulle stanze
		List<Room> rooms =Config.getInstance().getRooms();
		List<String> allRoomsIDs = new ArrayList<>();
		if(rooms != null) {
			for(Room room: rooms) {
				allRoomsIDs.add(room.getRoomID());
			}
		}
		return allRoomsIDs;
	}

	public List<String[]> getObjsInRoomInScenario(String roomName, String scenarioName) {
		List<String[]> allObjsInScenario = getObjsInScenario(scenarioName);
		List<String[]> objsInRoom = getObjsInRoom(roomName); 
		List<String[]> allObjsInRoomInScenario = new ArrayList<>();
		for(String[] obj: objsInRoom) {
			if(!allObjsInScenario.contains(obj))
				allObjsInRoomInScenario.add(obj);
		}
 		return allObjsInRoomInScenario;
	}
	
}
