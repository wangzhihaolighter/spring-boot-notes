package com.example.springboot.part0501freemarker.response;

import lombok.Data;

/**
 * Description:
 *
 * @author zhihao.wang
 * @date 2018/2/14
 */
@Data
public class SysResult {
    private String result;
    private String message;
    private Object data;

    public void success(SysResult sysResult,Object data){
        sysResult.setResult("SUCCESS");
        sysResult.setData(data);
    }

    public void fail(SysResult sysResult,String message){
        sysResult.setResult("FAIL");
        sysResult.setMessage(message);
    }

}
