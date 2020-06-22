package com.example.Galaxy.util;

import com.alibaba.fastjson.JSONObject;

public class JsonResponse {
    private int code;
    private String message;
    private JSONObject data;
    private final String []map = {"成功","失败"};
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public JSONObject getJsonRes(){
        JSONObject jsonRes = new JSONObject();
        jsonRes.put("code",this.code);
        jsonRes.put("message",this.map[this.code]);
        if(this.data!=null)
            jsonRes.put("data",this.data);
        return jsonRes;
    }
}
