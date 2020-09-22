package imdg;

import com.hazelcast.config.Config;
import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.EntryView;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.core.IMap;
import com.hazelcast.map.listener.EntryAddedListener;
import com.hazelcast.map.listener.EntryRemovedListener;
import com.hazelcast.map.listener.EntryUpdatedListener;


public class MapStats {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		final String MAP_NAME = "myMap";
        final long KEY_COUNT = 10_000;
        final long SLEEPING_TIME_MS = 1_000;
        EntryView entry=null ;
    	
        Config cfg = new Config();
        
        //cfg.getManagementCenterConfig().setEnabled(true);
        cfg.setProperty("hazelcast.jmx", "true");
        // cfg.getManagementCenterConfig().setUrl("http://localhost:8080/hazelcast-mancenter");
        
        
        
        
        
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);
        IMap<Integer, String> mapCustomers = instance.getMap("customers");
        
        ((IMap<Integer, String>) mapCustomers).addEntryListener( new myEntryListener() , true);
        
        mapCustomers.tryLock(1);
        try {
        mapCustomers.put(1, "Joe");
        }
        finally {
        	mapCustomers.forceUnlock(1);
        }
        mapCustomers.put(2, "Ali");
        mapCustomers.put(3, "Avi");
 
        System.out.println("Customer with key 1: "+ mapCustomers.get(1));
        System.out.println("Map Size:" + mapCustomers.size());
        
        entry = mapCustomers.getEntryView( 1);
    	System.out.println ( "size in memory : " + entry.getCost() );
    	System.out.println ( "creationTime : " + entry.getCreationTime() );
    	System.out.println ( "expirationTime : " + entry.getExpirationTime() );
    	System.out.println ( "number of hits : " + entry.getHits() );
    	System.out.println ( "lastAccessedTime: " + entry.getLastAccessTime() );
    	System.out.println ( "lastUpdateTime : " + entry.getLastUpdateTime() );
    	System.out.println ( "version : " + entry.getVersion() );
    	System.out.println ( "key : " + entry.getKey() );
    	System.out.println ( "value : " + entry.getValue() );
    	
    	System.out.println("Customer with key 1: "+ mapCustomers.get(1));
    	mapCustomers.delete(1);

	}
	
	 private static class myEntryListener implements EntryAddedListener<String, String>,
	    EntryUpdatedListener<String, String>,EntryRemovedListener<String, String>, HazelcastInstanceAware {
		 
		    private transient HazelcastInstance hazelcastInstance;
		    
		    @Override
	    	public void entryAdded( EntryEvent<String, String> event)
	    	{
		    	String myKey = null;
	    		System.out.println("Entry added :" + event);
	    		System.out.println("Event Class is  " + event.getName() ) ; 
	    		// myKey = event.getKey().toString();
			   // System.out.println("Event Key is  " + myKey ) ; 
			   EntryView listEntView= hazelcastInstance.getMap("customers").getEntryView( 1);
	           System.out.println ( "Listener number of hits : " + listEntView.getHits() );
	    	}

			@Override
			public void entryRemoved(EntryEvent<String, String> event) {
				
				
				// TODO Auto-generated method stub
				System.out.println("Entry Removed :" + event);
				System.out.println("Event Class is  " + event.getName() ) ; 
			    // System.out.println("Event Key is  " + event.getKey() ) ; 
			   // EntryView listEntView= hazelcastInstance.getMap("customers").getEntryView( event.getKey());
			   //  System.out.println ( "Listener number of hits : " + listEntView.getHits() );
			}

			@Override
			public void entryUpdated(EntryEvent<String, String> event) {
				// TODO Auto-generated method stub
				System.out.println("Entry updated :" + event);
			}

			@Override
			public void setHazelcastInstance(HazelcastInstance hz) {
				// TODO Auto-generated method stub
				  this.hazelcastInstance = hz;
				  System.out.println("hazelcastInstance set");
				
			}
			/*
			public HazelcastInstance getHazelcastInstance() {
				  return hazelcastInstance;
				  }
		   */
	    }

}
