package domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import domain.Object.ObjectType;
import domain.Sensor.SensorCategory;

public class Config {
	
	private DataDescription dataDescription;
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
			System.out.println("error");
			System.exit(0);
		}
		Room room = null;
		Alarm alarm = null;
		while(inputStream.hasNextLine()) {
			String riga = inputStream.nextLine();
			if(riga.equals(":room")) {
				String name = inputStream.nextLine();
				String floor = inputStream.nextLine();
				room = new Room(name, Short.parseShort(floor));
				rooms.add(room);
			}
			else if(riga.substring(1).equals(":ALARM")) {
				String name = inputStream.nextLine();
				alarm = new Alarm(name);
			}
			else if(riga.equals(":LIGHT")
					|| riga.equals(":WINDOW")
					|| riga.equals(":DOOR")
					|| riga.equals(":HEATER")) {
				String name = inputStream.nextLine();
				room.instantiateObject(ObjectType.valueOf(riga.substring(1)), name);	 
				if(riga.equals(":DOOR")) {
					String code = inputStream.nextLine();
					room.setDoorCode(code);
				}
			} else if(riga.equals(":AIR")
					|| riga.equals(":MOVEMENT")) {			
				String name = inputStream.nextLine();
				room.instantiateSensor(SensorCategory.valueOf(riga.substring(1)), name);
			}
			else if(riga.equals(":TEMPERATURE")) {
				String name = inputStream.nextLine();
				room.instantiateSensor(SensorCategory.valueOf(riga.substring(1)), name);
				
				String nuovaRiga = inputStream.nextLine();
				List<String> heatersIDs = null;
				while(!nuovaRiga.equals("")) {
					heatersIDs.add(nuovaRiga);
					nuovaRiga = inputStream.nextLine();
				}
				if(!heatersIDs.equals(null)) {
					for(Room singleRoom: rooms) {
						List<Object> heaters = singleRoom.getObjectList(ObjectType.HEATER);
						for(Object object: heaters) {
							Heater heater = (Heater)object;
							if(heatersIDs.contains(heater.getObjectID())) 
								singleRoom.bindHeater(heater, room.getSensorList(SensorCategory.TEMPERATURE).get(0));								
						}					
					}
				}
			} else if(riga.equals(":STARTDAYMODE")) {
				String sdm = inputStream.nextLine();
				AutomaticControl.setStartDayMode(sdm);
			}  else if(riga.equals(":STOPDAYMODE")) {
				String sdm = inputStream.nextLine();
				AutomaticControl.setStopDayMode(sdm);
			} 				
		}
		for(Room r: rooms) {
			r.setDoorsNum();
			r.setLightsNum();
			r.setHeatersNum();
			r.setWindowsNum();
		}
		
	}

	/**
	 * 
	 * @param fileHSC
	 */
	public void processFileHSC(File fileHSC) {
		
	}

}