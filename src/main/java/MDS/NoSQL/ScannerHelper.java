package MDS.NoSQL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class ScannerHelper {
	private ScannerHelper() {};
	
	private static Scanner SCANNER = new Scanner(System.in);
	
	public static Scanner getScanner() {
		return SCANNER;
	}
	
	public static Integer getAction(Object[] choices) {
		Integer result = null;
		
		do {
			System.out.println("Choisissez une action à effectuer");
			
			for(int i = 0; i < choices.length; i++) {
				System.out.println(String.valueOf(i) + " : " + choices[i].toString());
			}
			
			try {
				result = getScanner().nextInt();
			} catch (InputMismatchException e ) {
				System.err.println(e.getMessage());
			}
		} while(result > 0 && result < choices.length);
		
		return result;
	}
	
	public static String getChoice(ArrayList<String> choices) {
		
		String result = null;
		do {
			System.out.println("Choisissez un élément dans la liste ci-dessus");
			result = getScanner().nextLine();
			
		} while (!choices.contains(result));
		
		return result;
	}
}
