package com.example.service;

import com.example.entity.Provinces;
import com.example.mapper.ProvincesMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 省份-Service
 */
@Service
public class ProvincesService {

    @Resource
    private ProvincesMapper provincesMapper;

    //01.查询所有省份
    public List<Provinces> selectAll(Provinces provinces) {
        return provincesMapper.selectAll(provinces);
    }

    //02.新增一个省份
    public void add(Provinces provinces) {
        provincesMapper.insert(provinces);
    }

    //03.修改一个省份的信息
    public void updateById(Provinces provinces) {
        provincesMapper.updateById(provinces);
    }

    //04.删除一个省份的信息
    public void deleteById(Integer id) {
        provincesMapper.deleteById(id);
    }

    //05.批量删除多个省份的信息
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            provincesMapper.deleteById(id);
        }
    }

    //06.通过省份ID查询一条省份所有信息，返回给省份实体类
    public Provinces selectById(Integer id) {
        return provincesMapper.selectById(id);
    }

    //07.分页查询
    public PageInfo<Provinces> selectPage(Provinces provinces, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Provinces> list = provincesMapper.selectAll(provinces);
        return PageInfo.of(list);
    }
}
