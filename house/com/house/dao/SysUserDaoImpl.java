package com.house.dao;

import java.util.List;

import com.base.dao.BaseDataSetDao;
import com.base.entity.SysUser;
import com.base.utils.ParaMap;

public class SysUserDaoImpl {
	private BaseDataSetDao baseDataSetDao;


	public BaseDataSetDao getBaseDataSetDao() {
		return baseDataSetDao;
	}

	public void setBaseDataSetDao(BaseDataSetDao baseDataSetDao) {
		this.baseDataSetDao = baseDataSetDao;
	}

	public SysUser getSysUserById(String id) {
//		SysUser sysUser=(SysUser)sessionTemplate.selectOne("com.base.dao.SysUserDao.getSysUserById",id);
		return null;
//		return sysUser;
	}

	public ParaMap getSysUserAll() throws Exception {
		ParaMap keyData=new ParaMap();
		keyData.put("user_name", "Admin");
		return baseDataSetDao.querySimpleData("sys_user", keyData);
	}
	
	public ParaMap getSysUserLimit(String sql,int pageIndex,int pageRowCount) throws Exception {
		return baseDataSetDao.queryData(sql, pageIndex, pageRowCount);
	}
	
	public int insertZHUnit(){
//		String id=UUID.randomUUID().toString().replace("-", "");
//		String sql="insert into zh_unit (id,name,place) values('"+id+"','中国','t888')";
//		Map mp=new HashMap();
//		mp.put("sql", sql);
//		return sessionTemplate.insert("com.base.dao.SysUserDao.insertZHUnit",mp);
		return 1;
	}
	
}
