package com.example.springframework.boot.jdbc;

import com.example.springframework.boot.jdbc.entity.User;
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
public class JdbcApplicationTests {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testUserInsert() throws Exception {
        User user = new User();
        user.setUsername("飞翔的github");
        user.setPassword("123456");
        //mock接口测试
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/user/insert")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(user))).andReturn();
        //response
        String response = mvcResult.getResponse().getContentAsString();
        //日志
        log.info("response ==> " + response);
        //断言
        Assert.assertEquals(11L, Long.parseLong(response));
    }

    @Test
    public void testUserUpdate() throws Exception {
        long id = 1L;
        User user = new User();
        user.setId(id);
        user.setUsername("飞翔的github");
        user.setPassword("123456");
        //mock接口测试
        //更新
        MvcResult updateResult = mvc.perform(MockMvcRequestBuilders.put("/user/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(user))).andReturn();
        //查询
        MvcResult queryResult = mvc.perform(MockMvcRequestBuilders.get("/user/query/" + id)
                .contentType(MediaType.TEXT_PLAIN)).andReturn();
        //response
        String updateResponse = updateResult.getResponse().getContentAsString();
        String queryResponse = queryResult.getResponse().getContentAsString();
        //日志
        log.info("updateResponse ==> " + updateResponse);
        log.info("queryResponse ==> " + queryResponse);
        //断言
        Assert.assertEquals(1, Integer.parseInt(updateResponse));
        Assert.assertEquals(MAPPER.writeValueAsString(user), queryResponse);
    }

    @Test
    public void testUserDelete() throws Exception {
        long id = 1L;
        //mock接口测试
        MvcResult deleteResult = mvc.perform(MockMvcRequestBuilders.delete("/user/delete/" + id)
                .contentType(MediaType.TEXT_PLAIN)).andReturn();
        MvcResult queryResult = mvc.perform(MockMvcRequestBuilders.get("/user/query/" + id)
                .contentType(MediaType.TEXT_PLAIN)).andReturn();
        //response
        String deleteResponse = deleteResult.getResponse().getContentAsString();
        String queryResponse = queryResult.getResponse().getContentAsString();
        //日志
        log.info("deleteResponse ==> " + deleteResponse);
        log.info("queryResponse ==> " + queryResponse);
        //断言
        Assert.assertEquals(1, Integer.parseInt(deleteResponse));
        Assert.assertEquals("", queryResponse);
    }

}
