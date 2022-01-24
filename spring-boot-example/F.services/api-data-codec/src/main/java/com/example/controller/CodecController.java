package com.example.controller;

import com.example.config.codec.annotation.Codec;
import com.example.config.codec.constant.CodecTypeEnum;
import com.example.config.dto.InfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/codec")
public class CodecController {

  @Codec(res = CodecTypeEnum.BASE64)
  @GetMapping("/res")
  public Object encodeRes() {
    InfoDTO infoDTO = new InfoDTO();
    infoDTO.setId(1L);
    infoDTO.setContent("xxx");
    return infoDTO;
  }

  @Codec(req = CodecTypeEnum.BASE64)
  @PostMapping("/req")
  public Object decodeReq(@RequestBody InfoDTO infoDTO) {
    return infoDTO;
  }
}
