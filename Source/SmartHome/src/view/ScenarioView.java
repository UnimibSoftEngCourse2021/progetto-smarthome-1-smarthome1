package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import application.ScenarioFaçade;

public class ScenarioView {
	
	List<List<String[]>> rooms = new ArrayList<List<String[]>>();
	List<String[]> roomObjects = new ArrayList<>(); // primo valore è nome stanza
	
	String hour;
	String min;
	
	Scanner input = new Scanner(System.in);
	
	public void addRoom() {
		do {
			roomObjects.clear();
			System.out.println("Inserire il nome della stanza in cui eseguire le azioni");
			String[] roomName = {input.nextLine(), "null"};
			roomObjects.add(roomName);			
			System.out.print("inserire altre stanze? (s/n)");
		} while(input.nextLine() != "n");
	}
	
	public void setAction() {
		System.out.println("Selezionare l'operazione da effettuare sull'azione: ");
		System.out.println("create: aggiungerne una nuova");
		System.out.println("modify: modificarne una esistente");
		System.out.println("delete: eliminarne una esistente");
		switch(input.nextLine()) {
		case "create":
			do {
				System.out.println("Inserire il nome dell'oggetto su cui effettuare l'azione");
				String objectName = input.nextLine();
				System.out.println("Inserire lo stato desiderato per l'azione");
				String newState = input.nextLine();	
				String[] object = {objectName, newState};
				roomObjects.add(object);
				System.out.print("inserire altri oggetti? (s/n)");
			} while(input.nextLine() != "n");
			rooms.add(roomObjects);
			break;
		}
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
		System.out.println("Inserire il nome dello scenario da attivare: ");
		ScenarioFaçade.manageActivateScenario(input.nextLine());
	}
	
	public void createScenario() {
		System.out.println("Inserire il nome dello scenario da creare");
		String name = input.nextLine();
		System.out.println("Si desidera impostare un orario di inizio? (s/n)");
		if(input.nextLine().equals("s"))
			setTime();
		addRoom();
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
			setTime();
			break;
		case "modify":
			// scegliere la stanza da rooms e ritornare la lista della stanza desiderata
			setAction();
			
		}
		System.out.println("Inserire la stanza di cui modificare le azioni");
		String room = input.nextLine();
		System.out.println("Inserire il nome dell'oggetto da modificare");
		String object = input.nextLine();
		System.out.println("Inserire quale operazione si vuole effettuare sull'oggetto (modificare o eliminare");
		String action = input.nextLine();
		String newState;
		switch(input.nextLine()) {
		case "modificare":
			System.out.println("Inserire il nuovo stato desiderato per l'azione");
			newState = input.nextLine();
			break;
		case "eliminare":
			newState = null;
			break;
		}
		ScenarioFaçade.manageModifyScenario(name, room, object, newState);
	}
	
	public void deleteScenario() {
		System.out.println("Inserire il nome dello scenario da eliminare: ");
		ScenarioFaçade.manageDeleteScenario(input.nextLine());
	}
}
