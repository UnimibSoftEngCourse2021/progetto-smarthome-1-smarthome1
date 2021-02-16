package view;

import java.util.List;
import java.util.Scanner;
import application.DataFaçade;

public class ScenarioView {
	
	DataFaçade dataFaçade;
	
	List<List<String[]>> rooms;
	List<String[]> roomObjects; // primo valore è nome stanza
	
	String hour;
	String min;
	String[] roomName;
	
	Scanner input = new Scanner(System.in);
	
	public void addRoom() {
			roomObjects.clear();
			System.out.println("Inserire il nome della stanza in cui eseguire le azioni");
			roomName[0] = input.nextLine();
			roomName[1] = null;
			roomObjects.add(roomName);
	}
	
	public void setAction() {
		do {
			System.out.println("Selezionare l'azione da effettuare sulla stanza: ");
			System.out.println("create: aggiungerne una nuova");
			System.out.println("modify: modificarne una esistente");
			System.out.println("delete: eliminarne una esistente");
			switch(input.nextLine()) {
			case "create":
				//selezionare la stanza con cui lavorare
				
				do {
					System.out.println("Lista degli oggetti contenuti nella stanza: ");
					List<String[]> objectsInRoom = dataFaçade.getObjectsInRoom(roomName[0]);
					for(String[] objectInRoom: objectsInRoom) {
						System.out.println(objectInRoom[0]);
					}
					System.out.println("Selezionare l'oggetto");
					String objectName = input.nextLine();
					// controllo che appartenga alla lista di oggetti stampata prima
					for(String[] objectInRoom: objectsInRoom) {
						if(!objectInRoom[0].contains(objectName)) {
							System.out.println("Input non valido, riprovare");
							setAction();
							break;
						}
					}
					System.out.println("Inserire lo stato desiderato per l'azione");
					String newState = input.nextLine();	
					String[] object = {objectName, newState};
					roomObjects.add(object);
					System.out.print("inserire altri oggetti? (s/n)");
				} while(input.nextLine() != "n");
				rooms.add(roomObjects);
				break;
			case "modify":
				do {
					System.out.println("Lista degli oggetti interessati dallo scenario: ");
					List<String[]> scenarioObjects = dataFaçade.getObjectsInScenario();
					for(String[] scenarioObject: scenarioObjects) {
						System.out.println(scenarioObject[0]);
					}
					System.out.println("Inserire il nome dell'oggetto da modificare");
					String objectName = input.nextLine();					
					// controllo che appartenga alla lista di oggetti stampata prima
					for(String[] scenarioObject: scenarioObjects) {
						if(!scenarioObject[0].contains(objectName)) {
							System.out.println("Input non valido, riprovare");
							setAction();
							break;
						}
					}
					System.out.println("Inserire lo stato desiderato per l'azione");
					String newState = input.nextLine();						
					for(String[] oldObject: roomObjects) {
						if(objectName.equals(oldObject[0]))
							oldObject[1] = newState;
					}																		
					System.out.print("modificare altri oggetti? (s/n)");
				} while(input.nextLine() != "n");
				break;
			case "delete":
				do {
					System.out.println("Lista degli oggetti contenuti nella stanza: ");
					// chiama metodo di DataFaçade e stampa gli oggetti
					List<String[]> scenarioObjects = dataFaçade.getObjectsInScenario();
					for(String[] scenarioObject: scenarioObjects) {
						System.out.println(scenarioObject[0]);
					}
					System.out.println("Inserire il nome dell'oggetto da eliminare");
					String objectName = input.nextLine();					
					// controllo che appartenga alla lista di oggetti stampata prima
					for(String[] scenarioObject: scenarioObjects) {
						if(!scenarioObject[0].contains(objectName)) {
							System.out.println("Input non valido, riprovare");
							setAction();
							break;
						}
					}
					for(String[] oldObject: roomObjects) {
						if(objectName.equals(oldObject[0]))
							roomObjects.remove(oldObject);
					}																		
					System.out.print("eliminare altri oggetti? (s/n)");
				} while(input.nextLine() != "n");
				break;
			}
			System.out.println("Effettuare altre operazioni?");
		} while(input.nextLine() != "n");
	}
	
