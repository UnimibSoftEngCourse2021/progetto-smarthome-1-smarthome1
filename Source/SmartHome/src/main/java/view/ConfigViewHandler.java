package view;

import java.util.List;
import java.util.Scanner;

import application.DataFacade;
import application.GenericFacade;

public class ConfigViewHandler {
	
	private DataFacade df;
	private GenericFacade gf;
	private Scanner input = new Scanner(System.in);
	public ConfigViewHandler(DataFacade df, GenericFacade gf) {
		this.df = df;
		this.gf = gf;
	}
	
	public String roomConfig() {
		String roomName;
		
		do {
			System.out.println("Inserire il nome della stanza che si intende creare: ");
			roomName = input.nextLine();
			if(df.getRooms() != null &&  df.getRooms().contains(roomName)) {
				System.out.print("Nome già esistente");
			}
		} while(df.getRooms() != null && df.getRooms().contains(roomName));
		gf.manageWriteOnHCFile("room", roomName);
		return roomName;
	}
	
	public String floorConfig() {
		String roomFloor;
		System.out.println("Inserire il piano della stanza: ");
		roomFloor = input.nextLine();
		gf.manageWriteOnHCFile("floor", roomFloor);
		return roomFloor;
	}
 	
	public void alarmConfig() {
		String alarmName = "";
		System.out.println("Inserire il nome dell'allarme che si intende creare: (premere invio per non inserire allarmi)");
		alarmName = input.nextLine();
		gf.manageWriteOnHCFile("alarm", alarmName);
	}
	
	public void lightConfig() {
		do {
			String lightName;
			System.out.println("Inserire il nome della luce che si intende creare: (premere invio per non inserire luci)");
			lightName = input.nextLine();
			gf.manageWriteOnHCFile("light", lightName);
			System.out.print("continuare? (s/n)");
		} while (!input.nextLine().equals("n"));
	}
	
	public void windowConfig() {
		do {
			String windowName;
			System.out.println("Inserire il nome della finestra che si intende creare: (premere invio per non inserire finestre)");
			windowName = input.nextLine();
			gf.manageWriteOnHCFile("window", windowName);
			System.out.print("continuare? (s/n)");
		} while (!input.nextLine().equals("n"));
	}
	
	public void doorConfig() {
		do {
			String doorName;
			String doorCode;
			System.out.println("Inserire il nome della porta che si intende creare: (premere invio per non inserire porte)");
			doorName = input.nextLine();
			System.out.println("Inserire il codice della porta: ");
			doorCode = input.nextLine();
			gf.manageWriteOnHCFile("door", doorName);
			gf.manageWriteOnHCFile("code", doorCode);
			System.out.print("continuare? (s/n)");
		} while (!input.nextLine().equals("n"));
	}
	
	public List<String[]> heaterConfig(String roomName, String roomFloor, List<String[]> heaterNotBinded) {
		int n = 0;
		do {			
			System.out.println("Inserire il nome del calorifero che si intende creare: (premere invio per non inserire caloriferi)");
			String heaterName = input.nextLine();
			n++;
			String id = "HEATER_" + roomName +"_" + roomFloor + "_" + n;
			String[] temp = {heaterName, id};
			heaterNotBinded.add(temp);
			gf.manageWriteOnHCFile("heaterID", id);
			System.out.print("continuare? (s/n)");
		} while (!input.nextLine().equals("n"));
		return heaterNotBinded;
	}
	
	public void airSensorConfig() {
		String airSensorName;
		System.out.println("Inserire il nome del sensore dell'aria che si intende creare: (premere invio per non inserirlo)");
		airSensorName = input.nextLine();
		gf.manageWriteOnHCFile("airSensor", airSensorName);
	}
	
	public void movementSensorConfig() {
		String movementSensorName;
		System.out.println("Inserire il nome del sensore di movimento che si intende creare: (premere invio per non inserirlo)");
		movementSensorName = input.nextLine();
		gf.manageWriteOnHCFile("movementSensor", movementSensorName);
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
			System.out.println("Inserire l'ora di inizio della modalità giorno (hh:mm): ");
			startDayMode = input.nextLine();
			System.out.println("Inserire l'ora di fine della modalità giorno: (hh:mm): ");
			stopDayMode = input.nextLine();
			if(stopDayMode.equalsIgnoreCase(startDayMode))
				System.out.println("gli orari non possono coincidere, reinserire i due valori");
		}while(stopDayMode.equalsIgnoreCase(startDayMode));
		gf.manageWriteOnHCFile("startDayMode", startDayMode);
		gf.manageWriteOnHCFile("stopDayMode", stopDayMode);
	}
	
	public void bindHeaterSensor(List<String[]> numHeaterNotBinded, List<String[]> sensorList) {
		String config = "";
		if(!numHeaterNotBinded.isEmpty()) {
			// questo controllo lo fa alla fine dell'inserimento di ogni stanza
			System.out.print("Associare i sensori temp creati con gli oggetti heater: ");
			// se ho ancora caloriferi da associare
			
				for(String[] sensor: sensorList) {
					gf.manageWriteOnHCFile("temperature", sensor[0]);
					gf.manageWriteOnHCFile("roomNameSensor", sensor[1]);						
					do {
						if(!numHeaterNotBinded.isEmpty()) {
							System.out.println("Caloriferi disponibili: ");
							for(String[] heater: numHeaterNotBinded) { 
								System.out.print(" " + heater[0]);
							}
							System.out.println("");
							System.out.println("Inserire il nome del calorifero da associare al sensore " + sensor);
							config = input.nextLine();
							String heaterId = "";
							for(String[] heater: numHeaterNotBinded) 
								if(heater[0].equals(config)) {
									heaterId= heater[1];
									break;
								}
							gf.manageWriteOnHCFile("heaterID", heaterId);
							String[] temp = {config, heaterId};
							numHeaterNotBinded.remove(temp);
							if(!numHeaterNotBinded.isEmpty()) {
								System.out.println("Vuoi associare altri caloriferi al sensore " + sensor[0] +" ?");
								config = input.nextLine();
							}
						}							
					} while(config.equalsIgnoreCase("s") || !numHeaterNotBinded.isEmpty());
					gf.manageWriteOnHCFile("heaterID", "");
				}
		}
	}
	
	public void endConfig() {
		gf.manageWriteOnHCFile("end", null);
	}
	
}
