package application;

import domain.DataDescription;

public class GenericFa�ade {
	
	//private AutomaticControl automaticControl;
	private DataDescription dataDescription;

	public void manageWriteOnHCFile(String attribute, String value) {
		dataDescription.writeOnFileHC(attribute, value);
		
	}

	public void manageWriteOnHSCFile() {
		
	}
	
	public void manageFlagSettings(String flagName, boolean flagValue) {
		// TODO - implement SettingFa�ade.manageFlagSettings
		throw new UnsupportedOperationException();
	}

}