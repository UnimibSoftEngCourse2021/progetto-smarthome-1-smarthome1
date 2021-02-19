package view;

import java.util.Scanner;

import application.DataFacade;
import application.HandlerFacade;

public class IsAtHomeView {
	
	HandlerFacade handlerFacade;
	DataFacade dataFacade;
	
	Scanner input = new Scanner(System.in);

	public IsAtHomeView(HandlerFacade handlerFaçade, DataFacade dataFaçade) {
		this.handlerFacade = handlerFaçade;
		this.dataFacade = dataFaçade;
	}
	public void changeFlag() {
		String doorID = dataFacade.getObjectsTypeID("DOOR").get(0);
		System.out.println("Stai uscendo o entrando? (home/away)");
		String state = input.nextLine();
		if(state.equals("away"))
			handlerFacade.manageHomeFlagSettings("away", null, doorID);
		else {
			System.out.println("Inserire il codice di sicurezza");
			String code = input.nextLine();
			handlerFacade.manageHomeFlagSettings("home", code, doorID);
		}
	}
	
	public void wrongCode() {
		String doorID = dataFacade.getObjectsTypeID("DOOR").get(0);
		System.out.println("Codice errato. Reinserirlo");
		handlerFacade.manageHomeFlagSettings("home", input.nextLine(), doorID);
	}
	
	public void setHandlerFaçade(HandlerFacade handlerFacade) {
		this.handlerFacade = handlerFacade;		
	}
	
}
