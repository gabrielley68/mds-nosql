package MDS.NoSQL;

import java.util.Scanner;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class App {
	
	public static void main(String[] args) {
		if(args.length != 3) {
			System.out.println("Pour se connecter à la base de données, veuillez donner les arguments : host port database");
			System.exit(1);
		}
		String host = args[0];
		String port = args[1];
		String db = args[2];
		
		MongoClient mongoClient = MongoClients.create(
			"mongodb://" + host + ":" + port
		);
		
		MongoDatabase database = mongoClient.getDatabase(db);
		
		Scanner scanner = new Scanner(System.in);
		
		while(true) {
			System.out.println("Pour rechercher un document tapez 1, pour en insérer un tapez 2");
			int res = scanner.nextInt();
			if(res == 1) {
				System.out.println("rechercher un document");
			}
			if(res == 2) {
				System.out.println("insérer un document");
			}
		}
		
		
	}
}