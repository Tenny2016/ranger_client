package com.waniuzhang.ranger;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Created by 1 on 2020/3/13.
 */
public class RangerHeadersInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Accept","application/json");
        requestTemplate.header("X-XSRF-HEADER","\"\"");
        requestTemplate.header("Content-Type","application/json");

    }
}
