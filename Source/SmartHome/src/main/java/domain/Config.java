package domain;

import java.io.FileNotFoundException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import domain.AutomaticControl.ChoosenMatrix;
import domain.Obj.ObjType;
import domain.Sensor.SensorCategory;

public class Config {
	
	private static Config config = null;
	private DataDescription dataDescription;
	private List<Room> rooms = new ArrayList<Room>();
	private Room tempRoom;

	
	private Config() {
		
	}
	
	public static Config getInstance() {
		if(config == null)
			config = new Config();
		return config;
	}
	
	public void processFileHC() {  //home config		
		try (Scanner inputStream = new Scanner(dataDescription.getFileHC())){					
			while(inputStream.hasNextLine()) {
				String riga = inputStream.nextLine();
				if(riga.equals(":ROOM")) {
					String name = inputStream.nextLine();
					String floor = inputStream.nextLine();
					tempRoom = new Room(name, Short.parseShort(floor));
					rooms.add(tempRoom);
					tempRoom.createTimer();
				}
				else if(riga.equals(":ALARM")) {
					String name = inputStream.nextLine();
					Alarm.getInstance(name);
					ConflictHandler.getInstance().addObj(Alarm.getInstance());
					Alarm.getInstance().getSensor().attach(Alarm.getInstance());
				}
				else if(riga.equals(":LIGHT")
						|| riga.equals(":WINDOW")
						|| riga.equals(":DOOR")
						|| riga.equals(":HEATER")) {
					String name = inputStream.nextLine();
					tempRoom.instantiateObj(ObjType.valueOf(riga.substring(1)), name);	 
					if(riga.equals(":DOOR")) {
						String code = inputStream.nextLine();
						//Door door = (Door)room.getObjs(ObjType.DOOR).get(0);
						tempRoom.setDoorCode(code);
						//door.setCode(code);
					}
				} else if(riga.equals(":AIR")
						|| riga.equals(":MOVEMENT")) {			
					String name = inputStream.nextLine();
					tempRoom.instantiateSensor(SensorCategory.valueOf(riga.substring(1)), name);
				}
				else if(riga.equals(":TEMPERATURE")) {
					String name = inputStream.nextLine();
					String roomName = inputStream.nextLine();
					for(Room sensorRoom: rooms) {
						if(sensorRoom.getRoomID().equalsIgnoreCase(roomName)) {
							sensorRoom.instantiateSensor(SensorCategory.valueOf(riga.substring(1)), name);
							break;
						}
					}
					String nuovaRiga = inputStream.nextLine();
					List<String> heatersIDs = new ArrayList<String>();
					while(!nuovaRiga.equals("")) {
						heatersIDs.add(nuovaRiga);
						nuovaRiga = inputStream.nextLine();
					}
					if(heatersIDs != null) {
						for(Room singleRoom: rooms) {
							List<Obj> heaters = singleRoom.getObjs(ObjType.HEATER);
							for(Obj obj: heaters) {
								Heater heater = (Heater)obj;
								if(heatersIDs.contains(heater.getObjID())) {
									tempRoom.getSensors(SensorCategory.TEMPERATURE).get(0).attach(heater);	
									heater.setTempSensor(tempRoom.getSensors(SensorCategory.TEMPERATURE).get(0));
									//da rivedere bind heater con sensore di temp e viceversa
									// un sensore della stanza A puo bindare con un heater della stanza B
								}
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
			Sensor mSensor;
			for(Room r: rooms) {
				if(r.getSensors(SensorCategory.MOVEMENT) != null) {
						mSensor = r.getSensors(SensorCategory.MOVEMENT).get(0);
					if(r.getObjs(ObjType.LIGHT) != null)
						for(Obj obj: r.getObjs(ObjType.LIGHT)) {
							mSensor.attach(obj);
						}
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("error");
			System.exit(0);
		}		
	}

	public void processFileHSC() {
		try (Scanner inputStream = new Scanner(dataDescription.getFileHSC())) {
			while(inputStream.hasNextLine()) {
				AutomaticControl.getInstance().setChoosenMatrix(ChoosenMatrix.USER);
				String riga = inputStream.nextLine();
				if(riga.equals(":MONDAY")
					|| riga.equals(":TUESDAY")
					|| riga.equals(":WEDNESDAY")
					|| riga.equals(":THURSDAY")
					|| riga.equals(":FRIDAY")
					|| riga.equals(":SATURDAY")
					|| riga.equals(":SUNDAY")) {
					String passiveTemp = inputStream.nextLine();
					String  activeTemp = inputStream.nextLine();
					AutomaticControl.initUserMatrix(DayOfWeek.valueOf(riga.substring(1)).ordinal(), 24, Double.parseDouble(passiveTemp));
					AutomaticControl.initUserMatrix(DayOfWeek.valueOf(riga.substring(1)).ordinal(), 25, Double.parseDouble(activeTemp));
					for(int j = 0; j < 24; j++) {
						String temp = inputStream.nextLine();
						AutomaticControl.initUserMatrix(DayOfWeek.valueOf(riga.substring(1)).ordinal(), j, Double.parseDouble(temp));
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("error");
			System.exit(0);
		}
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(Room room) {
		rooms.add(room);
	}

	public DataDescription getDataDescription() {
		return dataDescription;
	}

	public void setDataDescription(DataDescription dataDescription) {
		this.dataDescription = dataDescription;
	}
	
	public static void clean() {
		config = null;
	}

}