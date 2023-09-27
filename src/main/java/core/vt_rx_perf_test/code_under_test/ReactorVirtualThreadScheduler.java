package core.vt_rx_perf_test.code_under_test;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Executors;

public class ReactorVirtualThreadScheduler implements AsyncTaskHandler {
    @Override
    public void doAsync(Runnable runnable) {
        Mono.fromRunnable(runnable).publishOn(Schedulers.fromExecutorService(Executors.newVirtualThreadPerTaskExecutor())).subscribe();
    }
}
