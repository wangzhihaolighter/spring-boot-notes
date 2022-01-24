package com.example.spring.doc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
        @ApiResponse(
            description = "欢迎语",
            content =
                @Content(
                    mediaType = MediaType.TEXT_PLAIN_VALUE,
                    schema = @Schema(implementation = String.class)))
      })
  @GetMapping("/welcome")
  public String home() {
    return "Hello, nice to meet you!";
  }
}
