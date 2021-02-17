package application;

import domain.AutomaticControl;
import domain.DataDescription;
import domain.Statistic;

public class GenericFaçade {
	
	private AutomaticControl automaticControl;
	private DataDescription dataDescription;
	private Statistic statistic;

	/**
	 * 
	 * @param fileType
	 * @param attributeName
	 * @param attributeValue
	 */
	public void manageWriteOnFile(String fileType, String attributeName, String attributeValue) {
		// TODO - implement SettingFaçade.manageWriteOnFile
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param flagName
	 * @param flagValue
	 */
	public void manageFlagSettings(String flagName, boolean flagValue) {
		// TODO - implement SettingFaçade.manageFlagSettings
		throw new UnsupportedOperationException();
	}
	
	/**
	 * 
	 * @param function
	 */
	public void manageStatisticOperations(String function) {
		// TODO - implement HomeFaçade.manageStatisticOperations
		throw new UnsupportedOperationException();
	}

}