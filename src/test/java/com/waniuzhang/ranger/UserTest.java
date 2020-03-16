package com.waniuzhang.ranger;

import com.waniuzhang.ranger.RangerClient;
import com.waniuzhang.ranger.config.RangerAuthConfig;
import com.waniuzhang.ranger.config.RangerClientConfig;
import com.waniuzhang.ranger.model.User;
import feign.Logger;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by 1 on 2020/3/14.
 */
public class UserTest {

    private static final String ranger_url =  "http://47.113.188.128:6080";

    private RangerClient rangerClient;

    @Test
    public void userTest(){

        RangerClientConfig config = RangerClientConfig.builder().connectTimeOutMills(1000).readTimeOutMills(1000).level(Logger.Level.BASIC)
                .authConfig(RangerAuthConfig.builder().userName("admin").password("zhangbt3").build())
                .url(ranger_url).build();

        rangerClient = new RangerClient(config);

        rangerClient.start();

        User user = User.builder().name("test").password("zhangbt123").firstName("zhang").lastName("san").isVisible(1).status(1)
                .userSource(0).userRoleList(Arrays.asList("ROLE_USER")).build();

//        User user1 = rangerClient.getUserApis().createUser(user);
//        System.out.print(user1.getName());

        User user2 = rangerClient.getUserApis().getUserByName("test");
        System.out.print(user2.getName());

        rangerClient.getUserApis().deleteUser(user2.getId(),true);
    }

}
