package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Notice;
import com.example.mapper.NoticeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 公告信息表业务处理
 **/
@Service
public class NoticeService {

    @Resource
    private NoticeMapper noticeMapper;

    /**
     * 01. 新增公告
     *
     * @param notice 公告信息
     */
    public void add(Notice notice) {
        notice.setTime(DateUtil.today());
        // 如果没有用户名，可以设置为null或者一个默认值
        notice.setUser("admin"); // 使用管理员作为默认用户名，或根据需要进行修改
        noticeMapper.insert(notice);
    }

    /**
     * 02. 删除公告
     *
     * @param id 公告ID
     */
    public void deleteById(Integer id) {
        noticeMapper.deleteById(id);
    }

    /**
     * 03. 批量删除公告
     *
     * @param ids 公告ID列表
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            noticeMapper.deleteById(id);
        }
    }

    /**
     * 04. 修改公告
     *
     * @param notice 公告信息
     */
    public void updateById(Notice notice) {
        noticeMapper.updateById(notice);
    }

    /**
     * 05. 根据ID查询公告
     *
     * @param id 公告ID
     * @return 公告信息
     */
    public Notice selectById(Integer id) {
        return noticeMapper.selectById(id);
    }

    /**
     * 06. 查询所有公告
     *
     * @param notice 查询条件
     * @return 公告列表
     */
    public List<Notice> selectAll(Notice notice) {
        return noticeMapper.selectAll(notice);
    }

    /**
     * 07. 分页查询公告
     *
     * @param notice 查询条件
     * @param pageNum 当前页码
     * @param pageSize 每页显示数量
     * @return 分页后的公告列表
     */
    public PageInfo<Notice> selectPage(Notice notice, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Notice> list = noticeMapper.selectAll(notice);
        return PageInfo.of(list);
    }
}
