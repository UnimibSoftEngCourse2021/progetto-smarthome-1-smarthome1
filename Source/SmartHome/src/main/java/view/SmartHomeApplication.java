package main.java.view;

import main.java.application.DataFaçade;
import main.java.application.GenericFaçade;
import main.java.application.HandlerFaçade;
import main.java.application.ScenarioFaçade;
import main.java.domain.DataDescription;
import main.java.domain.ScenariosHandler;

public class SmartHomeApplication {
	
	public static void main(String[] args) {	
		Menu menu;
		
		//inizializzazione oggetti di dominio singoli
		DataDescription dd = new DataDescription();
		ScenariosHandler sh = new ScenariosHandler();
		//inizializzazione oggetti facade
		DataFaçade df = new DataFaçade(sh);
		GenericFaçade gf = new GenericFaçade(dd);
		ScenarioFaçade sf = new ScenarioFaçade(sh);
		HandlerFaçade hf = new HandlerFaçade();
		menu = new Menu(df, gf, sf, hf);
		menu.start();
		
	}				
}
