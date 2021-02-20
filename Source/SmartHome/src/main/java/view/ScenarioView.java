package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import application.DataFacade;
import application.ScenarioFacade;

public class ScenarioView {
	
	//bisogna fare le giuste associazioni (nel main?)
	private DataFacade dataFacade;
	private ScenarioFacade scenarioFacade;
	
	List<List<String[]>> rooms = new ArrayList<>();;
	List<String[]> roomObjs = new ArrayList<>(); // primo valore � nome stanza
	String[] roomName;
	List<String> days = new ArrayList<>();	
	String time;
	private boolean createFlag = false; 
	
	Scanner input = new Scanner(System.in);
	
	public ScenarioView(DataFacade dataFacade, ScenarioFacade scenarioFacade) {
		this.dataFacade = dataFacade;
		this.scenarioFacade = scenarioFacade;
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
		List<String> scenarios = new ArrayList<>();
		scenarios = dataFacade.getScenarios();
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
			scenarioFacade.manageActivateScenario(selected);
	}
		
	
	public void createScenario() {
		System.out.println("Inserire il nome dello scenario da creare");
		String scenarioName = input.nextLine();
		System.out.println("Si desidera impostare un orario di inizio? (s/n)");
		if(input.nextLine().equals("s"))
			setDateTime();
			addRoom(scenarioName);
		scenarioFacade.manageCreateScenario(scenarioName, time, rooms, days);
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
			scenarioFacade.manageAddRoomsToScenario(scenarioName, rooms);
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
			scenarioFacade.setDateTime(scenarioName, time, days);
			break;
		case "modify":
			roomObjs.clear();
			System.out.println("Lista delle stanze");
			List<String> roomList = new ArrayList<>();
			roomList = dataFacade.getRooms();
			for(String room: roomList) {
				System.out.println(room);
			}
			System.out.println("Inserire il nome della stanza in cui eseguire le azioni");
			roomName[0] = input.nextLine();
			roomName[1] = null;
			roomObjs.add(roomName);
			setAction(scenarioName);
			// chiamata a controller solo se ha fatto create
			if(createFlag)
				scenarioFacade.manageAddRoomsToScenario(scenarioName, rooms);
			break;
		default:
			break;
		}
	}
	
	public void deleteScenario() {
		System.out.println("Lista degli scenari");
		List<String> scenariosList = new ArrayList<>();
		scenariosList = dataFacade.getScenarios();
		for(String scenario: scenariosList) {
			System.out.println(scenario);
		}
		System.out.println("Inserire il nome dello scenario da eliminare");
		scenarioFacade.manageDeleteScenario(input.nextLine());
	}
	
	public void addRoom(String scenarioName) {
		do {	
			roomObjs.clear();
			System.out.println("Inserire il nome della stanza in cui eseguire le azioni");
			roomName[0] = input.nextLine();
			roomName[1] = null;
			roomObjs.add(roomName);
			setAction(scenarioName);
			System.out.print("inserire altre stanze? (s/n)");
		} while(!input.nextLine().equals("n"));
		rooms.add(roomObjs);
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
					List<String[]> objsInRoom = new ArrayList<>();
					objsInRoom = dataFacade.getObjsInRoom(roomName[0]);
					for(String[] objInRoom: objsInRoom) {
						System.out.println(objInRoom[0]);
					}
					String objID = null;
					System.out.println("Selezionare l'oggetto");
					String objName = input.nextLine();	
					for(String[] objInRoom: objsInRoom) {
						if(objName.equals(objInRoom[0]))
							objID = objInRoom[1];
					}					
					System.out.println("Inserire lo stato desiderato per l'azione");
					String newState = input.nextLine();	
					String[] obj = {objID, newState};
					roomObjs.add(obj);
					System.out.print("inserire altri oggetti? (s/n)");
				} while(!input.nextLine().equals("n"));
				rooms.add(roomObjs);
				break;
			case "modify":
				do {
					System.out.println("Lista degli oggetti modificabili nella stanza: ");
					List<String[]> objsInRoomInScenario = new ArrayList<>();
					objsInRoomInScenario = dataFacade.getObjsInRoomInScenario(roomName[0], scenarioName);
					for(String[] obj: objsInRoomInScenario) {
						System.out.println(obj[0]);
					}
					System.out.println("Inserire il nome dell'oggetto da modificare");
					String objName = input.nextLine();
					String objID = null;
					for(String[] objInRoomInScenario: objsInRoomInScenario) {
						if(objName.equals(objInRoomInScenario[0]))
							objID = objInRoomInScenario[1];
					}	
					System.out.println("Inserire lo stato desiderato per l'azione");
					String newState = input.nextLine();						
					scenarioFacade.manageChangeAction(scenarioName, objID, newState);
					System.out.print("modificare altri oggetti? (s/n)");
				} while(!input.nextLine().equals("n"));
				break;
			case "delete":
				do {
					System.out.println("Lista degli oggetti eliminabili nella stanza: ");
					List<String[]> objsInRoomInScenario = new ArrayList<>();
					objsInRoomInScenario = dataFacade.getObjsInRoomInScenario(roomName[0], scenarioName);
					for(String[] obj: objsInRoomInScenario) {
						System.out.println(obj[0]);
					}
					System.out.println("Inserire il nome dell'oggetto da eliminare");
					String objName = input.nextLine();
					String objID = "";
					for(String[] objInRoomInScenario: objsInRoomInScenario) {
						if(objName.equals(objInRoomInScenario[0]))
							objID = objInRoomInScenario[1];
					}	
					scenarioFacade.manageDeleteAction(scenarioName, objID);
					System.out.print("eliminare altri oggetti? (s/n)");
				} while(!input.nextLine().equals("n"));
				break;
			default:
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