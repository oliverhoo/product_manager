package com.itheima.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.pojo.PageBean;
import com.itheima.pojo.ResultInfo;
import com.itheima.pojo.Product;
import com.itheima.service.ProductService;
import com.itheima.service.impl.ProductServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")//抑制警告
@WebServlet("/product")
public class ProductServlet extends HttpServlet {
    //调用service查询所有商品
    private ProductService productService = new ProductServiceImpl();
    /*
        通过action请求参数决定指定哪个方法【通过反射调用对应的方法】
        action=findAll 查询所有
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //获取action参数
            request.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=UTF-8");
            String action = request.getParameter("action");
            //获取该类的Class对象
            Class clazz = this.getClass();
            //获取action的方法
            Method method = clazz.getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            //调用执行
            method.invoke(this, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        查询所有
     */
    protected void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //1. 调用service查询所有商品
            List<Product> products = productService.findAll();
            //2. 将集合封装到ResultInfo
            ResultInfo resultInfo = new ResultInfo(true, "查询成功", products);
            //3. 将ResultInfo转成json字符串，回复给浏览器
            writeJson(response, resultInfo);
        } catch (Exception e) {
            //如果遇到问题，会执行catch，就手动封装一个ResultInfo，回复给浏览器
            e.printStackTrace();
            ResultInfo resultInfo = new ResultInfo(false, "服务器忙，稍后再试");
            writeJson(response, resultInfo);
        }
    }



    /*
        添加方法
     */
    protected void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            InputStream in = request.getInputStream();
            //2. 将流【json】转成Product
            ObjectMapper objectMapper = new ObjectMapper();
            Product product = objectMapper.readValue(in, Product.class);
            //3. 调用service添加
            productService.save(product);
            //4. 创建ResultInfo封装结果
            ResultInfo resultInfo = new ResultInfo(true, "添加成功");
            //5. 将ResultInfo回复给浏览器
            writeJson(response, resultInfo);
        } catch (Exception e) {
            e.printStackTrace();
            //封装添加失败的结果
            ResultInfo resultInfo = new ResultInfo(false, "添加失败，服务器忙");
            //回复给浏览器
            writeJson(response, resultInfo);
        }
    }

    /*
        分页查询
     */
    protected void findByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //1. 接收请求参数
            String pageNumStr = request.getParameter("pageNum");
            String pageSizeStr = request.getParameter("pageSize");
            //给出默认值
            if (pageNumStr == null || pageNumStr == "") {
                pageNumStr = "1";
            }
            if (pageSizeStr == null || pageSizeStr == "") {
                pageSizeStr = "5";
            }
            //转型
            Integer pageNum = Integer.valueOf(pageNumStr);
            Integer pageSize = Integer.valueOf(pageSizeStr);
            //2. 调用service进行分页查询
            PageBean<Product> pb = productService.findByPage(pageNum, pageSize);
            //3. 封装到ResultInfo
            ResultInfo resultInfo = new ResultInfo(true, "查询成功", pb);
            //4. 将ResultInfo回复给页面
            writeJson(response, resultInfo);
        } catch (Exception e) {
            e.printStackTrace();
            ResultInfo resultInfo = new ResultInfo(false, "服务器忙");
            writeJson(response, resultInfo);
        }
    }

    /*
        根据id查询
     */
    protected void findById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //1.接收请求参数
            String idStr = request.getParameter("id");
            //将id转成Integer
            Integer id = Integer.valueOf(idStr);
            //2. 调用service进行查询
            Product product = productService.findById(id);
            //3. 封装到ResultInfo
            ResultInfo resultInfo = new ResultInfo(true, "查询成功", product);
            //4. 将ResultInfo转成json
            writeJson(response, resultInfo);
        } catch (Exception e) {
            e.printStackTrace();
            ResultInfo resultInfo = new ResultInfo(false, "服务器忙");
            writeJson(response, resultInfo);
        }
    }

    /*
        模板，以后复制使用
     */
    protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //1. 获取输入流
            InputStream in = request.getInputStream();
            //2. 将流【json】转成Product
            ObjectMapper objectMapper = new ObjectMapper();
            Product product = objectMapper.readValue(in, Product.class);
            //3. 调用service修改
            productService.update(product);
            //4. 使用ResultInfo封装结果
            ResultInfo resultInfo = new ResultInfo(true, "修改成功");
            //5. 回复给浏览器
            writeJson(response, resultInfo);
        } catch (Exception e) {
            e.printStackTrace();
            ResultInfo resultInfo = new ResultInfo(false, "服务器忙，修改失败");
            writeJson(response, resultInfo);
        }
    }

    /*
        根据id删除
     */
    protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //获取请求参数id
            String idStr = request.getParameter("id");
            Integer id = Integer.valueOf(idStr);
            //调用service进行删除
            productService.delete(id);
            //给出回复
            ResultInfo resultInfo = new ResultInfo(true, "删除成功");
            writeJson(response, resultInfo);
        } catch (Exception e) {
            e.printStackTrace();
            ResultInfo resultInfo = new ResultInfo(false, "服务器忙，删除失败");
            writeJson(response, resultInfo);
        }
    }

    /*
        模板，以后复制使用
     */
    protected void template(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void writeJson(HttpServletResponse response, Object obj) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(obj);
        response.getWriter().write(json);
    }
}
