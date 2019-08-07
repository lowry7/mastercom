package com.mmall.dao.test;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.test.TestBase;

/**
 * Created by geely on mmall.
 */
public class DaoTest extends TestBase {

    @Autowired
    private IUserService userService;

    @Test
    public void testDao(){
        User a = new User();
        a.setPassword("111");
        a.setUsername("aaaaageely");
        a.setRole(0);
        a.setCreateTime(new Date());
        a.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        
        userService.register(a);
        System.out.println("aaaaaaaaaaaaaa");
    }


}
