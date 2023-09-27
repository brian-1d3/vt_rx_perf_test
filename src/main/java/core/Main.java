package core;

import core.vt_rx_perf_test.RunTest;
import core.vt_rx_perf_test.code_under_test.AsyncTaskHandler;
import core.vt_rx_perf_test.code_under_test.JdkVirtualThreadExecutor;
import core.vt_rx_perf_test.code_under_test.ReactorElasticScheduler;
import core.vt_rx_perf_test.code_under_test.ReactorParallelScheduler;
import core.vt_rx_perf_test.code_under_test.ReactorVirtualThreadScheduler;

import java.util.LinkedHashMap;

public class Main {
    public static void main(String[] args){

        int numberOfTasks = 1000;

        var asyncTaskHandlers = new LinkedHashMap<String, AsyncTaskHandler>();
        asyncTaskHandlers.put("Jdk Virtual Threads Executor", new JdkVirtualThreadExecutor());
        asyncTaskHandlers.put("Reactive Virtual Thread Scheduler", new ReactorVirtualThreadScheduler());
        asyncTaskHandlers.put("Reactive Elastic Scheduler", new ReactorElasticScheduler());
        asyncTaskHandlers.put("Reactive Parallel Scheduler", new ReactorParallelScheduler());

        var runTest = new RunTest();

        asyncTaskHandlers.forEach((k, v) ->{
            var result = runTest.runTasks(v, numberOfTasks);
            System.out.println("%s completed in %sms".formatted(k, result));
        });

        /*
        RESULTS:

        When number of tasks = 10

        Jdk Virtual Threads Executor completed in 1016 milliseconds
        Reactive Virtual Thread Scheduler completed in 1073 milliseconds
        Reactive Elastic Scheduler completed in 1018 milliseconds
        Reactive Parallel Scheduler completed in 2008 milliseconds

        ~~~
        When number of tasks = 100

        Jdk Virtual Threads Executor completed in 1023 milliseconds
        Reactive Virtual Thread Scheduler completed in 1066 milliseconds
        Reactive Elastic Scheduler completed in 2020 milliseconds
        Reactive Parallel Scheduler completed in 13059 milliseconds

        ~~~
        When number of tasks = 1000

        Jdk Virtual Threads Executor completed in 1041 milliseconds
        Reactive Virtual Thread Scheduler completed in 1074 milliseconds
        Reactive Elastic Scheduler completed in 13060 milliseconds
        Reactive Parallel Scheduler completed in 125400 milliseconds

        ~~~
        When number of tasks = 10000

        Jdk Virtual Threads Executor completed in 1278 milliseconds
        Reactive Virtual Thread Scheduler completed in 1203 milliseconds
        Reactive Elastic Scheduler completed in 125459 milliseconds
        Reactive Parallel Scheduler [No test - est. 1250000ms - 20.8 minutes]

        ~~~~
        When number of tasks = 100000

        Jdk Virtual Threads Executor completed in 2272ms
        Jdk Virtual Threads Executor completed in 1463ms
        Jdk Virtual Threads Executor completed in 1480ms
        Jdk Virtual Threads Executor completed in 1509ms
        Jdk Virtual Threads Executor completed in 2170ms
        Jdk Virtual Threads Executor completed in 1662ms
        Jdk Virtual Threads Executor completed in 2145ms
        Jdk Virtual Threads Executor completed in 2281ms
        Jdk Virtual Threads Executor completed in 1793ms
        Jdk Virtual Threads Executor completed in 2132ms

        Reactive Virtual Thread Scheduler completed in 2278ms
        Reactive Virtual Thread Scheduler completed in 2306ms
        Reactive Virtual Thread Scheduler completed in 1624ms
        Reactive Virtual Thread Scheduler completed in 2514ms
        Reactive Virtual Thread Scheduler completed in 2541ms
        Reactive Virtual Thread Scheduler completed in 3207ms
        Reactive Virtual Thread Scheduler completed in 2409ms
        Reactive Virtual Thread Scheduler completed in 2291ms
        Reactive Virtual Thread Scheduler completed in 2247ms
        Reactive Virtual Thread Scheduler completed in 2391ms
         */

    }
}

