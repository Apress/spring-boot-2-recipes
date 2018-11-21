package com.apress.springbootrecipes.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@SpringBootApplication
@EnableScheduling
public class SchedulingApplication implements SchedulingConfigurer {

    @Autowired
    private HelloWorld helloWorld;

    public static void main(String[] args) {
        SpringApplication.run(SchedulingApplication.class, args);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addFixedRateTask(
                () -> helloWorld.printMessage()
                , 4000);
    }
}
