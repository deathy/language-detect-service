WIP notes on studying performance of this service and used library.

To check in more detail:
* is `LanguageDetector` thread-safe?
* reducing memory usage
* caching detector combinations?

Benchmark environment A:
* Intel(R) Core(TM) i7-6700K CPU @ 4.00GHz 
* Windows 10 64-bit
* `java -version`:

```
openjdk version "11.0.7" 2020-04-14
OpenJDK Runtime Environment AdoptOpenJDK (build 11.0.7+10)
OpenJDK 64-Bit Server VM AdoptOpenJDK (build 11.0.7+10, mixed mode)
```
* Java process started on Windows
* Ubuntu 20.04 WSL2 instance for running `ab` and other commands 
* jvm args: `-server -Xmx8g`


VERY random unreliable initial benchmark.
* apache-bench calls for 10k of romanian "ana are mere si alune" with concurrency 1,4,8.
* ignored initial load time. Detector is cached statically.
* at least 3-4 runs before measurement run.

```
Document Path:          /api/detect-language?q=ana%20are%20mere%20si%20alune%20si%20pere
Document Length:        2265 bytes

Concurrency Level:      1
Time taken for tests:   12.301 seconds
Complete requests:      10000
Failed requests:        0
Total transferred:      23700000 bytes
HTML transferred:       22650000 bytes
Requests per second:    812.96 [#/sec] (mean)
Time per request:       1.230 [ms] (mean)
Time per request:       1.230 [ms] (mean, across all concurrent requests)
Transfer rate:          1881.56 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   0.0      0       1
Processing:     1    1   0.1      1       6
Waiting:        1    1   0.1      1       6
Total:          1    1   0.1      1       6

Percentage of the requests served within a certain time (ms)
  50%      1
  66%      1
  75%      1
  80%      1
  90%      1
  95%      1
  98%      2
  99%      2
 100%      6 (longest request)
```

```
Document Path:          /api/detect-language?q=ana%20are%20mere%20si%20alune%20si%20pere
Document Length:        2265 bytes

Concurrency Level:      4
Time taken for tests:   3.455 seconds
Complete requests:      10000
Failed requests:        0
Total transferred:      23700000 bytes
HTML transferred:       22650000 bytes
Requests per second:    2894.22 [#/sec] (mean)
Time per request:       1.382 [ms] (mean)
Time per request:       0.346 [ms] (mean, across all concurrent requests)
Transfer rate:          6698.54 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   0.0      0       1
Processing:     1    1   0.2      1       5
Waiting:        1    1   0.2      1       5
Total:          1    1   0.2      1       6

Percentage of the requests served within a certain time (ms)
  50%      1
  66%      1
  75%      1
  80%      1
  90%      2
  95%      2
  98%      2
  99%      2
 100%      6 (longest request)
```

```
Document Path:          /api/detect-language?q=ana%20are%20mere%20si%20alune%20si%20pere
Document Length:        2265 bytes

Concurrency Level:      8
Time taken for tests:   2.294 seconds
Complete requests:      10000
Failed requests:        0
Total transferred:      23700000 bytes
HTML transferred:       22650000 bytes
Requests per second:    4359.05 [#/sec] (mean)
Time per request:       1.835 [ms] (mean)
Time per request:       0.229 [ms] (mean, across all concurrent requests)
Transfer rate:          10088.82 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   0.1      0       1
Processing:     1    2   0.2      2       6
Waiting:        1    2   0.2      1       6
Total:          1    2   0.3      2       6
ERROR: The median and mean for the waiting time are more than twice the standard
       deviation apart. These results are NOT reliable.

Percentage of the requests served within a certain time (ms)
  50%      2
  66%      2
  75%      2
  80%      2
  90%      2
  95%      2
  98%      2
  99%      3
 100%      6 (longest request)
```

Initial notes:
* load time of detector is extreme, also memory usage
* at least detector seems thread-safe, no exceptions/errors with increased concurrency
* detection request time seems ok, will check later how much is detection and how much is spring and JSON response

