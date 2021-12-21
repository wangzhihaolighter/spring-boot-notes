package com.example.testing;

import com.example.testing.controller.UserController;
import com.example.testing.entity.User;
import com.example.testing.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("mock mvc 单元测试")
public class MockMvcTests {

  @Autowired private MockMvc mvc;
  @Autowired private UserRepository userRepository;
  @Autowired private ObjectMapper mapper;

  /** 使用@MockBean模拟相应对象 */
  @MockBean private UserController userController;

  @SneakyThrows
  @Test
  @DisplayName("mock请求响应数据")
  public void mockMvcResultTest() {
    List<User> users = new ArrayList<>();
    User user = new User();
    user.setId(1L);
    user.setUsername("Github");
    user.setPassword("123456");
    users.add(user);
    Mockito.when(userController.queryAll()).thenReturn(users);

    MvcResult mvcResult =
        this.mvc
            .perform(MockMvcRequestBuilders.get("/user"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(users)))
            .andReturn();

    System.out.println("===== Mock Mvc Query =====");
    System.out.println(mvcResult.getResponse().getContentAsString());

    System.out.println("===== Repository Query =====");
    List<User> userList = userRepository.findAll();
    System.out.println(userList);
  }
}
