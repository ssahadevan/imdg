package imdg;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.config.*;

public class MapSample {
    public static void main(String[] args) {
    	Config cfg = new Config();
        cfg.getManagementCenterConfig().setEnabled(true);
        cfg.getManagementCenterConfig().setUrl("http://localhost:8080/hazelcast-mancenter");
        System.out.println("Im MapSample");
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);
        // Start the Embedded Hazelcast Cluster Member.
        HazelcastInstance hz = Hazelcast.newHazelcastInstance();
        // Get the Distributed Map from Cluster.
        IMap<String, String> map = hz.getMap("my-distributed-map");
        //Standard Put and Get.
        map.put("key", "value");
        map.get("key");
        //Concurrent Map methods, optimistic updating
        map.putIfAbsent("somekey", "somevalue");
        map.replace("key", "value", "newvalue");
        
        System.out.println("Key's value is : "+ map.get("key"));
        
        // Shutdown the Hazelcast Cluster Member
        hz.shutdown();
    }
}