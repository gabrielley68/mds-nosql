package MDS.NoSQL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.bson.Document;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;


public class MongoManipulator {
	private MongoCollection<Document> collection;
	private ArrayList<String> fields;
	
	public MongoManipulator(MongoCollection<Document> collection) {
		this.collection = collection;
		fields = new ArrayList<String>();
		for(Document doc : collection.find()) {
			for(String key : doc.keySet()) {
				if(!fields.contains(key)) {
					fields.add(key);
				}
			}
		}
	}
	
	public static MongoClient getMongoClient(final String host, final int port) {
		return MongoClients.create(
			MongoClientSettings.builder()
			.applyToClusterSettings(builder -> 
					builder.hosts(Arrays.asList(new ServerAddress(host, port))))
			.build()
		);
	} 
	
	public FindIterable<Document> filter(String field, String operator, String value) {
		return collection.find(
			new Document(field, new Document(operator, value))
		);
		
	}

	public void deleteAll(FindIterable<Document> documents) {
		documents.forEach((doc) -> collection.deleteOne(eq("_id", doc.get("_id")))) ;
	}

	public void deleteOne(String id) {
		collection.deleteOne(eq("_id", id));	
	}

	public void modifyAll(FindIterable<Document> documents, String field, String value) {
		documents.forEach((doc) -> collection.updateOne(
			eq("_id", doc.get("id")), set(field, value))
		);
	}

	public void modifyOne(String id, String field, String value) {
		collection.updateOne(
			eq("_id", id), set(field, value)
		);
	}
	
	public void displayFields() {
		System.out.println("Les champs suivants sont dans la collection");
		for(String field : fields) {
			System.out.println(field);
		}
	}
}
