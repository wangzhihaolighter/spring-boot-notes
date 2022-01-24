package com.example.spring.doc.controller;

import com.example.spring.doc.config.ApiConstant;
import com.example.spring.doc.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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
@Tag(name = "用户", description = "系统用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

  @Operation(tags = "retrieve", summary = "查询所有", description = "查询所有用户数据")
  @SecurityRequirement(name = ApiConstant.SECURITY_SCHEME_BASIC_AUTH)
  @SecurityRequirement(name = ApiConstant.SECURITY_SCHEME_BEARER_TOKEN)
  @Parameter(ref = "#/components/parameters/" + ApiConstant.SECURITY_SCHEME_AUTH_TOKEN_HEADER)
  @ApiResponse(
      responseCode = "200",
      description = "用户信息列表",
      content = {
        @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            array =
                @ArraySchema(
                    schema = @Schema(description = "用户信息DTO", implementation = UserDTO.class)))
      })
  @ApiResponse(
      responseCode = "400",
      description = "请求异常",
      content = {@Content(schema = @Schema())})
  @ApiResponse(
      responseCode = "500",
      description = "服务器异常",
      content = {@Content(schema = @Schema())})
  @GetMapping("/all")
  public List<UserDTO> getAll() {
    return Collections.emptyList();
  }

  @Operation(
      tags = "retrieve",
      summary = "根据id查询",
      description = "根据id查询指定用户数据",
      security = {@SecurityRequirement(name = ApiConstant.SECURITY_SCHEME_BASIC_AUTH)},
      parameters = {@Parameter(name = "id", description = "用户id", required = true, example = "1")},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "用户信息列表",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(description = "用户信息DTO", implementation = UserDTO.class))
            })
      })
  @GetMapping("/{id}")
  public UserDTO getById(@PathVariable("id") Long id) {
    UserDTO userDTO = new UserDTO();
    userDTO.setId(id);
    return userDTO;
  }

  @Operation(
      tags = "create",
      summary = "新增",
      description = "新增用户信息，响应用户id",
      security = {@SecurityRequirement(name = ApiConstant.SECURITY_SCHEME_BEARER_TOKEN)},
      parameters =
          @Parameter(
              ref = "#/components/parameters/" + ApiConstant.SECURITY_SCHEME_AUTH_TOKEN_HEADER),
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "新增用户id",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(description = "用户id", implementation = Long.class))
            })
      })
  @PostMapping
  public Long save(@RequestBody UserDTO userDTO) {
    log.info("save {}", userDTO);
    return 1L;
  }

  @Operation(
      tags = "update",
      summary = "更新",
      description = "更新指定用户信息，响应更新后的用户信息",
      security = {@SecurityRequirement(name = ApiConstant.SECURITY_SCHEME_BEARER_TOKEN)},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "更新后的用户信息",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(description = "用户信息DTO", implementation = UserDTO.class))
            })
      })
  @PutMapping
  public UserDTO update(@RequestBody UserDTO userDTO) {
    log.info("update {}", userDTO);
    return userDTO;
  }

  @Operation(
      tags = "delete",
      summary = "根据id删除",
      description = "根据id删除指定用户，响应是否删除成功",
      security = {@SecurityRequirement(name = ApiConstant.SECURITY_SCHEME_BEARER_TOKEN)},
      parameters = {
        @Parameter(
            name = "id",
            description = "用户id",
            schema = @Schema(implementation = Long.class),
            in = ParameterIn.QUERY,
            example = "1")
      },
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "是否成功",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(description = "是否成功", implementation = Boolean.class))
            })
      })
  @DeleteMapping
  public Boolean delete(@RequestParam("id") Long id) {
    log.info("delete {}", id);
    return true;
  }
}
