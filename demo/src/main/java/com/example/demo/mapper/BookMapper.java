package com.example.demo.mapper;

import com.example.demo.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface BookMapper {
    int booknumber();
    List<Book> booklist(@Param("Book_id")Long Book_id,@Param("Book_name") String Book_name,@Param("page") Integer page,@Param("pageSize") Integer pageSize);
    Book bookdetail(@Param("Book_id") Long Book_id);
    int addBook(@Param("Book_id")Long Book_id,
                @Param("Book_name") String Book_name,
                @Param("Book_writer")String Book_writer,
                @Param("Book_company") String Book_company,
                @Param("Book_date")String Book_date,
                @Param("Book_brief") String Book_brief,
                @Param("Book_img") String Book_img,
                @Param("Book_price") Double Book_price,
                @Param("Book_number")Integer Book_number);
    int updateBook(@Param("Book_id")Long Book_id,
                   @Param("Book_name") String Book_name,
                   @Param("Book_writer")String Book_writer,
                   @Param("Book_company") String Book_company,
                   @Param("Book_date")String Book_date,
                   @Param("Book_brief") String Book_brief,
                   @Param("Book_price") Double Book_price,
                   @Param("Book_number")Integer Book_number);
    int deleteBook(@Param("Book_id")Long Book_id);
    public List<Book> userBooks(@Param("User_id") Integer User_id);
    public Book isBorrow(@Param("User_username")String User_username,@Param("Book_id")Long Book_id);
}
