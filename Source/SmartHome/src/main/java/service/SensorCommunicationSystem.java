package service;

import domain.Sensor;

public class SensorCommunicationSystem {

	public void notifySensor(Sensor sensor, boolean newValue) {
		double value = 0.00;
		if(newValue)
			value = 1.00;
		sensor.notifies(value);
	}
	
	/*
	 * dato che non abbiamo l'accesso ai sensori reali non è stato possibile implementare un metodo per la notifica di sensori particolari
	 * come air, movement e temperature che rilevano valori senza monitorare un oggetto in particolare. 
	 * Questi casi sono stati gestiti assegnando dei valori di default durante la fase di test.
	 */
	
}