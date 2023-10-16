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

[--numberOfTasks=10, --taskHandlers=JdkVirtualThreadsExecutor,JdkPlatformThread,ReactiveVirtualThreadScheduler,ReactiveElasticScheduler,ReactiveParallelScheduler]
Running 10 tasks using [JdkVirtualThreadsExecutor, JdkPlatformThread, ReactiveVirtualThreadScheduler, ReactiveElasticScheduler, ReactiveParallelScheduler]
10 tasks, JdkVirtualThreadsExecutor completed in 1015ms
10 tasks, JdkPlatformThread completed in 1006ms
10 tasks, ReactiveVirtualThreadScheduler completed in 1075ms
10 tasks, ReactiveElasticScheduler completed in 1021ms
10 tasks, ReactiveParallelScheduler completed in 2009ms
[--numberOfTasks=100, --taskHandlers=JdkVirtualThreadsExecutor,JdkPlatformThread,ReactiveVirtualThreadScheduler,ReactiveElasticScheduler,ReactiveParallelScheduler]
Running 100 tasks using [JdkVirtualThreadsExecutor, JdkPlatformThread, ReactiveVirtualThreadScheduler, ReactiveElasticScheduler, ReactiveParallelScheduler]
100 tasks, JdkVirtualThreadsExecutor completed in 1017ms
100 tasks, JdkPlatformThread completed in 1020ms
100 tasks, ReactiveVirtualThreadScheduler completed in 1067ms
100 tasks, ReactiveElasticScheduler completed in 2024ms
100 tasks, ReactiveParallelScheduler completed in 13055ms
[--numberOfTasks=1000, --taskHandlers=JdkVirtualThreadsExecutor,JdkPlatformThread,ReactiveVirtualThreadScheduler,ReactiveElasticScheduler,ReactiveParallelScheduler]
Running 1000 tasks using [JdkVirtualThreadsExecutor, JdkPlatformThread, ReactiveVirtualThreadScheduler, ReactiveElasticScheduler, ReactiveParallelScheduler]
1000 tasks, JdkVirtualThreadsExecutor completed in 1040ms
1000 tasks, JdkPlatformThread completed in 1089ms
1000 tasks, ReactiveVirtualThreadScheduler completed in 1073ms
1000 tasks, ReactiveElasticScheduler completed in 13078ms
1000 tasks, ReactiveParallelScheduler completed in 125377ms
[--numberOfTasks=10000, --taskHandlers=JdkVirtualThreadsExecutor,JdkPlatformThread,ReactiveVirtualThreadScheduler,ReactiveElasticScheduler]
Running 10000 tasks using [JdkVirtualThreadsExecutor, JdkPlatformThread, ReactiveVirtualThreadScheduler, ReactiveElasticScheduler]
10000 tasks, JdkVirtualThreadsExecutor completed in 1322ms
10000 tasks, JdkPlatformThread completed in 3127ms
10000 tasks, ReactiveVirtualThreadScheduler completed in 1127ms
10000 tasks, ReactiveElasticScheduler completed in 126719ms

