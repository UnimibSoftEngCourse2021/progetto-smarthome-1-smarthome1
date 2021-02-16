package view;

import java.util.List;
import java.util.Scanner;

import application.DataFaçade;

// importare handlerFaçade

public class ManualActionView {
	
	DataFaçade dataFaçade;
	Scanner input = new Scanner(System.in);

	public void performAction() {
		System.out.println("Oggetti disponibili: ");
		List<String[]> objectsList = dataFaçade.getAllObjects();
		for(String[] object: objectsList)
			System.out.println(object[0]);
		System.out.println("Selezionare l'oggetto");
		String objectName = input.nextLine();
		String objectID;
		for(String[] object: objectsList)
			if(object[0].contains(objectName))
				objectID = object[1];		
		System.out.println("Selezionare l'azione da effettuare (on/off)");
		String action = input.nextLine();
		if(action.equals("on"))
			HandlerFaçade.manageManualAction(objectID, true);
		else
			HandlerFaçade.manageManualAction(objectID, false);
	}
	
}
