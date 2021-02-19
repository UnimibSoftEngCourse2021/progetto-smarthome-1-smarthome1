package view;

import java.util.List;
import java.util.Scanner;

import application.DataFacade;

public class ObjStateView {
	
	DataFacade dataFacade;
	Scanner input = new Scanner(System.in);

	public ObjStateView(DataFacade dataFacade) {
		this.dataFacade = dataFacade; 
	}
	public void getObjState() {
		do {
			System.out.println("Lista degli oggetti: ");
			List<String[]> objs = dataFacade.getAllObjs();
			for(String[] obj: objs) {
				System.out.println(obj);
			}
			System.out.println("Selezionare l'oggetto");
			String objName = input.nextLine();
			for(String[] obj: objs) {
				if(!obj[0].contains(objName)) {
					System.out.println("Input non valido, riprovare");
					break;
				}
				else if(obj[0].equals(objName))
					System.out.println("Stato dell'oggetto: " + obj[1]);
			}
			System.out.print("inserire altri oggetti? (s/n)");
		} while(!input.nextLine().equals("n"));
		
	}
}
