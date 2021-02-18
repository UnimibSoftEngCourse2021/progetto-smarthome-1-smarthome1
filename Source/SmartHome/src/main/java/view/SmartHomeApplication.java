package view;

import application.DataFacade;
import application.GenericFacade;
import application.HandlerFacade;
import application.ScenarioFacade;
import domain.DataDescription;
import domain.ScenariosHandler;

public class SmartHomeApplication {
	
	public static void main(String[] args) {	
		Menu menu;
		
		//inizializzazione oggetti di dominio singoli
		DataDescription dd = new DataDescription();
		ScenariosHandler sh = new ScenariosHandler();
		//inizializzazione oggetti facade
		DataFacade df = new DataFacade(sh);
		GenericFacade gf = new GenericFacade(dd);
		ScenarioFacade sf = new ScenarioFacade(sh);
		HandlerFacade hf = new HandlerFacade();
		menu = new Menu(df, gf, sf, hf);
		menu.start();
		
	}				
}
