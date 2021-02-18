package main.java.view;

import application.DataFaçade;
import application.GenericFaçade;
import application.HandlerFaçade;
import application.ScenarioFaçade;
import domain.DataDescription;
import domain.ScenariosHandler;

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
