package com.moesama.giteedemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.moesama.giteedemo.utility.HttpUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/gitee")
public class AuthController {

    @RequestMapping("/login")
    public String login() {
        return "/user/login";
    }

    @RequestMapping("/index")
    public String index() {
        return "";
    }

    @RequestMapping("/callback")
    public String callback(String code, Model model) {
        System.out.println("得到的code为：" + code);
        Map<String, String> params = new HashMap<>(5);

        String url = "https://gitee.com/oauth/token";
//申请应用时分配的AppKey
        params.put("client_id", "00000000000.。。。");
//申请应用时分配的AppSecret
        params.put("client_secret", "0.。。。。。。。。。。。。。。");
//请求的类型，填写authorization_code
        params.put("grant_type", "authorization_code");
//调用authorize获得的code值
        params.put("code", code);
//回调地址，需需与注册应用里的回调地址一致。
        params.put("redirect_uri", "http://127.0.0.1:8080/gitee/callback");
        try {
            String result = HttpUtil.post(url, params);
            System.out.println("得到的结果为：" + result);

            JSONObject jsonObject = (JSONObject) JSONObject.parse(result);
            url = "https://gitee.com/api/v5/user";
            String getUserInfo = HttpUtil.get(url, jsonObject.get("access_token"));
            System.out.println("得到的用户信息为：" + getUserInfo);
            jsonObject = (JSONObject) JSONObject.parse(getUserInfo);
            model.addAttribute("userName", jsonObject.get("name"));
            model.addAttribute("userImage", jsonObject.get("avatar_url"));
        } catch (IOException e) {
            e.printStackTrace();
        }
// 否则返回到登陆页面
        return "/user/success";
    }

}
