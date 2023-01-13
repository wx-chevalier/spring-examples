package com.cloud.feign;

import com.cloud.config.FeignInterceptorConfig;
import com.cloud.utils.ResultHelper;
import com.cloud.model.user.AppUser;
import com.cloud.model.user.Organization;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "user-center",configuration = FeignInterceptorConfig.class, url = "http://127.0.0.1:8000")
public interface UserClient {
    @GetMapping(value = "/users/{id}")
    ResultHelper findUserById(@PathVariable(name = "id") String id);

    @GetMapping(value = "/users/getByNickname")
    ResultHelper getByNickname(@RequestParam("nickname") String nickname);

    @GetMapping(value = "/users/byNickName")
    List<AppUser> findByNickName(@RequestParam("nickName") String nickName);

    @PostMapping("/users/register")
    public ResultHelper register(@RequestBody AppUser appUser);



    @GetMapping(value = "/organization/findOrganizationByName")
    List<Organization> findOrganizationByName(@RequestParam("organizationName") String organizationName);

    @GetMapping(value = "/organization/findOrganizationById")
    Organization findOrganizationById(@RequestParam("id") Integer id);
}
