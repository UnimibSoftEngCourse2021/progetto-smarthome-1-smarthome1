package view;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import application.DataFaçade;
import application.GenericFaçade;
import application.HandlerFaçade;
import application.RoomFaçade;
import application.ScenarioFaçade;
import domain.DataDescription;
import domain.Object;
import domain.ScenariosHandler;

public class Menu {

	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		
		//inizializzazione oggetti di dominio singoli
		DataDescription dd = new DataDescription();
		ScenariosHandler sh = new ScenariosHandler();
		//inizializzazione oggetti facade
		DataFaçade df = new DataFaçade(sh);
		GenericFaçade gf = new GenericFaçade(dd);
		ScenarioFaçade sf = new ScenarioFaçade(sh);
		//da vedere
		HandlerFaçade hf = new HandlerFaçade();
		RoomFaçade rf = new RoomFaçade();
		
		//inizializzazione oggetti view
		ConfigView configView = new ConfigView(df, gf);
		ScenarioView scenarioView = new ScenarioView(df, sf);
		ObjectStateView objectStateView = new ObjectStateView(df);
		ManualActionView manualActionView = new ManualActionView(df, hf); 
		IsAtHome isAtHome = new IsAtHome(hf);
		
		do {
			System.out.println("Selezionare l'operazione desiderata scrivendo la parola chiave tra quelle presentate qui sotto");
			System.out.println("config: configurazione dei parametri relativi al sistema");
			System.out.println("heat: configurazione sistema di riscaldamento");
			System.out.println("scenario: attivazione, modifica e eliminazione di scenari");
			System.out.println("stati: visualizzare informazioni relative agli stati degli oggetti");
			System.out.println("azioni: effettuare manualmente azioni sugli oggetti");
			System.out.println("isAtHome: impostare lo stato di home oppure di away");
		
			switch(input.nextLine()) {
			case "config":
				
				String config;
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
						} while(config.equalsIgnoreCase("s") || !numHeaterNotBinded.isEmpty());
						genericFacade.manageWriteOnFile("heaterID", "");
					}
					genericFacade.manageWriteOnFile("end", null);							
				break;
			case "heat":
				configView.heatSystemConfig();
				break;
			case "scenario":
				scenarioView.selectOperation();
				break;
			case "stati":
				objectStateView.getObjectState();
				break;
			case "azioni":
				manualActionView.performAction();
				break;
			case "isAtHome":
				isAtHome.changeFlag();
				break;
			default:
				System.out.println("Input non valido");
				break;	
			}		
			System.out.println("Si desidera effettuare altre operazioni? (s/n)");		
		} while (input.nextLine().equals("n"));
			input.close();
	}
}
