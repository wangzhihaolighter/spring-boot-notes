package com.example.poi.config.image;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * sm.ms网站图片上传响应dto
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SmMsUploadResponseDto {
    private Boolean success;
    private String code;
    private String message;
    private ResponseData data;
    @JsonProperty("RequestId")
    private String requestId;
    /**
     * success is false, image repeated, images is imagesUrl
     */
    private String images;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ResponseData {
        private Integer width;
        private Integer height;
        private String filename;
        @JsonProperty("storename")
        private String storeName;
        private Integer size;
        private String path;
        private String hash;
        private String url;
        private String delete;
        private String page;
    }
}
