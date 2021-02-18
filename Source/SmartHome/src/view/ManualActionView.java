package view;

import java.util.List;
import java.util.Scanner;
import application.DataFa�ade;
import application.HandlerFa�ade;

public class ManualActionView {
	
	HandlerFa�ade handlerFa�ade;  
	DataFa�ade dataFa�ade;
	Scanner input = new Scanner(System.in);

	public ManualActionView(DataFa�ade dataFa�ade, HandlerFa�ade handlerFa�ade) {
		this.dataFa�ade = dataFa�ade;
		this.handlerFa�ade = handlerFa�ade;
	}
	
	public void performAction() {
		System.out.println("Oggetti disponibili: ");
		List<String[]> objectsList = dataFa�ade.getAllObjects();
		for(String[] object: objectsList)
			System.out.println(object[0]);
		System.out.println("Selezionare l'oggetto");
		String objectName = input.nextLine();
		String objectID = "";
		for(String[] object: objectsList)
			if(object[0].contains(objectName))
				objectID = object[1];		
			handlerFa�ade.manageManualAction(objectID);
	}
}