~~~ 100000 Tasks
[--numberOfTasks=100000, --taskHandlers=JdkPlatformThread]
Running 100000 tasks using [JdkPlatformThread]
100000 tasks, JdkPlatformThread completed in 27246ms
[--numberOfTasks=100000, --taskHandlers=JdkPlatformThread]
Running 100000 tasks using [JdkPlatformThread]
100000 tasks, JdkPlatformThread completed in 28043ms
[--numberOfTasks=100000, --taskHandlers=JdkPlatformThread]
Running 100000 tasks using [JdkPlatformThread]
100000 tasks, JdkPlatformThread completed in 27743ms
[--numberOfTasks=100000, --taskHandlers=JdkPlatformThread]
Running 100000 tasks using [JdkPlatformThread]
100000 tasks, JdkPlatformThread completed in 27157ms
[--numberOfTasks=100000, --taskHandlers=JdkPlatformThread]
Running 100000 tasks using [JdkPlatformThread]
100000 tasks, JdkPlatformThread completed in 28109ms
[--numberOfTasks=100000, --taskHandlers=JdkPlatformThread]
Running 100000 tasks using [JdkPlatformThread]
100000 tasks, JdkPlatformThread completed in 26809ms
[--numberOfTasks=100000, --taskHandlers=JdkPlatformThread]
Running 100000 tasks using [JdkPlatformThread]
100000 tasks, JdkPlatformThread completed in 26520ms
[--numberOfTasks=100000, --taskHandlers=JdkPlatformThread]
Running 100000 tasks using [JdkPlatformThread]
100000 tasks, JdkPlatformThread completed in 26652ms
[--numberOfTasks=100000, --taskHandlers=JdkPlatformThread]
Running 100000 tasks using [JdkPlatformThread]
100000 tasks, JdkPlatformThread completed in 26680ms
[--numberOfTasks=100000, --taskHandlers=JdkPlatformThread]
Running 100000 tasks using [JdkPlatformThread]
100000 tasks, JdkPlatformThread completed in 26513ms
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
100000 tasks, ReactiveVirtualThreadScheduler completed in 2396ms
[--numberOfTasks=100000, --taskHandlers=JdkVirtualThreadsExecutor]
Running 100000 tasks using [JdkVirtualThreadsExecutor]
100000 tasks, JdkVirtualThreadsExecutor completed in 1553ms
[--numberOfTasks=100000, --taskHandlers=JdkVirtualThreadsExecutor]
Running 100000 tasks using [JdkVirtualThreadsExecutor]
100000 tasks, JdkVirtualThreadsExecutor completed in 2256ms
[--numberOfTasks=100000, --taskHandlers=JdkVirtualThreadsExecutor]
Running 100000 tasks using [JdkVirtualThreadsExecutor]
100000 tasks, JdkVirtualThreadsExecutor completed in 2170ms
[--numberOfTasks=100000, --taskHandlers=JdkVirtualThreadsExecutor]
Running 100000 tasks using [JdkVirtualThreadsExecutor]
100000 tasks, JdkVirtualThreadsExecutor completed in 2141ms
[--numberOfTasks=100000, --taskHandlers=JdkVirtualThreadsExecutor]
Running 100000 tasks using [JdkVirtualThreadsExecutor]
100000 tasks, JdkVirtualThreadsExecutor completed in 2330ms
[--numberOfTasks=100000, --taskHandlers=JdkVirtualThreadsExecutor]
Running 100000 tasks using [JdkVirtualThreadsExecutor]
100000 tasks, JdkVirtualThreadsExecutor completed in 2178ms
[--numberOfTasks=100000, --taskHandlers=JdkVirtualThreadsExecutor]
Running 100000 tasks using [JdkVirtualThreadsExecutor]
100000 tasks, JdkVirtualThreadsExecutor completed in 2131ms
[--numberOfTasks=100000, --taskHandlers=JdkVirtualThreadsExecutor]
Running 100000 tasks using [JdkVirtualThreadsExecutor]
100000 tasks, JdkVirtualThreadsExecutor completed in 1733ms
[--numberOfTasks=100000, --taskHandlers=JdkVirtualThreadsExecutor]
Running 100000 tasks using [JdkVirtualThreadsExecutor]
100000 tasks, JdkVirtualThreadsExecutor completed in 1496ms
[--numberOfTasks=100000, --taskHandlers=JdkVirtualThreadsExecutor]
Running 100000 tasks using [JdkVirtualThreadsExecutor]
100000 tasks, JdkVirtualThreadsExecutor completed in 2182ms

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
1000000 tasks, ReactiveVirtualThreadScheduler completed in 15900ms
[--numberOfTasks=1000000, --taskHandlers=JdkVirtualThreadsExecutor]
Running 1000000 tasks using [JdkVirtualThreadsExecutor]
1000000 tasks, JdkVirtualThreadsExecutor completed in 10429ms
[--numberOfTasks=1000000, --taskHandlers=JdkVirtualThreadsExecutor]
Running 1000000 tasks using [JdkVirtualThreadsExecutor]
1000000 tasks, JdkVirtualThreadsExecutor completed in 10535ms
[--numberOfTasks=1000000, --taskHandlers=JdkVirtualThreadsExecutor]
Running 1000000 tasks using [JdkVirtualThreadsExecutor]
1000000 tasks, JdkVirtualThreadsExecutor completed in 10801ms
[--numberOfTasks=1000000, --taskHandlers=JdkVirtualThreadsExecutor]
Running 1000000 tasks using [JdkVirtualThreadsExecutor]
1000000 tasks, JdkVirtualThreadsExecutor completed in 16309ms
[--numberOfTasks=1000000, --taskHandlers=JdkVirtualThreadsExecutor]
Running 1000000 tasks using [JdkVirtualThreadsExecutor]
1000000 tasks, JdkVirtualThreadsExecutor completed in 11619ms
[--numberOfTasks=1000000, --taskHandlers=JdkVirtualThreadsExecutor]
Running 1000000 tasks using [JdkVirtualThreadsExecutor]
1000000 tasks, JdkVirtualThreadsExecutor completed in 16701ms
[--numberOfTasks=1000000, --taskHandlers=JdkVirtualThreadsExecutor]
Running 1000000 tasks using [JdkVirtualThreadsExecutor]
1000000 tasks, JdkVirtualThreadsExecutor completed in 11502ms
[--numberOfTasks=1000000, --taskHandlers=JdkVirtualThreadsExecutor]
Running 1000000 tasks using [JdkVirtualThreadsExecutor]
1000000 tasks, JdkVirtualThreadsExecutor completed in 15591ms
[--numberOfTasks=1000000, --taskHandlers=JdkVirtualThreadsExecutor]
Running 1000000 tasks using [JdkVirtualThreadsExecutor]
1000000 tasks, JdkVirtualThreadsExecutor completed in 16263ms
[--numberOfTasks=1000000, --taskHandlers=JdkVirtualThreadsExecutor]
Running 1000000 tasks using [JdkVirtualThreadsExecutor]
1000000 tasks, JdkVirtualThreadsExecutor completed in 10967ms
 */