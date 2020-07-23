package com.example.springfox.controller;

import com.example.springfox.dto.UserDTO;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = {"API文档标签"})
@RestController
public class SimpleController {

    @ApiIgnore
    @GetMapping("/")
    public String sayHello() {
        return "Hello,World!";
    }

    @ApiOperation(value = "welcome", produces = MediaType.TEXT_PLAIN_VALUE, notes = "返回：Hello swagger")
    @GetMapping("/welcome")
    public String home() {
        return "Hello, nice to meet you!";
    }

    @ApiOperation(value = "查询所有用户", produces = MediaType.TEXT_PLAIN_VALUE, notes = "返回：所有用户数据")
    @GetMapping("/user/all")
    public List<UserDTO> getUserAll() {
        return null;
    }

    @ApiOperation(value = "根据id查询用户", produces = MediaType.TEXT_PLAIN_VALUE, notes = "返回：用户数据")
    @GetMapping("/user/{id}")
    public UserDTO getUserById(@ApiParam(value = "用户id", required = true, example = "1") @PathVariable("id") Long id) {
        return null;
    }

    @ApiOperation(value = "保存用户", produces = MediaType.APPLICATION_JSON_VALUE, notes = "返回：新增用户id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "授权码", required = true, dataType = "string", paramType = "header", example = "abc"),
            @ApiImplicitParam(name = "id", value = "用户id", dataType = "string", paramType = "query", example = "123456"),
    })
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "参数填写错误"),
            @ApiResponse(code = 500, message = "接口异常")
    })
    @PostMapping("/user/save")
    public Long saveUser(@RequestBody UserDTO user) {
        return 1L;
    }

    @ApiOperation(value = "根据id删除用户", produces = MediaType.TEXT_PLAIN_VALUE, notes = "无返回")
    @DeleteMapping("/user/delete")
    public void deleteUser(
            @ApiParam(value = "用户id", required = true, example = "1")
            @RequestParam("id") Long id) {
    }
}
