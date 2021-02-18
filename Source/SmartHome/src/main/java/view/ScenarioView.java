package main.java.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.java.application.DataFaçade;
import main.java.application.ScenarioFaçade;

public class ScenarioView {
	
	//bisogna fare le giuste associazioni (nel main?)
	private DataFaçade dataFaçade;
	private ScenarioFaçade scenarioFaçade;
	
	List<List<String[]>> rooms = new ArrayList<List<String[]>>();;
	List<String[]> roomObjects = new ArrayList<String[]>(); // primo valore è nome stanza
	String[] roomName;
	List<String> days = new ArrayList<String>();	
	String time;
	private boolean createFlag = false; 
	
	Scanner input = new Scanner(System.in);
	
	public ScenarioView(DataFaçade dataFaçade, ScenarioFaçade scenarioFaçade) {
		this.dataFaçade = dataFaçade;
		this.scenarioFaçade = scenarioFaçade;
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
			break;
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
		List<String> scenarios = new ArrayList<String>();
		scenarios = dataFaçade.getScenarios();
		for(String scenario: scenarios) {
			System.out.println(scenario);
		}
		System.out.println("Inserire il nome dello scenario da attivare: ");
		String selected = input.nextLine();
		if(!scenarios.contains(selected)) {
			System.out.println("Input non valido, riprovare");
			activateScenario();
		}
		else		
			scenarioFaçade.manageActivateScenario(selected);
	}
		
	
	public void createScenario() {
		System.out.println("Inserire il nome dello scenario da creare");
		String scenarioName = input.nextLine();
		System.out.println("Si desidera impostare un orario di inizio? (s/n)");
		if(input.nextLine().equals("s"))
			setDateTime();
			addRoom(scenarioName);
		scenarioFaçade.manageCreateScenario(scenarioName, time, rooms, days);
	}
	
	/*
	 * aggiunta azioni nuova stanza
	 * cambiare orario
	 * aggiunta/modifica/elimiazione azioni stanza esistente
	 */
	public void modifyScenario() {
		// lista scenari esistenti
		System.out.println("Inserire il nome dello scenario da modificare");
		String scenarioName = input.nextLine();
		System.out.println("Selezionare l'operazione da effettuare: ");
		System.out.println("add: aggiunta nuove stanze");
		System.out.println("change: cambio orario");
		System.out.println("modify: modifica stanza esistente");
		switch(input.nextLine()) {
		case "add":
			addRoom(scenarioName);
			scenarioFaçade.manageAddRoomsToScenario(scenarioName, rooms);
			break;
		case "change":
			System.out.println("Rimuovere la programmazione oraria? (s/n)");
			if(input.nextLine().equals("n")) {
				setDateTime();
			}
			else {
				time = null;
				days = null;
			}
			scenarioFaçade.setDateTime(scenarioName, time, days);
			break;
		case "modify":
			roomObjects.clear();
			System.out.println("Lista delle stanze");
			List<String> roomList = new ArrayList<String>();
			roomList = dataFaçade.getRooms();
			for(String room: roomList) {
				System.out.println(room);
			}
			System.out.println("Inserire il nome della stanza in cui eseguire le azioni");
			roomName[0] = input.nextLine();
			roomName[1] = null;
			roomObjects.add(roomName);
			setAction(scenarioName);
			// chiamata a controller solo se ha fatto create
			if(createFlag)
				scenarioFaçade.manageAddRoomsToScenario(scenarioName, rooms);
			//a liv di scenario non ho bisogno di sapere dove stanno le coppie
			//azioni - objcetID quindi posso riusare il metodo sopra - d.barzio
		}
	}
	
	public void deleteScenario() {
		System.out.println("Lista degli scenari");
		List<String> scenariosList = new ArrayList<String>();
		scenariosList = dataFaçade.getScenarios();
		for(String scenario: scenariosList) {
			System.out.println(scenario);
		}
		System.out.println("Inserire il nome dello scenario da eliminare");
		scenarioFaçade.manageDeleteScenario(input.nextLine());
	}
	
