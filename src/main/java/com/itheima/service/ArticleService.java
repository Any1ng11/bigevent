package com.itheima.service;

import com.itheima.pojo.Article;
import com.itheima.pojo.PageBean;

public interface ArticleService {
    // 添加文章
    void add(Article article);

    // 分页查询文章
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    // 根据id查询文章
    Article findById(Integer id);

    // 修改文章
    void update(Article article);

    // 删除文章
    void delete(Integer id);
}
