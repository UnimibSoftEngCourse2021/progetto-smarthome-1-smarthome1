package view;

import java.util.Scanner;

import application.HandlerFa�ade;

public class IsAtHome {
	
	HandlerFa�ade handlerFa�ade;
	Scanner input = new Scanner(System.in);

	public void changeFlag() {
		System.out.println("Stai uscendo o entrando? (home/away)");
		if(input.nextLine().equals("away"))
			handlerFa�ade.manageHomeFlagSettings("away", null);
		else {
			System.out.println("Inserire il codice di sicurezza");
			handlerFa�ade.manageHomeFlagSettings("home", input.nextLine());
		}
	}
	
	public void wrongCode() {
		System.out.println("Codice errato. Reinserirlo");
		handlerFa�ade.manageHomeFlagSettings("home", input.nextLine());
	}
	
}
