package imdg;


import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.Predicates;
import com.hazelcast.query.SqlPredicate;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

public class QuerySample {

    
    public static class CardRange implements Serializable {
    	String rangeName;
        int min , max ;
          
       
        public CardRange(String rangeName, int min, int max) {
            this.rangeName = rangeName;
            this.min = min;
            this.max = max;
        }
       
        @Override
        public String toString() {
            return "CardRange{"
                    + "rangeName='" + rangeName + '\''
                    + ", min=" + min
                    + ", max=" + max
                    + '}';
        }
    }
    
      
    
    private static void generateCardRange(IMap<String, CardRange> cardRange) {
        cardRange.put("First", new CardRange("First", 100, 1000));
        cardRange.put("Second", new CardRange("Second", 1001, 2000));
        cardRange.put("Third", new CardRange("Third", 2001, 3000));
     }

    public static void main(String[] args) {
        // Start the Embedded Hazelcast Cluster Member.
        HazelcastInstance hz = Hazelcast.newHazelcastInstance();
              
        
        IMap<String, CardRange> cardRange = hz.getMap("cardRange");
        // Add some users to the Distributed Map
        generateCardRange(cardRange);
        // Create a Predicate from a String (a SQL like Where clause)
        int accountNo = 1234;
        Collection<CardRange> rangeResults = cardRange.values( new SqlPredicate("min <="+accountNo+" AND max >= "+accountNo ) );
       
        // Print out the results
        System.out.println(rangeResults);
      
        // Shutdown the Hazelcast Cluster Member
        
        
        hz.shutdown();
    }

}
