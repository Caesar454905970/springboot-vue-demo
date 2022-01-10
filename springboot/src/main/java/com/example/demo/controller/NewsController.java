package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.News;
import com.example.demo.mapper.NewsMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@ResponseBody //允许前端跨域
@RestController
@RequestMapping("/news")
public class NewsController {


    @Resource
    NewsMapper NewsMapper;




    //02-新增新闻
    @PostMapping
    public Result<?> save(@RequestBody News News){ //拿到的前端对象，映射成数据库的实体


        NewsMapper.insert(News);//插入数据库
        return Result.success();
    }

    //分页查询所有的新闻信息
    @GetMapping
    public Result<?> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search
    ){ //拿到的前端对象，映射成数据库的实体
        LambdaQueryWrapper<News> wrapper = Wrappers.<News>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            //如果搜索不为空
            wrapper.like(News::getTitle,search);
        }
        //搜索条件为空，返回所有的信息
        Page<News> NewsPage = NewsMapper.selectPage(new Page<>(pageNum,pageSize),wrapper );

        return Result.success(NewsPage);
    }

    //根据id删除对应的新闻:逻辑删除（硬删除；实际开发中，最好不要使用；使用软删除）
    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id) {
        NewsMapper.deleteById(id);
        return Result.success();
    }

    //04-更新新闻信息
    @PutMapping
    public Result<?> update(@RequestBody News News) {
        NewsMapper.updateById(News);
        return Result.success();
    }



}
