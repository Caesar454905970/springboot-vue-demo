package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Book;
import com.example.demo.mapper.BookMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@ResponseBody //允许前端跨域
@RestController
@RequestMapping("/Book")
public class BookController {


    @Resource
    BookMapper BookMapper;




    //02-新增
    @PostMapping
    public Result<?> save(@RequestBody Book Book){ //拿到的前端对象，映射成数据库的实体
        BookMapper.insert(Book);//插入数据库
        return Result.success();
    }

    //分页查询所有的新闻信息
    @GetMapping
    public Result<?> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search
    ){ //拿到的前端对象，映射成数据库的实体
        LambdaQueryWrapper<Book> wrapper = Wrappers.<Book>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            //如果搜索不为空

        }
        //搜索条件为空，返回所有的信息
        Page<Book> BookPage = BookMapper.selectPage(new Page<>(pageNum,pageSize),wrapper );

        return Result.success(BookPage);
    }

    //根据id删除对应的新闻:逻辑删除（硬删除；实际开发中，最好不要使用；使用软删除）
    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id) {
        BookMapper.deleteById(id);
        return Result.success();
    }

    //04-更新新闻信息
    @PutMapping
    public Result<?> update(@RequestBody Book Book) {
        BookMapper.updateById(Book);
        return Result.success();
    }



}
