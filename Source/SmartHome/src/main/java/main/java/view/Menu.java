package main.java.view;

import java.util.Scanner;

import application.DataFaçade;
import application.GenericFaçade;
import application.HandlerFaçade;
import application.ScenarioFaçade;
import domain.ConflictHandler;

public class Menu {

	private ConfigView configView;
	private ScenarioView scenarioView;
	private ObjectStateView objectStateView;
	private ManualActionView manualActionView; 
	private IsAtHomeView isAtHomeView;
	
	public Menu(DataFaçade df, GenericFaçade gf, ScenarioFaçade sf, HandlerFaçade hf) {
		configView = new ConfigView(df, gf);
		scenarioView = new ScenarioView(df, sf);
		objectStateView = new ObjectStateView(df);
		manualActionView = new ManualActionView(df, hf);
		isAtHomeView = new IsAtHomeView(hf, df);
		hf.setIsAtHomeView(isAtHomeView);
		isAtHomeView.setHandlerFaçade(hf);
		ConflictHandler.getInstance().setHandlerFaçade(hf);
	}
	public void start() {	
		Scanner input = new Scanner(System.in);
		do {			
			System.out.println("Selezionare l'operazione desiderata scrivendo la parola chiave tra quelle presentate qui sotto");
			System.out.println("config: configurazione dei parametri relativi al sistema");
			System.out.println("heat: configurazione sistema di riscaldamento");
			System.out.println("scenario: attivazione, modifica e eliminazione di scenari");
			System.out.println("stati: visualizzare informazioni relative agli stati degli oggetti");
			System.out.println("azioni: effettuare manualmente azioni sugli oggetti");
			System.out.println("isAtHome: impostare lo stato di home oppure di away");
		
			switch(input.nextLine()) {
			case "config":
				configView.config();							
				break;
			case "heat":
				configView.heatSystemConfig();
				break;
			case "scenario":
				scenarioView.selectOperation();
				break;
			case "stati":
				objectStateView.getObjectState();
				break;
			case "azioni":
				manualActionView.performAction();
				break;
			case "isAtHome":
				isAtHomeView.changeFlag();
				break;
			default:
				System.out.println("Input non valido");
				break;	
			}		
			System.out.println("Si desidera effettuare altre operazioni? (s/n)");		
		} while (input.nextLine().equals("n"));
			input.close();
	}
}
