package com.example.demo.controller;

import com.example.demo.base.ResponseData;
import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import com.example.demo.service.BookService;
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
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookService bookService;
    @Autowired
    UserService userService;

    @GetMapping("/list")
    public ResponseData BookList(Long Book_id, String Book_name, Integer page, Integer pageSize) {
        if (page == null) {
            page = 0;
        }
        if (pageSize == null) {
            pageSize = 8;
        }
        if (page > 0) {
            page = (page - 1) * pageSize;
            pageSize += page;
        }
        int total = bookService.booknumber();
        List<Book> bookList = bookService.booklist(Book_id, Book_name, page, pageSize);
        Map<String, Object> result = new HashMap<>();
        result.put("list", bookList);
        result.put("currentPage", page);
        result.put("total", total);
        result.put("pageSize", pageSize);
        return ResponseData.ok(result);
    }

    @GetMapping("detail")
    public ResponseData BookDetails(Long Book_id) {
        Book book = bookService.bookdetail(Book_id);
        Map<String, Object> result = new HashMap<>();
        result.put("detail", book);
        return ResponseData.ok(result);
    }

    @PostMapping("add")
    public ResponseData BookAdd(Long Book_id, String Book_name, String Book_writer, String Book_company, String Book_date, String Book_brief, String Book_img, Double Book_price, Integer Book_number) {

        Map<String, Object> result = new HashMap<>();
        Book book = bookService.bookdetail(Book_id);
        if (book != null) {
            result.put("msg", "error");
            return ResponseData.ok(result);
        }
        StringBuffer book_img = new StringBuffer("../../../static/book/");

        book_img.append(Book_img);
        int data = 0;
        try {
            data = bookService.addBook(Book_id, Book_name, Book_writer, Book_company, Book_date, Book_brief, book_img.toString(), Book_price, Book_number);
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

    @PostMapping("update")
    public ResponseData BookUpdate(Long Book_id, String Book_name, String Book_writer, String Book_company, String Book_date, String Book_brief, Double Book_price, Integer Book_number) {

        Map<String, Object> result = new HashMap<>();
        int data =0;
        try {
            data = bookService.updateBook(Book_id, Book_name, Book_writer, Book_company, Book_date, Book_brief, Book_price, Book_number);
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
    public ResponseData BookDelete(Long Book_id) {
        Map<String, Object> result = new HashMap<>();
        int data=0;
        try {
            data = bookService.deleteBook(Book_id);
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
    @GetMapping("book")
    public ResponseData UserBooks(Integer User_id)
    {
        List<Book> userbooks = bookService.userBooks(User_id);
        Map<String, Object> result = new HashMap<>();
        result.put("list", userbooks);
        return ResponseData.ok(result);
    }
    @GetMapping("isborrow")
    public ResponseData IsBorrow(String User_username,Long Book_id)
    {
        Map<String, Object> result = new HashMap<>();
        Book isborrow = bookService.isBorrow(User_username, Book_id);
        User user = userService.getUser(User_username);
        int count = userService.isCount(user.getUser_id());
        if(isborrow!=null){


                result.put("msg", "true");

        }
        else
        {
            if(count==6){
                result.put("msg", "crossed");
            }
            else
            {
                result.put("msg", "false");
            }

        }
        return ResponseData.ok(result);
    }
}
