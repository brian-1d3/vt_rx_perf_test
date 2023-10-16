# Virtual Threads, Platform Threads & Reactive Java - Performance Comparison

## Description

[Virtual Threads](https://docs.oracle.com/en/java/javase/21/core/virtual-threads.html#GUID-DC4306FC-D6C1-4BCC-AECE-48C32C1A8DAA)
were released as part of Java 21, having been in preview since Java 19. Until this point, Java
threads were 'Platform Threads', implemented as wrapper objects around real operating system
threads. While powerful, this model has inherent limitations in high throughput applications
where hardware is limited to hosting a low number of threads. Virtual Threads solve this issue
by allowing a Platform Thread to switch between multiple Virtual Threads when one of them
encounters a blocking I/O operation.

-> Virtual threading as a concept is not new, comes from C/C#

The Java ecosystem responded to this limitation with other solutions. Reactive Programming,
first through RxJava in 2013 but currently more prominently through [Project Reactor]
(https://projectreactor.io/), which is packaged in [Spring WebFlux](https://docs.spring.
io/spring-framework/reference/web/webflux.html), [co-operative threading](https://luminousmen.
com/post/asynchronous-programming-cooperative-multitasking). Kotlin released the similar
concept of [co-routines](https://kotlinlang.org/docs/coroutines-guide.html#table-of-contents)    
in 2018.

While it might be tempting to attempt a direct comparison between Virtual Threads and Reactive
Programming framework, the reality is that they are approaching the same issue at different
levels of abstraction, and have therefore been developed with different design concerns in mind.
Perhaps the more relevant question is to ask, to what extent we can bring them together for a
production ready performance improvement?

Fortunitly, the Project Reactor framework and the Virtual Threads implementation facilitate a
very simple integration between the two and therefore making the traditional co-operative
threading approach and the new virtual threading approach relatively comparable, with some caveats.

This performance comparison is interested in answering the question - to what extent does
virtual threading offer a performance boost to the reactive programming model?

## Method

### Measurement:

The completion times in milliseconds of [10, 100, 1000, 10000, 100000, 1000000] concurrent function
calls passing a Runnable object consisting of a 1-second Thread.sleep() operation, on a set of
different function implementations using:

- Project Reactor w/ Virtual Thread Scheduler
- Project Reactor w/ Elastic Scheduler
- Project Reactor w/ Parallel Scheduler

For the range of 10 - 1000 concurrent calls, all implementations were measured once.

For 10000 concurrent calls, all except the Parallel Scheduler were measured once.

For 100000 concurrent calls, only the Virtual Thread and Virtual Thread Scheduler were measured,
taking the mean average of results from 10 executions.

Figures was gathered by running the following scripts:

```shell
sh test_scripts/_run-10-10000.sh
sh test_scripts/_run-100000.sh
sh test_scripts/_run-1000000.sh
```

The raw data is recorded in a comment in Main.java

#### Explanation:

In Project Reactor a Scheduler is the class used to configure multithreading behaviour. This
test uses `Schedulers.boundedElastic()`, `Schedulers.parallel()`,
and `Schedulers.fromExecutorService(...)`. With `boundedElastic` and `parallel`, Project Reactor
manages the multithreading for us. `boundedElastic` uses co-operative threading mentioned
above, it is also the default approach used by Spring WebFlux. `parallel` uses one-thread-per-task.
With `fromExecutorService` we are able to integrate any Java executor, allowing the Virtual thread
Executor to be integrated for this test
using: `Schedulers.fromExecutorService(Executors.newVirtualThreadPerTaskExecutor())`

#### Limitations:

There are some important limitations to this test:

- This does not simulate calls on a limited resource, such a database with a connection pool, or
  parallel intensive CPU operations. The `Thread.sleep()` operation represents an unlimited
  resource, which is unlikely to exist in a real environment. Perhaps the most analogous 
  activity would be API calls, which can still of course run into rate limiting.
- Completion times are mostly used for illustrative purposes, with the assumption that the overall
  pattern might be similar on a machine with the same number of cores. Results might follow a
  different pattern using a machine with more or less cores. 
- Using the `boundedElastic` Scheduler hints at a limitation of comparing the virtual threads to
  the standard approach in Project Reactor. The `BoundedElasticScheduler` java doc notes that it
  is "suited for moderate amount of blocking work" and previous versions of project reactor has an
  'unbounded' Elastic scheduler which was deprecated. Therefore, design considerations beyond
  pure concurrent performance play a role in its implementation. The integration with the virtual
  thread scheduler has not been through the same process of framework and community review yet and
  therefore the test here is more speculative about potential performance gains in the future,
  rather than saying this is production ready now.

#### Hardware:

2.3 GHz Quad-Core Intel Core i7 on a MacBookPro16,2

Physical Cores: 4

Logical Cores: 8

### Results

|                                   | 10   | 100   | 1000   | 10000  | 100000\* | 1000000\* |
| --------------------------------- | ---- | ----- | ------ | ------ | -------- | --------- |
| Reactive Virtual Thread Scheduler | 1075 | 1067  | 1073   | 1127    | 2545.6* | 16156.5* |
| Reactive Elastic Scheduler        | 1021 | 2024  | 13078  | 126719  | 1254028 | N/A      |
| Reactive Parallel Scheduler       | 2009 | 13055 | 125377 | 1253536 | N/A     | N/A      |

_Measurements in milliseconds_

_\* Mean average completion time of 10 executions_

### Discussion

The virtual thread scheduler executed the tasks orders of magnitude faster than the other 
schedulers, 10,000 sleeps in approx 1 second while the elastic scheduler took approx 2 mins and 
the parallel scheduler to 20 mins.

The parallel scheduler and elastic scheduler timings, within the limits if this test, followed a 
more obviously liner pattern. The parallel schedulers time roughly followed the result of N 
tasks * 1000ms / 8 cores. The elastic scheduler followed the same pattern except an order of 
magnitude faster. The virtual thread scheduler didn't start to significantly slow down until it 
reached 1,000,000 sleeps; completing that in 16 seconds. 

Interestingly, the Virtual Thread Scheduler was easy to use, requiring no configuration and 
appeared  to seamlessly integrate into Project Reactor code. That said, it would be useful to know 
the upper limit and how to mitigate against whatever errors / behaviours that caused, before 
integrating it completely into a production setting. 

My personal opinion, and conclusion here, is that I can see a future for virtual threads at the 
very least for tasks that they will excel at, such as API calls. I'd speculate that future 
implementations of HTTP clients will by default run the requests on virtual threads. I think 
this may have a particularly large on impact infrastructure that uses network calls (as opposed 
to connection pools) such as NoSQL databases that use HTTP calls to manage data. On the other 
hand, virtual threads might fan out very easily however limited resources might leave a lot of 
promises unfulfilled! While I think that in the right context virtual threads offer an amazing 
performance boost, I'd speculate that it will take time for the exact benefits and best
practices around virtual threads to be realised in our production code.

### Further Reading

[Spring - Embracing Virtual Threads](https://spring.io/blog/2022/10/11/embracing-virtual-threads)

[Performance Comparison of Netty and Tomcat](https://medium.com/@skhatri.dev/springboot-performance-testing-various-embedded-web-servers-7d460bbfdb1b)

[Historical Origins of Reactive Programming](https://subscription.packtpub.com/book/programming/9781787284951/2/ch02lvl1sec09/brief-history-of-reactive-libraries)

https://spring.io/blog/2019/12/13/flight-of-the-flux-3-hopping-threads-and-schedulers