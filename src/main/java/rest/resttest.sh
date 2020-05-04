set +x
curl --data "dev&dev-pass" http://localhost:5701/hazelcast/rest/management/cluster/state
echo "Nodes: \n"
curl --data "dev&dev-pass" http://localhost:5701/hazelcast/rest/management/cluster/nodes

curl --data "dev&dev-pass" http://localhost:5702/hazelcast/rest/management/cluster/state
echo "Nodes: "
curl --data "dev&dev-pass" http://localhost:5702/hazelcast/rest/management/cluster/nodes
curl --data "dev&dev-pass" http://localhost:5703/hazelcast/rest/management/cluster/state
curl --data "dev&dev-pass" http://localhost:5703/hazelcast/rest/management/cluster/nodes

curl --data "dev&dev-pass" http://localhost:5701//hazelcast/health/cluster-state
curl --data "dev&dev-pass" http://localhost:5702//hazelcast/health/cluster-state

curl --data "dev&dev-pass" http://localhost:5701//hazelcast/health/cluster-size
curl --data "dev&dev-pass" http://localhost:5702//hazelcast/health/cluster-size
