package core;

import core.vt_rx_perf_test.RunTest;
import core.vt_rx_perf_test.Utils;
import core.vt_rx_perf_test.code_under_test.AsyncTaskHandler;
import core.vt_rx_perf_test.code_under_test.JdkPlatformThread;
import core.vt_rx_perf_test.code_under_test.JdkVirtualThreadExecutor;
import core.vt_rx_perf_test.code_under_test.ReactorElasticScheduler;
import core.vt_rx_perf_test.code_under_test.ReactorParallelScheduler;
import core.vt_rx_perf_test.code_under_test.ReactorVirtualThreadScheduler;

import java.util.Arrays;
import java.util.LinkedHashMap;

import static core.vt_rx_perf_test.RunTest.getTaskHandlers;

public class Main {
    public static void main(String[] args){

        var commands = Utils.parseCommands(args);
        var numberOfTasks = commands.numberOfTasks();
        var asyncTaskHandlers = getTaskHandlers(commands.asyncTaskHandlers());

        System.out.printf("Running %s tasks using %s\n", numberOfTasks, asyncTaskHandlers.keySet());

        var runTest = new RunTest();

        asyncTaskHandlers.forEach((k, v) ->{
            var result = runTest.runTasks(v, numberOfTasks);
            System.out.println("%s tasks, %s completed in %sms".formatted(numberOfTasks, k, result));
        });
    }
}

