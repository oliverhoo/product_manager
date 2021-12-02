package com.itheima.service;

import com.itheima.pojo.PageBean;
import com.itheima.pojo.Product;

import java.util.List;

public interface ProductService {
    //查询所有商品
    List<Product> findAll();

    //添加商品
    void save(Product product);

    //分页查询
    PageBean<Product> findByPage(Integer pageNum, Integer pageSize);

    //根据id查询
    Product findById(Integer id);

    //修改
    void update(Product product);

    //根据id删除
    void delete(Integer id);
}