	public void setTime() {
		System.out.println("Inserire l'ora di inizio: (hour)");
		hour = input.nextLine();
		System.out.println("Inserire il minuto di inizio: (min)");
		min = input.nextLine();		
	}
	
	public void selectOperation() {
		System.out.println("Selezionare l'operazione desiderata: (scrivere la parola chiave tra parentesi)");
		System.out.println("attivazione scenario (activate)");
		System.out.println("creazione nuovo scenario (create)");
		System.out.println("modifica scenario (modify)");
		System.out.println("eliminazione scenario (delete)");
		switch(input.nextLine()) {
		case "activate":
			activateScenario();
			break;
		case "create":
			createScenario();
		case "modify":
			modifyScenario();
			break;
		case "delete":
			deleteScenario();
			break;
		}
	}
	
	public void activateScenario() {
		System.out.println("Lista degli scenari: ");
		List<String[]> scenarios = dataFaçade.getScenarios();
		for(String[] scenario: scenarios) {
			System.out.println(scenario[0]);
		}
		System.out.println("Inserire il nome dello scenario da attivare: ");
		// ScenarioFaçade.manageActivateScenario(input.nextLine());
	}
	
	public void createScenario() {
		//restituire la lista degli scenari presenti
		System.out.println("Inserire il nome dello scenario da creare");
		String name = input.nextLine();
		// controllare che lo scenario selezionato esista
		System.out.println("Si desidera impostare un orario di inizio? (s/n)");
		if(input.nextLine().equals("s"))
			setTime();
		do {
			addRoom();
			setAction();
			System.out.print("inserire altre stanze? (s/n)");
		} while(input.nextLine() != "n");
		// invia nome scenario + lista rooms al controller
	}
	
	/*
	 * aggiunta azioni nuova stanza
	 * cambiare orario
	 * aggiunta/modifica/elimiazione azioni stanza esistente
	 */
	public void modifyScenario() {
		System.out.println("Inserire il nome dello scenario da modificare");
		String name = input.nextLine();
		System.out.println("Selezionare l'operazione da effettuare: ");
		System.out.println("add: aggiunta nuove stanze");
		System.out.println("change: cambio orario");
		System.out.println("modify: modifica stanza esistente");
		switch(input.nextLine()) {
		case "add":
			addRoom();
			break;
		case "change":
			System.out.println("Rimuovere la programmazione oraria? (s/n)");
			if(input.nextLine().equals("s"))
				setTime();
			else {
				hour = null;
				min =  null;
			}
			// inviare dati a controller
			break;
		case "modify":
			roomObjects.clear();
			System.out.println("Lista delle stanze");
			List<String[]> roomList = dataFaçade.getRooms();
			for(String[] room: roomList) {
				System.out.println(room);
			}
			System.out.println("Inserire il nome della stanza in cui eseguire le azioni");
			roomName[0] = input.nextLine();
			for(String[] room: roomList) {
				if(!room[0].contains(roomName[0])) {
					System.out.println("Input non valido, riprovare");
					modifyScenario();
					break;
				}
			}
			roomName[1] = null;
			roomObjects.add(roomName);
			setAction();			
		}
	}
	
	public void deleteScenario() {
		//restituire la lista degli scenari presenti
		System.out.println("Lista degli scenari");
		List<String[]> scenariosList = dataFaçade.getScenarios();
		for(String[] scenario: scenariosList) {
			System.out.println(scenario);
		}
		System.out.println("Inserire il nome dello scenario da eliminare");
		String scenarioName = input.nextLine();
		for(String[] scenario: scenariosList) {
			if(!scenario[0].contains(scenarioName)) {
				System.out.println("Input non valido, riprovare");
				modifyScenario();
				break;
			}
		}
		// eliminare l'occorrenza con lo stesso nome nella lista
	}
}