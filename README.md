# imdg
 Hazelcast In Memory Data Grid samples
 
 Instructions are here  - https://hazelcast.org/getting-started-with-hazelcast/
 
 
 From Eclispe:
 
 
 mvn clean
 mvn install
 
 Run as a java application 


Hazelcast Client:

     GettingStartedClient
     
     Clients will connect to an existing Hazelcast Cluster. This represents the Client-server topology
     
Hazelcast Member:
   
     GettingStarted
     
      Each member forms one node in a cluster. If you start multiple instances, they will autodiscover and form a cluster.
      This is an example of embedded Hazelcast topology. Here the application embeds Hazelcast within itself.
      
