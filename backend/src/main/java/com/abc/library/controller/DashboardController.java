package com.abc.library.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.abc.library.LoginUser;
import com.abc.library.commom.Result;
import com.abc.library.entity.Book;
import com.abc.library.entity.LendRecord;
import com.abc.library.entity.User;
import com.abc.library.mapper.BookMapper;
import com.abc.library.mapper.LendRecordMapper;
import com.abc.library.mapper.UserMapper;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    @Resource
    private UserMapper userMapper;
    @Resource
    private LendRecordMapper lendRecordMapper;
    @Resource
    private BookMapper bookMapper;
    @GetMapping
    public  Result<?> dashboardrecords(){
        int visitCount = LoginUser.getVisitCount();
        QueryWrapper<User> queryWrapper1=new QueryWrapper();
        int userCount = userMapper.selectCount(queryWrapper1);
        QueryWrapper<LendRecord> queryWrapper2=new QueryWrapper();
        int lendRecordCount = lendRecordMapper.selectCount(queryWrapper2);
        QueryWrapper<Book> queryWrapper3=new QueryWrapper();
        int bookCount = bookMapper.selectCount(queryWrapper3);
        Map<String, Object> map = new HashMap<>();//键值对形式
        map.put("visitCount", visitCount);//放置visitCount到map中，拿取各个数据返回给前端
        map.put("userCount", userCount);
        map.put("lendRecordCount", lendRecordCount);
        map.put("bookCount", bookCount);
        return Result.success(map);
    }



}
