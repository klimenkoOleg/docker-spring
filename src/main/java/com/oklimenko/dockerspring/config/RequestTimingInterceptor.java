package com.oklimenko.dockerspring.config;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;

@RequiredArgsConstructor
@Component
public class RequestTimingInterceptor extends HandlerInterceptorAdapter {
    private static final String REQ_PARAM_TIMING = "timing";


    private static final String TAG_METHOD = "method";
    private static final String TAG_ENDPOINT = "endpoint";
    public static final String TIMER_APP_REQUEST_LATENCY_SECONDS = "app_request_latency_seconds";
    private Timer responseTimeInMs;// = Summary
    private Timer responseTimeInMs2;// = Summary
//    private DistrSt

    private final MeterRegistry meterRegistry;

//            .build()
//            .name("app_request_count")
//            .labelNames(TAG_METHOD, TAG_ENDPOINT)
//            .help("Application Request Latency")
//            .register();

    @PostConstruct
    public void init() {
        meterRegistry.config().meterFilter(new MeterFilter() {
            @Override
            public DistributionStatisticConfig configure(Meter.Id id, DistributionStatisticConfig config) {
                if (id.getName().equals(TIMER_APP_REQUEST_LATENCY_SECONDS)) {
                    return DistributionStatisticConfig.builder()
                            .sla(Duration.ofMillis(5).toNanos(),
                                    Duration.ofMillis(10).toNanos(),
                                    Duration.ofMillis(25).toNanos(),
                                    Duration.ofMillis(50).toNanos(),
                                    Duration.ofMillis(75).toNanos(),
                                    Duration.ofMillis(100).toNanos(),
                                    Duration.ofMillis(250).toNanos(),
                                    Duration.ofMillis(500).toNanos(),
                                    Duration.ofMillis(750).toNanos(),
                                    Duration.ofMillis(1000).toNanos(),
                                    Duration.ofMillis(2500).toNanos(),
                                    Duration.ofMillis(5000).toNanos(),
                                    Duration.ofMillis(7500).toNanos(),
                                    Duration.ofMillis(10000).toNanos())
                            .build()
                            .merge(config);
                }
                return config;
            }
        });

//        responseTimeInMs = Timer.builder(TIMER_APP_REQUEST_LATENCY_SECONDS)
//                .tags(TAG_METHOD, "", TAG_ENDPOINT, "")
//                .publishPercentileHistogram()
//                .publishPercentiles(0.005, 0.1, 0.25, 0.5, 0.75, 0.95)
//                .publishPercentileHistogram()
//                .sla(Duration.ofMillis(10).toNanos(),
//                        Duration.ofMillis(100), Duration.ofMillis(200), Duration.ofMillis(1000))
//                .register(meterRegistry);
/*responseTimeInMs = Timer.builder(TIMER_APP_REQUEST_LATENCY_SECONDS)
                .tags(TAG_METHOD, "", TAG_ENDPOINT, "")
//                .publishPercentileHistogram()
//                .publishPercentiles(0.005, 0.1, 0.25, 0.5, 0.75, 0.95)
//                .publishPercentileHistogram()
                .sla(Duration.ofMillis(10), Duration.ofMillis(20), Duration.ofMillis(50), Duration.ofMillis(100),
                        Duration.ofMillis(200), Duration.ofMillis(500))
                .minimumExpectedValue(Duration.ofMillis(5))
                .maximumExpectedValue(Duration.ofSeconds(10))
//                .percentilePrecision(20)
                .register(meterRegistry);*/

        /*responseTimeInMs2 = Timer.builder(TIMER_APP_REQUEST_LATENCY_SECONDS+2)
                .tags(TAG_METHOD, "", TAG_ENDPOINT, "")
                .publishPercentileHistogram()
//                .publishPercentiles(0.005, 0.1, 0.25, 0.5, 0.75, 0.95)
//                .publishPercentileHistogram()
                .sla(Duration.ofMillis(200))
                .minimumExpectedValue(Duration.ofMillis(1))
                .maximumExpectedValue(Duration.ofSeconds(10))
                .percentilePrecision(2)
                .register(meterRegistry);*/

      /*  Timer timer = Timer.builder("test.timer").
                .quantiles(WindowSketchQuantiles
                        .quantiles(0.3, 0.5, 0.95)
                        .create())
                .register(registry);

        DistributionSummary hist = PrometheusDistributionSummary.Builder
                .builder("summary")
                .histogram(Histogram.linear(0, 10, 5))
                .register(registry);*/

//        DistributionSummary timer = DistributionSummary
//                .builder("timer2")
//                .histogram(Histogram.linearTime(TimeUnit.MILLISECONDS, 0, 200, 3))
//                .register(registry);
//
//                publishPercentiles()
//                .quantiles(WindowSketchQuantiles
//                        .quantiles(0.3, 0.5, 0.95)
//                        .create())
//                .register(registry);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute(REQ_PARAM_TIMING, System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) {
        Long timingAttr = (Long) request.getAttribute(REQ_PARAM_TIMING);
        long completedTime = System.currentTimeMillis() - timingAttr;
//        String handlerLabel = getShortFormOfHandlerMethodName(handler, handler.toString());
//        responseTimeInMs.labels(request.getMethod(), handlerLabel, Integer.toString(response.getStatus())).observe(
//                completedTime);
        meterRegistry.timer(TIMER_APP_REQUEST_LATENCY_SECONDS,
                TAG_METHOD, request.getMethod(),
                TAG_ENDPOINT, request.getRequestURI())
                .record(Duration.ofMillis(completedTime));

        /*meterRegistry.timer(TIMER_APP_REQUEST_LATENCY_SECONDS+2,
                TAG_METHOD, request.getMethod(),
                TAG_ENDPOINT, request.getRequestURI()).record(completedTime, TimeUnit.MILLISECONDS);*/
    }

    /*private String getShortFormOfHandlerMethodName(Object handler, String handlerLabel) {
        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            handlerLabel = method.getDeclaringClass().getSimpleName() + "." + method.getName();
        }
        return handlerLabel;
    }*/
}