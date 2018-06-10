package nl.klm.fares.config;

import nl.klm.fares.metrics.Metric;
import nl.klm.fares.metrics.MetricsService;
import nl.klm.fares.metrics.ThreadLocalMetric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MetricsInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private MetricsService metricsService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Metric metric = new Metric();
        metric.setStartTime(System.nanoTime());
        ThreadLocalMetric.setMetric(metric);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Metric metric = ThreadLocalMetric.getMetric();
        metric.setEndTime(System.nanoTime());
        metric.setStatus(response.getStatus());
        metricsService.addMetric(metric);
    }
}
