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
	
	private static MongoClient getMongoClient(final String host, final int port) {
		return MongoClients.create(
			MongoClientSettings.builder()
			.applyToClusterSettings(builder -> 
					builder.hosts(Arrays.asList(new ServerAddress(host, port))))
			.build()
		);
	}

	public static void main(String[] args) throws Exception {
		String db = "Tps";
		String host = "127.0.0.1";
		int port = 27017;
		for (int i = 0; i < args.length; i++) {
			if(args[i].equals("--db")) {
				if(i + 1 < args.length && !args[i + 1].startsWith("--")) {
					db = args[i + 1];
				}
				else {
					throw new Exception("--db not defined");
				}
			}
			else if(args[i].equals("--host")) {
				if(i + 1 < args.length && !args[i + 1].startsWith("--")) {
					host = args[i + 1];
				}
				else {
					throw new Exception("--host not defined");
				}
			}
			else if (args[i].equals("--port")) {
				if(i + 1 < args.length && !args[i + 1].startsWith("--")) {
					port = Integer.parseInt(args[i + 1]);
				}
				else {
					throw new Exception("--port not defined");
				}
			}
		}
		
		MongoClient mongoClient = getMongoClient(host, port);
		
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
					search(collection);
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
		
		System.out.println("Que souhaitez-vous faire ?");
		System.out.println(" * 1 : Supprimer tous les documents filtrés");
		System.out.println(" * 2 : Supprimer un des document");
		System.out.println(" * 3 : Modifier tous les documents filtrés");
		System.out.println(" * 4 : Modifier un des document");
		
	}
	
	
	private static void insert(MongoCollection<Document> collection) {
		
	}
}