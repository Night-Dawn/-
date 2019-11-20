package com.example.demo.controller;

import com.example.demo.base.ResponseData;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseData Userlist(Integer User_id,String User_name,Integer page,Integer pageSize ){
        if (page == null) {
            page = 0;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        if (page > 0) {
            page = (page - 1) * pageSize;
            pageSize += page;
        }
        Map<String, Object> result = new HashMap<>();
        int total = userService.usernumber();
        List<User> userList = userService.Userlist(User_id, User_name, page, pageSize);
        result.put("list", userList);
        result.put("page", page);
        result.put("total", total);
        result.put("pageSize", pageSize);
        return ResponseData.ok(result);
    }
    @GetMapping("/check")
    public ResponseData CheckUser(Integer User_id)
    {
        Map<String, Object> result = new HashMap<>();
        User user = userService.checkUser(User_id);
        if(user!=null)
        {
            result.put("msg", "exist");
        }
        else
        {
            result.put("msg", "ok");
        }
        return ResponseData.ok(result);
    }
    @PostMapping("add")
    public ResponseData UserAdd(Integer User_id, String  User_name, String  User_sex,
                                String  User_address,
                                String  User_phone,
                                String  User_username,
                                String  User_password,
                                Integer  User_borrow_max,
                                Integer  User_borrow_number){
        Map<String, Object> result = new HashMap<>();
        int data = 0;
        try{
            data = userService.addUser(User_id, User_name, User_sex, User_address, User_phone, User_username, User_password, User_borrow_max, User_borrow_number);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result.put("msg", "fail");
            return ResponseData.ok(result);
        }
        if (data > 0) {
            result.put("msg", "ok");
        } else {
            result.put("msg", "fail");
        }
        return ResponseData.ok(result);
    }
    @PostMapping("update")
    public ResponseData UserUpdate(Integer User_id, String  User_name, String  User_sex,
                                   String  User_address,
                                   String  User_phone,
                                   String  User_username,
                                   String  User_password){
        Map<String, Object> result = new HashMap<>();
        int data =0;
        try{
            data= userService.updateUser(User_id, User_name, User_sex, User_address, User_phone, User_username, User_password);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result.put("msg", "fail");
            return ResponseData.ok(result);
        }
        if (data > 0) {
            result.put("msg", "ok");
        } else {
            result.put("msg", "fail");
        }
        return ResponseData.ok(result);
    }
    @GetMapping("delete")
    public ResponseData UserDelete(Integer User_id) {
        Map<String, Object> result = new HashMap<>();
        int data=0;
        try {
            data = userService.deleteUser(User_id);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("msg", "fail");
            return ResponseData.ok(result);
        }
        if (data > 0) {
            result.put("msg", "ok");
        } else {
            result.put("msg", "fail");
        }
        return ResponseData.ok(result);
    }
}
