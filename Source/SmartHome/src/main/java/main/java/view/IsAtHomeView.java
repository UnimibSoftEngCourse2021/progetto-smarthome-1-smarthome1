package main.java.view;

import java.util.Scanner;

import application.DataFa�ade;
import application.HandlerFa�ade;

public class IsAtHomeView {
	
	HandlerFa�ade handlerFa�ade;
	DataFa�ade dataFa�ade;
	
	Scanner input = new Scanner(System.in);

	public IsAtHomeView(HandlerFa�ade handlerFa�ade, DataFa�ade dataFa�ade) {
		this.handlerFa�ade = handlerFa�ade;
		this.dataFa�ade = dataFa�ade;
	}
	public void changeFlag() {
		String doorID = dataFa�ade.getObjectsTypeID("DOOR").get(0);
		System.out.println("Stai uscendo o entrando? (home/away)");
		String state = input.nextLine();
		if(state.equals("away"))
			handlerFa�ade.manageHomeFlagSettings("away", null, doorID);
		else {
			System.out.println("Inserire il codice di sicurezza");
			handlerFa�ade.manageHomeFlagSettings("home", state, doorID);
		}
	}
	
	public void wrongCode() {
		String doorID = dataFa�ade.getObjectsTypeID("DOOR").get(0);
		System.out.println("Codice errato. Reinserirlo");
		handlerFa�ade.manageHomeFlagSettings("home", input.nextLine(), doorID);
	}
	public void setHandlerFa�ade(HandlerFa�ade handlerFa�ade) {
		this.handlerFa�ade = handlerFa�ade;		
	}
	
}
