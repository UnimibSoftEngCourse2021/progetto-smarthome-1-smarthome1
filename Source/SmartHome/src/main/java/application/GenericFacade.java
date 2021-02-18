package application;

import domain.DataDescription;

public class GenericFacade {
	

	private DataDescription dataDescription;

	public GenericFacade(DataDescription dataDescription) {
		this.dataDescription = dataDescription;
	}
	public void manageWriteOnHCFile(String attribute, String value) {
		dataDescription.writeOnFileHC(attribute, value);
		
	}

	public void manageWriteOnHSCFile(String attribute, double[] values) {
		dataDescription.writeOnFileHSC(attribute, values);
	}

}