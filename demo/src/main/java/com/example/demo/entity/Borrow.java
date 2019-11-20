package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.List;

public class Borrow {
    private int Borrow_id;
    private long Book_id;
    private int User_id;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private String Borrow_date;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private String Return_date;
    private List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public int getBorrow_id() {
        return Borrow_id;
    }

    public void setBorrow_id(int borrow_id) {
        Borrow_id = borrow_id;
    }

    public long getBook_id() {
        return Book_id;
    }

    public void setBook_id(long book_id) {
        Book_id = book_id;
    }

    public int getUser_id() {
        return User_id;
    }

    public void setUser_id(int user_id) {
        User_id = user_id;
    }

    public String getBorrow_date() {
        return Borrow_date;
    }

    public void setBorrow_date(String borrow_date) {
        Borrow_date = borrow_date;
    }

    public String getReturn_date() {
        return Return_date;
    }

    public void setReturn_date(String return_date) {
        Return_date = return_date;
    }
}
