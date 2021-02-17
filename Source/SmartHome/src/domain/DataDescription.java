package domain;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class DataDescription {

	//private static DataDescription dataDescription = null;
	private final String HCFILENAME = "homeConfig.txt";
	private final String HSCFILENAME = "heatSystemConfig.txt";
	
/*
	private DataDescription() {
		
	}
	
	public static DataDescription getInstance() {
		if(dataDescription == null)
			dataDescription = new DataDescription();
		return dataDescription;
	}
*/
	public String getHCFILENAME() {
		return HCFILENAME;
	}

	public String getHSCFILENAME() {
		return HSCFILENAME;
	}

	public void writeOnFileHC(String attribute, String value) {
		PrintWriter outputStream = null;
		try {
			outputStream = new PrintWriter(new FileOutputStream(HCFILENAME, true));
		} catch (FileNotFoundException e) {
			System.out.println("errore da gestire durante il debug");
		}
		
		if(attribute.equalsIgnoreCase("code") || attribute.equalsIgnoreCase("heaterID") || attribute.equalsIgnoreCase("roomNameSensor")) {
			outputStream.println(value);
			outputStream.close();
		}
		else if(attribute.equalsIgnoreCase("end")) {
			outputStream.close();
			Config.getInstance().processFileHC();
		}	
		else {
		//in generale per ogni attributo ho sempre il nome
		outputStream.println(":" + attribute.toUpperCase());
		outputStream.println(value);
		outputStream.close();
		}
		
	}

	public void writeOnFileHSC(String attribute, double[] values) {
		PrintWriter outputStream = null;
		try {
			outputStream = new PrintWriter(new FileOutputStream(HSCFILENAME, true));
		} catch (FileNotFoundException e) {
			System.out.println("errore da gestire durante il debug");
		}
		
		if(attribute.equalsIgnoreCase("end")) {
			outputStream.close();
			Config.getInstance().processFileHSC();
		}
		else{
			outputStream.println(":" + attribute.toUpperCase());
			outputStream.println(String.valueOf(values[0]));
			outputStream.println(String.valueOf(values[1]));
			for(int i = 2;i < 26; i++) {
				outputStream.println(String.valueOf(values[i]));
			}
			outputStream.close();
		}	
	}
}