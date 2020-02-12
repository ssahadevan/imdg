package imdg;

import com.hazelcast.core.*;
import com.hazelcast.config.*;
import com.hazelcast.config.cp.CPSubsystemConfig;
import com.hazelcast.map.listener.*;
import com.hazelcast.query.EntryObject;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;

import java.util.Collection;
import java.util.Map;
import java.util.Queue;

import javax.swing.text.html.HTMLDocument.Iterator;

import imdg.Employee;
import com.google.gson.Gson; 
 
public class GettingStarted {
    public static void main(String[] args) {
        Config cfg = new Config();
        cfg.getManagementCenterConfig().setEnabled(true);
        cfg.setProperty("hazelcast.jmx", "true");
        cfg.getManagementCenterConfig().setUrl("http://localhost:8080/hazelcast-mancenter");
        CPSubsystemConfig cpSubsystemConfig = cfg.getCPSubsystemConfig();
        cpSubsystemConfig
        .setCPMemberCount(3)
        .setGroupSize(3)
        .setSessionTimeToLiveSeconds(300)
        .setSessionHeartbeatIntervalSeconds(5)
        .setMissingCPMemberAutoRemovalSeconds(14400)
        .setFailOnIndeterminateOperationState(false)
        
        ;
        
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
        
        
        IMap<Integer, Employee> mapEmployee = instance.getMap("employeeMap");
        int i = 1;
        int age= 25 ; 
        int salary = 50 ; /* in 1000 Currency Units  */
        int ageIncrement = 1;
        int salaryIncrement= 10 ;
        while ( i <= 10 )
        {
        
           Employee employee = new Employee( "SS", age, true, salary);
           age = age + ageIncrement;
           salary = salary + salaryIncrement ;
           mapEmployee.put( i , employee) ;
           i++ ; 
        } 
        
        System.out.println("mapEmployee.size=" + mapEmployee.size() );
        
        EntryObject e = new PredicateBuilder().getEntryObject();
        int ageFilterIncrement=5;
        Predicate<?, ?> predicate = e.is( "active" ).and( e.get( "age" ).lessThan( 30 + ageFilterIncrement  ) );

        Collection<Employee> employees = mapEmployee.values( predicate );
        System.out.println("Results=" + employees );
               
        // for-each loop
        for (Employee  emp : employees) {
          // System.out.println("value= " + s);
          Gson gson = new Gson();    
          String json = gson.toJson(emp);
          System.out.println(json);
        }

        
                
        
    } // End main
    /*
    public static void add( Integer amtToAdd, String filter , IMap mapData) {
    	
    	mapData.get
    	
    }
    */
    
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

