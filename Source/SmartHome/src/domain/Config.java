package domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import domain.Object.ObjectType;

public class Config {
	
	private DataDescription dataDescription;
	private List<Object> objects;
	private List<Room> rooms;
	private AutomaticControl automaticControl;

	/**
	 * 
	 * @param fileHC
	 */
	public void processFileHC(File fileHC) {  //home config
		Scanner inputStream = null;
		try {
			inputStream = new Scanner(new File (dataDescription.getFileHomeConfig().getName()));
		} catch (FileNotFoundException e) {
			System.out.println("errore");
			System.exit(0);
		}
		Room room = null;
		int count = 0;
		while(inputStream.hasNextLine()) {
			if(count == 0) {
				String riga = inputStream.nextLine();
				if(riga.charAt(0) == ':') {
					if(riga.substring(1).equals("room")) {
						room = new Room();
						rooms.add(room);
					}
					else if(riga.substring(1).equals("object_LIGHT")
							|| riga.substring(1).equals("object_WINDOW")
							|| riga.substring(1).equals("object_DOOR")) {
						String name = inputStream.nextLine();
						objectInstantiationMethod(riga.substring(7), name);	
						count++;
					} else if(riga.substring(1).equals("object_HEATER")) {
						String name = inputStream.nextLine();
						objectInstantiationMethod(riga.substring(7), name);	
						count++;
						String sensorID = inputStream.nextLine();
						for(Object object: objects) {
							Heater heater = null;
							if(object.getObjectType().equals(ObjectType.HEATER) && object.getName().equals(name))
								heater = (Heater)object;
								heater.setSensorID(sensorID);
						}
						count++;
					}
					else if(riga.substring(1).equals("sensor_AIR")) {
						room.instatiateSensor(riga.substring(7));
						String name = inputStream.nextLine();
						room.setName(name);
						count++;
					} else if(riga.substring(1).equals("sensor_TEMPERATURE")) {
						room.instatiateSensor(riga.substring(7));
						String name = inputStream.nextLine();
						room.setName(name);
						count++;
						//fare anche roomID da associare al sensore di temp
					}
				} else {
				
				}		
						
			} else
				count--;
		}
	}

	/**
	 * 
	 * @param fileHSC
	 */
	public void processFileHSC(File fileHSC) {
		
	}

	private void objectInstantiationMethod(String objectType, String name) {
		// TODO - implement Config.objectInstatiationMethod
		//....
		//objects.add(objectCreated);
		throw new UnsupportedOperationException();
	}

}