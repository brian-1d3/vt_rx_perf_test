./gradlew clean build -x test

java -jar build/libs/vt_rx_perf_test-1.0-SNAPSHOT.jar --numberOfTasks=10 \
                                                      --taskHandlers=ReactiveVirtualThreadScheduler,ReactiveElasticScheduler,ReactiveParallelScheduler
java -jar build/libs/vt_rx_perf_test-1.0-SNAPSHOT.jar --numberOfTasks=100 \
                                                      --taskHandlers=ReactiveVirtualThreadScheduler,ReactiveElasticScheduler,ReactiveParallelScheduler
java -jar build/libs/vt_rx_perf_test-1.0-SNAPSHOT.jar --numberOfTasks=1000 \
                                                      --taskHandlers=ReactiveVirtualThreadScheduler,ReactiveElasticScheduler,ReactiveParallelScheduler
java -jar build/libs/vt_rx_perf_test-1.0-SNAPSHOT.jar --numberOfTasks=10000 \
                                                      --taskHandlers=ReactiveVirtualThreadScheduler,ReactiveElasticScheduler,ReactiveParallelScheduler