package domain;

import java.util.ArrayList;
import java.util.List;

import application.HandlerFacade;
import domain.Object.ObjectType;
import domain.Sensor.AirState;
import service.ObjectCommunicationSystem;


public class ConflictHandler {
	
	private HandlerFacade handler;
	
	private static ConflictHandler conflictHandler = null; //singleton instance
	private boolean atHome = true;
	
	private List<Object> objects;
	//private List<Scenario> scenario; non credo serva (sono gli scenari a chiamare this, non il contrario)
	private ObjectCommunicationSystem adapter;
	
	private ConflictHandler() {
		objects = new ArrayList<Object>();
		adapter = new ObjectCommunicationSystem();
	}
	
	public static ConflictHandler getInstance() {
		if(conflictHandler == null)
			conflictHandler = new ConflictHandler();
		return conflictHandler;
	}

	public void isAway(String doorID) {
		if(atHome) {
			atHome = false;
			// allarme armato
			Alarm.getInstance().setArmed(true);
			doAction(doorID, false);
			/*
			 *  chiusura/spegnimento forzata di tutti gli oggetti
			 *  non influisce su allarme perchè il fatto che sia armato non c'entra con lo stato
			 *  CONTROLLARE COLLEGAMENTO CON ADAPTER
			 */
			for(Object object: objects) {
					adapter.triggerAction(object, false);
			}
		}
	}

	public void isHome(String doorID, String code) {
		if(!atHome) {
				for(Object object: objects) {	
					Door door = (Door)object;
					if(object.getObjectID().equals(doorID)) {						
						if(code.equalsIgnoreCase(door.getCode())) {
							atHome = true; // flag attivo			
							doAction(doorID, true); // sbloccare porta
							break;
						}
						else {
							//richiedere codice all'utente
							handler.manageWrongCode();
						}					
					}
				}
				// disarmare allarme
				Alarm.getInstance().setArmed(false);			
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
							if(handler.userNotifies("Le finestre sono aperte.\nIl sistema vorrebbe accendere i caloriferi, confermi?")) {
								if(handler.userNotifies("Vuoi anche chiudere le finestre?"))
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
						else if(handler.userNotifies("L'allarme è armato.\nVuoi comunque aprire le finestre?"))
							if(handler.userNotifies("Sei sicuro?"))
								if(handler.userNotifies("Sei proprio sicuro?"))
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
									userDecision = handler.userNotifies("L'aria è sporca.\nPerò i caloriferi sono accesi.\nVuoi comunque aprire le finestre?");
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
						else if(Alarm.isCreated()) {
							if(handler.userNotifies("C'è una fuga di gas.\nPerò l'allarme è armato.\nApro comunque le finestre?")) {
								if(window.getShader().isActive())
									adapter.triggerAction(window.getShader(), false);
								adapter.triggerAction(window, true);			
							}
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
	
	public void setAtHome(boolean atHome) {
		this.atHome = atHome;
	}

	public List<Object> getObjects() {
		return objects;
	}

	public void addObject(Object object) {
		objects.add(object);
	}

	public HandlerFacade getHandlerFaçade() {
		return handler;
	}

	public void setHandlerFaçade(HandlerFacade handlerFaçade) {
		this.handler = handlerFaçade;
	}
}