package com.itheima;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest//如果在测试类里注入这个注解，那么这个测试类会先初始化springboot的容器了
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void testSet(){
        //往redis存储一个键值对  StringRedisTemplate
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set("username", "zhangsan");
    }

    @Test
    public void testGet(){
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        String username = operations.get("username");
        System.out.println(username);
    }
}
