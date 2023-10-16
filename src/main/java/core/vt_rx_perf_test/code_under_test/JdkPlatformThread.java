package core.vt_rx_perf_test.code_under_test;

public class JdkPlatformThread implements AsyncTaskHandler {
    @Override
    public void doAsync(Runnable runnable) {
        new Thread(runnable).start();
    }
}
