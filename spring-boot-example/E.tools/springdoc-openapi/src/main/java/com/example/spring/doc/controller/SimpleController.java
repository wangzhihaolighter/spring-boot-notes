package com.example.spring.doc.controller;

import com.example.spring.doc.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "SimpleController", description = "API文档标签")
@RestController
public class SimpleController {

    @Operation(hidden = true)
    @GetMapping("/")
    public String sayHello() {
        return "Hello,World!";
    }

    @Operation(
            summary = "welcome",
            description = "欢迎",
            method = MediaType.TEXT_PLAIN_VALUE,
            security = {@SecurityRequirement(name = "bearer-key")},
            responses = {
                    @ApiResponse(description = "欢迎语", content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(implementation = String.class)))
            })
    @GetMapping("/welcome")
    public String home() {
        return "Hello, nice to meet you!";
    }

    @Operation(
            summary = "查询所有用户",
            description = "返回：所有用户数据",
            method = MediaType.TEXT_PLAIN_VALUE,
            security = {@SecurityRequirement(name = "basicScheme")})
    @GetMapping("/user/all")
    public List<UserDTO> getUserAll() {
        return null;
    }

    @Operation(
            summary = "根据id查询用户",
            description = "返回：用户数据",
            method = MediaType.TEXT_PLAIN_VALUE)
    @GetMapping("/user/{id}")
    public UserDTO getUserById(@Parameter(description = "用户id", required = true, example = "1") @PathVariable("id") Long id) {
        return null;
    }

    @Operation(
            summary = "保存用户",
            description = "返回：新增用户id",
            method = MediaType.APPLICATION_JSON_VALUE,
            parameters = @Parameter(ref = "#/components/parameters/myHeader1"))
    @Parameters({
            @Parameter(name = "access_token", description = "授权码", required = true, schema = @Schema(implementation = String.class), in = ParameterIn.HEADER, example = "abc"),
            @Parameter(name = "id", description = "用户id", schema = @Schema(implementation = Long.class), in = ParameterIn.QUERY, example = "123456"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "参数填写错误"),
            @ApiResponse(responseCode = "500", description = "接口异常")
    })
    @PostMapping("/user/save")
    public Long saveUser(@RequestBody UserDTO user) {
        return 1L;
    }

    @Operation(
            summary = "根据id删除用户",
            description = "无返回",
            method = MediaType.TEXT_PLAIN_VALUE,
            parameters = @Parameter(ref = "#/components/headers/myHeader2"))
    @DeleteMapping("/user/delete")
    public void deleteUser(
            @Parameter(name = "id", description = "用户id", required = true, example = "1")
            @RequestParam("id") Long id) {
    }
}
