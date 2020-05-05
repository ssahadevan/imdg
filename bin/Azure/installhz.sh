#helm install my-release stable/hazelcast
helm install my-release --set service.type=LoadBalancer,service.clusterIP="" stable/hazelcast

kubectl get all
