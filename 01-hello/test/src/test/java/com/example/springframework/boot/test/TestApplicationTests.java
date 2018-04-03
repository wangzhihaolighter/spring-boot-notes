package com.example.springframework.boot.test;

import com.example.springframework.boot.test.entity.User;
import com.example.springframework.boot.test.web.SimpleController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplicationTests {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private SimpleController simpleController;

    //单元测试示例1：打印日志
    @Test
    public void logTest() throws JsonProcessingException {
        log.info(MAPPER.writeValueAsString(simpleController.consumer()));
    }

    //单元测试示例2：断言
    @Test
    public void AssertTest() {
        User user = simpleController.consumer();
        //正确
        Assert.assertEquals("飞翔的大白菜", user.getUsername());
        Assert.assertSame(1L, user.getId());
        //错误
        Assert.assertEquals("123456", user.getPassword());
    }

    //单元测试示例3：Mock测试,测试接口
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mvc;
    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Test
    public void mockTest() throws Exception {
        User user = new User();
        user.setId(10086L);
        user.setUsername("中国移动");
        user.setPassword("China Mobile");
        //mock接口测试
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/revert")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(user))).andReturn();
        //日志
        log.info("response ==> "+mvcResult.getResponse().getContentAsString());
        //断言
        Assert.assertEquals(MAPPER.writeValueAsString(user), mvcResult.getResponse().getContentAsString());
    }

}
