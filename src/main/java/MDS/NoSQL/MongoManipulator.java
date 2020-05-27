package MDS.NoSQL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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
	private Map<String, String> fields;
	
	public MongoManipulator(MongoCollection<Document> collection) {
		this.collection = collection;
		fields = new HashMap<String, String>();
		for(Document doc : collection.find()) {
			for(String key : doc.keySet()) {
				if(!fields.containsKey(key)) {
					String type = doc.get(key) == null ? "null" : doc.get(key).getClass().getName();
					fields.put(key, type);
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
	
	public Object getValue(String field, String value) {
		Class<?> type;
		try {
			type = Class.forName(fields.get(field));
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
			type = String.class;
		}
		if(type == Integer.class) {
			return Integer.parseInt(value);
		} else {
			return value;
		}
	}
	
	public FindIterable<Document> filter(String field, String operator, String value) {
		Document filter = new Document(operator, this.getValue(field, value));
		
		return collection.find(
			new Document(field, filter)
		);
	}

	public void deleteAll(FindIterable<Document> documents) {
		documents.forEach((doc) -> collection.deleteOne(eq("_id", doc.get("_id")))) ;
	}

	public void deleteOne(Document doc) {
		collection.deleteOne(eq("_id", doc.get("_id")));	
	}

	public void modifyAll(FindIterable<Document> documents, String field, String value) {
		documents.forEach((doc) -> collection.updateOne(
			eq("_id", doc.get("_id")), set(field, this.getValue(field, value)))
		);
	}

	public void modifyOne(Document doc, String field, String value) {
		collection.updateOne(
			eq("_id", doc.get("_id")), set(field, this.getValue(field, value))
		);
	}
	
	public void displayFields() {
		System.out.println("Les champs suivants sont dans la collection");
		for(String field : fields.keySet()) {
			System.out.println(field);
		}
	}

	public ArrayList<Document> displayAndConvertDocuments(FindIterable<Document> filtered_collection) {
		ArrayList<Document> list = new ArrayList<>();
		
		for(Document doc : filtered_collection) {
			list.add(doc);
			System.out.println("" + list.size() + " - " + doc.toJson());
		}
		
		return list;
	}
	
	public void addDocument(Document doc) {
		collection.insertOne(doc);
	}
	
	public Map<String, String> getFields(){
		return fields;
	}
}
