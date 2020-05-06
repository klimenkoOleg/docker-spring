package com.oklimenko.dockerspring.config;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Component
public class RequestCounterInterceptor
        extends HandlerInterceptorAdapter {

    private static final String APP_REQUEST_COUNT = "app_request_count";
    private static final String TAG_METHOD = "method";
    private static final String TAG_ENDPOINT = "endpoint";
    private static final String TAG_STATUS = "http_status";
    private final MeterRegistry meterRegistry;

//    private Counter requestTotal;

    @PostConstruct
    public void init() {
//        requestTotal =
//                Counter.builder(APP_REQUEST_COUNT)
//                        .tags(TAG_METHOD, "", TAG_ENDPOINT, "", TAG_STATUS, "")
//                        .description("Application Request Count")
//                        .register(meterRegistry);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception e) {
//        String handlerLabel = getShortFormOfHandlerMethodName(handler, handler.toString());
//        requestTotal.labels(request.getMethod(), handlerLabel, Integer.toString(response.getStatus())).inc();

        meterRegistry.counter(APP_REQUEST_COUNT, TAG_METHOD, request.getMethod(),
                TAG_ENDPOINT, request.getRequestURI(),
                TAG_STATUS, Integer.toString(response.getStatus())).increment();
    }

    /*private String getShortFormOfHandlerMethodName(Object handler, String handlerLabel) {
        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            handlerLabel = method.getDeclaringClass().getSimpleName() + "." + method.getName();
        }
        return handlerLabel;
    }*/
}
