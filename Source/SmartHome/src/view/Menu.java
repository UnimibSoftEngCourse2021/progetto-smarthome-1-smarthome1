package view;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import domain.Object;



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
			String config;
			ConfigView configView = new ConfigView();
			List<String[]> numHeaterNotBinded = new ArrayList<String[]>();
			List<String[]> sensorList = new ArrayList<String[]>();
			
			configView.alarmConfig();
			configView.dayMode();
			do {
				String room = configView.roomConfig();
				String floor = configView.floorConfig();
				System.out.println("Inserire gli oggetti e sensori che si vuole inserire");
				System.out.println("Oggetti: light, window, door, heater");
				System.out.println("Sensori: air, movement, temperature");
				config = input.nextLine(); 
				switch(config) {
				case "light":
					configView.lightConfig();
					break;
				case "window":
					configView.windowConfig();
					break;
				case "door":
					configView.doorConfig();
					break;
				case "heater":
					numHeaterNotBinded = configView.heaterConfig(room, floor, numHeaterNotBinded);
					break;
				case "air":
					configView.airSensorConfig();
					break;
				case "movement":
					configView.movementSensorConfig();
					break;
				case "temperature":
					sensorList = configView.temperatureSensorConfig(room, sensorList);
					break;
				}
				System.out.println("Inserire altre stanze? (s/n)");
				config = input.nextLine();
				
			}while(config.equalsIgnoreCase("s"));
			
			
				// questo controllo lo fa alla fine dell'inserimento di ogni stanza
				System.out.print("Associare i sensori temp creati con gli oggetti heater: ");
				// se ho ancora caloriferi da associare
				do {
					for(String[] sensor: sensorList) {
						genericFacade.manageWriteOnFile("temperature", sensor[0]);
						genericFacade.manageWriteOnFile("roomNameSensor", sensor[1]);
						
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
								genericFacade.manageWriteOnFile("heaterID", heaterId);
								String[] temp = {config, heaterId};
								numHeaterNotBinded.remove(temp);
								if(!numHeaterNotBinded.isEmpty()) {
									System.out.println("Vuoi associare altri caloriferi al sensore " + sensor[0] +" ?");
									config = input.nextLine();
								}
							}
							
						}while(config.equalsIgnoreCase("s") || !numHeaterNotBinded.isEmpty());
					}	
					if(!numHeaterNotBinded.isEmpty())
						System.out.println("Alcuni caloriferi devono essere ancora associati");
				}while(!numHeaterNotBinded.isEmpty());	
					
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
