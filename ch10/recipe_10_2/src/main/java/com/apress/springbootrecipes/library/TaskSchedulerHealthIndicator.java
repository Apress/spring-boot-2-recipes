package com.apress.springbootrecipes.library;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@Component
class TaskSchedulerHealthIndicator extends AbstractHealthIndicator {

  private final ThreadPoolTaskScheduler taskScheduler;

  TaskSchedulerHealthIndicator(ThreadPoolTaskScheduler taskScheduler) {
    this.taskScheduler = taskScheduler;
  }



  @Override
  protected void doHealthCheck(Health.Builder builder) throws Exception {

    int poolSize = taskScheduler.getPoolSize();
    int active = taskScheduler.getActiveCount();
    int free = poolSize - active;

    builder
            .withDetail("active", active)
            .withDetail("poolsize", poolSize);

    if (poolSize > 0 && free <= 1) {
      builder.down();
    } else {
      builder.up();
    }
  }
}
