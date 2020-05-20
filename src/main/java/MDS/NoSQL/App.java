package MDS.NoSQL;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class App {
	
	public static void main(String[] args) {
		String host = args[0];
		String port = args[1];
		String db = args[2];
		
		MongoClient mongoClient = MongoClients.create(
			"mongodb://" + host + ":" + port
		);
		
		MongoDatabase database = mongoClient.getDatabase(db);
	}
}