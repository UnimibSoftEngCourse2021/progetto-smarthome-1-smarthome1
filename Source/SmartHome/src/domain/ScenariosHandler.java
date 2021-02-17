package domain;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class ScenariosHandler {
	
	private List<Scenario> scenarios;
	private List<Object> objects; //non e' necessario (quindi neanche l'associazione nel modello di prog) tanto lo scenario conosce gli objectID che poi passa al ConflictHandler
	//cancellare quindi anceh getter e setter per objects
	
	//definire un costruttore, deve esistere una singola istanza di questa classe ma non e necessario usare il singleton
	
	public List<Object> getObjects(){
		return objects;
	}
	
	public void addObject(Object object) {
		objects.add(object);
	}
	
	//non so in che modo arriva la lista dei giorni della settimana
	public void createScenario(String nameID, int dateTime, List<DayOfWeek> days, List<List<String[]>> rooms) {
		List<String> objectIDs = new ArrayList<String>();
		List<Boolean> actions = new ArrayList<Boolean>();
		List<String> roomsIDs = new ArrayList<String>();
		for(List<String[]> list: rooms) {
			for(String[] couple: list) {
				if(list.indexOf(couple) == 0) 
					roomsIDs.add(couple[0]);
				else {
					objectIDs.add(couple[0]);
					roomsIDs.add(couple[1]);
				}
			}
		}
		Scenario scenario = new Scenario(nameID,null /*convertire dateTime in LOcalDateTime*/, objectIDs, actions, days, roomsIDs);
		addScenario(scenario);
		
	}

	public void modifyScenario(String nameID) {
		
	}
	
	public void deleteScenario(String nameID) {
		
	}
	
	public void activateScenario(String nameID) {
		
	}

	public List<Scenario> getScenarios() {
		return scenarios;
	}

	public void addScenario(Scenario scenario) {
		scenarios.add(scenario);
	}

	public void setObjects(List<Object> objects) {
		this.objects = objects;
	}
	
}
