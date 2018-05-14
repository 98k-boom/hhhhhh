package com.hhhhhh.cart;

import com.hhhhhh.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServantApplicationTest {

    @Resource
    private RedisService redisService;

    @Test
    public void contextLoads() {
        // ...
    }
}
