package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@ResponseBody
@RestController
@RequestMapping("/user")
public class UserController {


    @Resource
    UserMapper userMapper;

    //02-新增用户
    @PostMapping
    public Result<?> save(@RequestBody User user){ //拿到的前端对象，映射成数据库的实体
        userMapper.insert(user);//插入数据库
        return Result.success();
    }

    //分页查询所有的用户信息
    @GetMapping
    public Result<?> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search
    ){ //拿到的前端对象，映射成数据库的实体
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            //如果搜索不为空
            wrapper.like(User::getNickName,search);
        }
        //搜索条件为空，返回所有的信息
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum,pageSize),wrapper );

        return Result.success(userPage);
    }

    //根据id删除对应的用户:逻辑删除（硬删除；实际开发中，最好不要使用；使用软删除）
    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id) {
        userMapper.deleteById(id);
        return Result.success();
    }

    //04-更新用户信息
    @PutMapping
    public Result<?> update(@RequestBody User user) {
        userMapper.updateById(user);
        return Result.success();
    }


}