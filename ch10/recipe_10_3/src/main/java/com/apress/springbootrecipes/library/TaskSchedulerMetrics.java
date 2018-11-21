package com.apress.springbootrecipes.library;

import io.micrometer.core.instrument.FunctionCounter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@Component
class TaskSchedulerMetrics implements MeterBinder {

  private final ThreadPoolTaskScheduler taskScheduler;

  public TaskSchedulerMetrics(ThreadPoolTaskScheduler taskScheduler) {
    this.taskScheduler = taskScheduler;
  }

  @Override
  public void bindTo(MeterRegistry registry) {
    FunctionCounter
            .builder("task.scheduler.active", taskScheduler,
                    ThreadPoolTaskScheduler::getActiveCount)
            .register(registry);

    FunctionCounter
            .builder("task.scheduler.pool-size", taskScheduler,
                    ThreadPoolTaskScheduler::getPoolSize)
            .register(registry);

  }
}
