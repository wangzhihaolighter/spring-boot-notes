package com.example.benchmarks;

import com.example.MacroBenchmarksApplication;
import com.example.domain.TestDTO;
import com.example.service.TestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@BenchmarkMode(Mode.AverageTime) // 测试方法平均执行时间
@OutputTimeUnit(TimeUnit.NANOSECONDS) // 输出结果的时间粒度为微秒
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS) // 代码预热迭代 x 次
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS) // 循环运行 x 次
@Fork(1) // fork x 个分叉运行基准测试
@State(Scope.Benchmark) // 定义类实例的生命周期
public class JsonBenchmark {

  private TestService testService;

  private static final TestDTO dto = new TestDTO();

  static {
    dto.setId(1L);
    dto.setName("test");
  }

  /** 初始化容器的时候只执行一次 */
  @Setup(Level.Trial)
  public void init() {
    ConfigurableApplicationContext context =
        SpringApplication.run(MacroBenchmarksApplication.class);
    testService = context.getBean(TestService.class);
  }

  @Benchmark
  @Threads(10)
  public String jackson() throws JsonProcessingException {
    return testService.jackson(dto);
  }

  @Benchmark
  @Threads(10)
  public String gson() {
    return testService.gson(dto);
  }

  @Benchmark
  @Threads(10)
  public String fastjson() {
    return testService.fastjson(dto);
  }

  public static void main(String[] args) throws RunnerException {
    Options build = new OptionsBuilder().include(JsonBenchmark.class.getSimpleName()).build();
    new Runner(build).run();
  }
}
