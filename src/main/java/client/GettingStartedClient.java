package client;
 
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientConnectionStrategyConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionConfig;
import com.hazelcast.config.EvictionConfig.MaxSizePolicy;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.InMemoryFormat;
import com.hazelcast.config.NearCacheConfig;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.HazelcastClientOfflineException;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
 
/* 
 * Usage:
 *    GettingStartedClient ip port
 *    or 
 *    GettingStartedClient 
 *       to connect to defaults which are 
 */
public class GettingStartedClient {
    public static void main(String[] args) {
    	
   
        ClientConfig config = new ClientConfig();
       	
       

        int numberOfArgs=args.length;
        if (numberOfArgs == 2)
        {
        	String ip = args[0];
        	String port = args[1];
        	config.addAddress( args[0] + ":" + args[1]);
        }
        else
        {
        // Connect to localhost 
        config.addAddress("127.0.0.1:5701");
        }
        HazelcastInstance client = HazelcastClient.newHazelcastClient(config);
        
        
       
        try {		
        IMap customers = client.getMap("customers");
        customers.put(1,"One");
        System.out.println("customer map Size:" + customers.size());
       
        } catch (HazelcastClientOfflineException offlineException) {

        System.out.println("EXPECTED exception");

        }
        
                
        // client.shutdown();
        
        
    }
}
