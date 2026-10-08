package com.itheima.service;

import com.itheima.pojo.Category;

import java.util.List;

public interface CategoryService {
    // 添加分类
    void add(Category category);

    // 查询分类列表
    List<Category> list();

    // 根据id查询分类
    Category findById(Integer id);

    // 修改分类
    void update(Category category);

    // 删除分类
    void delete(Integer id);
}
