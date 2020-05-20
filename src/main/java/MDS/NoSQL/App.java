package MDS.NoSQL;

import java.util.Arrays;
import java.util.Scanner;

import org.bson.Document;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class App {
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		if(args.length != 3) {
			System.out.println("Pour se connecter à la base de données, veuillez donner les arguments : host port database");
			System.exit(1);
		}
		String host = args[0];
		int port = Integer.parseInt(args[1]);
		String db = args[2];
		
		MongoClient mongoClient = MongoClients.create(
			MongoClientSettings.builder()
				.applyToClusterSettings(builder -> 
						builder.hosts(Arrays.asList(new ServerAddress(host, port))))
				.build()
		);
		
		MongoDatabase database = mongoClient.getDatabase(db);
		
		
		while(true) {
			System.out.println("Pour rechercher un document tapez 1, pour en insérer un tapez 2");
			int res = scanner.nextInt();
			if(res == 1 || res == 2) {
				System.out.println("Liste des collections");
				for(String collectionName : database.listCollectionNames()) {
					System.out.println("  - " + collectionName);
				}
				System.out.println("Quelle collection manipuler ?");
				MongoCollection<Document> collection = database.getCollection(scanner.next());
				
				if(res == 1) {
					
				}
				
			}
		}
	}
	
	private static void search(MongoCollection<Document> collection) {
		System.out.println("Sélectionner le champ à filtrer");
		String field = scanner.next();
		System.out.println("Sélectionner un opérateur de comparaison (ex: $lte, $eq, ...");
		String operator = scanner.next();
		System.out.println("Sélectionner la valeur du filtre");
		String value = scanner.next();
		
		FindIterable<Document> filtered_collection = collection.find(
			new Document(field, new Document(operator, value))
		);
		
		for(Document document : filtered_collection) {
			System.out.println(document.toJson());
		}
		
	}
	
	private static void insert(MongoCollection<Document> collection) {
		
	}
}