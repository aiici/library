package com.abc.library.controller;

import com.abc.library.LoginUser;
import com.abc.library.commom.Result;
import com.abc.library.entity.User;
import com.abc.library.mapper.UserMapper;
import com.abc.library.utils.TokenUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserMapper userMapper;
    @PostMapping("/register")//注册
    public Result<?> register(@RequestBody User user){
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername,user.getUsername()));
        if(res != null)
        {
            return Result.error("-1","用户名已重复");
        }
        userMapper.insert(user);
        return Result.success();
    }
    @CrossOrigin
    @PostMapping("/login")//登录
    public Result<?> login(@RequestBody User user){
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername,user.getUsername()).eq(User::getPassword,user.getPassword()));
        //通过用户名和密码查询用户，返回用户的数据
        if(res == null)
        {
            return Result.error("-1","用户名或密码错误");
        }
        String token = TokenUtils.genToken(res);//查到用户后会生成令牌
        res.setToken(token);
        LoginUser loginuser = new LoginUser();//添加登录用户数
        loginuser.addVisitCount();
        return Result.success(res);//将用户信息一并返回前端
    }
    @PostMapping//？？？
    public Result<?> save(@RequestBody User user){
        if(user.getPassword() == null){
            user.setPassword("abc123456");
        }
        userMapper.insert(user);
        return Result.success();
    }
    @PutMapping("/password")//修改密码
    public  Result<?> update( @RequestParam Integer id,
                              @RequestParam String password2){
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);//创建条件查询修改密码的用户
        User user = new User();
        user.setPassword(password2);
        userMapper.update(user,updateWrapper);//将密码写入到数据库中
        return Result.success();
    }
    @PutMapping//更新用户信息
    public  Result<?> updateInfo(@RequestBody User user){
        userMapper.updateById(user);
        return Result.success();
    }
    @PostMapping("/deleteBatch")//批量删除用户
    public  Result<?> deleteBatch(@RequestBody List<Integer> ids){
        userMapper.deleteBatchIds(ids);//根据ids集合删除
        return Result.success();
    }
    @DeleteMapping("/{id}")//删除用户
    public Result<?> delete(@PathVariable Long id){
        userMapper.deleteById(id);
        return Result.success();
    }
    @GetMapping//？？？
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<User> wrappers = Wrappers.<User>lambdaQuery();
        if(StringUtils.isNotBlank(search)){
            wrappers.like(User::getName,search);
        }
        wrappers.like(User::getRole,2);
        Page<User> userPage =userMapper.selectPage(new Page<>(pageNum,pageSize), wrappers);
        return Result.success(userPage);
    }
    @GetMapping("/usersearch")
    public Result<?> findPage2(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search1,
                               @RequestParam(defaultValue = "") String search2,
                               @RequestParam(defaultValue = "") String search3,
                               @RequestParam(defaultValue = "") String search4){
        LambdaQueryWrapper<User> wrappers = Wrappers.<User>lambdaQuery();
        if(StringUtils.isNotBlank(search1)){
            wrappers.like(User::getId,search1);
        }
        if(StringUtils.isNotBlank(search2)){
            wrappers.like(User::getName,search2);
        }
        if(StringUtils.isNotBlank(search3)){
            wrappers.like(User::getPhone,search3);
        }
        if(StringUtils.isNotBlank(search4)){
            wrappers.like(User::getAddress,search4);
        }
        wrappers.like(User::getRole,2);
        Page<User> userPage =userMapper.selectPage(new Page<>(pageNum,pageSize), wrappers);
        return Result.success(userPage);
    }
}
