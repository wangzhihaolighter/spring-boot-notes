package com.example.poi.config.image;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

/**
 * sm.ms业务类
 */
@Slf4j
@Service
public class SmMsImageService {
    /**
     * 这里上传图片使用 https://sm.ms 网站的 API
     * API文档：https://doc.sm.ms/
     */
    private static final String IMAGE_UPLOAD_URL = "https://sm.ms/api/v2/upload";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final RestTemplate restTemplate;

    public SmMsImageService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String upload(byte[] imageData) {
        //请求头
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("multipart/form-data");
        headers.setContentType(type);
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36");
        //图片参数
        ByteArrayResource resource = new ByteArrayResource(imageData) {
            @Override
            public String getFilename() {
                return UUID.randomUUID().toString() + ".jpg";
            }
        };
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        form.add("smfile", resource);
        HttpEntity<MultiValueMap<String, Object>> files = new HttpEntity<>(form, headers);

        try {
            String result = restTemplate.postForObject(IMAGE_UPLOAD_URL, files, String.class);
            log.info("sm.ms upload result:{}}", result);
            SmMsUploadResponseDto smMsUploadResponseDto = MAPPER.readValue(result, SmMsUploadResponseDto.class);
            if (smMsUploadResponseDto.getSuccess()) {
                return smMsUploadResponseDto.getData().getUrl();
            }
            //重复图片
            else if ("image_repeated".equals(smMsUploadResponseDto.getCode())) {
                return smMsUploadResponseDto.getImages();
            }
        } catch (Exception e) {
            log.error("sm.ms upload error: " + e.getMessage(), e);
        }
        return null;
    }

}
