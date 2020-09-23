package client;
 
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientConnectionStrategyConfig;
import com.hazelcast.client.config.ConnectionRetryConfig;
import com.hazelcast.client.util.ClientStateListener;
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
public class NearCache {
    public static void main(String[] args) {
    	
   
        ClientConfig clientConfig = new ClientConfig();
        /*
        ClientConnectionStrategyConfig connectionStrategyConfig = clientConfig.getConnectionStrategyConfig();
        clientConfig.getConnectionStrategyConfig().setReconnectMode
        (ClientConnectionStrategyConfig.ReconnectMode.ASYNC);
        
        clientConfig.getConnectionStrategyConfig().getConnectionRetryConfig();
       // .setClusterConnectTimeoutMillis(Integer.MAX_VALUE);
        ConnectionRetryConfig connectionRetryConfig = connectionStrategyConfig.getConnectionRetryConfig();
        connectionRetryConfig.setInitialBackoffMillis(1000)
                             .setMaxBackoffMillis(60000)
                             .setMultiplier(2)
                             .setClusterConnectTimeoutMillis(50000)
                             .setJitter(0.2);
        */
        ClientConnectionStrategyConfig connectionStrategyConfig = clientConfig.getConnectionStrategyConfig();
        connectionStrategyConfig.setReconnectMode(ClientConnectionStrategyConfig.ReconnectMode.ASYNC);
        ConnectionRetryConfig connectionRetryConfig = connectionStrategyConfig.getConnectionRetryConfig();
        connectionRetryConfig.setInitialBackoffMillis(1000)
                             .setMaxBackoffMillis(60000)
                             .setMultiplier(2)
                             .setFailOnMaxBackoff(true)
                             .setJitter(0.2)
                             .setEnabled(true);
        NearCacheConfig nearCacheConfig = new NearCacheConfig("map");
        clientConfig.addNearCacheConfig(nearCacheConfig);
        
        ClientStateListener clientStateListener = new ClientStateListener(clientConfig);
        
        int numberOfArgs=args.length;
        if (numberOfArgs == 2)
        {
        	String ip = args[0];
        	String port = args[1];
        	clientConfig.addAddress( args[0] + ":" + args[1]);
        }
        else
        {
        // Connect to localhost 
        clientConfig.addAddress("127.0.0.1:5701");
        }
        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
       
        try {
			if (clientStateListener.awaitConnected()) {
				System.out.println("isClientConnected : " + clientStateListener.isConnected()) ;
			} else {
			        //client failed to connect to cluster
				System.out.println("isClientConnected : " + clientStateListener.isConnected()) ;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
       
        try {		
        IMap customers = client.getMap("customers");
        customers.put(1,"One");
        System.out.println("customer map Size:" + customers.size());
       
        } catch (HazelcastClientOfflineException offlineException) {

        System.out.println("EXPECTED exception");

        }
        
        IMap<Object, Object> map = client.getMap("map");

        for (int i = 0; i < 100; i++) {

        map.put(i, i);

        }

        //populate near cache

        for (int i = 0; i < 100; i++) {

        map.get(i);

        }
        
       // member.shutdown();
      //  while (true ) {
        
      //verify the availability of Near Cache data while client is disconnected
        System.out.println("In While loop");
        for (int i = 0; i < 100; i++) {

        if (!map.get(i).equals(i)) {

        throw new IllegalStateException();

        }

        else{

        System.out.println("Reading from non-stop near cache: " + map.get(i));

        }

        }

        //verify that if client asks for an unavailable key, we get offline exception immediately

        try {

        map.get(200);

        } catch (HazelcastClientOfflineException offlineException) {

        System.out.println("EXPECTED exception");

        }

        // verify that if client tries to write, we get offline exception immediately

        try {

        map.put(1, 2);

        } catch (HazelcastClientOfflineException offlineException) {

        System.out.println("EXPECTED exception");

        }
        
       // } // End while
                
        // client.shutdown();
        
        
    }
}

