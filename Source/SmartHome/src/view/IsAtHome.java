package view;

import java.util.Scanner;

import application.HandlerFaçade;

public class IsAtHome {
	
	HandlerFaçade handlerFaçade;
	Scanner input = new Scanner(System.in);

	public void changeFlag() {
		System.out.println("Stai uscendo o entrando? (home/away)");
		if(input.nextLine().equals("away"))
			handlerFaçade.manageHomeFlagSettings("away", null);
		else {
			System.out.println("Inserire il codice di sicurezza");
			handlerFaçade.manageHomeFlagSettings("home", input.nextLine());
		}
	}
	
	public void wrongCode() {
		System.out.println("Codice errato. Reinserirlo");
		handlerFaçade.manageHomeFlagSettings("home", input.nextLine());
	}
	
}
