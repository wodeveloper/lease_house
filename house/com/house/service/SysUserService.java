package com.house.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.base.entity.SysUser;
import com.house.dao.SysUserDaoImpl;

public class SysUserService {
	private SysUserDaoImpl sysUserDao;
	
	public SysUserDaoImpl getSysUserDao() {
		return sysUserDao;
	}

	public void setSysUserDao(SysUserDaoImpl sysUserDao) {
		this.sysUserDao = sysUserDao;
	}

	public SysUser getSysUserById(String id){
		return sysUserDao.getSysUserById(id);
	}
	
	
	
	public List getSysUserAll() throws Exception{
		return sysUserDao.getSysUserAll().getList("list");
	}
	
	@Transactional
	public int insertZHUnit(){
		return sysUserDao.insertZHUnit();
	}
	
	public List getSysUserLimt(int pageIndex,int pageRowCount) throws Exception{
		String sql="SELECT * FROM sys_user ORDER BY create_date ,id ";
		return sysUserDao.getSysUserLimit(sql, pageIndex, pageRowCount).getList("list");
	}
}
