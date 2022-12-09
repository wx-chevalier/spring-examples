package com.cloud.user.dao;


import com.cloud.model.user.AppUser;
import com.cloud.user.service.AppUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestDao {

    @Autowired
    private AppUserService appUserService;

    @Test
    public void password() {
        List<AppUser> byNickName = appUserService.findByNickName("1");
        System.out.println(byNickName);
    }

    @Test
    public void password11() {
        String a="12";
        String[] split = a.split(",");
        for (String s : split) {
            System.out.println(s);
        }
    }


}
