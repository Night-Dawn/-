package com.example.demo.mapper;

import com.example.demo.entity.Book;
import com.example.demo.entity.Borrow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.format.annotation.DateTimeFormat;


import java.sql.Date;
import java.util.List;

@Mapper
public interface BorrowMapper {
    public List<Borrow> Borrowlist(@Param("Borrow_id")Integer Borrow_id, @Param("Book_id") Long Book_id,@Param("User_id") Integer User_id, @Param("page") Integer page, @Param("pageSize") Integer pageSize);
    public Borrow getBorrow(@Param("Borrow_id")Integer Borrow_id);
    public int Borrownumber();
    public int Borrowupdate(@Param("Borrow_id")Integer Borrow_id, @Param("Book_id") Long Book_id, @Param("User_id") Integer User_id, @Param("Borrow_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date Borrow_date, @Param("Return_date") @DateTimeFormat(pattern = "yyyy-MM-dd")Date Return_date);
    public int deleteBorrow(@Param("Borrow_id")Integer Borrow_id);
    public int insertBorrow(@Param("Book_id")Long Book_id,@Param("User_id") Integer User_id,@Param("Borrow_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date Borrow_date, @Param("Return_date") @DateTimeFormat(pattern = "yyyy-MM-dd")Date Return_date);
    public int deleteUserBorrow(@Param("Book_id")Long Book_id,@Param("User_id") Integer User_id);
    public  List<Book> borrowBook(@Param("User_id") Integer User_id);
}
