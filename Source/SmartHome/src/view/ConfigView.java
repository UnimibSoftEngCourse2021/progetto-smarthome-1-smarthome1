package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import application.DataFaçade;
import application.GenericFaçade;
import application.RoomFaçade;

public class ConfigView {
	
// aggiungere a tutte i metodi controllo di roomconfig
	
	private GenericFaçade genericFacade;
	private DataFaçade dataFacade;
	
	public ConfigView(DataFaçade dataFacade, GenericFaçade genericFacade) {
		this.dataFacade = dataFacade;
		this.genericFacade = genericFacade;
 	}
	Scanner input = new Scanner(System.in);

	public String roomConfig() {
		String roomName;
		
		do {
			System.out.println("Inserire il nome della stanza che si intende creare: ");
			roomName = input.nextLine();
			if(!dataFacade.manageRoomConfig(roomName)) {
				System.out.print("Nome non disponibile");
			}
		}while(!dataFacade.manageRoomConfig(roomName));
		//inviare dati
		return roomName;
	}
	
	public String floorConfig() {
		String roomFloor;
		System.out.println("Inserire il piano della stanza: ");
		roomFloor = input.nextLine();
		// inviare dati
		return roomFloor;
	}
 	
	public void alarmConfig() {
		String alarmName = "";
		System.out.println("Inserire il nome dell'allarme che si intende creare: (premere invio per non inserire allarmi)");
		alarmName = input.nextLine();
		genericFacade.manageWriteOnHCFile("alarm", alarmName);
	}
	
	public void lightConfig() {
		do {
			String lightName;
			System.out.println("Inserire il nome della luce che si intende creare: (premere invio per non inserire luci)");
			lightName = input.nextLine();
			genericFacade.manageWriteOnHCFile("light", lightName);
			System.out.print("continuare? (s/n)");
		} while (input.nextLine() != "n");
	}
	
	public void windowConfig() {
		do {
			String windowName;
			System.out.println("Inserire il nome della finestra che si intende creare: (premere invio per non inserire finestre)");
			windowName = input.nextLine();
			genericFacade.manageWriteOnHCFile("window", windowName);
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
			genericFacade.manageWriteOnHCFile("door", doorName);
			genericFacade.manageWriteOnHCFile("code", doorCode);
			System.out.print("continuare? (s/n)");
		} while (input.nextLine() != "n");
	}
	
	public List<String[]> heaterConfig(String roomName, String roomFloor, List<String[]> heaterNotBinded) {
		do {
			System.out.println("Inserire il nome del calorifero che si intende creare: (premere invio per non inserire caloriferi)");
			String heaterName = input.nextLine();
			String[] temp = {heaterName, "HEATER_" + roomName +"_" + roomFloor + "_" + heaterNotBinded.indexOf(heaterName)};
			heaterNotBinded.add(temp);
			//genericFacade.manageWriteOnFile("heaterID", "HEATER" + roomName  + roomFloor + heaterNotBinded.indexOf(heaterName));
			System.out.print("continuare? (s/n)");
		} while (input.nextLine() != "n");
		return heaterNotBinded;
	}
	
	public void airSensorConfig() {
		String airSensorName;
		System.out.println("Inserire il nome del sensore dell'aria che si intende creare: (premere invio per non inserirlo)");
		airSensorName = input.nextLine();
		genericFacade.manageWriteOnHCFile("airSensor", airSensorName);
	}
	
	public void movementSensorConfig() {
		String movementSensorName;
		System.out.println("Inserire il nome del sensore di movimento che si intende creare: (premere invio per non inserirlo)");
		movementSensorName = input.nextLine();
		genericFacade.manageWriteOnHCFile("movementSensor", movementSensorName);
	}
	
	public List<String[]> temperatureSensorConfig(String room, List<String[]> sensorList) {
		String temperatureSensorName;
		System.out.println("Inserire il nome del sensore di temperatura che si intende creare: (premere invio per non inserirlo)");
		temperatureSensorName = input.nextLine();
		String[] temp = {temperatureSensorName, room};
		sensorList.add(temp);
		return sensorList;
		
	}
	
	public void dayMode() {
		String startDayMode;
		String stopDayMode;
		// aggiungere controlli su formato ora inserito dall'utente
		do {
			System.out.println("Inserire l'ora di inizio della modalità giorno (h:m): ");
			startDayMode = input.nextLine();
			System.out.println("Inserire l'ora di fine della modalità giorno: (h:m): ");
			stopDayMode = input.nextLine();
			if(stopDayMode.equalsIgnoreCase(startDayMode))
				System.out.println("gli orari non possono coincidere, reinserire i due valori");
		}while(stopDayMode.equalsIgnoreCase(startDayMode));
		genericFacade.manageWriteOnHCFile("startDayMode", startDayMode);
		genericFacade.manageWriteOnHCFile("stopDayMode", stopDayMode);
	}
	
	public void heatSystemConfig () {
		int tempPassiva;
		int tempAttiva;
		String giorno;
		double sceltaTemp = 0.00;
		//double[][] matrix = new double[7][23];
		double[] setProgDay = new double[26];
		// controlla che l'utente ha inserito almeno la tempAttiva Passiva di tutti i giorni
		boolean[] checkProgDay = new boolean[7];
		
		for(int i=0; i < checkProgDay.length; i++)
			checkProgDay[i] = false;
		
		
			boolean fine = false;
			boolean notAllProg;
			while(!fine) {
				do {
				notAllProg = false;
				
				System.out.println("Scegliere il giorno della settimana che si vuole programmare: ");
				giorno = input.nextLine();
			
				//switch(giorno) {
				//case "lunedi":
				//case "martedi":
				//case "mercoledi":
				//case "giovedi":
				//case "venerdi'":
				//case "sabato":
				//case "domenica":
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
					
					setProgDay[0] = tempPassiva;
					setProgDay[1] = tempAttiva;
					
					for(int i = 2; i < setProgDay.length; i++) 
						setProgDay[i] = 0;
					
					System.out.println("Inserire la programmazione per ogni ora: ");
					for(int i = 2; i < setProgDay.length; i++) {
						do {
							System.out.println("Inserire 0 per temperatura Passiva o 1 per Attiva per le ore: " + i);
							setProgDay[i] = input.nextDouble();
							if(sceltaTemp != 0 || sceltaTemp != 1)
								System.out.println("Valore non valido!");
						}while(sceltaTemp != 0 || sceltaTemp != 1);
					}
					
					genericFacade.manageWriteOnHSCFile(giorno, setProgDay);
					
						switch(giorno) {
						case "lunedi":
							checkProgDay[0] = true;
							break;
						case "martedi":
							checkProgDay[1] = true;
							break;
						case "mercoledi":
							checkProgDay[2] = true;
							break;
						case "giovedi":
							checkProgDay[3] = true;
							break;
						case "venerdi'":
							checkProgDay[4] = true;
							break;
						case "sabato":
							checkProgDay[5] = true;
							break;
						case "domenica":
							checkProgDay[6] = true;
							break;
						}
					
					for(int i = 0; i < checkProgDay.length; i++) {
						if(checkProgDay[i] == false) {
							notAllProg = true;
							break;
						}
					}
					if(notAllProg == true)
						System.out.println("Inserire i giorni rimanenti");
					
					
				}while(!notAllProg);
				
				System.out.print("continuare con la programmazione dei giorni? (s/n)");
				if(input.nextLine().equalsIgnoreCase("n"))
					fine = true;
			}
	}
	
	
	
}