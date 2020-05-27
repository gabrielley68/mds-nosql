package MDS.NoSQL;

import java.util.ArrayList;
import java.util.Scanner;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class UserInteraction {
	private static Scanner scanner = ScannerHelper.getScanner();
	private MongoDatabase database;
	private MongoManipulator manipulator;
	
	public UserInteraction(MongoDatabase database) {
		this.database = database;
	}
	
	public void run() {
		while(true) {
			Integer res = ScannerHelper.getAction(new String[] {"Rechercher un document", "Insérer un document"});
			System.out.println("Liste des collections : ");
			ArrayList<String> collectionNames = new ArrayList<String>();
			for(String name : database.listCollectionNames()) {
				System.out.println("- " + name);
				collectionNames.add(name);
			}
			String collInput = ScannerHelper.getChoice(collectionNames);
					
			MongoCollection<Document> collection = database.getCollection(collInput);
			
			manipulator = new MongoManipulator(collection);
			
			if(res == 0) {
				search();
			} else if (res == 1) {
				insert();
			}
		}
	}
	
	private void search() {
		manipulator.displayFields();
		System.out.println("Sélectionner le champ à filtrer");
		String field = scanner.next();
		System.out.println("Sélectionner un opérateur de comparaison (ex: $lte, $eq, ...");
		String operator = scanner.next();
		System.out.println("Sélectionner la valeur du filtre");
		String value = scanner.next();
		
		FindIterable<Document> filtered_collection = manipulator.filter(field, operator, value);
		
		for(Document document : filtered_collection) {
			System.out.println(document.toJson());
		}
		
		Integer res = ScannerHelper.getAction(new String[] {
			"Supprimer tous les documents filtrés", "Supprimer un des document",
			"Modifier tous les documents filtrés", "Modifier un des document"
		});

		switch(res) {
			case 0:
				manipulator.deleteAll(filtered_collection);
				break;
			case 1:
				System.out.println("Entrez l'identifiant du document à supprimer");
				manipulator.deleteOne(scanner.next());
				break;
			case 2:
				System.out.println("Entrez le champ à modifier");
				field = scanner.next();
				System.out.println("Entrez la valeur à insérer");
				value = scanner.next();
				manipulator.modifyAll(filtered_collection, field, value);
			case 3:
				System.out.println("Entrez l'identifiant du document à supprimer");
				String id = scanner.next();
				System.out.println("Entrez le champ à modifier");
				field = scanner.next();
				System.out.println("Entrez la valeur à insérer");
				value = scanner.next();
				manipulator.modifyOne(id, field, value);
				break;
		}
	}
	
	
	private void insert() {
		
	}
	
	
}
