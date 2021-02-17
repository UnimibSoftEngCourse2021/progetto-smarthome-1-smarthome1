package application;

import domain.DataDescription;

public class GenericFaçade {
	

	private DataDescription dataDescription;

	public GenericFaçade(DataDescription dataDescription) {
		this.dataDescription = dataDescription;
	}
	public void manageWriteOnHCFile(String attribute, String value) {
		dataDescription.writeOnFileHC(attribute, value);
		
	}

	public void manageWriteOnHSCFile(String attribute, double[] values) {
		dataDescription.writeOnFileHSC(attribute, values);
	}

}