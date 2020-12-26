package com.example.Galaxy;


import com.example.Galaxy.service.RecordService;
import com.example.Galaxy.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GalaxyApplicationTests {
    @Autowired
    private RedisService redisService;

    @Autowired
    private RecordService recordService;

    @Test
    void contextLoads() {
        redisService.deleteCacheByClass(recordService.getClass());
    }
}
