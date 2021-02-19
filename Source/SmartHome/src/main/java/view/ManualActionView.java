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
		List<String[]> objsList = dataFacade.getAllObjs();
		for(String[] obj: objsList)
			System.out.println(obj[0]);
		System.out.println("Selezionare l'oggetto");
		String objName = input.nextLine();
		String objID = "";
		for(String[] obj: objsList)
			if(obj[0].contains(objName))
				objID = obj[1];		
			handlerFacade.manageManualAction(objID);
	}
}
