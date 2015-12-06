package com.house.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.base.entity.Human;
import com.base.entity.SysUser;
import com.base.entity.User;
import com.base.utils.StrUtils;
import com.house.service.SysUserService;


public class LoginController extends AbstractController {
	public SysUserService sysUserSerivce;
	public Human human;
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

    public Human getHuman() {
		return human;
	}

	public void setHuman(Human human) {
		this.human = human;
	}

	public SysUserService getSysUserSerivce() {
		return sysUserSerivce;
	}

	public void setSysUserSerivce(SysUserService sysUserSerivce) {
		this.sysUserSerivce = sysUserSerivce;
	}

	//为了方便直接写的验证方法   
    public User getUser(String username,String password){  
        if(username.equals("1") && password.equals("1")){  
            return new User(username,password);  
        }else{  
            return null;  
        }  
    }
    @Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse arg1) throws Exception {
	        //不应该是这样写，但是这样看起来比较容易理解   
	        int pageIndex=1;
	        if(StrUtils.isNotNull(request.getParameter("pageIndex"))){
	        	pageIndex=Integer.parseInt(request.getParameter("pageIndex"));
	        }
	        int pageRowCount=3;
	        if(StrUtils.isNotNull(request.getParameter("pageRowCount"))){
	        	pageRowCount=Integer.parseInt(request.getParameter("pageRowCount"));
	        }
	        List limitList=getSysUserLimit(pageIndex,pageRowCount);
	        //保存相应的参数，通过ModelAndView返回   
	        HashMap<String ,Object> model=new HashMap<String,Object>();  
            request.setAttribute("list", limitList);
            request.setAttribute("pageIndex", pageIndex);
            return new ModelAndView("showUser",model);
	               
	}  
	
	private List getUserAll() throws Exception{
		return sysUserSerivce.getSysUserAll();
	}
	
	private SysUser getSysUserById(String id){
		return sysUserSerivce.getSysUserById(id);
	}
	
	private List getSysUserLimit(int pageIndex,int pageRowCount) throws Exception{
		return sysUserSerivce.getSysUserLimt(pageIndex, pageRowCount);
	}
	
	private int insertZHUnit(){
		return sysUserSerivce.insertZHUnit();
	}
//	public static void main(String[] args) {
//		  ApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/applicationContext-web.xml");
//	        Sleepable sleeper = (Sleepable)appCtx.getBean("human");
//	        sleeper.sleep();
//
//	}
}  
