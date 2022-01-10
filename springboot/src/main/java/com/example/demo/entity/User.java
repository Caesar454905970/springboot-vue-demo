package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

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
}
