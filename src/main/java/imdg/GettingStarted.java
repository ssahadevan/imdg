package imdg;

import com.hazelcast.core.*;
import com.hazelcast.config.*;
import com.hazelcast.map.listener.*;

 
import java.util.Map;
import java.util.Queue;
 
public class GettingStarted {
    public static void main(String[] args) {
        Config cfg = new Config();
        cfg.getManagementCenterConfig().setEnabled(true);
        cfg.getManagementCenterConfig().setUrl("http://localhost:8080/hazelcast-mancenter");
        
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);
        IMap<Integer, String> mapCustomers = instance.getMap("customers");
        ((IMap<Integer, String>) mapCustomers).addEntryListener( new myEntryListener() , true);
        mapCustomers.put(1, "Joe");
        mapCustomers.put(2, "Ali");
        mapCustomers.put(3, "Avi");
 
        System.out.println("Customer with key 1: "+ mapCustomers.get(1));
        System.out.println("Map Size:" + mapCustomers.size());
        
        
 
        Queue<String> queueCustomers = instance.getQueue("customers");
        queueCustomers.offer("Tom");
        queueCustomers.offer("Mary");
        queueCustomers.offer("Jane");
        System.out.println("First customer: " + queueCustomers.poll());
        System.out.println("Second customer: "+ queueCustomers.peek());
        System.out.println("Queue size: " + queueCustomers.size());
    }
    
    private static class myEntryListener implements EntryAddedListener<String, String>,
    EntryUpdatedListener<String, String>,EntryRemovedListener<String, String> {
    	@Override
    	public void entryAdded( EntryEvent<String, String> event)
    	{
    		System.out.println("Entry added :" + event);
    	}

		@Override
		public void entryRemoved(EntryEvent<String, String> event) {
			// TODO Auto-generated method stub
			System.out.println("Entry Removed :" + event);
			
		}

		@Override
		public void entryUpdated(EntryEvent<String, String> event) {
			// TODO Auto-generated method stub
			System.out.println("Entry updated :" + event);
		}
    }
}

