# Virtual Threads, Platform Threads & Reactive Java - Performance Comparison

## Description
This is a simple performance test to compare Virtual Threads and Reactive Java

Instructions to run the test locally on your machine, as well as the results on my machine, are below.

The write-up can be found here: [ARTICLE.md](ARTICLE.md)

## Environment

-  JDK 21
-  Gradle version that supports JDK 21
   - I used this Gradle [release candidate](https://docs.gradle.org/8.4-rc-1/release-notes.html)  
   by running `./gradlew wrapper --gradle-version=8.4-rc-1`

## Run the Tests

```shell
sh test_scripts/run-range-10-10000.sh
sh test_scripts/run-1-100_000.sh
sh test_scripts/run-2-1_000_000.sh
```

## The Results

|                                   | 10   | 100   | 1000   | 10000    | 100000    | 1000000   |
| --------------------------------- | ---- | ----- | ------ |----------|-----------|-----------|
| Reactive Virtual Thread Scheduler | 1075 | 1067  | 1073   | 1127     | 2545.6*   | 16156.5*  |
| Reactive Elastic Scheduler        | 1021 | 2024  | 13078  | 126719   | 1254028   | N/A       |
| Reactive Parallel Scheduler       | 2009 | 13055 | 125377 | 1253536  | N/A       | N/A       |

_Measurements in milliseconds_

_\* Mean average completion time of 10 executions_