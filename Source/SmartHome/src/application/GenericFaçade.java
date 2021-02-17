package application;

import domain.DataDescription;

public class GenericFaçade {
	
	//private AutomaticControl automaticControl;
	private DataDescription dataDescription;

	public void manageWriteOnHCFile(String attribute, String value) {
		dataDescription.writeOnFileHC(attribute, value);
		
	}

	public void manageWriteOnHSCFile() {
		
	}
	
	public void manageFlagSettings(String flagName, boolean flagValue) {
		// TODO - implement SettingFaçade.manageFlagSettings
		throw new UnsupportedOperationException();
	}

}