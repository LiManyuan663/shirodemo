package com.example.demo.controller;


import com.example.demo.util.JWTUtil;
import com.example.demo.util.ResultMap;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
public class HelloController {


    private final ResultMap resultMap;

    @Autowired
    public HelloController(ResultMap resultMap) {
        this.resultMap = resultMap;
    }


//    @RequestMapping("/index")
//    public String sayHello(){
//        return "index";
//    }

    @PostMapping("/login")
    public ResultMap login(@RequestParam("username") String username) {
        System.out.println("在/login接口里面登录");
//        String realPassword = userMapper.getPassword(username);
//        if (realPassword == null) {
//            return resultMap.fail().code(401).message("用户名错误");
//        } else if (!realPassword.equals(password)) {
//            return resultMap.fail().code(401).message("密码错误");
//        } else {
        return resultMap.success().code(200).message(JWTUtil.createToken(username));
//        }
    }

    @RequestMapping(path = "/unauthorized/{message}")
    public ResultMap unauthorized(@PathVariable String message) throws UnsupportedEncodingException {
        return resultMap.success().code(401).message(message);
    }

    @GetMapping("/updatePassword")
    @RequiresRoles(logical = Logical.OR, value = {"user","ADMIN"})
    public ResultMap updatePassword() {
        System.out.println("进入到/updatePassword接口里面了");
//        String dataBasePassword = userMapper.getPassword(username);
//        if (dataBasePassword.equals(oldPassword)) {
//            userMapper.updatePassword(username, newPassword);
//        } else {
//            return resultMap.fail().message("密码错误！");
//        }
        return resultMap.success().code(200).message("成功获得信息！");
    }

    /**
     * 拥有 vip 权限可以访问该页面
     */
    @GetMapping("/getVipMessage")
    @RequiresRoles(logical = Logical.AND, value = {"user", "ADMIN"})
    public ResultMap getVipMessage() {
        return resultMap.success().code(200).message("成功获得 vip 信息！");
    }

}