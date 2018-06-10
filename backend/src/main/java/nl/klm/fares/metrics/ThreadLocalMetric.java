package nl.klm.fares.metrics;

public class ThreadLocalMetric {

    private static ThreadLocal<Metric> metricThreadLocal = new ThreadLocal();

    public static Metric getMetric() {
        return metricThreadLocal.get();
    }

    public static void setMetric(Metric metric) {
        metricThreadLocal.set(metric);
    }
}
