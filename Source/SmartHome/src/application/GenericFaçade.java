package application;

import domain.AutomaticControl;
import domain.DataDescription;
import domain.Statistic;

public class GenericFa�ade {
	
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
		// TODO - implement SettingFa�ade.manageWriteOnFile
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param flagName
	 * @param flagValue
	 */
	public void manageFlagSettings(String flagName, boolean flagValue) {
		// TODO - implement SettingFa�ade.manageFlagSettings
		throw new UnsupportedOperationException();
	}
	
	/**
	 * 
	 * @param function
	 */
	public void manageStatisticOperations(String function) {
		// TODO - implement HomeFa�ade.manageStatisticOperations
		throw new UnsupportedOperationException();
	}

}