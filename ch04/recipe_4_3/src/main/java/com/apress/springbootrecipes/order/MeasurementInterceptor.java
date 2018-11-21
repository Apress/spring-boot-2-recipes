package com.apress.springbootrecipes.order;

import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MeasurementInterceptor implements AsyncHandlerInterceptor {

  private static final String START_TIME = "startTime";

  public boolean preHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler) throws Exception {

    if (request.getAttribute(START_TIME) == null) {
      request.setAttribute(START_TIME, System.currentTimeMillis());
    }
    return true;
  }

  public void postHandle(HttpServletRequest request,
                         HttpServletResponse response,
                         Object handler,
                         ModelAndView modelAndView) throws Exception {

    var startTime = (Long) request.getAttribute(START_TIME);
    request.removeAttribute(START_TIME);
    var duration = System.currentTimeMillis() - startTime;
    System.out.printf("Response-Processing-Time: %dms.%n", duration);
    System.out.printf("Response-Processing-Thread: %s%n", Thread.currentThread().getName());
  }

  @Override
  public void afterConcurrentHandlingStarted(HttpServletRequest request,
                                             HttpServletResponse response,
                                             Object handler) throws Exception {

    var startTime = (Long) request.getAttribute(START_TIME);
    request.setAttribute(START_TIME, System.currentTimeMillis());
    var duration = System.currentTimeMillis() - startTime;

    System.out.printf("Request-Processing-Time: %dms.%n", duration);
    System.out.printf("Request-Processing-Thread: %s%n", Thread.currentThread().getName());
  }
}
