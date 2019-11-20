package com.example.demo.service;

import com.example.demo.entity.Book;
import com.example.demo.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookMapper bookMapper;

    public List<Book> booklist(Long Book_id, String Book_name, Integer page, Integer pageSize) {

        List<Book> bookList = bookMapper.booklist(Book_id, Book_name, page, pageSize);
        return bookList;
    }

    public Book bookdetail(Long Book_id) {
        return bookMapper.bookdetail(Book_id);
    }

    public int addBook(Long Book_id,
                       String Book_name,
                       String Book_writer,
                       String Book_company,
                       String Book_date,
                       String Book_brief,
                       String Book_img,
                       Double Book_price,
                       Integer Book_number) {
        int data = bookMapper.addBook(Book_id,
                Book_name,
                Book_writer,
                Book_company,
                Book_date,
                Book_brief,
                Book_img,
                Book_price,
                Book_number);
        return data;
    }

    public int booknumber() {
        int total = bookMapper.booknumber();
        return total;
    }

    public int updateBook(Long Book_id,
                          String Book_name,
                          String Book_writer,
                          String Book_company,
                          String Book_date,
                          String Book_brief,
                          Double Book_price,
                          Integer Book_number) {
        int data = bookMapper.updateBook(Book_id,
                                        Book_name,
                                        Book_writer,
                                        Book_company,
                                        Book_date,
                                        Book_brief,
                                        Book_price,
                                        Book_number);
        return data;
    }
    public int deleteBook(Long Book_id)
    {
        int data = bookMapper.deleteBook(Book_id);
        return data;
    }
    public List<Book> userBooks(Integer User_id)
    {
        List<Book> bookList = bookMapper.userBooks(User_id);
        return bookList;
    }
    public Book isBorrow(String User_username,Long Book_id){
        return bookMapper.isBorrow(User_username,Book_id);
    }
}
