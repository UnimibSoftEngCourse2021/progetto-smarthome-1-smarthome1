package domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class DataDescription {

	private static final String HCFILENAME = "homeConfig.txt";
	private static final String HSCFILENAME = "heatSystemConfig.txt";
	private File fileHC = new File(HCFILENAME);
	private File fileHSC = new File(HSCFILENAME);
	
	public String getHCFILENAME() {
		return HCFILENAME;
	}

	public String getHSCFILENAME() {
		return HSCFILENAME;
	}

	/*
	 * metodo che permette di scrivere su un file, poi successivamente letto e interpretato dalla
	 * classe Config, i valori inseriti dall'utente riguardanti le funzionalità della casa
	 */
	public void writeOnFileHC(String attribute, String value) {
		try {
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
				if(fileHC.delete());
				fileHC = new File(HCFILENAME);				
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

	/*
	 * metodo che permette di scrivere su un file, poi successivamente letto e interpretato dalla
	 * classe Config, i valori inseriti dall'utente riguardanti il sistema di riscaldamento
	 */
	public void writeOnFileHSC(String attribute, double[] values) {
		try {
			FileOutputStream f = new FileOutputStream(fileHSC, true);
			PrintWriter outputStream = new PrintWriter(f);				
			if(attribute.equalsIgnoreCase("end")) {
				outputStream.close();
				Config.getInstance().processFileHSC();
				if(fileHSC.delete());
				fileHSC = new File(HSCFILENAME);
			}
			else{
				outputStream.println(":" + attribute.toUpperCase());
				outputStream.println(String.valueOf(values[0]));
				outputStream.println(String.valueOf(values[1]));
				for(int i = 2;i < values.length; i++) {
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