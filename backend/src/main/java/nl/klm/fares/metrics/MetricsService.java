package nl.klm.fares.metrics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {

    @Autowired
    private MetricsHolder metricsHolder;

    @Async
    public void addMetric(Metric metric) {
        metricsHolder.calculateMetric(metric);
    }
}
