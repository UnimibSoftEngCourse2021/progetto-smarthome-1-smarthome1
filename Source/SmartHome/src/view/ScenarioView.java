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
		do {
			System.out.println("Selezionare l'operazione da effettuare sull'azione: ");
			System.out.println("create: aggiungerne una nuova");
			System.out.println("modify: modificarne una esistente");
			System.out.println("delete: eliminarne una esistente");
			switch(input.nextLine()) {
			case "create":
				do {
					System.out.println("Lista degli oggetti contenuti nella stanza: ");
					// chiama metodo di DataFaçade e stampa gli oggetti
					System.out.println("Inserire il nome dell'oggetto su cui effettuare l'azione");
					String objectName = input.nextLine();
					// controllo che appartenga alla lista di oggetti stampata prima
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
					System.out.println("Lista degli oggetti contenuti nella stanza: ");
					// chiama metodo di DataFaçade e stampa gli oggetti
					System.out.println("Inserire il nome dell'oggetto da modificare");
					String objectName = input.nextLine();					
					// controllo che appartenga alla lista di oggetti stampata prima
					System.out.println("Inserire lo stato desiderato per l'azione");
					String newState = input.nextLine();	
					String[] object = {objectName, newState};
					
					for(String[] oldObject: roomObjects) {
						if(objectName.equals(oldObject[0]))
							oldObject = object;
					} // non ne sono sicuro																			
					System.out.print("modificare altri oggetti? (s/n)");
				} while(input.nextLine() != "n");
				break;
			case "delete":
				do {
					System.out.println("Lista degli oggetti contenuti nella stanza: ");
					// chiama metodo di DataFaçade e stampa gli oggetti
					System.out.println("Inserire il nome dell'oggetto sda eliminare");
					String objectName = input.nextLine();					
					// controllo che appartenga alla lista di oggetti stampata prima
					for(String[] oldObject: roomObjects) {
						if(objectName.equals(oldObject[0]))
							roomObjects.remove(oldObject);
					} // non ne sono sicuro																			
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
			// scegliere la stanza da rooms e ritornare la lista della stanza desiderata
			setAction();			
		}
	}
	
	public void deleteScenario() {
		//restituire la lista degli scenari presenti
		System.out.println("Inserire il nome dello scenario da eliminare");
		String name = input.nextLine();
		// controllare che lo scenario selezionato esista
		// eliminare l'occorrenza con lo stesso nome nella lista
	}
}