package main.java.application;

import main.java.domain.DataDescription;

public class GenericFa�ade {
	

	private DataDescription dataDescription;

	public GenericFa�ade(DataDescription dataDescription) {
		this.dataDescription = dataDescription;
	}
	public void manageWriteOnHCFile(String attribute, String value) {
		dataDescription.writeOnFileHC(attribute, value);
		
	}

	public void manageWriteOnHSCFile(String attribute, double[] values) {
		dataDescription.writeOnFileHSC(attribute, values);
	}

}