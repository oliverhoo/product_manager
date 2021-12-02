package com.itheima.dao;

import com.itheima.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {
    //查询所有商品
    List<Product> findAll();

    //添加商品
    void save(Product product);

    //查询一部分数据
    List<Product> findList(@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    //查询总数量
    Integer findCount();

    //定义方法，根据id查询商品
    Product findById(Integer id);

    //定义方法，修改商品
    void update(Product product);

    //定义方法，用来删除
    void delete(Integer id);
}
