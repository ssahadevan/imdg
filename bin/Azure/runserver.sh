set -x

export IMDG_HOME=/home/sharath/imdg
echo $IMDG_HOME
echo $HOME
java -Dfile.encoding=UTF-8 -classpath $IMDG_HOME/target/classes:$HOME/.m2/repository/com/hazelcast/hazelcast/3.12.6/hazelcast-3.12.6.jar:$HOME/.m2/repository/com/hazelcast/hazelcast-client/3.12.6/hazelcast-client-3.12.6.jar:/$HOME/.m2/repository/com/google/code/gson/gson/2.3.1/gson-2.3.1.jar:$HOME/.m2/repository/org/glassfish/jersey/core/jersey-client/2.8/jersey-client-2.8.jar:$HOME/.m2/repository/org/glassfish/jersey/core/jersey-common/2.8/jersey-common-2.8.jar:$HOME/.m2/repository/javax/annotation/javax.annotation-api/1.2/javax.annotation-api-1.2.jar:$HOME/.m2/repository/org/glassfish/jersey/bundles/repackaged/jersey-guava/2.8/jersey-guava-2.8.jar:$HOME/.m2/repository/org/glassfish/hk2/osgi-resource-locator/1.0.1/osgi-resource-locator-1.0.1.jar:$HOME/.m2/repository/javax/ws/rs/javax.ws.rs-api/2.0/javax.ws.rs-api-2.0.jar:$HOME/.m2/repository/org/glassfish/hk2/hk2-api/2.2.0/hk2-api-2.2.0.jar:$HOME/.m2/repository/org/glassfish/hk2/hk2-utils/2.2.0/hk2-utils-2.2.0.jar:$HOME/.m2/repository/org/glassfish/hk2/external/aopalliance-repackaged/2.2.0/aopalliance-repackaged-2.2.0.jar:$HOME/.m2/repository/org/glassfish/hk2/external/javax.inject/2.2.0/javax.inject-2.2.0.jar:$HOME/.m2/repository/org/glassfish/hk2/hk2-locator/2.2.0/hk2-locator-2.2.0.jar:$HOME/.m2/repository/org/javassist/javassist/3.18.1-GA/javassist-3.18.1-GA.jar imdg.GettingStarted
