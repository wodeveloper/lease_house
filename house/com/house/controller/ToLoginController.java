package com.house.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class ToLoginController extends AbstractController {

    //成功与失败字段   
//    private String successView;  
//    private String failView;  
//      
//    public String getSuccessView() {  
//        return successView;  
//    }  
//  
//    public void setSuccessView(String successView) {  
//        this.successView = successView;  
//    }  
//  
//    public String getFailView() {  
//        return failView;  
//    }  
//  
//    public void setFailView(String failView) {  
//        this.failView = failView;  
//    }  
//  
    @Override  
    protected ModelAndView handleRequestInternal(HttpServletRequest request,  
            HttpServletResponse response) throws Exception {  
        return new ModelAndView("login");  
    }  
}  
