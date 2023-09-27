## Virtual Threads vs Reactive Java - Performance Comparison

### Description

[Virtual Threads](https://docs.oracle.com/en/java/javase/21/core/virtual-threads.html#GUID-DC4306FC-D6C1-4BCC-AECE-48C32C1A8DAA)
were released as part of Java 21, having been in preview since Java 19. Until this point, Java threads were 'Platform Threads', 
implemented as wrapper objects around real operating system threads. While powerful, this model has inherent limitations 
in high throughput applications where hardware is limited to hosting a low number of threads. Virtual Threads solve this 
issue by allowing a Platform Thread to switch between multiple Virtual Threads when one of them encounters a blocking 
I/O operation. 

The Java ecosystem responded to this limitation with other solutions. Reactive Programming, first through RxJava in 2013 
but currently more prominently through [Project Reactor](https://projectreactor.io/), which is packaged in 
[Spring WebFlux](https://docs.spring.io/spring-framework/reference/web/webflux.html), uses 
[co-operative threading](https://luminousmen.com/post/asynchronous-programming-cooperative-multitasking).
Kotlin released the similar concept of [co-routines](https://kotlinlang.org/docs/coroutines-guide.html#table-of-contents) 
in 2018.

This performance comparison is interested in answering the question - what difference will Virtual Threads make in 
comparison to using a Reactive Programming library?

### Method

#### Measurement:
The completion times in milliseconds of [10, 100, 1000, 10000, 100000] concurrent function calls passing a Runnable object consisting of a 1-second 
Thread.sleep() operation, on a set of different function implementations using:
- Virtual Threads
- Project Reactor w/ Virtual Thread Scheduler
- Project Reactor w/ Elastic Scheduler 
- Project Reactor w/ Parallel Scheduler

For the range of 10 - 1000 concurrent calls, all implementations were measured once. 

For 10000 concurrent calls, all except the Parallel Scheduler were measured once.

For 100000 concurrent calls, only the Virtual Thread and Virtual Thread Scheduler were measured, taking the mean 
average of results from 10 executions. 

The raw data is recorded in a comment in Main.java

##### Explanation:
The Virtual Thread is created and managed by the java library `Executors.newVirtualThreadPerTaskExecutor()` API call.

Project Reactor was chosen as the most prominant current Reactive Library for Java.

A Scheduler is the method that Project Reactor API uses to configure multithreading. This test uses 
`Schedulers.boundedElastic()`, `Schedulers.parallel()`, and `Schedulers.fromExecutorService(...)`.
With `boundedElastic` and `parallel`, Project Reactor manages the multithreading for us. `boundedElastic` uses co-operative 
threading mentioned above. `parallel` uses one-thread-per-task, making it analogues to the Platform Threading model.
With `fromExecutorService` we are able to integrate any Java executor, including the new Virtual Thread executor, 
so that was added. 

For 10000 calls, Parallel Scheduler was left out due length of completion time (Estimated - 20.8 minutes. 10000 tasks, 
each taking 1 second, divided by 8 cores).

For 100000 calls, an average was taken due variability in completion time I noted during development.

##### Limitations:
Does not simulate calls on a limited resource, such a database with a connection pool. 

Completion times are mostly used for illustrative purposes, with the assumption that the overall pattern might be similar
on a machine with the same number of cores. Results might follow a different pattern using a machine with more or less cores.

Not a comparison of the usability of Java and Project Reactor async/streams libraries.

I attempted to configure a Scheduler using `Schedulers.newBoundedElastic()` however I did not get it to work
successfully at higher ranges, so have not included that in the test.

Lack of real world experience with Reactive frameworks means I don't know how these figures would translate 
into a real project.

#### Hardware: 
2.3 GHz Quad-Core Intel Core i7 on a MacBookPro16,2

Physical Cores: 4

Logical Cores: 8

### Results

|                                   | 10   | 100   | 1000   | 10000  | 1000000\* |
| --------------------------------- | ---- | ----- | ------ | ------ |-----------|
| Jdk Virtual Threads Executor      | 1016 | 1023  | 1041   | 1278   | 1890.7    |
| Reactive Virtual Thread Scheduler | 1073 | 1066  | 1074   | 1203   | 2380.8    |
| Reactive Elastic Scheduler        | 1018 | 2020  | 13060  | 125459 | N/A       |
| Reactive Parallel Scheduler       | 2008 | 13059 | 125400 | N/A    | N/A       |
_Measurements in milliseconds_

_\* Mean average completion time of 10 executions_

### Analysis

Starting with the slowest first, the Reactive Parallel Scheduler, which is analogous to the Platform Thread model, 
struggles to scale as effectively as the others. One-task-per thread means that a maximum of 8 tasks can be executed at 
once, with the others blocked until the task is complete and the thread is released. This is what pushes 1000 1-second 
tasks into ~125000ms (2.09 minutes): 1000 tasks * 1000ms / 8 threads

The real comparison here that answers the question in the description is between the Reactive Elastic Scheduler 
and Jdk Virtual Threads Executor. While it is possible to 
[configure an elastic scheduler](https://projectreactor.io/docs/core/release/api/reactor/core/scheduler/Schedulers.html#newBoundedElastic-int-int-java.lang.String-),
the magnitude of difference is so great that, just based on opinion, it seems unlikely that 
the configuration change would be powerful enough to close it significantly. It would be interesting to know the
implementation details of the Reactive Elastic Scheduler as with this limited data set it appears to be predictably an 
order of magnitude faster than the Reactive Parallel Scheduler, which is actually very impressive and begs the question 
of how it manages to overcome the Platform Thread model. 

Meanwhile, the Jdk Virtual Threads Executor can process up to a million of these tasks in ~2 seconds. 
The Reactive Virtual Thread Scheduler appears slower by ~0.5 seconds, however the simplicity of this test and lack 
statistics mean the difference cant be analysed fruitfully here. Just based on opinion, the difference between them 
seems so small I would regard them as equal for the sake of this test. If you have a Project Reactor 
app it appears that you can immediately benefit from the Virtual Thread performance boost with this simple integration.

### Conclusion

Virtual Threads are an incredibly powerful new feature in Java 21. In this test they appear two orders of magnitude 
faster than the co-operative threading model. Furthermore, the Virtual Thread API was easy to use, required no 
configuration and appeared to seamlessly integrate into Project Reactor code.   

It would be interesting to explore what can limit the performance of Virtual Threads. Perhaps the benefit becomes less 
clear when there is limited resource that each virtual thread needs to consume.

### Further Reading

[Spring - Embracing Virtual Threads](https://spring.io/blog/2022/10/11/embracing-virtual-threads)

[Performance Comparison of Netty and Tomcat](https://medium.com/@skhatri.dev/springboot-performance-testing-various-embedded-web-servers-7d460bbfdb1b)

[Historical Origins of Reactive Programming](https://subscription.packtpub.com/book/programming/9781787284951/2/ch02lvl1sec09/brief-history-of-reactive-libraries)
