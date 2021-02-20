package domain;

import java.util.ArrayList;
import java.util.List;

import application.HandlerFacade;
import domain.Obj.ObjType;
import domain.Sensor.AirState;
import service.ObjCommunicationSystem;


public class ConflictHandler {
	
	private HandlerFacade handler;
	
	private static ConflictHandler conflictHandler = null; //singleton instance
	private boolean atHome = true; // flag per indicare la presenza o meno dell'utente a casa
	
	private List<Obj> objs;
	private ObjCommunicationSystem adapter;
	
	private ConflictHandler() {
		objs = new ArrayList<>();
		adapter = new ObjCommunicationSystem();
	}
	
	/*
	 * metodo che restituisce l'istanza dell'oggetto, se necessario viene anche creato l'oggetto
	 * questo metodo è l'applicazione del pattern singleton
	 */
	public static ConflictHandler getInstance() {
		if(conflictHandler == null)
			conflictHandler = new ConflictHandler();
		return conflictHandler;
	}

	/*
	 * metodo per impostare lo stato ad away, armare l'allarme e bloccare la porta
	 * il codice inserito non è influente
	 */
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
			for(Obj obj: objs) {
					adapter.triggerAction(obj, false);
			}
		}
	}

	/*
	 * metodo per impostare lo stato ad home, disarmare l'allarme e sbloccare la porta
	 * verifica se il codice d'ingresso è valido
	 */
	public void isHome(String doorID, String code) {
		if(!atHome) {
				for(Obj obj: objs) {	
					
					if(obj.getObjID().equals(doorID)) {	
						Door door = (Door)obj;
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

	/*
	 * metodo per effettuare l'azione su un oggetto
	 * l'azione è specifica in base al tipo di oggetto, considerando i possibili conflitti generati
	 */
	public void doAction(String objID, boolean changeState) {
		for(Obj obj: objs) {
			if(obj.getObjID().equals(objID)) { 
				if(changeState) {
					switch(obj.getObjType()) {
					case ALARM:
					case DOOR:
						adapter.triggerAction(obj, true);
						break;
					case HEATER:
						/*
						 * prima di accendere il calorifero controllo che tutte le finestre della stanza siano chiuse
						 * invio notifica all'utente per chiedere cosa fare
						 */
						boolean windowOpen = false;	
						List<Obj> windows = obj.getRoom().getObjs(ObjType.WINDOW);
						for(Obj window: windows) {
							if(window.isActive()) {							
								windowOpen = true;
								break;
							}															
						}
						if(windowOpen) {
							if(handler.userNotifies("Le finestre sono aperte.\nIl sistema vorrebbe accendere i caloriferi, confermi?")) {
								if(handler.userNotifies("Vuoi anche chiudere le finestre?"))
									for(Obj window: windows) 
										adapter.triggerAction(window, false);
								adapter.triggerAction(obj, true);
							}
						}
						else
							
							adapter.triggerAction(obj, true);
						break;
					case SHADER: // non generano conflitti
					case LIGHT:
						adapter.triggerAction(obj, true);
						break;
					case WINDOW:
						if(Alarm.isCreated() && !Alarm.getInstance().isArmed())							
							adapter.triggerAction(obj, true);
						else if(handler.userNotifies("L'allarme è armato.\nVuoi comunque aprire le finestre?") &&
								(handler.userNotifies("Sei sicuro?")) &&
								(handler.userNotifies("Sei proprio sicuro?")))
							adapter.triggerAction(obj, true);
						break;
					default:
						break;
					}
				}
				else // se si vuole spegnere qualcosa non ci sono conflitti
					adapter.triggerAction(obj, false);
				break;
			}
		}
	}
	
	/*
	 * metodo per eseguire accensione e spegnimento di luci e shader in modo automatico
	 * a seconda della dayMode 
	 */
	public void doAction(String lightID, boolean dayMode, boolean changeState) {
		for(Obj obj: objs) {
			if(obj.getObjID().equals(lightID)) { 
				if(changeState) {
					if(!dayMode)
						adapter.triggerAction(obj, true);
					else {
						List<Obj> shaders = obj.getRoom().getObjs(ObjType.SHADER);
						boolean shaderOpen = false; 
						for(Obj shader: shaders) {
							if(!shader.isActive()) { // is active = fermano la luce
								shaderOpen = true;
								break;
							}
						}
						if(!shaderOpen) {
							adapter.triggerAction(obj, true);
						}
					}
				}
				else
					adapter.triggerAction(obj, false);
				break;
			}
		}
	}

	/*
	 * metodo per aprire le finestre quando reso necessario dal valore del sensore di pulizia dell'aria
	 */
	public void doAction(String windowID, AirState airState, boolean changeState) {
		// a seconda del valore di airState l'azione ha priorità diversa
		for(Obj obj: objs) {			
			if(obj.getObjID().equals(windowID)) {
				Window window = (Window)obj;
				if(changeState) {			
					if(airState.equals(AirState.POLLUTION)) {
						if(Alarm.isCreated() && !Alarm.getInstance().isArmed()) {
							boolean userDecision = false;
							List<Obj> heaters = window.getRoom().getObjs(ObjType.HEATER);
							for(Obj heater: heaters)
								if(heater.isActive()) {
									userDecision = handler.userNotifies("L'aria è sporca.\nPerò i caloriferi sono accesi.\nVuoi comunque aprire le finestre?");
									break;
								}
							if(window.getShader().isActive() && userDecision) 
								adapter.triggerAction(window.getShader(), false);
							adapter.triggerAction(window, true);													
						}
						break;
					}
					else if(airState.equals(AirState.GAS)) {
						/*
						 * nel caso ci sia una fuga di gas vengono sempre aperte le finestre
						 */
						List<Obj> lights = window.getRoom().getObjs(ObjType.LIGHT);
						for(Obj light: lights)
							adapter.triggerAction(light, false);
						if(Alarm.isCreated() && !Alarm.getInstance().isArmed()) {
							if(window.getShader().isActive())
								adapter.triggerAction(window.getShader(), false);
							adapter.triggerAction(window, true);
						}
						else if(Alarm.isCreated()) {
							if(handler.userNotifies("C'è una fuga di gas.\nPerò l'allarme è armato.\nApro comunque le finestre?") &&
									window.getShader().isActive()) {
								adapter.triggerAction(window.getShader(), false);
								adapter.triggerAction(window, true);
							}
						}						
						break;
					}
				}
				adapter.triggerAction(window, false);
			}
		}
	}

	/*
	 * metodo per gestire un'azione manuale voluta dall'utente
	 */
	public void doManualAction(String objID) {
		for(Obj obj: objs) {
			if(obj.getObjID().equals(objID))
				adapter.triggerAction(obj, !obj.isActive());
		}
	}
	
	public boolean isAtHome() {
		return atHome;
	}
	
	public void setAtHome(boolean atHome) {
		this.atHome = atHome;
	}

	public List<Obj> getObjs() {
		return objs;
	}

	public void addObj(Obj obj) {
		objs.add(obj);
	}

	public HandlerFacade getHandlerFacade() {
		return handler;
	}

	public void setHandlerFacade(HandlerFacade handlerFacade) {
		this.handler = handlerFacade;
	}
	
	public static void clean() {
		conflictHandler = null;
	}

	public void setHandler(HandlerFacade handler) {
		this.handler = handler;
	}
}