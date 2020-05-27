package MDS.NoSQL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import org.bson.Document;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

public class App {
	private static Scanner scanner = ScannerHelper.getScanner();

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
		
		MongoClient mongoClient = MongoManipulator.getMongoClient(host, port);
		
		MongoDatabase database = mongoClient.getDatabase(db);
		
		UserInteraction ui = new UserInteraction(database);
		ui.run();
	}
	
	
	private static void insert(MongoManipulator manipulator) {
		
	}
}