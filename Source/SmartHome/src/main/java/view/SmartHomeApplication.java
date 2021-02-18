package main.java.view;

import main.java.application.DataFa�ade;
import main.java.application.GenericFa�ade;
import main.java.application.HandlerFa�ade;
import main.java.application.ScenarioFa�ade;
import main.java.domain.DataDescription;
import main.java.domain.ScenariosHandler;

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
