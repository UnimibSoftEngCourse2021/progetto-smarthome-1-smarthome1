package view;

import java.util.List;
import java.util.Scanner;
import application.DataFaçade;
import application.HandlerFaçade;

public class ManualActionView {
	
	HandlerFaçade handlerFaçade;  
	DataFaçade dataFaçade;
	Scanner input = new Scanner(System.in);

	public ManualActionView(DataFaçade dataFaçade, HandlerFaçade handlerFaçade) {
		this.dataFaçade = dataFaçade;
		this.handlerFaçade = handlerFaçade;
	}
	
	public void performAction() {
		System.out.println("Oggetti disponibili: ");
		List<String[]> objectsList = dataFaçade.getAllObjects();
		for(String[] object: objectsList)
			System.out.println(object[0]);
		System.out.println("Selezionare l'oggetto");
		String objectName = input.nextLine();
		String objectID = "";
		for(String[] object: objectsList)
			if(object[0].contains(objectName))
				objectID = object[1];		
			handlerFaçade.manageManualAction(objectID);
	}
}
