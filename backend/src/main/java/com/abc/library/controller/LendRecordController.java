package com.abc.library.controller;

import com.abc.library.commom.Result;
import com.abc.library.entity.Book;
import com.abc.library.entity.LendRecord;
import com.abc.library.mapper.BookMapper;
import com.abc.library.mapper.LendRecordMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//

@RestController
@RequestMapping("/LendRecord")
public class LendRecordController {
    @Resource
    LendRecordMapper LendRecordMapper;
    @Resource
    BookMapper bookMapper;

    @PutMapping("/updateStatus") //更新书籍状态
    public Result<?> updateStatus(@RequestBody LendRecord LendRecord) {
        if (LendRecord.getStatus().equals("1") && LendRecord.getReturnTime() == null) {
            //状态值0：未归还，1：已归还，如果状态值是1且返还时间为空，则获取现在时间作为返还时间
            LendRecord.setReturnTime(new Date());
        }
        LendRecordMapper.update(LendRecord,new UpdateWrapper<LendRecord>().eq("isbn",LendRecord.getIsbn()).eq("lend_time",LendRecord.getLendTime()));
        //LendRecord封装更新数据，通过isbn值和借出时间来筛选需要更新的记录
        Book book = bookMapper.selectOne(new QueryWrapper<Book>().eq("isbn", LendRecord.getIsbn()));
        //通过isbn值找到相关图书
        book.setStatus(LendRecord.getStatus());//将借阅记录的状态值赋给图书的状态属性
        bookMapper.updateById(book);//通过book中的id属性更新图书
        return Result.success();
    }

//    @DeleteMapping("/{isbn}")//删除借阅记录，在前端无调用
//    public Result<?> delete(@PathVariable String isbn) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("isbn", isbn);
//        LendRecordMapper.deleteByMap(map);//通过isbn值删除借阅记录
//        return Result.success();
//    }

    //删除单条借阅记录
    @PostMapping("/deleteRecord")
    public Result<?> deleteRecord(@RequestBody LendRecord LendRecord) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", LendRecord.getId());
        LendRecordMapper.deleteByMap(map);//通过id值来删除指定的借阅记录
        return Result.success();
    }

    //批量删除借阅记录
    @PostMapping("/deleteRecords")
    public Result<?> deleteRecords(@RequestBody List<LendRecord> LendRecords) {
        int len = LendRecords.size();
        for (int i = 0; i < len; i++) {
            LendRecord curRecord = LendRecords.get(i);
            Map<String, Object> map = new HashMap<>();
            map.put("id", curRecord.getId());
            LendRecordMapper.deleteByMap(map);//通过循环批量删除借阅记录
        }
        return Result.success();
    }

    @PostMapping//新增借阅记录，借书时会调用
    public Result<?> save(@RequestBody LendRecord LendRecord) {
        LendRecordMapper.insert(LendRecord);
        return Result.success();
    }

    @GetMapping//分页查询
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search1,
                              @RequestParam(defaultValue = "") String search2,
                              @RequestParam(defaultValue = "") String search3) {
        LambdaQueryWrapper<LendRecord> wrappers = Wrappers.<LendRecord>lambdaQuery();
        if (StringUtils.isNotBlank(search1)) {
            wrappers.like(LendRecord::getIsbn, search1);
        }
        if (StringUtils.isNotBlank(search2)) {
            wrappers.like(LendRecord::getBookname, search2);
        }
        if (StringUtils.isNotBlank(search3)) {
            wrappers.like(LendRecord::getReaderId, search3);
        }
        Page<LendRecord> LendRecordPage = LendRecordMapper.selectPage(new Page<>(pageNum, pageSize), wrappers);
        return Result.success(LendRecordPage);
    }

//    @PutMapping("/{isbn}")
//    public Result<?> update(@PathVariable String isbn, @RequestBody LendRecord lendRecord) {
//        UpdateWrapper<LendRecord> updateWrapper = new UpdateWrapper<>();
//        updateWrapper.eq("isbn", isbn);
//        LendRecord lendrecord = new LendRecord();
//        lendrecord.setLendTime(lendRecord.getLendTime());
//        lendrecord.setReturnTime(lendRecord.getReturnTime());
//        lendrecord.setStatus(lendRecord.getStatus());
//        LendRecordMapper.update(lendrecord, updateWrapper);
//        return Result.success();
//    }
//
//    @PutMapping("/{lendTime}")
//    public Result<?> update2(@PathVariable Date lendTime, @RequestBody LendRecord lendRecord) {
//        UpdateWrapper<LendRecord> updateWrapper = new UpdateWrapper<>();
//        updateWrapper.eq("lendTime", lendTime);
//        LendRecord lendrecord = new LendRecord();
//        lendrecord.setReturnTime(lendRecord.getReturnTime());
//        lendrecord.setStatus(lendRecord.getStatus());
//        LendRecordMapper.update(lendrecord, updateWrapper);
//        return Result.success();
//    }

}
