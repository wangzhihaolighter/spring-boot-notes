package com.example.testing.junit5;

import com.example.testing.controller.UserController;
import com.example.testing.entity.User;
import com.example.testing.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MockMvcTests {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepository userRepository;
    /**
     * 使用@MockBean模拟相应对象
     */
    @MockBean
    private UserController userController;
    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void exampleTest() throws Exception {
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setId(1L);
        user.setUsername("Github");
        user.setPassword("123456");
        users.add(user);
        Mockito.when(userController.queryAll()).thenReturn(users);

        MvcResult mvcResult = this.mvc.perform(get("/user/query/all"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().string(mapper.writeValueAsString(users)))
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());

        System.out.println("===== Repository Query =====");
        List<User> userList = userRepository.findAll();
        System.out.println(userList);
    }

}