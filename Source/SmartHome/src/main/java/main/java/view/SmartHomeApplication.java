package main.java.view;

import application.DataFa�ade;
import application.GenericFa�ade;
import application.HandlerFa�ade;
import application.ScenarioFa�ade;
import domain.DataDescription;
import domain.ScenariosHandler;

public class SmartHomeApplication {
	
	public static void main(String[] args) {	
		Menu menu;
		
		//inizializzazione oggetti di dominio singoli
		DataDescription dd = new DataDescription();
		ScenariosHandler sh = new ScenariosHandler();
		//inizializzazione oggetti facade
		DataFa�ade df = new DataFa�ade(sh);
		GenericFa�ade gf = new GenericFa�ade(dd);
		ScenarioFa�ade sf = new ScenarioFa�ade(sh);
		HandlerFa�ade hf = new HandlerFa�ade();
		menu = new Menu(df, gf, sf, hf);
		menu.start();
		
	}				
}
