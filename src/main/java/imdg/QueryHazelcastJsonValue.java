package imdg;

import java.util.Collection;
import java.util.Map.Entry;

import com.hazelcast.aggregation.Aggregators;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastJsonValue;
import com.hazelcast.core.IMap;
import com.hazelcast.internal.json.Json;
import com.hazelcast.internal.json.JsonValue;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.Predicates;
import com.hazelcast.query.SqlPredicate;

public class QueryHazelcastJsonValue {
	private static final String CUSTOMER_MAP_NAME = "Customer";
	private static final String PORTFOLIO_MAP_NAME = "Portfolio";
	

	private static HazelcastInstance hazelcastInstance = null;
    

	private static StringBuffer jsonCustomerDoument; 
	static { jsonCustomerDoument = new StringBuffer().append("{")
			 .append( "\"firstName\": \"John\",")
			 .append( "\"lastName\": \"Smith\",")
			 .append( "\"customerId\": \"00001\",")
			 .append( "\"age\": 25,")
			 .append( "\"address\":")
			 .append( "{")
			 .append( "\"streetAddress\": \"21 2nd Street\",")
			 .append( "\"city\": \"New York\",")
			 .append( "\"state\": \"NY\",")
			 .append( "\"postalCode\": \"10021\"")
			 .append( "},")
			 .append( "\"phoneNumber\":")
			 .append( "[")
			 .append( "{")
			 .append( " \"type\": \"home\",")
			 .append( "\"number\": \"212 555-1234\"")
			 .append( "},")
            .append( "{")
			 .append( " \"type\": \"mobile\",")
			 .append( "\"number\": \"212 555-5678\"")
			 .append( "},")
            .append( "{")
            .append( "\"type\": \"fax\",")
			 .append( "\"number\": \"646 555-4567\"")
			 .append( "}")
			 .append( "]")
			 .append( "}");
	}
	

	private static StringBuffer jsonPortfolioDoument;
	static { jsonPortfolioDoument= new StringBuffer().append("{")
		 .append( "\"portId\" : 10001,")
		 .append( "\"region\" : \"UK\",")
		 .append( "\"positions\" : [ {")
		 .append( "\"secId\" : \"SEC0\",")
		 .append( "\"quantity\" : 23283.5241094934")
		 .append( "}, {")
		 .append( "\"secId\" : \"SEC1\",")
		 .append( "\"quantity\" : 15580.9964148989")
		 .append( "}, {")
		 .append( "\"secId\" : \"SEC2\",")
		 .append( "\"quantity\" : 34635.37425655331")
		 .append( "} ]")
		.append( "}");
	}
	

	public static void main(String[] args) { 	
    	try {

    		Collection<Object> results;
    		hazelcastInstance = getHazelcastInstance();
    		IMap<Object, Object> customerMap = hazelcastInstance.getMap(CUSTOMER_MAP_NAME);
    		IMap<Object, Object> portfolioMap = hazelcastInstance.getMap(PORTFOLIO_MAP_NAME);
    		

    		

    		JsonValue customerJsonValue = Json.parse(jsonCustomerDoument.toString());
    		JsonValue portfolioJsonValue = Json.parse(jsonPortfolioDoument.toString());
    		

    		HazelcastJsonValue customer = new HazelcastJsonValue(customerJsonValue.toString());
    		String customerKey = customerJsonValue.asObject().getString("customerId", null);
    		

    		HazelcastJsonValue portfolio = new HazelcastJsonValue(portfolioJsonValue.toString());
    		Long portfolioKey = portfolioJsonValue.asObject().getLong("portId", 0L);
    		

    		//Put entries into the maps
    		customerMap.put(customerKey,customer);
    		portfolioMap.put(portfolioKey,portfolio);
    		

    		//Show entry counts using fast aggrigation API
    		getMapEntryCount((IMap<Object, Object>) customerMap);
    		getMapEntryCount((IMap<Object, Object>) portfolioMap);
    		

    		

    		//Customer Query
    		Predicate<?, ?> customerIdPredicate= Predicates.between("customerId",0,10);
    		results = customerMap.values(customerIdPredicate);
    		printResults(results);
			Predicate<?, ?> customerPhoneNumberPredicate = new SqlPredicate("phoneNumber[any].type = 'home'");
    		results = customerMap.values(customerPhoneNumberPredicate);
    		printResults(results);
			Predicate<?, ?> customerHomeNumerPredicate = new SqlPredicate("phoneNumber[any].type = 'home' AND  phoneNumber[any].number = '212 555-5678'");
    		results = customerMap.values(customerHomeNumerPredicate);
    		printResults(results);

    		//Portfolio Query
    		Predicate<?, ?> predicate = new SqlPredicate("portId BETWEEN 10000 AND 10003");
    		results =  portfolioMap.values(predicate);
    		printResults(results);
    		

    	}catch (Throwable t) {
    		t.printStackTrace();
    	}finally {
    		if (hazelcastInstance != null) {
    			hazelcastInstance.shutdown();
    		}
    	} 	
		

    }

	public static HazelcastInstance getHazelcastInstance() {
 		if(hazelcastInstance == null) {
			Config cfg = new Config();
			hazelcastInstance = Hazelcast.newHazelcastInstance(cfg);
		}
		return hazelcastInstance;
	}
    

    private static void getMapEntryCount(IMap<Object, Object> map) {
        // execute the aggregation and print the result
        long entryCount = map.aggregate(Aggregators.<Entry<Object, Object>>count());
        System.out.println("Number of entries in <" +map.getName()+">: " + entryCount);
        System.out.println("\n");
    }

    private static void printResults(Collection<Object> results){
    	System.out.println("\nQuery Reslutls..." );
    	results.forEach(e-> System.out.println(e));
    	System.out.println("\n" );
    }


}
