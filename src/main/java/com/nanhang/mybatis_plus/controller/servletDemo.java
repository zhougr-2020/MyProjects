package com.nanhang.mybatis_plus.controller;

import com.nanhang.mybatis_plus.pojo.User;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: immortal
 * @CreateDate: 2021/6/23 9:53
 * @Description:
 */
@WebServlet(urlPatterns = "/")
public class servletDemo extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        new ModelAndView();
        System.out.println("hello");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    public static void main(String[] args) {
        User user = new User();
        String city = user.getCity();
        System.out.println(city);
    }
}

