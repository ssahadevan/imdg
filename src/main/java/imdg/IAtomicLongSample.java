package imdg;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IAtomicLong;
import com.hazelcast.core.IFunction;
import com.hazelcast.util.function.Function;

public class IAtomicLongSample {

public static void main(String[] args) {
HazelcastInstance hz = Hazelcast.newHazelcastInstance();
IAtomicLong counter = hz.getAtomicLong("counter");
for (int k = 0; k < 1000 * 1000; k++) {
if (k % 500000 == 0){
System.out.println("At: "+k);
}
counter.incrementAndGet();
}
System.out.printf("Count is %s\n", counter.get());

IAtomicLong atomicLong = hz.getAtomicLong("newcounter");
atomicLong.set(1);
/*
Long result = atomicLong.apply( new Add2Function());
System.out.println("apply.result:" + result);
System.out.println("apply.value:" + atomicLong.get());
atomicLong.set(1);
atomicLong.alter(new Add2Function());
System.out.println("alter.value:"+atomicLong.get());
atomicLong.set(1);
result = atomicLong.alterAndGet(new Add2Function());
System.out.println("alterAndGet.result:" + result);
System.out.println("alterAndGet.value:"+atomicLong.get());
atomicLong.set(1);
result = atomicLong.getAndAlter(new Add2Function());
System.out.println("getAndAlter.result:"+result);
*/
System.out.println("getAndAlter.value:"+atomicLong.get());
System.exit(0);

}


private static class Add2Function implements Function<Long,Long> {
@Override
public Long apply(Long input) {
return input+2;
}


}

}




