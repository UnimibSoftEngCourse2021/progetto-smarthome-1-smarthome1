package application;

import domain.DataDescription;

public class GenericFaçade {
	
	//private AutomaticControl automaticControl;
	private DataDescription dataDescription;

	public void manageWriteOnFile(String attribute, String value) {
		dataDescription.writeOnFileHC(attribute, value);
		
	}

	public void manageFlagSettings(String flagName, boolean flagValue) {
		// TODO - implement SettingFaçade.manageFlagSettings
		throw new UnsupportedOperationException();
	}

	public void manageStatisticOperations(String function) {
		// TODO - implement HomeFaçade.manageStatisticOperations
		throw new UnsupportedOperationException();
	}

}