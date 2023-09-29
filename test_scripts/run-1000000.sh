./gradlew clean build -x test

for (( i=0; i < 10; ++i ))
do
    java -jar build/libs/vt_rx_perf_test-1.0-SNAPSHOT.jar --numberOfTasks=1000000 \
                                                          --taskHandlers=ReactiveVirtualThreadScheduler
done
for (( i=0; i < 10; ++i ))
do
    java -jar build/libs/vt_rx_perf_test-1.0-SNAPSHOT.jar --numberOfTasks=1000000 \
                                                          --taskHandlers=JdkVirtualThreadsExecutor
done