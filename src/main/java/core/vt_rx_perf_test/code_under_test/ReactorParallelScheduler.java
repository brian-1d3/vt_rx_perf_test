package core.vt_rx_perf_test.code_under_test;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class ReactorParallelScheduler implements AsyncTaskHandler {
    @Override
    public void doAsync(Runnable runnable) {
        Mono.fromRunnable(runnable).publishOn(Schedulers.parallel()).subscribe();
    }
}
