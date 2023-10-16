package core.vt_rx_perf_test.code_under_test;

import java.util.concurrent.Executors;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class ReactorPlatformThreadScheduler implements AsyncTaskHandler {
    @Override
    public void doAsync(Runnable runnable) {
        Mono.fromRunnable(runnable).publishOn(Schedulers.fromExecutorService(Executors.newWorkStealingPool())).subscribe();
    }
}
