package rest;

import com.hazelcast.config.Config;
import com.hazelcast.config.RestApiConfig;
import com.hazelcast.config.RestEndpointGroup;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class RestEnabledMemberSample {

    public static void main(String[] args) throws Exception {
        Config config = new Config();
        RestApiConfig restApiConfig = new RestApiConfig()
                .setEnabled(true)
                .disableAllGroups()
                .enableGroups(RestEndpointGroup.DATA)
                .enableGroups(RestEndpointGroup.CLUSTER_READ)
                .enableGroups(RestEndpointGroup.CLUSTER_WRITE)
                .enableGroups(RestEndpointGroup.HEALTH_CHECK)
                ;
        config.getNetworkConfig().setRestApiConfig(restApiConfig);
        
        config.getManagementCenterConfig().setEnabled(true);
        config.setProperty("hazelcast.jmx", "true");
        config.getManagementCenterConfig().setUrl("http://localhost:8080/hazelcast-mancenter");
        
        HazelcastInstance hz = Hazelcast.newHazelcastInstance(config);
        Person person = new Person("Joe");

        IMap<String, String> hzSimpleMap = hz.getMap("simple");
        hzSimpleMap.set("key1", "value1");

        IMap<String, Person> hzObjectMap = hz.getMap("object");
        hzObjectMap.set("key1", person);
    }
}
