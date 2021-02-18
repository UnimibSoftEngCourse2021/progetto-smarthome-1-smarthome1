package main.java.view;

import java.util.Scanner;

import application.DataFaçade;
import application.HandlerFaçade;

public class IsAtHomeView {
	
	HandlerFaçade handlerFaçade;
	DataFaçade dataFaçade;
	
	Scanner input = new Scanner(System.in);

	public IsAtHomeView(HandlerFaçade handlerFaçade, DataFaçade dataFaçade) {
		this.handlerFaçade = handlerFaçade;
		this.dataFaçade = dataFaçade;
	}
	public void changeFlag() {
		String doorID = dataFaçade.getObjectsTypeID("DOOR").get(0);
		System.out.println("Stai uscendo o entrando? (home/away)");
		String state = input.nextLine();
		if(state.equals("away"))
			handlerFaçade.manageHomeFlagSettings("away", null, doorID);
		else {
			System.out.println("Inserire il codice di sicurezza");
			handlerFaçade.manageHomeFlagSettings("home", state, doorID);
		}
	}
	
	public void wrongCode() {
		String doorID = dataFaçade.getObjectsTypeID("DOOR").get(0);
		System.out.println("Codice errato. Reinserirlo");
		handlerFaçade.manageHomeFlagSettings("home", input.nextLine(), doorID);
	}
	public void setHandlerFaçade(HandlerFaçade handlerFaçade) {
		this.handlerFaçade = handlerFaçade;		
	}
	
}
