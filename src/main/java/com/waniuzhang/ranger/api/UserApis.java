package com.waniuzhang.ranger.api;

import com.waniuzhang.ranger.RangerClientException;
import com.waniuzhang.ranger.model.User;
import feign.Param;
import lombok.AllArgsConstructor;

/**
 * Created by 1 on 2020/3/14.
 */
@AllArgsConstructor
public class UserApis {

    private UserFeignClient feignClient;

    public User createUser(User user) throws RangerClientException{
        return feignClient.createUser(user);
    }

    public void deleteUser(@Param("id") Integer id, @Param("forceDelete") boolean forceDelete){
        feignClient.deleteUser(id,forceDelete);
    }

    public User getUserByName(@Param("name") String name) throws RangerClientException{
        return feignClient.getUserByName(name);
    }
}
