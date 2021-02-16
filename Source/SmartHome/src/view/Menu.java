package view;

import java.util.Scanner;

public class Menu {

	public static void main(String[] args) {
		
		System.out.println("Selezionare l'operazione desiderata scrivendo la parola chiave tra quelle presentate qui sotto");
		System.out.println("config: configurazione dei parametri relativi al sistema");
		System.out.println("scenario: attivazione, modifica e eliminazione di scenari");
		System.out.println("stati: visualizzare informazioni relative agli stati degli oggetti");
		System.out.println("azioni: effettuare manualmente azioni sugli oggetti");
		System.out.println("isAtHome: impostare lo stato di home oppure di away");

		Scanner input = new Scanner(System.in);
		switch(input.nextLine()) {
		case "config":
			break;
		case "scenario":
			break;
		case "stati":
			break;
		case "azioni":
			break;
		case "isAtHome":
			break;
		default:
			break;	
		}
		input.close();
	}

}
