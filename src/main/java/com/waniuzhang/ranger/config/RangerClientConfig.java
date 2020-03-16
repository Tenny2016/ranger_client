package com.waniuzhang.ranger.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import feign.Logger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by 1 on 2020/3/13.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class RangerClientConfig {

    private int connectTimeOutMills = 5*1000;
    private int readTimeOutMills = 30*1000;

    private Logger.Level level = Logger.Level.BASIC;

    private String url = "http://47.113.188.128:6080";

    private RangerAuthConfig authConfig = new RangerAuthConfig();
}
