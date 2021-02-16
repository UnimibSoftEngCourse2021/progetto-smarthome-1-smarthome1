package view;

import java.util.List;
import java.util.Scanner;

import application.ScenarioFaçade;

public class ScenarioInterface {
	
	Scanner input = new Scanner(System.in);
	
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
		List<String> actionList;
		List<String> roomList;
		System.out.println("Inserire il nome dello scenario da creare");
		String name = input.nextLine();
		do {
			System.out.println("Inserire la stanza in cui inserire le azioni le azioni");
			String room = input.nextLine();
			do {
				System.out.println("Inserire il nome dell'oggetto da modificare");
				String object = input.nextLine();
				System.out.println("Inserire il nuovo stato desiderato per l'azione");
				String newState = input.nextLine();
				
				System.out.print("continuare? (s/n)");
			} while(input.nextLine() != "n");
			
			System.out.print("continuare? (s/n)");
		} while(input.nextLine() != "n");
	}
	
	/*
	 * aggiunta azioni nuova stanza
	 * cambiare orario
	 * aggiunta/modifica/elimiazione azioni stanza esistente
	 */
	public void modifyScenario() {
		System.out.println("Inserire il nome dello scenario da modificare");
		String name = input.nextLine();
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
