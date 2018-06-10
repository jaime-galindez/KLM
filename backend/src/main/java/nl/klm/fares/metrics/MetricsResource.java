package nl.klm.fares.metrics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/metrics")
public class MetricsResource {

    @Autowired
    private MetricsHolder metricsHolder;

    @GetMapping(value = "")
    public MetricsHolder getMetrics() {
        return metricsHolder.copy();
    }
}
