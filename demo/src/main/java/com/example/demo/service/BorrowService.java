package com.example.demo.service;

import com.example.demo.entity.Book;
import com.example.demo.entity.Borrow;
import com.example.demo.mapper.BorrowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class BorrowService {
    @Autowired
    private BorrowMapper borrowMapper;

    public List<Borrow> Borrowlist(Integer Borrow_id,Long Book_id,Integer User_id, Integer page,Integer pageSize)
    {
        List<Borrow> list = borrowMapper.Borrowlist(Borrow_id, Book_id, User_id, page, pageSize);
        return list;
    }
    public int Borrownumber()
    {
        return borrowMapper.Borrownumber();
    }
    public int Borrowupdate(Integer Borrow_id, Long Book_id, Integer User_id, Date Borrow_date, Date Return_date)
    {
        return borrowMapper.Borrowupdate( Borrow_id, Book_id, User_id,  Borrow_date, Return_date);
    }
    public int Borrowdelete(Integer Borrow_id)
    {
        return borrowMapper.deleteBorrow(Borrow_id);
    }
    public int insertBorrow(Long Book_id, Integer User_id, Date Borrow_date, Date Return_date){
        return borrowMapper.insertBorrow(Book_id, User_id, Borrow_date, Return_date);
    }
    public int deleteUserBorrow(Long Book_id,Integer User_id)
    {
        return borrowMapper.deleteUserBorrow(Book_id, User_id) ;
    }
    public Borrow getBorrow(Integer Borrow_id){
        return borrowMapper.getBorrow(Borrow_id);
    }
    public List<Book> borrowBook(Integer User_id){
        List<Book> list= null;
        try{
            list = borrowMapper.borrowBook(User_id);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
}
