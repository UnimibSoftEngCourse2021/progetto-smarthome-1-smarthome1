package domain;

import java.io.File;

public class DataDescription {

	private File fileHomeConfig;
	private File fileHeatSystemConfig;
	
	private Config config;

	public File getFileHomeConfig() {
		return fileHomeConfig;
	}

	public void setFileHomeConfig(File fileHomeConfig) {
		this.fileHomeConfig = fileHomeConfig;
	}

	public File getFileHeatSystemConfig() {
		return fileHeatSystemConfig;
	}

	public void setFileHeatSystemConfig(File fileHeatSystemConfig) {
		this.fileHeatSystemConfig = fileHeatSystemConfig;
	}

	/**
	 * 
	 * @param parametersToBeAdded
	 */
	public void writeOnFileHC(int parametersToBeAdded) {
		// TODO - implement DataDescription.writeOnFileHC
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param parametersToBeAdded
	 */
	public void writeOnFileHSC(int parametersToBeAdded) {
		// TODO - implement DataDescription.writeOnFileHSC
		throw new UnsupportedOperationException();
	}

}