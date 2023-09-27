package core.vt_rx_perf_test.code_under_test;

import java.util.concurrent.Executors;

public class JdkVirtualThreadExecutor implements AsyncTaskHandler {
    @Override
    public void doAsync(Runnable runnable) {
        Executors.newVirtualThreadPerTaskExecutor().submit(runnable);
    }
}
