package imdg;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
 
public class MainMillionEntries {
 
    public static void main(String[] args){
        HazelcastInstance hz = Hazelcast.newHazelcastInstance();
        IMap map =  hz.getMap("foo");
        for(int k=0;k<1000*1000;k++){
            map.put(k,k);
        }
        System.out.println("Done!");
    }
}
