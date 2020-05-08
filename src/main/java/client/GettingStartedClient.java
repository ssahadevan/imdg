package client;
 
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.HazelcastClient;
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
        ClientConfig clientConfig = new ClientConfig();
        
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
        IMap map = client.getMap("customers");
        System.out.println("Map Size:" + map.size());
    }
}
