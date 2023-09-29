package core.vt_rx_perf_test;

import core.vt_rx_perf_test.code_under_test.AsyncTaskHandler;
import core.vt_rx_perf_test.code_under_test.JdkPlatformThread;
import core.vt_rx_perf_test.code_under_test.JdkVirtualThreadExecutor;
import core.vt_rx_perf_test.code_under_test.ReactorElasticScheduler;
import core.vt_rx_perf_test.code_under_test.ReactorParallelScheduler;
import core.vt_rx_perf_test.code_under_test.ReactorVirtualThreadScheduler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class RunTest {

    public static String JDK_VIRTUAL_THREADS_EXECUTOR = "JdkVirtualThreadsExecutor";
    public static String JDK_PLATFORM_THREADS = "JdkPlatformThread";
    public static String REACTIVE_VIRTUAL_THREADS_SCHEDULER = "ReactiveVirtualThreadScheduler";
    public static String REACTIVE_ELASTIC_SCHEDULER ="ReactiveElasticScheduler";
    public static String REACTIVE_PARALLEL_SCHEDULER ="ReactiveParallelScheduler";

    public record Commands(int numberOfTasks, Set<String> asyncTaskHandlers) {
        public static String NUMBER_OF_TASKS = "--numberOfTasks";
        public static String TASK_HANDLERS = "--taskHandlers";
    }

    public long runTasks(AsyncTaskHandler asyncTaskHandlerUnderTest, int numberOfTasks){
        var completableFutures = makeCompletableFutures(numberOfTasks);
        long start = System.nanoTime();

        for(var i = 0; i < numberOfTasks; i++){
            int taskNumber = i;
            asyncTaskHandlerUnderTest.doAsync(() ->{
                sleep();
                completableFutures[taskNumber].complete(1);
            });
        }

        CompletableFuture.allOf(completableFutures).join();

        long finish = System.nanoTime();
        long timeElapsed = finish - start;

        return TimeUnit.MILLISECONDS.convert(timeElapsed, TimeUnit.NANOSECONDS);
    }

    public static LinkedHashMap<String, AsyncTaskHandler> getTaskHandlers(Set<String> commands) {
        var asyncTaskHandlers = new LinkedHashMap<String, AsyncTaskHandler>();
        // to preserve the ordering for more repeatable tests
        if(commands.contains(JDK_VIRTUAL_THREADS_EXECUTOR))
            asyncTaskHandlers.put(JDK_VIRTUAL_THREADS_EXECUTOR, new JdkVirtualThreadExecutor());
        if(commands.contains(JDK_PLATFORM_THREADS))
            asyncTaskHandlers.put(JDK_PLATFORM_THREADS, new JdkPlatformThread());
        if(commands.contains(REACTIVE_VIRTUAL_THREADS_SCHEDULER))
            asyncTaskHandlers.put(REACTIVE_VIRTUAL_THREADS_SCHEDULER, new ReactorVirtualThreadScheduler());
        if(commands.contains(REACTIVE_ELASTIC_SCHEDULER))
            asyncTaskHandlers.put(REACTIVE_ELASTIC_SCHEDULER, new ReactorElasticScheduler());
        if(commands.contains(REACTIVE_PARALLEL_SCHEDULER))
            asyncTaskHandlers.put(REACTIVE_PARALLEL_SCHEDULER, new ReactorParallelScheduler());

        return asyncTaskHandlers;
    }
    private  CompletableFuture<Integer>[] makeCompletableFutures(int amount) {
        var array = new CompletableFuture[amount];

        for(var i = 0; i < amount ; i++){
            array[i] = new CompletableFuture();
        }

        return array;
    }

    public static void sleep(){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
