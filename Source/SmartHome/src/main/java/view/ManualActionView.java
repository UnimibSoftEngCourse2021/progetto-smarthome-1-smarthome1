package view;

import java.util.List;
import java.util.Scanner;

import application.DataFacade;
import application.HandlerFacade;

public class ManualActionView {
	
	HandlerFacade handlerFacade;  
	DataFacade dataFacade;
	Scanner input = new Scanner(System.in);

	public ManualActionView(DataFacade dataFacade, HandlerFacade handlerFacade) {
		this.dataFacade = dataFacade;
		this.handlerFacade = handlerFacade;
	}
	
	public void performAction() {
		System.out.println("Oggetti disponibili: ");
		List<String[]> objectsList = dataFacade.getAllObjects();
		for(String[] object: objectsList)
			System.out.println(object[0]);
		System.out.println("Selezionare l'oggetto");
		String objectName = input.nextLine();
		String objectID = "";
		for(String[] object: objectsList)
			if(object[0].contains(objectName))
				objectID = object[1];		
			handlerFacade.manageManualAction(objectID);
	}
}
