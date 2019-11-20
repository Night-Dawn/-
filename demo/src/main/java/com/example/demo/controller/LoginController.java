package com.example.demo.controller;

import com.example.demo.base.ResponseCode;
import com.example.demo.base.ResponseData;
import com.example.demo.entity.Admin;
import com.example.demo.entity.User;
import com.example.demo.service.LoginService;
import com.example.demo.utils.JWTUtils;
import com.example.demo.vo.MenuVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RestController
@RequestMapping("/user")
@Validated
@CrossOrigin
public class LoginController {
    @Autowired
    private LoginService loginService;


    @ApiOperation(value = "进入首页", notes = "")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseData login(String username, String password) {
        if (!StringUtils.hasText(username)) {
            return ResponseData.fail("用户名不能为空", ResponseCode.PARAM_ERROR_CODE.getCode());
        }
        if (!StringUtils.hasText(password)) {
            return ResponseData.fail("密码不能为空", ResponseCode.PARAM_ERROR_CODE.getCode());
        }
        User user = loginService.getUser(username);
        if (user != null) {
            if (username.equals(user.getUser_username()) && password.equals(user.getUser_password())) {
                List<MenuVo> meuns = new ArrayList<>();
                MenuVo m = new MenuVo();
                m.setIcon("el-icon-setting");
                m.setIndex("/home");
                m.setTitle("首页");
                meuns.add(m);

                MenuVo borrow = new MenuVo();
                borrow.setIndex("/user/borrow");
                borrow.setTitle("图书借阅");

                MenuVo borrowlog= new MenuVo();
                borrowlog.setIndex("/user/log");
                borrowlog.setTitle("借阅记录");

                MenuVo user_Borrow = new MenuVo();
                user_Borrow.setIcon("el-icon-edit");
                user_Borrow.setIndex("3");
                user_Borrow.setTitle("图书列表");
                user_Borrow.setSubs(Arrays.asList(borrow,borrowlog));
                meuns.add(user_Borrow);

                Map<String, Object> result = new HashMap<>();
                result.put("token", JWTUtils.getToken("1001"));
                result.put("meuns", meuns);
                result.put("routers", "/home_/users_/user/borrow_/user/log");
                return ResponseData.ok(result);
            }

        }
        Admin admin =loginService.getAdmin(username);
        if(admin!=null)
        if (username.equals(admin.getUsername()) && password.equals(admin.getPassword())) {
            List<MenuVo> meuns = new ArrayList<>();

            MenuVo m = new MenuVo();
            m.setIcon("el-icon-setting");
            m.setIndex("/home");
            m.setTitle("首页");
            meuns.add(m);

            MenuVo m3 = new MenuVo();
            m3.setIndex("/users");
            m3.setTitle("读者管理");


            MenuVo m2 = new MenuVo();
            m2.setIcon("el-icon-menu");
            m2.setIndex("2");
            m2.setTitle("读者管理");
            m2.setSubs(Arrays.asList(m3));
            meuns.add(m2);

            //书籍管理
            MenuVo BookIndex =new MenuVo();
            BookIndex.setTitle("图书列表");
            BookIndex.setIndex("/book/list");

            MenuVo BookManage = new MenuVo();
            BookManage.setTitle("图书管理");
            BookManage.setIndex("/book/manage");

            MenuVo Bookmenu = new MenuVo();
            Bookmenu.setIcon("el-icon-document");
            Bookmenu.setIndex("3");
            Bookmenu.setTitle("书籍管理");
            Bookmenu.setSubs(Arrays.asList(BookIndex,BookManage));

            meuns.add(Bookmenu);
            //借阅管理
            MenuVo Borrowmenu = new MenuVo();
            Borrowmenu.setIcon("el-icon-star-on");
            Borrowmenu.setTitle("借还管理");
            //借阅记录
            MenuVo Borrowlog = new MenuVo();
            Borrowlog.setTitle("借阅日志");
            Borrowlog.setIndex("/borrow/log");

            Borrowmenu.setSubs(Arrays.asList(Borrowlog));


            meuns.add(Borrowmenu);

            Map<String, Object> result = new HashMap<>();
            result.put("token", JWTUtils.getToken("1002"));
            result.put("meuns", meuns);
            result.put("routers", "/home_/users_/book/list_/book/manage_/borrow/log");
            return ResponseData.ok(result);
        }

        return ResponseData.fail("账号或者密码错误");

    }


}
