package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

//数据库表
@TableName("user")
@Data
public class User {
    @TableId(type= IdType.AUTO) //定义数据库ID自增长
    private Integer id;
    private String username;
    private String password;
    private String nickName;
    private Integer age;
    private  String sex;
    private String address;

    @TableField(exist = false) //告诉数据库这个字段是不存在
    private List<Book> bookList;  //bookList是用于额外查询的字段
}
