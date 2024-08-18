package com.example.service;

import com.example.entity.News;
import com.example.mapper.NewsMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NewsService {

    @Resource
    private NewsMapper newsMapper;

    /**
     * 01.查询所有文章
     */
    public List<News> selectAll(News news) {
        return newsMapper.selectAll(news);
    }

    /**
     * 02.新增一篇文章
     */
    public void add(News news) {
        newsMapper.insert(news);
    }

    /**
     * 03修改一篇文章
     */
    public void updateById(News news) {
        newsMapper.updateById(news);
    }

    /**
     * 04.删除一篇文章
     */
    public void deleteById(Integer id) {
        newsMapper.deleteById(id);
    }

    /**
     * 05.批量删除文章
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            newsMapper.deleteById(id);
        }
    }

    /**
     * 06.根据ID查询文章
     */
    public News selectById(Integer id) {
        return newsMapper.selectById(id);
    }


    /**
     * 07.分页查询
     */
    public PageInfo<News> selectPage(News news, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<News> list = newsMapper.selectAll(news);
        return PageInfo.of(list);
    }

}
