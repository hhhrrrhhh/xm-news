package com.example.controller;

import cn.hutool.core.date.DateUtil;
import com.example.common.Result;
import com.example.entity.News;
import com.example.service.NewsService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Resource
    private NewsService newsService;

    /**
     * 01.查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(News news) {
        List<News> list = newsService.selectAll(news);
        System.out.print(list);

        return Result.success(list);
    }

    /**
     * 02.新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody News news) {
        news.setTime(DateUtil.today());
        newsService.add(news);
        return Result.success();
    }

    /**
     * 03.修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody News news) {
        newsService.updateById(news);
        return Result.success();
    }

    /**
     * 04.删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        newsService.deleteById(id);
        return Result.success();
    }

    /**
     * 05.批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        newsService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 06.根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        News news = newsService.selectById(id);
        return Result.success(news);
    }

    /**
     * 07.分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(News news,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<News> page = newsService.selectPage(news, pageNum, pageSize);
        return Result.success(page);
    }
}