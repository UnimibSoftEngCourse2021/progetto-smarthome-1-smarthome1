package main.java.domain;

import service.DatabaseCommunicationSystem;

public class Statistic {
	
	private DatabaseCommunicationSystem database;

	public DatabaseCommunicationSystem getDatabase() {
		return database;
	}

	public void setDatabase(DatabaseCommunicationSystem database) {
		this.database = database;
	}

	/**
	 * 
	 * @param timeRange
	 */
	public double average(int timeRange) {
		// TODO - implement Statistic.average
		throw new UnsupportedOperationException();
	}

}