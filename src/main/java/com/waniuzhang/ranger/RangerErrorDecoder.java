package com.waniuzhang.ranger;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;

import java.io.IOException;

/**
 * Created by 1 on 2020/3/13.
 */
public class RangerErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        return new RangerClientException(response.status(),errorMassage(methodKey,response));
    }

    private String errorMassage(String methodKey, Response response){
        String massage = String.format("status %s reading %s", response.status(),methodKey);

        try {
            if(response.body() != null){
               massage += "content:\n" + Util.toString(response.body().asReader());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return massage;
    }
}
