package core.vt_rx_perf_test;

import core.vt_rx_perf_test.code_under_test.AsyncTaskHandler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class RunTest {
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
