package com.example.springframework.boot.restful;

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

import java.util.ArrayList;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleControllerTests {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mvc;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testHome() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/")
                .contentType(MediaType.TEXT_PLAIN)).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.info(contentAsString);
        Assert.assertEquals(contentAsString, "Hello World");
    }

    @Test
    public void testGetUserAll() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/user/all")
                .contentType(MediaType.TEXT_PLAIN)).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.info(contentAsString);
        Assert.assertEquals(MAPPER.readValue(contentAsString, ArrayList.class).size(), 1);
    }

}
