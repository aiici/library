package com.abc.library.controller;

import com.abc.library.commom.Result;
import com.abc.library.entity.Book;
import com.abc.library.entity.BookWithUser;
import com.abc.library.entity.LendRecord;
import com.abc.library.mapper.BookMapper;
import com.abc.library.mapper.BookWithUserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bookwithuser")
public class BookWithUserController {
    @Resource
    BookWithUserMapper BookWithUserMapper;
    @Resource
    BookMapper BookMapper;
//
//    @PostMapping
//    public Result<?> save(@RequestBody Book Book){
//        BookMapper.insert(Book);
//        return Result.success();
//    }

//    //    批量删除
//    @PostMapping("/deleteBatch")
//    public  Result<?> deleteBatch(@RequestBody List<Integer> ids){
//        BookMapper.deleteBatchIds(ids);
//        return Result.success();
//    }
//    @PutMapping
//    public  Result<?> update(@RequestBody Book Book){
//        BookMapper.updateById(Book);
//        return Result.success();
//    }
//    @DeleteMapping("/{id}")
//    public Result<?> delete(@PathVariable Long id){
//        BookMapper.deleteById(id);
//        return Result.success();
//    }
    //添加用户新书
    @PostMapping("/insertNew")
    public Result<?> save(@RequestBody BookWithUser BookWithUser){
        BookWithUserMapper.insert(BookWithUser); //传入BookWithUser实体类，调用MybatisPlus的insert方法插入,添加新书
        return Result.success();
    }

    //更新用户所借的书籍信息（续借）
    @PostMapping
    public Result<?> update(@RequestBody BookWithUser BookWithUser){
        UpdateWrapper<BookWithUser> updateWrapper = new UpdateWrapper<>(); //创建一个UpdateWrapper用于封装更新条件
        updateWrapper.eq("isbn",BookWithUser.getIsbn()).eq("id",BookWithUser.getId());
        //添加更新条件，isbn的值必须为BookWithUser对象的属性值isbn以及id的值必须为BookWithUser对象的属性值id
        BookWithUserMapper.update(BookWithUser, updateWrapper); //开始更新，BookWithUser是更新数据，updateWrapper是更新条件
        return Result.success();
    }
//删除一条记录
    @PostMapping("/deleteByLendRecord")
    public Result<?> deleteByLendRecord(@RequestBody LendRecord lendRecord){
        BookWithUserMapper.delete(new QueryWrapper<BookWithUser>().eq("isbn",lendRecord.getIsbn()));
        //根据isbn值删除借阅记录
        return Result.success();
    }


    @PostMapping("/deleteRecord")//删除记录（借阅记录或图书）
    public  Result<?> deleteRecord(@RequestBody BookWithUser BookWithUser){
        Map<String,Object> map = new HashMap<>();
        map.put("isbn",BookWithUser.getIsbn()); //添加条件
        map.put("id",BookWithUser.getId());
        BookWithUserMapper.deleteByMap(map);//根据isbn和id删除书籍
        return Result.success();
    }


    @PostMapping("/deleteRecords")//批量删除记录
    public Result<?> deleteRecords(@RequestBody List<BookWithUser> BookWithUsers){
        int len = BookWithUsers.size();
        for(int i=0;i<len;i++) {
            BookWithUser curRecord = BookWithUsers.get(i);
            Map<String,Object> map = new HashMap<>();
            map.put("isbn",curRecord.getIsbn());
            map.put("id",curRecord.getId());
            BookWithUserMapper.deleteByMap(map);//根据就id和isbn删除记录
        }
        return Result.success();
    }
    @GetMapping//分页查询
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search1,
                              @RequestParam(defaultValue = "") String search2,
                              @RequestParam(defaultValue = "") String search3){
        LambdaQueryWrapper<BookWithUser> wrappers = Wrappers.<BookWithUser>lambdaQuery();//创建了一个 LambdaQueryWrapper 对象，用于封装查询条件
        if(StringUtils.isNotBlank(search1)){
            wrappers.like(BookWithUser::getIsbn,search1);//查询条件中添加一个条件，要求 isbn 字段的值包含 search1
        }
        if(StringUtils.isNotBlank(search2)){
            wrappers.like(BookWithUser::getBookName,search2);
        }
        if(StringUtils.isNotBlank(search3)){
            wrappers.like(BookWithUser::getReaderId,search3);
        }
        Page<BookWithUser> BookPage =BookWithUserMapper.selectPage(new Page<>(pageNum,pageSize), wrappers);
        //传入分页信息和查询条件，调用selectPage方法查询
        List<BookWithUser> records = BookPage.getRecords();
        for (BookWithUser BookWithUser:records){//循环records集合获得每个BookWithUser对象 ???
            Book Book = BookMapper.selectOne(new QueryWrapper<Book>().eq("isbn", BookWithUser.getIsbn()));
            //通过BookWithUser对象中的isbn属性查询书籍
            BookWithUser.setBook(Book);
            //将查询到的书籍复制给BookWithUser对象的book属性
        }
        return Result.success(BookPage);
    }
}
