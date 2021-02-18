package main.java.view;

import java.util.List;
import java.util.Scanner;

import application.DataFa�ade;

public class ObjectStateView {
	
	DataFa�ade dataFa�ade;
	Scanner input = new Scanner(System.in);

	public ObjectStateView(DataFa�ade dataFa�ade) {
		this.dataFa�ade = dataFa�ade; 
	}
	public void getObjectState() {
		do {
			System.out.println("Lista degli oggetti: ");
			List<String[]> objects = dataFa�ade.getAllObjects();
			for(String[] object: objects) {
				System.out.println(object);
			}
			System.out.println("Selezionare l'oggetto");
			String objectName = input.nextLine();
			for(String[] object: objects) {
				if(!object[0].contains(objectName)) {
					System.out.println("Input non valido, riprovare");
					break;
				}
				else if(object[0].equals(objectName))
					System.out.println("Stato dell'oggetto: " + object[1]);
			}
			System.out.print("inserire altri oggetti? (s/n)");
		} while(!input.nextLine().equals("n"));
		
	}
}
