package nl.klm.fares.metrics;

import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class MetricsHolder {

    private final Lock lock = new ReentrantLock();

    private long totalNumberOfRequests = 0;
    private long totalNumberOf200Requests = 0;
    private long totalNumberOf4xxRequests = 0;
    private long totalNumberOf5xxRequests = 0;
    private double averageResponseTime = 0.0;
    private double minResponseTime = Double.MAX_VALUE;
    private double maxResponseTime = 0.0;

    public void calculateMetric(Metric metric) {
        lock.lock();

        try {
            totalNumberOfRequests++;
            int status = metric.getStatus();
            if (status == 200) {
                totalNumberOf200Requests++;
            } else if (status >= 400 && status <= 499) {
                totalNumberOf4xxRequests++;
            } else if (status >= 500 && status <= 599) {
                totalNumberOf5xxRequests++;
            }
            long responseTime = metric.getEndTime() - metric.getStartTime();
            minResponseTime = Math.min(minResponseTime, responseTime);
            maxResponseTime = Math.max(maxResponseTime, responseTime);
            averageResponseTime = (((totalNumberOfRequests - 1) * averageResponseTime) + responseTime) / totalNumberOfRequests;
        } finally {
            lock.unlock();
        }
    }

    public long getTotalNumberOfRequests() {
        return totalNumberOfRequests;
    }

    public long getTotalNumberOf200Requests() {
        return totalNumberOf200Requests;
    }

    public long getTotalNumberOf4xxRequests() {
        return totalNumberOf4xxRequests;
    }

    public long getTotalNumberOf5xxRequests() {
        return totalNumberOf5xxRequests;
    }

    public double getAverageResponseTime() {
        return averageResponseTime;
    }

    public double getMinResponseTime() {
        return minResponseTime;
    }

    public double getMaxResponseTime() {
        return maxResponseTime;
    }

    public MetricsHolder copy() {
        lock.lock();
        try {
            MetricsHolder other = new MetricsHolder();
            other.totalNumberOfRequests = this.totalNumberOfRequests;
            other.averageResponseTime = this.averageResponseTime;
            other.maxResponseTime = this.maxResponseTime;
            other.minResponseTime = this.minResponseTime;
            other.totalNumberOf5xxRequests = this.totalNumberOf5xxRequests;
            other.totalNumberOf4xxRequests = this.totalNumberOf4xxRequests;
            other.totalNumberOf200Requests = this.totalNumberOf200Requests;
            return other;
        } finally {
            lock.unlock();
        }
    }
}
