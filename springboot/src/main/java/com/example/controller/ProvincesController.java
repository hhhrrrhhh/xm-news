package com.example.controller;

import com.example.common.Result;
import com.example.entity.Provinces;
import com.example.service.ProvincesService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 省份Controller
 */

@RestController
@RequestMapping("/provinces")
public class ProvincesController {

    @Resource
    private ProvincesService provincesService;

    //01.查询所有省份
    @GetMapping("/selectAll")
    public Result selectAll(Provinces provinces) {
        List<Provinces> list = provincesService.selectAll(provinces);
        return Result.success(list);
    }

    //02.新增一个省份
    @PostMapping("/add")
    public Result add(@RequestBody Provinces provinces) {
        provincesService.add(provinces);
        return Result.success();
    }

    //03.修改一个省份的信息
    @PutMapping("/update")
    public Result updateById(@RequestBody Provinces provinces) {
        provincesService.updateById(provinces);
        return Result.success();
    }

    //04.删除一个省份的信息
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        provincesService.deleteById(id);
        return Result.success();
    }

    //05.批量删除多个省份的信息
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        provincesService.deleteBatch(ids);
        return Result.success();
    }

    //06.通过省份ID查询一条省份所有信息，返回给省份实体类
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Provinces provinces = provincesService.selectById(id);
        return Result.success(provinces);
    }

    //07.分页查询
    @GetMapping("/selectPage")
    public Result selectPage(Provinces provinces,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Provinces> page = provincesService.selectPage(provinces, pageNum, pageSize);
        return Result.success(page);
    }

}
