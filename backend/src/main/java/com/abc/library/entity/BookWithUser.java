package com.abc.library.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@TableName("t_book_with_user")
@Data
public class BookWithUser {
    private Integer id;
    private Integer readerId;
    private String isbn;
    private String bookName;
    private String nickName;
    @JsonFormat(locale="zh",timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date lendTime;
    @JsonFormat(locale="zh",timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date deadTime;
    private Integer prolong;
    @TableField(select = false,exist = false)
    private Book Book;
}
