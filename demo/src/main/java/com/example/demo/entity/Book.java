package com.example.demo.entity;

import java.util.List;

public class Book {
    //图书编号
    private String Book_id;
    //图书名称
    private String Book_name;
    //图书作者
    private String Book_writer;
    //图书出版社
    private String Book_company;
    //出版日期
    private String Book_date;
    //简介
    private String Book_brief;
    //图片路径
    private String Book_img;
    //图书价格
    private double Book_price;
    //在馆数量
    private int Book_number;
    //图书总量
    private int Book_total;

    private List<Borrow> borrows;

    public List<Borrow> getBorrows() {
        return borrows;
    }

    public void setBorrows(List<Borrow> borrows) {
        this.borrows = borrows;
    }

    public String getBook_id() {
        return Book_id;
    }

    public void setBook_id(String book_id) {
        Book_id = book_id;
    }

    public String getBook_name() {
        return Book_name;
    }

    public void setBook_name(String book_name) {
        Book_name = book_name;
    }

    public String getBook_writer() {
        return Book_writer;
    }

    public void setBook_writer(String book_writer) {
        Book_writer = book_writer;
    }

    public String getBook_company() {
        return Book_company;
    }

    public void setBook_company(String book_company) {
        Book_company = book_company;
    }

    public String getBook_date() {
        return Book_date;
    }

    public void setBook_date(String book_date) {
        Book_date = book_date;
    }

    public String getBook_brief() {
        return Book_brief;
    }

    public void setBook_brief(String book_brief) {
        Book_brief = book_brief;
    }

    public String getBook_img() {
        return Book_img;
    }

    public void setBook_img(String book_img) {
        Book_img = book_img;
    }

    public double getBook_price() {
        return Book_price;
    }

    public void setBook_price(double book_price) {
        Book_price = book_price;
    }

    public int getBook_number() {
        return Book_number;
    }

    public void setBook_number(int book_number) {
        Book_number = book_number;
    }

    public int getBook_total() {
        return Book_total;
    }

    public void setBook_total(int book_total) {
        Book_total = book_total;
    }

    @Override
    public String toString() {
        return "Book{" +
                "Book_id='" + Book_id + '\'' +
                ", Book_name='" + Book_name + '\'' +
                ", Book_writer='" + Book_writer + '\'' +
                ", Book_company='" + Book_company + '\'' +
                ", Book_date='" + Book_date + '\'' +
                ", Book_brief='" + Book_brief + '\'' +
                ", Book_img='" + Book_img + '\'' +
                ", Book_price=" + Book_price +
                ", Book_number=" + Book_number +
                ", Book_total=" + Book_total +
                '}';
    }
}
