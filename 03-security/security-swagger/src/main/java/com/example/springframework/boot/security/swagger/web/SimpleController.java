package com.example.springframework.boot.security.swagger.web;

import com.example.springframework.boot.security.swagger.entity.User;
import com.example.springframework.boot.security.swagger.repository.UserRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Api(tags = "simple demo 接口")
@RestController
public class SimpleController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    @ApiIgnore
    public void toSwagger(HttpServletResponse response) throws IOException {
        response.sendRedirect("http://localhost:8080/swagger-ui.html");
    }

    @GetMapping("/welcome")
    @ApiOperation(value = "welcome", produces = MediaType.TEXT_PLAIN_VALUE, notes = "返回：Hello swagger")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数填写错误"),
            @ApiResponse(code = 500, message = "接口异常")
    })
    public String home() {
        return "Hello security swagger";
    }

    @GetMapping("/user/all")
    @ApiOperation(value = "查询所有用户", produces = MediaType.TEXT_PLAIN_VALUE, notes = "返回：所有用户数据")
    public List<User> getUserAll() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    @ApiOperation(value = "根据id查询用户", produces = MediaType.TEXT_PLAIN_VALUE, notes = "返回：用户数据")
    public User getUserById(@ApiParam(value = "用户id", required = true, example = "1")
                            @PathVariable("id") Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @PostMapping("/user/save")
    @ApiOperation(value = "保存用户", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, notes = "返回：新增用户id")
    public Long saveUser(@ApiParam(required = true)
                         @RequestBody User user) {
        return userRepository.save(user).getId();
    }

    @PostMapping("/user/delete")
    @ApiOperation(value = "根据id删除用户", produces = MediaType.TEXT_PLAIN_VALUE, notes = "无返回")
    public void deleteUser(
            @ApiParam(value = "用户id", required = true, example = "1")
            @RequestParam("id") Long id) {
        userRepository.deleteById(id);
    }

}
