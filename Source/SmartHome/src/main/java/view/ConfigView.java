package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import application.DataFacade;
import application.GenericFacade;

public class ConfigView {
	
// aggiungere a tutte i metodi controllo di roomconfig
	

	private ConfigViewHandler ch;
	
	public ConfigView(DataFacade df, GenericFacade gf) {
		ch = new ConfigViewHandler(df, gf);
 	}
	Scanner input = new Scanner(System.in);

	public void config() {
		
		String config;
		List<String[]> numHeaterNotBinded = new ArrayList<String[]>();
		List<String[]> sensorList = new ArrayList<String[]>();		
		ch.alarmConfig();
		ch.dayMode();
		System.out.println("Configurazione iniziale del sistema");
	
		do {
						
			String room = ch.roomConfig();
			String floor = ch.floorConfig();
			do {	
				System.out.println("Inserire il tipo di sensore o oggetto che si vuole aggiungere.");
				System.out.println("Scegliere una delle opzioni seguenti:");
				System.out.println("\tOggetti: light, window, door, heater.");
				System.out.println("\tSensori: air, movement, temperature.");
				config = input.nextLine(); 
				switch(config) {
				case "light":
					ch.lightConfig();
					break;
				case "window":
					ch.windowConfig();
					break;
				case "door":
					ch.doorConfig();
					break;
				case "heater":
					numHeaterNotBinded = ch.heaterConfig(room, floor, numHeaterNotBinded);
					break;
				case "air":
					ch.airSensorConfig();
					break;
				case "movement":
					ch.movementSensorConfig();
					break;
				case "temperature":
					sensorList = ch.temperatureSensorConfig(room, sensorList);
					break;
				}
				System.out.println("Inserire altre tipologie di oggetti/sensori nella stanza "+ room +" ? (s/n)");
				config = input.nextLine();
				
			} while(config.equalsIgnoreCase("s"));
			
			System.out.println("Inserire altre stanze? (s/n)");
			config = input.nextLine();
			
		}while(config.equalsIgnoreCase("s"));
		ch.endConfig();
	}
	
	
	
	
	
}