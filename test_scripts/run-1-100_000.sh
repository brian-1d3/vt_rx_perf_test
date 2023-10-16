./gradlew clean build -x test



java -jar build/libs/vt_rx_perf_test-1.0-SNAPSHOT.jar --numberOfTasks=100000 \
                                                          --taskHandlers=ReactiveElasticScheduler


for (( i=0; i < 10; ++i ))
do
    java -jar build/libs/vt_rx_perf_test-1.0-SNAPSHOT.jar --numberOfTasks=100000 \
                                                          --taskHandlers=ReactiveVirtualThreadScheduler
done