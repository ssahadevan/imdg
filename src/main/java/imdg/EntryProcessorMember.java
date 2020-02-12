package imdg;

import java.util.Map;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.map.AbstractEntryProcessor;
import com.hazelcast.map.EntryProcessor;

public class EntryProcessorMember{

    public static void main(String[] args) {
    	Config cfg = new Config();
        cfg.getManagementCenterConfig().setEnabled(true);
        cfg.setProperty("hazelcast.jmx", "true");
        cfg.getManagementCenterConfig().setUrl("http://localhost:8080/hazelcast-mancenter");
        
        HazelcastInstance hz = Hazelcast.newHazelcastInstance();
        IMap<String,Employee> employees = hz.getMap("employees");
        employees.put("John", new Employee("John", 45, true , 1000));
        employees.put("Mark", new Employee("Mark", 45, true ,1000));
        employees.put("Spencer", new Employee("Spencer", 45, true ,1000));

        employees.executeOnEntries((EntryProcessor) new EmployeeRaiseEntryProcessor());

        for(Map.Entry<String,Employee> entry: employees.entrySet()){
            System.out.println(entry.getKey()+
                " salary: "+entry.getValue().getSalary());
        }
        System.exit(0);
    }

    static class EmployeeRaiseEntryProcessor
            extends AbstractEntryProcessor<String, Employee> {

        @Override
        public Object process(Map.Entry< String, Employee> entry) {
            Employee employee = entry.getValue();
            employee.incSalary(10);
            entry.setValue(employee);
            return null;
        }
    }
}
