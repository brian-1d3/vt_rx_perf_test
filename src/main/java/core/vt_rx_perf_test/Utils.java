package core.vt_rx_perf_test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static core.vt_rx_perf_test.RunTest.JDK_PLATFORM_THREADS;
import static core.vt_rx_perf_test.RunTest.JDK_VIRTUAL_THREADS;
import static core.vt_rx_perf_test.RunTest.JDK_VIRTUAL_THREADS_EXECUTOR;
import static core.vt_rx_perf_test.RunTest.REACTIVE_ELASTIC_SCHEDULER;
import static core.vt_rx_perf_test.RunTest.REACTIVE_PARALLEL_SCHEDULER;
import static core.vt_rx_perf_test.RunTest.REACTIVE_VIRTUAL_THREADS_SCHEDULER;

public class Utils {

    public static Set<String> asynTaskHandelerNameSet = Set.of(
        JDK_VIRTUAL_THREADS_EXECUTOR, JDK_VIRTUAL_THREADS, JDK_PLATFORM_THREADS, REACTIVE_VIRTUAL_THREADS_SCHEDULER,
        REACTIVE_ELASTIC_SCHEDULER, REACTIVE_PARALLEL_SCHEDULER
    );

    public static RunTest.Commands parseCommands(String[] args){
        System.out.println(Arrays.toString(args));
        Map<String, String> argsMap = new HashMap<>();
        for (String arg: args) {
            String[] parts = arg.split("=");
            argsMap.put(parts[0], parts[1]);
        }

        var numberOfTasks = Integer.parseInt(argsMap.get(RunTest.Commands.NUMBER_OF_TASKS));
        var taskHandlerSet = Set.of(argsMap.get(RunTest.Commands.TASK_HANDLERS).split(","));

        if(!asynTaskHandelerNameSet.containsAll(taskHandlerSet)){
            throw new IllegalArgumentException("Task handlers not formatted correctly");
        }

        return new RunTest.Commands(numberOfTasks, taskHandlerSet);
    }
}
