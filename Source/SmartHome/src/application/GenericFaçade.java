package application;

import domain.DataDescription;

public class GenericFa�ade {
	
	//private AutomaticControl automaticControl;
	private DataDescription dataDescription;

	public void manageWriteOnFile(String attribute, String value) {
		dataDescription.writeOnFileHC(attribute, value);
		
	}

	public void manageFlagSettings(String flagName, boolean flagValue) {
		// TODO - implement SettingFa�ade.manageFlagSettings
		throw new UnsupportedOperationException();
	}

	public void manageStatisticOperations(String function) {
		// TODO - implement HomeFa�ade.manageStatisticOperations
		throw new UnsupportedOperationException();
	}

}