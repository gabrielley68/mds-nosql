package MDS.NoSQL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

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
		} while(result < 0 || result > choices.length);
		
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
	
	public static int getIndexChoice(int list_size) {
		int result = -1;
		
		do {
			System.out.println("Choisissez le numéro d'un des éléments ci-dessus");
			result = getScanner().nextInt(); 
		} while (result < 0 || result > list_size);
		
		return result - 1;
	}
	
	public static String getValue(String label) {
		System.out.print(label + " : ");
		return getScanner().nextLine();
	}
}
