package com.itheima.service.impl;

import com.itheima.dao.ProductDao;
import com.itheima.pojo.PageBean;
import com.itheima.pojo.Product;
import com.itheima.service.ProductService;
import com.itheima.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    @Override
    public List<Product> findAll() {
        //获取Dao对象
        SqlSession sqlSession = MyBatisUtils.openSession();
        ProductDao productDao = sqlSession.getMapper(ProductDao.class);
        //查询所有
        List<Product> list = productDao.findAll();
        //释放资源
        sqlSession.close();
        return list;
    }

    @Override
    public void save(Product product) {
        //获取Dao对象
        SqlSession sqlSession = MyBatisUtils.openSession();
        ProductDao productDao = sqlSession.getMapper(ProductDao.class);
        //添加
        productDao.save(product);
        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();
    }

    @Override
    public PageBean<Product> findByPage(Integer pageNum, Integer pageSize) {
        //获取Dao对象
        SqlSession sqlSession = MyBatisUtils.openSession();
        ProductDao productDao = sqlSession.getMapper(ProductDao.class);
        //1. 查询数据
        //计算索引
        Integer startIndex = (pageNum - 1) * pageSize;
        //调用dao查询数据
        List<Product> list = productDao.findList(startIndex, pageSize);
        //2. 查询总数量
        Integer totalCount = productDao.findCount();
        //3. 计算总页数
        Integer totalPage = (totalCount % pageSize == 0) ? totalCount / pageSize : totalCount / pageSize + 1;
        //4. 创建pageBean用于封装分页数据
        PageBean<Product> pageBean = new PageBean<>();
        pageBean.setTotalCount(totalCount);
        pageBean.setTotalPage(totalPage);
        pageBean.setList(list);
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        //5. 释放资源
        sqlSession.close();
        return pageBean;
    }

    @Override
    public Product findById(Integer id) {
        //获取Dao对象
        SqlSession sqlSession = MyBatisUtils.openSession();
        ProductDao productDao = sqlSession.getMapper(ProductDao.class);
        //查询
        Product product = productDao.findById(id);
        //释放资源
        sqlSession.close();
        return product;
    }

    @Override
    public void update(Product product) {
        //获取Dao对象
        SqlSession sqlSession = MyBatisUtils.openSession();
        ProductDao productDao = sqlSession.getMapper(ProductDao.class);
        //修改
        productDao.update(product);
        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();
    }

    @Override
    public void delete(Integer id) {
        //获取dao对象
        SqlSession sqlSession = MyBatisUtils.openSession();
        ProductDao productDao = sqlSession.getMapper(ProductDao.class);
        //删除
        productDao.delete(id);
        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();
    }
}
