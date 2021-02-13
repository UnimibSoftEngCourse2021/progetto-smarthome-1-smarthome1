package domain;

import java.util.List;

import domain.Object.ObjectType;
import service.ObjectCommunicationAdapter;

public class ConflictHandler {
	
	private boolean dayMode;
	/*
	 * variabile per identificare modalità giorno/notte per accensione luci
	 * gli orari sono flessibili e devono essere scelti dall'utente
	 */
	private boolean atHome = true;
	
	private AutomaticControl automaticControl;
	private List<Object> objects;
	private List<Scenario> scenario;
	private ObjectCommunicationAdapter adapter;

	public boolean isDayMode() {
		return dayMode;
	}

	public void setDayMode(boolean dayMode) {
		this.dayMode = dayMode;
	}
	
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
			if(object.getObjectID().equals(objectID)) // associo l'id dell'oggetto passato all'oggetto attuale del for
				if(changeState) {
					switch(object.getObjectType()) {
					case ALARM:
					case DOOR:
						adapter.triggerAction(object, true);
						break;
					case HEATER:
						/*
						 * prima di accendere il calorifero controllo che tutte le finestre della stanza siano chiuse
						 */
						for(Object window: objects) {
							if(object.getReferencedRoomID().equals(window.getReferencedRoomID()) && window.getObjectType().equals(ObjectType.WINDOW) && window.isActive() == false)								
								adapter.triggerAction(object, true);
						}
						break;
					case LIGHT:
						/*
						 * prima di accendere le luci controllo che non sia giorno
						 * aggiungere variabile per identificare giorno/notte
						 */
						if(!dayMode)
							adapter.triggerAction(object, true);
						break;
					case WINDOW: // gestito nell'altro metodo doAction
					default:
						break;
					}
				}
				else // se si vuole spegnere qualcosa non ci sono conflitti
					adapter.triggerAction(object, false);
		}
	}
	
	/*
	 * chiama update nel caso di alarm
	 */
	/**
	 * 
	 * @param objectID
	 * @param airState
	 * @param changeState
	 */
	public void doAction(String windowID, String airState, boolean changeState) {
		// a seconda di airState azione ha priorità diversa
		for(Object window: objects) {
			if(window.getObjectID().equals(windowID)) // associo l'id dell'oggetto passato all'oggetto attuale del for
				if(changeState) {			
					if(airState.equalsIgnoreCase("pollution")) {
						for(Object object: objects) {
							if(object.getReferencedRoomID().equals(window.getReferencedRoomID())) {
								switch(object.getObjectType()) {
								case ALARM:
									Alarm alarm = (Alarm)object;
									if(!alarm.isArmed())
										adapter.triggerAction(window, true);
									else
										adapter.triggerAction(window, false);
									break;
								case HEATER:
								case DOOR:
								case LIGHT:
								case WINDOW:
									if(object.isActive() == false)
										adapter.triggerAction(window, true);
									else
										adapter.triggerAction(window, false);
									break;
								default:
									break;
								/*
								 * come gestire il caso in cui all'attivazione della pulizia c'è qualcuno in stanza?
								 * handler non conosce sensore
								 * potremmo creare un oggetto relativo al sensore di movimento
								 */
								}
							}								
						}
					}
					else {
						/*
						 * nel caso ci sia una fuga di gas aprire sempre le finestre
						 * considerare un possibile comportamento diverso per allarme
						 */
						adapter.triggerAction(window, true);
					}
				}
				else // se si vogliono chiudere le finestre non ci sono conflitti
					adapter.triggerAction(window, false); 
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
	
}