	public void addRoom(String scenarioName) {
		do {	
			roomObjects.clear();
			System.out.println("Inserire il nome della stanza in cui eseguire le azioni");
			roomName[0] = input.nextLine();
			roomName[1] = null;
			roomObjects.add(roomName);
			setAction(scenarioName);
			System.out.print("inserire altre stanze? (s/n)");
		} while(!input.nextLine().equals("n"));
		rooms.add(roomObjects);
	}
	
	public void setAction(String scenarioName) {
		do {
			System.out.println("Selezionare l'azione da effettuare sulla stanza: ");
			System.out.println("create: aggiungerne una nuova");
			System.out.println("modify: modificarne una esistente");
			System.out.println("delete: eliminarne una esistente");
			switch(input.nextLine()) {
			case "create":
				createFlag = true;
				do {
					System.out.println("Lista degli oggetti contenuti nella stanza: ");
					List<String[]> objectsInRoom = new ArrayList<String[]>();
					objectsInRoom = dataFaçade.getObjectsInRoom(roomName[0]);
					for(String[] objectInRoom: objectsInRoom) {
						System.out.println(objectInRoom[0]);
					}
					String objectID = null;
					System.out.println("Selezionare l'oggetto");
					String objectName = input.nextLine();	
					for(String[] objectInRoom: objectsInRoom) {
						if(objectName.equals(objectInRoom[0]))
							objectID = objectInRoom[1];
					}					
					System.out.println("Inserire lo stato desiderato per l'azione");
					String newState = input.nextLine();	
					String[] object = {objectID, newState};
					roomObjects.add(object);
					System.out.print("inserire altri oggetti? (s/n)");
				} while(!input.nextLine().equals("n"));
				rooms.add(roomObjects);
				break;
			case "modify":
				do {
					System.out.println("Lista degli oggetti modificabili nella stanza: ");
					List<String[]> objectsInRoomInScenario = new ArrayList<String[]>();
					objectsInRoomInScenario = dataFaçade.getObjectsInRoomInScenario(roomName[0], scenarioName);
					for(String[] object: objectsInRoomInScenario) {
						System.out.println(object[0]);
					}
					System.out.println("Inserire il nome dell'oggetto da modificare");
					String objectName = input.nextLine();
					String objectID = null;
					for(String[] objectInRoomInScenario: objectsInRoomInScenario) {
						if(objectName.equals(objectInRoomInScenario[0]))
							objectID = objectInRoomInScenario[1];
					}	
					System.out.println("Inserire lo stato desiderato per l'azione");
					String newState = input.nextLine();						
					scenarioFaçade.manageChangeAction(scenarioName, objectID, newState);
					System.out.print("modificare altri oggetti? (s/n)");
				} while(!input.nextLine().equals("n"));
				break;
			case "delete":
				do {
					System.out.println("Lista degli oggetti eliminabili nella stanza: ");
					List<String[]> objectsInRoomInScenario = new ArrayList<String[]>();
					objectsInRoomInScenario = dataFaçade.getObjectsInRoomInScenario(roomName[0], scenarioName);
					for(String[] object: objectsInRoomInScenario) {
						System.out.println(object[0]);
					}
					System.out.println("Inserire il nome dell'oggetto da eliminare");
					String objectName = input.nextLine();
					String objectID = "";
					for(String[] objectInRoomInScenario: objectsInRoomInScenario) {
						if(objectName.equals(objectInRoomInScenario[0]))
							objectID = objectInRoomInScenario[1];
					}	
					scenarioFaçade.manageDeleteAction(scenarioName, objectID);
					System.out.print("eliminare altri oggetti? (s/n)");
				} while(!input.nextLine().equals("n"));
				break;
			}
			System.out.println("Effettuare altre operazioni?");
		} while(!input.nextLine().equals("n"));
	}
	
	public void setDateTime() {
		System.out.println("Inserire l'ora di inizio nel formato hh:mm:");
		time = input.nextLine();		
		do {
			System.out.println("Inserire in quali giorni della settimana si vuole attivare lo scenario");
			days.add(input.nextLine());
			System.out.println("Inserire altri giorni? (s/n)");
		} while(input.nextLine().equals("s"));
	}
		
}