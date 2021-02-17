package domain;

import java.util.List;

public class ScenariosHandler {
	
	private List<Scenario> scenarios;
	private List<Object> objects;
	
	//definire un costruttore, deve esistere una singola istanza di questa classe ma non e necessario usare il singleton
	
	public List<Object> getObjects(){
		return objects;
	}
	
	public void addObject(Object object) {
		objects.add(object);
	}
	
	public void createScenario(String scenarioID, int dateTime, List<List<String[]>> rooms) {
		
	}

	public void modifyScenario() {
		
	}
	
	public void deleteScenario() {
		
	}
	
	public void activateScenario() {
		
	}
	
}
