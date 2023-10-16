package core.vt_rx_perf_test.code_under_test;

import java.util.concurrent.Executors;

public class JdkVirtualThread implements AsyncTaskHandler {
    @Override
    public void doAsync(Runnable runnable) {
        Thread.ofVirtual().start(runnable);
    }
}
