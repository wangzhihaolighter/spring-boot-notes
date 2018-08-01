package com.example.springframework.boot.interceptor.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import java.util.Map;

/**
 * 实现WebRequestInterceptor接口，处理请求参数
 */
@Slf4j
public class WebRequestParamInterceptor implements WebRequestInterceptor {
    @Override
    public void preHandle(WebRequest webRequest) throws Exception {
        log.info("-----" + this.getClass().getSimpleName() + " preHandle-----");
        //处理请求参数 -> 消除参数中所有的空白字符
        Map<String, String[]> parameterMap = webRequest.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String[] value = entry.getValue();
            for (int i = 0; i < value.length; i++) {
                value[0] = value[0].replaceAll("\\s", "");
            }
        }
        log.info("requestParam:" + new ObjectMapper().writeValueAsString(parameterMap));
    }

    @Override
    public void postHandle(WebRequest webRequest, ModelMap modelMap) throws Exception {
        log.info("-----" + this.getClass().getSimpleName() + " postHandle-----");
    }

    @Override
    public void afterCompletion(WebRequest webRequest, Exception e) throws Exception {
        log.info("-----" + this.getClass().getSimpleName() + " afterCompletion-----");
    }
}
