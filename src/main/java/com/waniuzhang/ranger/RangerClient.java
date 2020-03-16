package com.waniuzhang.ranger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.waniuzhang.ranger.api.UserApis;
import com.waniuzhang.ranger.api.UserFeignClient;
import com.waniuzhang.ranger.config.RangerClientConfig;
import feign.Feign;
import feign.Logger;
import feign.Request;
import feign.auth.BasicAuthRequestInterceptor;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * Created by 1 on 2020/3/13.
 */
@Slf4j
public class RangerClient {

    @Getter
    private UserApis userApis;

    private RangerClientConfig rangerClientConfig;

    public RangerClient(RangerClientConfig rangerClientConfig) {
        this.rangerClientConfig = rangerClientConfig;
    }

    private final static ObjectMapper mapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .configure(SerializationFeature.INDENT_OUTPUT,true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);

    private final static JacksonDecoder decoder = new JacksonDecoder(mapper);
    private final static JacksonEncoder encoder = new JacksonEncoder(mapper);

    private AtomicBoolean started = new AtomicBoolean(false);

    public void start(){
        if(started.get()){
            log.info("com.waniuzhang.ranger is started");
            return;
        }

        userApis = new UserApis(builder().target(UserFeignClient.class,rangerClientConfig.getUrl()));

        this.started.set(true);
        log.info("com.waniuzhang.ranger client starting");
    }

    private Feign.Builder builder(){
        return Feign.builder().logger(new Logger.JavaLogger())
                .logLevel(rangerClientConfig.getLevel())
                .options(new Request.Options(rangerClientConfig.getConnectTimeOutMills(),rangerClientConfig.getReadTimeOutMills()))
                .encoder(encoder)
                .decoder(decoder)
                .client(new OkHttpClient())
                .errorDecoder(new RangerErrorDecoder())
                .requestInterceptor(new RangerHeadersInterceptor())
                .requestInterceptor(new BasicAuthRequestInterceptor(rangerClientConfig.getAuthConfig().getUserName(),rangerClientConfig.getAuthConfig().getPassword()));
    }

    public void stop(){
        if(started.get()){
            this.started.set(false);
        }else{
            log.info("com.waniuzhang.ranger client not start");
        }
    }
}
