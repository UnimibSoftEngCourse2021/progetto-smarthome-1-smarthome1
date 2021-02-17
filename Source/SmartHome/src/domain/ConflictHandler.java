package domain;

import java.util.ArrayList;
import java.util.List;

import domain.Object.ObjectType;
import domain.Sensor.AirState;
import service.ObjectCommunicationAdapter;

public class ConflictHandler {
	
	private static ConflictHandler conflictHandler = null; //singleton instance
	private boolean atHome = true;
	
	private List<Object> objects;
	private List<Scenario> scenario; //non credo serva (sono gli scenari a chiamare this, non il contrario)
	private ObjectCommunicationAdapter adapter;
	
	private ConflictHandler() {
		
	}
	
	public static ConflictHandler getInstance() {
		if(conflictHandler == null)
			conflictHandler = new ConflictHandler();
		return conflictHandler;
	}

	public void isAway(String alarmID, String doorID) {
		if(atHome) {
			atHome = false;
			// allarme armato
			Alarm.getInstance().setArmed(true);
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
				// disarmare allarme
				Alarm.getInstance().setArmed(false);
			} while (i < 5 || atHome);
			if (i >= 5) {
				System.out.println("Numero massimo di tentativi superato. Chiamare l'assistenza");
				// bloccare la richiesta di codice
			}
		}
	}

	public void doAction(String objectID, boolean changeState) {
		for(Object object: objects) {
			if(object.getObjectID().equals(objectID)) { 
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
						List<Object> windows = object.getRoom().getObjects(ObjectType.WINDOW);
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
					case SHADER: // non generano conflitti
					case LIGHT:
						adapter.triggerAction(object, true);
						break;
					case WINDOW:
						if(Alarm.isCreated() && !Alarm.getInstance().isArmed())							
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
			if(object.getObjectID().equals(lightID)) { 
				if(changeState) {
					if(!dayMode)
						adapter.triggerAction(object, true);
					else {
						List<Object> shaders = object.getRoom().getObjects(ObjectType.SHADER);
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

	public void doAction(String windowID, AirState airState, boolean changeState) {
		// a seconda di airState azione ha priorità diversa
		for(Object object: objects) {
			Window window = (Window)object;
			if(window.getObjectID().equals(windowID))
				if(changeState) {			
					if(airState.equals(AirState.POLLUTION)) {
						if(Alarm.isCreated() && !Alarm.getInstance().isArmed()) {
							boolean userDecision = false;
							List<Object> heaters = window.getRoom().getObjects(ObjectType.HEATER);
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
					else if(airState.equals(AirState.GAS)) {
						/*
						 * nel caso ci sia una fuga di gas aprire sempre le finestre
						 * considerare un possibile comportamento diverso per allarme
						 */
						List<Object> lights = window.getRoom().getObjects(ObjectType.LIGHT);
						for(Object light: lights)
							adapter.triggerAction(light, false);
						if(Alarm.isCreated() && !Alarm.getInstance().isArmed()) {
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

	public void doManualAction(String objectID) {
		for(Object object: objects) {
			if(object.getObjectID().equals(objectID))
				adapter.triggerAction(object, !object.isActive());
		}
	}
	
	public boolean isAtHome() {
		return atHome;
	}
	
	public List<Object> getObjects() {
		return objects;
	}

	public void addObject(Object object) {
		objects.add(object);
	}
}