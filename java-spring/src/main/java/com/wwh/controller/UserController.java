package com.wwh.controller;


import com.wwh.dao.UserMapper;
import com.wwh.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class UserController{

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public String index(){
        List<User> users = userMapper.selectAll();
        System.out.println(users);
        return "index";
    }
}
