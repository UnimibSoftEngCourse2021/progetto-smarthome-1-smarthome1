package view;
import java.util.Scanner;

import application.GenericFacade;

public class HSConfigView {
	
	private GenericFacade gf;
	
	
	public HSConfigView(GenericFacade gf) {
		this.gf = gf;
	}


	public void heatSystemConfig() {
		Scanner input = new Scanner(System.in);
		int tempPassiva;
		int tempAttiva;
		String giorno;
		double sceltaTemp = 0.00;
		//double[][] matrix = new double[7][23];
		double[] setProgDay = new double[26];
		// controlla che l'utente ha inserito almeno la tempAttiva Passiva di tutti i giorni
		boolean[] checkProgDay = new boolean[7];		
		for(int i=0; i < checkProgDay.length; i++)
			checkProgDay[i] = false;				
			boolean fine = false;
			boolean notAllProg;
			while(!fine) {
				do {
				notAllProg = false;				
				System.out.println("Scegliere il giorno della settimana che si vuole programmare: ");
				giorno = input.nextLine();			
			
					do {
						System.out.println("Inserire la temperatura passiva: ");
						tempPassiva = input.nextInt();
						if(tempPassiva < 7 || tempPassiva > 40) 
							System.out.print("La temperatura inserita deve essere compresa tra 7 e 40 °C");
					} while(tempPassiva < 7 || tempPassiva > 40);					
					do {
						System.out.println("Inserire la temperatura attiva: ");
						tempAttiva = input.nextInt();
						if(tempAttiva < 7 || tempAttiva > 40) 
							System.out.print("La temperatura inserita deve essere compresa tra 7 e 40 °C");
					} while(tempAttiva < 7 || tempAttiva > 40);					
					setProgDay[0] = tempPassiva;
					setProgDay[1] = tempAttiva;					
					for(int i = 2; i < setProgDay.length; i++) 
						setProgDay[i] = 0;					
					System.out.println("Inserire la programmazione per ogni ora: ");
					for(int i = 2; i < setProgDay.length; i++) {
						do {
							System.out.println("Inserire 0 per temperatura Passiva o 1 per Attiva per le ore: " + i);
							setProgDay[i] = input.nextDouble();
							if(sceltaTemp != 0 || sceltaTemp != 1)
								System.out.println("Valore non valido!");
						} while(sceltaTemp != 0 || sceltaTemp != 1);
					}					
					gf.manageWriteOnHSCFile(giorno, setProgDay);					
					switch(giorno) {
					case "lunedi":
						checkProgDay[0] = true;
						break;
					case "martedi":
						checkProgDay[1] = true;
						break;
					case "mercoledi":
						checkProgDay[2] = true;
						break;
					case "giovedi":
						checkProgDay[3] = true;
						break;
					case "venerdi'":
						checkProgDay[4] = true;
						break;
					case "sabato":
						checkProgDay[5] = true;
						break;
					case "domenica":
						checkProgDay[6] = true;
						break;
					}					
					for(int i = 0; i < checkProgDay.length; i++) {
						if(checkProgDay[i] == false) {
							notAllProg = true;
							break;
						}
					}
					if(notAllProg == true)
						System.out.println("Inserire i giorni rimanenti");									
				} while(!notAllProg);				
				System.out.print("continuare con la programmazione dei giorni? (s/n)");
				if(input.nextLine().equalsIgnoreCase("n"))
					fine = true;
			}
			gf.manageWriteOnHSCFile("end", null);
			input.close();
	}
}
