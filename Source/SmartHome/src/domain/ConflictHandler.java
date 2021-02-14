package domain;

import java.util.ArrayList;
import java.util.List;

import domain.Object.ObjectType;
import service.ObjectCommunicationAdapter;

public class ConflictHandler {
	

	/*
	 * variabile per identificare modalità giorno/notte per accensione luci
	 * gli orari sono flessibili e devono essere scelti dall'utente
	 */
	private boolean atHome = true;
	
	private AutomaticControl automaticControl;
	private List<Object> objects;
	private List<Scenario> scenario;
	private ObjectCommunicationAdapter adapter;
	
	public boolean isAtHome() {
		return atHome;
	}

	public void setAtHome(boolean atHome) {
		this.atHome = atHome;
	}
	
	/**
	 * 
	 * @param alarmID
	 * @param doorID
	 */
	public void isAway(String alarmID, String doorID) {
		if(atHome) {
			atHome = false;
			// allarme armato		
			for(Object object: objects) {
				Alarm alarm = (Alarm)object;
				if(alarm.getObjectType().equals(ObjectType.ALARM))
					alarm.setArmed(true);
			}
			/*
			 * non chiama l'adapter perchè imposta un flag e non cambia lo stato dell'oggetto
			 */
			// blocca porta
			doAction(doorID, false);
			/*
			 *  chiusura/spegnimento forzata di tutti gli oggetti
			 *  non influisce su allarme perchè il fatto che sia armato non c'entra con lo stato
			 */
			for(Object object: objects) {
				adapter.triggerAction(object, false);
			}
		}
	}

	/**
	 * 
	 * @param alarmID
	 * @param doorID
	 * @param code
	 */
	public void isHome(String alarmID, String doorID, String code) {
		if(!atHome) {
			int i = 0;
			do {
				for(Object object: objects) {
					Door door = (Door)object;
					if (door.getObjectID().equals(doorID) && code.equals(door.getCode())) {
						atHome = true; // flag attivo			
						doAction(doorID, true); // sbloccare porta
						break;
					}
					else if (door.getObjectID().equals(doorID)) {
						//richiedere codice all'utente
						i++; // contatore degli accessi errati
					}
				}				
				for(Object object: objects) {
					// disarmare allarme
					if(object.getObjectID().equals(alarmID)) {
						Alarm alarm = (Alarm)object;
						alarm.setArmed(false);	
						break;
					}
				}
			} while (i < 5 || atHome);
			if (i >= 5) {
				System.out.println("Numero massimo di tentativi superato. Chiamare l'assistenza");
				// bloccare la richiesta di codice
			}
		}
	}

	/**
	 * 
	 * @param objectID
	 * @param changeState
	 */
	public void doAction(String objectID, boolean changeState) {
		for(Object object: objects) {
			if(object.getObjectID().equals(objectID)) { // associo l'id dell'oggetto passato all'oggetto attuale del for
				if(changeState) {
					switch(object.getObjectType()) {
					case ALARM:
					case DOOR:
						adapter.triggerAction(object, true);
						break;
					case HEATER:
						/*
						 * prima di accendere il calorifero controllo che tutte le finestre della stanza siano chiuse
						 * invio notifica all'utente per chiedere cosa fare
						 */
						boolean windowOpen = false;	
						ArrayList<Object> windows = getSpecificObjectsInRoom(object.getReferencedRoomID(), ObjectType.WINDOW);
						for(Object window: objects) {
							if(window.isActive() == true) {							
								windowOpen = true;
								break;
							}															
						}
						if(windowOpen) {
							if(true/*controller.userNotify(heater)*/) {
								if(true/*controller.userNotify(window)*/)
									for(Object window: windows) 
										adapter.triggerAction(window, false);
								adapter.triggerAction(object, true);
							}
						}
						else
							adapter.triggerAction(object, true);
						break;
					case SHADER: // non genera conflitti
					case LIGHT:
						adapter.triggerAction(object, true);
						break;
					case WINDOW:
						if(!Alarm.isArmed())							
							adapter.triggerAction(object, true);
						else if(true/* controller.userNotify()*/)
								adapter.triggerAction(object, true);
						break;
					default:
						break;
					}
				}
				else // se si vuole spegnere qualcosa non ci sono conflitti
					adapter.triggerAction(object, false);
				break;
			}
		}
	}
	
	public void doAction(String lightID, boolean dayMode, boolean changeState) {
		for(Object object: objects) {
			if(object.getObjectID().equals(lightID)) { // associo l'id dell'oggetto passato all'oggetto attuale del for
				if(changeState) {
					if(!dayMode)
						adapter.triggerAction(object, true);
					else {
						ArrayList<Object> shaders = getSpecificObjectsInRoom(object.getReferencedRoomID(), ObjectType.SHADER);
						boolean shaderOpen = false; 
						for(Object shader: shaders) {
							if(!shader.isActive()) { // is active = fermano la luce
								shaderOpen = true;
								break;
							}
						}
						if(!shaderOpen) {
							adapter.triggerAction(object, true);
						}
					}
				}
				else
					adapter.triggerAction(object, false);
				break;
			}
		}
	}
	
	/**
	 * 
	 * @param objectID
	 * @param airState
	 * @param changeState
	 */
	public void doAction(String windowID, String airState, boolean changeState) {
		// a seconda di airState azione ha priorità diversa
		for(Object object: objects) {
			Window window = (Window)object;
			if(window.getObjectID().equals(windowID)) // associo l'id dell'oggetto passato all'oggetto attuale del for
				if(changeState) {			
					if(airState.equalsIgnoreCase("pollution")) {
						if(!Alarm.isArmed()) {
							boolean userDecision = false;
							ArrayList<Object> heaters = getSpecificObjectsInRoom(window.getReferencedRoomID(), ObjectType.HEATER);
							for(Object heater: heaters)
								if(heater.isActive()) {
									// controller.notifyUser(...) : boolean true se l'utente vuole aprire le finestre
									break;
								}
							if(window.getShader().isActive() && userDecision) {
								adapter.triggerAction(window.getShader(), false);
								adapter.triggerAction(window, true);
							}							
						}
						break;
					}
					else if(airState.equalsIgnoreCase("gas")) {
						/*
						 * nel caso ci sia una fuga di gas aprire sempre le finestre
						 * considerare un possibile comportamento diverso per allarme
						 */
						ArrayList<Object> lights = getSpecificObjectsInRoom(window.getReferencedRoomID(), ObjectType.LIGHT);
						for(Object light: lights)
							adapter.triggerAction(light, false);
						if(!Alarm.isArmed()) {
							if(window.getShader().isActive())
								adapter.triggerAction(window.getShader(), false);
							adapter.triggerAction(window, true);
						}
						else {
							// if(controller.notifyUser(...)) apri finestre + controllo shader						
						}
						break;
					}
				}
		}
	}

	/**
	 * 
	 * @param objectID
	 */
	public void doManualAction(String objectID) {
		for(Object object: objects) {
			if(object.getObjectID().equals(objectID))
				adapter.triggerAction(object, !object.isActive());
		}
	}
	
	public ArrayList<Object> getSpecificObjectsInRoom(String roomID, ObjectType type) {
		ArrayList<Object> roomObjects = new ArrayList<>();
		for(Object object: objects) {
			if(object.getObjectType().equals(type) && object.getReferencedRoomID().equals(roomID)) {
				roomObjects.add(object);
			}
		}
		return roomObjects;
	}
	
}