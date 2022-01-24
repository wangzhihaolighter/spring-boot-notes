package com.example.springfox.controller;

import com.example.springfox.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = {"用户"})
@RestController
@RequestMapping("/user")
public class UserController {

  @ApiOperation(value = "查询所有", notes = "查询所有用户数据")
  @GetMapping("/all")
  public List<UserDTO> getAll() {
    return Collections.emptyList();
  }

  @ApiOperation(value = "根据id查询", notes = "根据id查询指定用户数据")
  @ApiParam(name = "id", value = "用户id", required = true, example = "1")
  @GetMapping("/{id}")
  public UserDTO getById(@PathVariable("id") Long id) {
    log.info("getById {}", id);
    return null;
  }

  @ApiOperation(value = "新增", notes = "新增用户数据，响应新增用户id")
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "x-one-header-key",
        value = "一个普通请求头参数",
        required = true,
        dataType = "String",
        paramType = "header",
        example = "abc"),
  })
  @PostMapping
  public Long save(@RequestBody UserDTO userDTO) {
    log.info("save {}", userDTO);
    return 1L;
  }

  @ApiOperation(value = "更新", notes = "更新用户数据，响应更新后的用户数据")
  @PutMapping
  public UserDTO update(@RequestBody UserDTO userDTO) {
    log.info("update {}", userDTO);
    return userDTO;
  }

  @ApiOperation(value = "根据id删除", notes = "根据id删除指定用户，响应是否成功")
  @ApiParam(value = "用户id", required = true, example = "1")
  @DeleteMapping
  public void delete(@RequestParam("id") Long id) {
    log.info("delete {}", id);
  }
}