/*

[--numberOfTasks=10, --taskHandlers=ReactiveVirtualThreadScheduler,ReactiveElasticScheduler,ReactiveParallelScheduler]
Running 10 tasks using [ReactiveVirtualThreadScheduler, ReactiveElasticScheduler, ReactiveParallelScheduler]
10 tasks, ReactiveVirtualThreadScheduler completed in 1072ms
10 tasks, ReactiveElasticScheduler completed in 1018ms
10 tasks, ReactiveParallelScheduler completed in 2011ms
[--numberOfTasks=100, --taskHandlers=ReactiveVirtualThreadScheduler,ReactiveElasticScheduler,ReactiveParallelScheduler]
Running 100 tasks using [ReactiveVirtualThreadScheduler, ReactiveElasticScheduler, ReactiveParallelScheduler]
100 tasks, ReactiveVirtualThreadScheduler completed in 1066ms
100 tasks, ReactiveElasticScheduler completed in 2019ms
100 tasks, ReactiveParallelScheduler completed in 13048ms
[--numberOfTasks=1000, --taskHandlers=ReactiveVirtualThreadScheduler,ReactiveElasticScheduler,ReactiveParallelScheduler]
Running 1000 tasks using [ReactiveVirtualThreadScheduler, ReactiveElasticScheduler, ReactiveParallelScheduler]
1000 tasks, ReactiveVirtualThreadScheduler completed in 1087ms
1000 tasks, ReactiveElasticScheduler completed in 13060ms
1000 tasks, ReactiveParallelScheduler completed in 125392ms
[--numberOfTasks=10000, --taskHandlers=ReactiveVirtualThreadScheduler,ReactiveElasticScheduler,ReactiveParallelScheduler]
Running 10000 tasks using [ReactiveVirtualThreadScheduler, ReactiveElasticScheduler, ReactiveParallelScheduler]
10000 tasks, ReactiveVirtualThreadScheduler completed in 1364ms
10000 tasks, ReactiveElasticScheduler completed in 125406ms
10000 tasks, ReactiveParallelScheduler completed in 1253536ms


~~~ 100000 Tasks
[--numberOfTasks=100000, --taskHandlers=ReactiveVirtualThreadScheduler]
Running 100000 tasks using [ReactiveVirtualThreadScheduler]
100000 tasks, ReactiveVirtualThreadScheduler completed in 2555ms
[--numberOfTasks=100000, --taskHandlers=ReactiveVirtualThreadScheduler]
Running 100000 tasks using [ReactiveVirtualThreadScheduler]
100000 tasks, ReactiveVirtualThreadScheduler completed in 2660ms
[--numberOfTasks=100000, --taskHandlers=ReactiveVirtualThreadScheduler]
Running 100000 tasks using [ReactiveVirtualThreadScheduler]
100000 tasks, ReactiveVirtualThreadScheduler completed in 2444ms
[--numberOfTasks=100000, --taskHandlers=ReactiveVirtualThreadScheduler]
Running 100000 tasks using [ReactiveVirtualThreadScheduler]
100000 tasks, ReactiveVirtualThreadScheduler completed in 3163ms
[--numberOfTasks=100000, --taskHandlers=ReactiveVirtualThreadScheduler]
Running 100000 tasks using [ReactiveVirtualThreadScheduler]
100000 tasks, ReactiveVirtualThreadScheduler completed in 2330ms
[--numberOfTasks=100000, --taskHandlers=ReactiveVirtualThreadScheduler]
Running 100000 tasks using [ReactiveVirtualThreadScheduler]
100000 tasks, ReactiveVirtualThreadScheduler completed in 2247ms
[--numberOfTasks=100000, --taskHandlers=ReactiveVirtualThreadScheduler]
Running 100000 tasks using [ReactiveVirtualThreadScheduler]
100000 tasks, ReactiveVirtualThreadScheduler completed in 2645ms
[--numberOfTasks=100000, --taskHandlers=ReactiveVirtualThreadScheduler]
Running 100000 tasks using [ReactiveVirtualThreadScheduler]
100000 tasks, ReactiveVirtualThreadScheduler completed in 2605ms
[--numberOfTasks=100000, --taskHandlers=ReactiveVirtualThreadScheduler]
Running 100000 tasks using [ReactiveVirtualThreadScheduler]
100000 tasks, ReactiveVirtualThreadScheduler completed in 2411ms
[--numberOfTasks=100000, --taskHandlers=ReactiveVirtualThreadScheduler]
Running 100000 tasks using [ReactiveVirtualThreadScheduler]

---- 1000000 tasks

[--numberOfTasks=1000000, --taskHandlers=ReactiveVirtualThreadScheduler]
Running 1000000 tasks using [ReactiveVirtualThreadScheduler]
1000000 tasks, ReactiveVirtualThreadScheduler completed in 16833ms
[--numberOfTasks=1000000, --taskHandlers=ReactiveVirtualThreadScheduler]
Running 1000000 tasks using [ReactiveVirtualThreadScheduler]
1000000 tasks, ReactiveVirtualThreadScheduler completed in 15366ms
[--numberOfTasks=1000000, --taskHandlers=ReactiveVirtualThreadScheduler]
Running 1000000 tasks using [ReactiveVirtualThreadScheduler]
1000000 tasks, ReactiveVirtualThreadScheduler completed in 16351ms
[--numberOfTasks=1000000, --taskHandlers=ReactiveVirtualThreadScheduler]
Running 1000000 tasks using [ReactiveVirtualThreadScheduler]
1000000 tasks, ReactiveVirtualThreadScheduler completed in 16635ms
[--numberOfTasks=1000000, --taskHandlers=ReactiveVirtualThreadScheduler]
Running 1000000 tasks using [ReactiveVirtualThreadScheduler]
1000000 tasks, ReactiveVirtualThreadScheduler completed in 16499ms
[--numberOfTasks=1000000, --taskHandlers=ReactiveVirtualThreadScheduler]
Running 1000000 tasks using [ReactiveVirtualThreadScheduler]
1000000 tasks, ReactiveVirtualThreadScheduler completed in 16185ms
[--numberOfTasks=1000000, --taskHandlers=ReactiveVirtualThreadScheduler]
Running 1000000 tasks using [ReactiveVirtualThreadScheduler]
1000000 tasks, ReactiveVirtualThreadScheduler completed in 16556ms
[--numberOfTasks=1000000, --taskHandlers=ReactiveVirtualThreadScheduler]
Running 1000000 tasks using [ReactiveVirtualThreadScheduler]
1000000 tasks, ReactiveVirtualThreadScheduler completed in 15703ms
[--numberOfTasks=1000000, --taskHandlers=ReactiveVirtualThreadScheduler]
Running 1000000 tasks using [ReactiveVirtualThreadScheduler]
1000000 tasks, ReactiveVirtualThreadScheduler completed in 15537ms
[--numberOfTasks=1000000, --taskHandlers=ReactiveVirtualThreadScheduler]
Running 1000000 tasks using [ReactiveVirtualThreadScheduler]

[--numberOfTasks=100000, --taskHandlers=ReactiveElasticScheduler]
Running 100000 tasks using [ReactiveElasticScheduler]
100000 tasks, ReactiveElasticScheduler completed in 1254028ms

 */