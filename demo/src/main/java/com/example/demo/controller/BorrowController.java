package com.example.demo.controller;

import com.example.demo.base.ResponseData;
import com.example.demo.entity.Book;
import com.example.demo.entity.Borrow;
import com.example.demo.entity.User;
import com.example.demo.service.BorrowService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/borrow")
public class BorrowController {
    @Autowired
    BorrowService borrowService;
    @Autowired
    UserService userService;

    @GetMapping("/list")
    public ResponseData Borrowlist(Integer Borrow_id, Long Book_id, Integer User_id, Integer page, Integer pageSize) {
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
        List<Borrow> borrowList = borrowService.Borrowlist(Borrow_id, Book_id, User_id, page, pageSize);
        int total = borrowService.Borrownumber();
        result.put("list", borrowList);
        result.put("currentPage", page);
        result.put("total", total);
        result.put("pageSize", pageSize);
        return ResponseData.ok(result);
    }

    @PostMapping("update")
    public ResponseData Borrowupdate(Integer Borrow_id, Long Book_id, Integer User_id, @DateTimeFormat(pattern = "yyyy-MM-dd") Date Borrow_date, @DateTimeFormat(pattern = "yyyy-MM-dd") Date Return_date) {
        Map<String, Object> result = new HashMap<>();

        java.sql.Date borrow_date = new java.sql.Date(Borrow_date.getTime());
        java.sql.Date return_date = new java.sql.Date(Return_date.getTime());
        int data = 0;
        try {
            data = borrowService.Borrowupdate(Borrow_id, Book_id, User_id, borrow_date, return_date);
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

    @GetMapping("delete")
    public ResponseData BorrowDelete(Integer Borrow_id) {
        Map<String, Object> result = new HashMap<>();
        Borrow borrow = borrowService.getBorrow(Borrow_id);
        User user = userService.checkUser(borrow.getUser_id());
        int data = 0;
        try {
            data = borrowService.Borrowdelete(Borrow_id);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("msg", "fail");
            return ResponseData.ok(result);
        }
        if (data > 0) {
            result.put("msg", "ok");
            userService.updateCount(user.getUser_id(), user.getUser_borrow_number() - 1);
        } else {
            result.put("msg", "fail");

        }
        return ResponseData.ok(result);
    }

    @PostMapping("insert")
    public ResponseData InsertBorrow(Long Book_id, String User_username) {
        Map<String, Object> result = new HashMap<>();
        User user = userService.getUser(User_username);

        String timeStr1 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date sDate = null;
        try {
            sDate = sdf.parse(timeStr1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Date borrow_date = new java.sql.Date(sDate.getTime());
        Calendar c = Calendar.getInstance();
        c.setTime(sDate);
        c.add(Calendar.MONTH, 2);
        sDate = c.getTime();
        java.sql.Date return_date = new java.sql.Date(sDate.getTime());
        int data = 0;
        try {
            data = borrowService.insertBorrow(Book_id, user.getUser_id(), borrow_date, return_date);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("msg", "fail");
            return ResponseData.ok(result);
        }
        if (data > 0) {
            result.put("msg", "ok");
            userService.updateCount(user.getUser_id(), user.getUser_borrow_number() + 1);
        } else {
            result.put("msg", "fail");

        }
        return ResponseData.ok(result);
    }

    @GetMapping("deleteborrow")
    public ResponseData DeleteUserBorrow(Long Book_id, String User_username) {
        Map<String, Object> result = new HashMap<>();
        User user = userService.getUser(User_username);
        int data = 0;
        try {
            data = borrowService.deleteUserBorrow(Book_id, user.getUser_id());
        } catch (Exception e) {
            e.printStackTrace();
            result.put("msg", "fail");
            return ResponseData.ok(result);
        }
        if (data > 0) {
            result.put("msg", "ok");
            userService.updateCount(user.getUser_id(), user.getUser_borrow_number() - 1);
        } else {
            result.put("msg", "fail");
        }
        return ResponseData.ok(result);
    }

    @GetMapping("borrowbook")
    public ResponseData BorrowBook(String User_username)
    {
        User user = userService.getUser(User_username);
        Map<String, Object> result = new HashMap<>();
        List<Book> bookList=borrowService.borrowBook(user.getUser_id());
        result.put("list", bookList);
        return ResponseData.ok(result);
    }
}
