package com.abc.library.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.abc.library.commom.Result;
import com.abc.library.entity.Book;
import com.abc.library.mapper.BookMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Resource
    BookMapper BookMapper;

    @PostMapping//添加新书
    public Result<?> save(@RequestBody Book Book) {
        BookMapper.insert(Book);//传入book实体类，调用MybatisPlus的insert方法插入,添加新书
        return Result.success();//返回成功结果集
    }

    @PutMapping//更新书籍
    public Result<?> update(@RequestBody Book Book) {
        if (Book.getId() != null) { //判断id是否为null，id为自增
            BookMapper.updateById(Book); //如果不是null则直接插入数据
        } else {
            BookMapper.update(Book, new UpdateWrapper<Book>().eq("isbn", Book.getIsbn()));
            //如果id为null则根据 isbn 字段的值为 Book 对象的 isbn 属性的值，更新 Book 对象所代表的数据库记录
        }
        return Result.success();
    }

    //    批量删除
    @PostMapping("/deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Integer> ids) {
        BookMapper.deleteBatchIds(ids); //根据传进来的ids集合删除集合中指定的记录
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        BookMapper.deleteById(id);//根据形参id删除数据
        return Result.success();
    }

    //分页查询
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search1,
                              @RequestParam(defaultValue = "") String search2,
                              @RequestParam(defaultValue = "") String search3) {
        LambdaQueryWrapper<Book> wrappers = Wrappers.<Book>lambdaQuery();//创建了一个 LambdaQueryWrapper 对象，用于封装查询条件
        if (StringUtils.isNotBlank(search1)) {
            wrappers.like(Book::getIsbn, search1);//查询条件中添加一个条件，要求 isbn 字段的值包含 search1
        }
        if (StringUtils.isNotBlank(search2)) {
            wrappers.like(Book::getName, search2);
        }
        if (StringUtils.isNotBlank(search3)) {
            wrappers.like(Book::getAuthor, search3);
        }
        Page<Book> BookPage = BookMapper.selectPage(new Page<>(pageNum, pageSize), wrappers);
        //传入分页信息和查询条件，调用selectPage方法查询
        return Result.success(BookPage);
    }
}
