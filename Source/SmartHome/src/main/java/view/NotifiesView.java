package view;

import java.util.Scanner;

public class NotifiesView {
	
	Scanner input = new Scanner(System.in);

	public boolean notification(String message) {
		System.out.println("E' arrivata una notifica! \n");
		System.out.println("\t" + message);
		String answer = input.nextLine();
		return answer.equalsIgnoreCase("si");
	}
	
}
