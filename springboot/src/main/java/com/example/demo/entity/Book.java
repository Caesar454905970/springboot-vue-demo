package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

//数据库表
@TableName("user")
@Data
public class Book {
    @TableId(type= IdType.AUTO) //定义数据库ID自增长
    private Integer id;
    private String name;
    private BigDecimal price;
    private String author;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8") //格式化时间
    private Date createTime;
    private Date cover;  //文件上传的地址
}
