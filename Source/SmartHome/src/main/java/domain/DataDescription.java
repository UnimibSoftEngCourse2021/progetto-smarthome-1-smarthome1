package domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class DataDescription {

	private final String HCFILENAME = "homeConfig.txt";
	private final String HSCFILENAME = "heatSystemConfig.txt";
	private File fileHC;
	private File fileHSC;
	public String getHCFILENAME() {
		return HCFILENAME;
	}

	public String getHSCFILENAME() {
		return HSCFILENAME;
	}

	public void writeOnFileHC(String attribute, String value) {
		try {
			if(fileHC == null) 
				fileHC = new File(HCFILENAME);
			FileOutputStream f = new FileOutputStream(fileHC, true);
			PrintWriter outputStream = new PrintWriter(f);
		
			if(attribute.equalsIgnoreCase("code") 
					|| attribute.equalsIgnoreCase("heaterID") 
					|| attribute.equalsIgnoreCase("roomNameSensor")
					|| attribute.equalsIgnoreCase("floor")) {
				outputStream.println(value);
				outputStream.close();
			}
			else if(attribute.equalsIgnoreCase("end")) {
				outputStream.close();
				Config.getInstance().processFileHC();
				fileHC.delete();
			}	
			else {
			//in generale per ogni attributo ho sempre il nome
			outputStream.println(":" + attribute.toUpperCase());
			outputStream.println(value);
			outputStream.close();
			}
		} catch (FileNotFoundException e) {
			System.err.println("Errore: il file non è stato trovato");
			System.exit(0);
		}
		
	}

	public void writeOnFileHSC(String attribute, double[] values) {
		try {
			if(fileHSC == null)
				fileHSC = new File(HSCFILENAME);
			FileOutputStream f = new FileOutputStream(fileHSC, true);
			PrintWriter outputStream = new PrintWriter(f);				
			if(attribute.equalsIgnoreCase("end")) {
				outputStream.close();
				Config.getInstance().processFileHSC();
				fileHSC.delete();
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
		} catch (FileNotFoundException e) {
			System.err.println("Errore: il file non è stato trovato");
			System.exit(0);
		}
	}

	public File getFileHC() {
		return fileHC;
	}

	public File getFileHSC() {
		return fileHSC;
	}
}