package view;

import java.util.Scanner;

import application.GenericFaçade;
import application.RoomFaçade;

public class ConfigView {
	
	Scanner input = new Scanner(System.in);

	public void roomConfig() {
		String roomName;
		String roomFloor;
		do {
			System.out.println("Inserire il nome della stanza che si intende creare: ");
			roomName = input.nextLine();
			if(!RoomFaçade.manageRoomConfig(roomName)) {
				System.out.print("Nome non disponibile");
			}
		}while(!RoomFaçade.manageRoomConfig(roomName));
				
		System.out.println("Inserire il piano della stanza: ");
		roomFloor = input.nextLine();
		// inviare dati
	}
	
	public void alarmConfig() {
		String alarmName;
		System.out.println("Inserire il nome dell'allarme che si intende creare: (premere invio per non inserire allarmi)");
		alarmName = input.nextLine();
		// inviare dati
	}
	
	public void lightConfig() {
		do {
			String lightName;
			System.out.println("Inserire il nome della luce che si intende creare: (premere invio per non inserire luci)");
			lightName = input.nextLine();
			// inviare dati
			System.out.print("continuare? (s/n)");
		} while (input.nextLine() != "n");
	}
	
	public void windowConfig() {
		do {
			String windowName;
			System.out.println("Inserire il nome della finestra che si intende creare: (premere invio per non inserire finestre)");
			windowName = input.nextLine();
			// inviare dati
			System.out.print("continuare? (s/n)");
		} while (input.nextLine() != "n");
	}
	
	public void doorConfig() {
		do {
			String doorName;
			String doorCode;
			System.out.println("Inserire il nome della porta che si intende creare: (premere invio per non inserire porte)");
			doorName = input.nextLine();
			System.out.println("Inserire il codice della porta: ");
			doorCode = input.nextLine();
			// inviare dati
			System.out.print("continuare? (s/n)");
		} while (input.nextLine() != "n");
	}
	
	public void heaterConfig() {
		do {
			String heaterName;
			System.out.println("Inserire il nome del calorifero che si intende creare: (premere invio per non inserire caloriferi)");
			heaterName = input.nextLine();
			// inviare dati
			System.out.print("continuare? (s/n)");
		} while (input.nextLine() != "n");
	}
	
	public void airSensorConfig() {
		String airSensorName;
		System.out.println("Inserire il nome del sensore dell'aria che si intende creare: (premere invio per non inserirlo)");
		airSensorName = input.nextLine();
		// inviare dati
	}
	
	public void movementSensorConfig() {
		String movementSensorName;
		System.out.println("Inserire il nome del sensore di movimento che si intende creare: (premere invio per non inserirlo)");
		movementSensorName = input.nextLine();
		// inviare dati
	}
	
	public void temperatureSensorConfig() {
		String temperatureSensorName;
		System.out.println("Inserire il nome del sensore di temperatura che si intende creare: (premere invio per non inserirlo)");
		temperatureSensorName = input.nextLine();
		// inviare dati
		// chiedere collegamento con heaters?
	}
	
	public void dayMode() {
		String startDayMode;
		String stopDayMode;
		System.out.println("Inserire l'ora di inizio della modalità giorno: ");
		startDayMode = input.nextLine();
		System.out.println("Inserire l'ora di fine della modalità giorno: ");
		stopDayMode = input.nextLine();
		// inviare dati
	}
	
	public void heatSystemConfig () {
		int tempPassiva;
		int tempAttiva;
		String giorno;
		do {
			System.out.println("Inserire la temperatura passiva: ");
			tempPassiva = input.nextInt();
			if(tempPassiva < 7 || tempPassiva > 40) 
				System.out.print("La temperatura inserita deve essere compresa tra 7 e 40 C");
		}while(tempPassiva < 7 || tempPassiva > 40);
		do {
			System.out.println("Inserire la temperatura attiva: ");
			tempAttiva = input.nextInt();
			if(tempAttiva < 7 || tempAttiva > 40) 
				System.out.print("La temperatura inserita deve essere compresa tra 7 e 40 C");
		}while(tempAttiva < 7 || tempAttiva > 40);
		
		
			System.out.println("Scegliere il giorno della settimana che si vuole programmare: ");
			giorno = input.nextLine();
			boolean fine = false;
			while(!fine)
				switch(giorno) {
				case "lunedi":
				case "martedi":
				case "mercoledi":
				case "giovedi":
				case "venerdi'":
				case "sabato":
				case "domenica":
					for(int i = 0; i < 24, i++) {
						System.out.println("Scegliere la ")
					}
				}	
				
		
			
		
		
	}
}