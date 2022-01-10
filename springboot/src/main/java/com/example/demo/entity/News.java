package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

//数据库表
@TableName("news")
@Data
public class News {
    @TableId(type= IdType.AUTO) //定义数据库ID自增长
    private Integer id;
    private String title; //标题
    private String text; //内容
    private String author; //作者
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time; //发布时间
}
