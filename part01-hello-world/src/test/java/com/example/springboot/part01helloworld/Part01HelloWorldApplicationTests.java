package com.example.springboot.part01helloworld;

import com.example.springboot.part01helloworld.hello.SampleController;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Part01HelloWorldApplicationTests {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Autowired
	SampleController sampleController;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mvc;

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	/**
	 * 单元测试示例1：断言结果
	 */
	@Test
	public void home() {
		//断言结果是否相等，不相等报错
		assertEquals("Hello World!",sampleController.home());
	}

	/**
	 * 单元测试示例2：传递参数，查看结果
	 */
	@Test
	public void sayHello() throws Exception {
		String content = "hello";
		System.out.println(MAPPER.writeValueAsString(sampleController.say(content)));
	}

	/**
	 * 单元测试示例3：Mock测试
	 */
	@Test
	public void change() throws Exception {
		List<String> contents = new ArrayList<>();
		contents.add("Hey, brother!");
		contents.add("everything is going well!");
		commonPostMock(contents,"/change");
	}

	private void commonPostMock(Object value, String url) throws Exception {
		MvcResult rs = mvc.perform(MockMvcRequestBuilders.post(url)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(MAPPER.writeValueAsString(value))
		).andReturn();
		System.out.println("response code ====>>> " + rs.getResponse().getStatus());
		System.out.println("response body ====>>> " + rs.getResponse().getContentAsString());
	}

}
