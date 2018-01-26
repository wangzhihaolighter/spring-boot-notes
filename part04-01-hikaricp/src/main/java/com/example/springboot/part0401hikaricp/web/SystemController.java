package com.example.springboot.part0401hikaricp.web;

import com.example.springboot.part0401hikaricp.model.second.User;
import com.example.springboot.part0401hikaricp.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *
 * @author zhihao.wang
 * @date 2018/1/25
 */
@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @PostMapping("/user/add")
    public Map<String,Object> saveUser(@RequestBody User user) {
        Map<String,Object> result = new HashMap<>(2);
        try {
            int i = systemService.saveUser(user);
            setData(result, i);
        } catch (Exception e) {
            e.printStackTrace();
            setError(result, e.getMessage());
        }
        return result;
    }

    @GetMapping("/user/query")
    public Map<String,Object> queryUserInfo(String username, String password) {
        Map<String,Object> result = new HashMap<>(2);
        try {
            User user = systemService.queryUserInfo(username,password);
            setData(result, user);
        } catch (Exception e) {
            e.printStackTrace();
            setError(result, e.getMessage());
        }
        return result;
    }

    private void setData(Map<String, Object> result, Object data) {
        result.put("message","success");
        result.put("result",data);
    }

    private void setError(Map<String, Object> result, Object error) {
        result.put("message","fail");
        result.put("result",error);
    }

}
