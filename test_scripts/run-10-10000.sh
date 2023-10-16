./gradlew clean build -x test

#java -jar build/libs/vt_rx_perf_test-1.0-SNAPSHOT.jar --numberOfTasks=10 \
#                                                      --taskHandlers=JdkVirtualThreadsExecutor,JdkPlatformThread,ReactiveVirtualThreadScheduler,ReactiveElasticScheduler,ReactiveParallelScheduler
#java -jar build/libs/vt_rx_perf_test-1.0-SNAPSHOT.jar --numberOfTasks=100 \
#                                                      --taskHandlers=JdkVirtualThreadsExecutor,JdkPlatformThread,ReactiveVirtualThreadScheduler,ReactiveElasticScheduler,ReactiveParallelScheduler
#java -jar build/libs/vt_rx_perf_test-1.0-SNAPSHOT.jar --numberOfTasks=1000 \
#                                                      --taskHandlers=JdkVirtualThreadsExecutor,JdkPlatformThread,ReactiveVirtualThreadScheduler,ReactiveElasticScheduler,ReactiveParallelScheduler
#java -jar build/libs/vt_rx_perf_test-1.0-SNAPSHOT.jar --numberOfTasks=10000 \
#                                                      --taskHandlers=JdkVirtualThreadsExecutor,JdkPlatformThread,ReactiveVirtualThreadScheduler,ReactiveElasticScheduler


#java -jar build/libs/vt_rx_perf_test-1.0-SNAPSHOT.jar --numberOfTasks=1000 \
#                                                      --taskHandlers=JdkVirtualThreadsExecutor,JdkVirtualThread,JdkPlatformThread
#java -jar build/libs/vt_rx_perf_test-1.0-SNAPSHOT.jar --numberOfTasks=10000 \
#                                                      --taskHandlers=JdkVirtualThreadsExecutor,JdkVirtualThread,JdkPlatformThread
#java -jar build/libs/vt_rx_perf_test-1.0-SNAPSHOT.jar --numberOfTasks=100000 \
#                                                      --taskHandlers=JdkVirtualThreadsExecutor,JdkVirtualThread,ReactiveVirtualThreadScheduler
java -jar build/libs/vt_rx_perf_test-1.0-SNAPSHOT.jar --numberOfTasks=1000000 \
                                                      --taskHandlers=JdkVirtualThreadsExecutor,JdkVirtualThread