package com.example.controller;

import com.example.service.WrapService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Objects;

@RestController
class SimpleController {

    private final WrapService wrapService;
    private final OkHttpClient okHttpClient;

    public SimpleController(WrapService wrapService, OkHttpClient okHttpClient) {
        this.wrapService = wrapService;
        this.okHttpClient = okHttpClient;
    }

    @GetMapping("/")
    public String hello() {
        return "Hello,World!";
    }

    @GetMapping("/wrap/{word}")
    public String wrap(@PathVariable("word") String word) {
        if (wrapService == null) {
            return word;
        }
        return wrapService.wrap(word);
    }

    @GetMapping("/okhttp")
    public String useOkHttp() throws IOException {
        Request request = new Request.Builder()
                .url("https://www.baidu.com/")
                .get()
                .build();
        Response response = okHttpClient.newCall(request).execute();
        String result = Objects.requireNonNull(response.body()).string();
        System.out.println(result);
        return result;
    }
}
