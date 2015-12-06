package com.base.dao;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.base.utils.ParaMap;
import com.base.utils.StrUtils;

public class DeleteDataDao {
	/**
	 * 删除数据
	 * 
	 * @param tableName
	 *           删除数据的表名
	 * @param data
	 *           删除的数据内容，如：<br/>
	 *            id: 1234567890<br/>
	 *            no: abcdefg<br/>
	 *            name: xxxxxx<br/>
	 *            主键字段也需包含，单主键字段为空值将自动新增数据，并且自动获取主键字段值。
	 *            create_date: to_date(?, 'yyyy-mm-dd hh24:mi:ss')
	 * @return 返回更新结果，通过state=1表示更新成功，否则请检查message参数
	 * @throws Exception
	 */
	public ParaMap deleteData(String tableName,ParaMap data)
			throws Exception {
		ParaMap result = new ParaMap();
		if (StrUtils.isNull(tableName)) {
			result.put("state", 0);
			result.put("message", "更新数据必须传入目标表名");
			return result;
		}
		ParaMap convertData = BaseDaoHelper.convertDataFieldName(data);
		try {
			if (data==null || data.size()==0) {
				result.put("state", 0);
				result.put("message", "删除数据不允许不带条件");
				return result;
			}
			StringBuffer sql = new StringBuffer("delete ");
			sql.append(tableName);
			sql.append(" where 1 = 1 ");
			Iterator it = convertData.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Entry) it.next();
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				boolean blnExclude = false;
				boolean isIn=false;
				if (key.substring(0, 1).equals("!")) {
					key = key.substring(1);
					blnExclude = true;
				}
				if(key.endsWith(" in")){
					isIn=true;
				}
				if (StrUtils.isNotNull(key)) {
					if (value == null) {
						if (blnExclude)
							sql.append(" and " + key + " is not null ");
						else
							sql.append(" and " + key + " is null ");
					} else {
						if (blnExclude){
							if(isIn){
								sql.append(" and " + key + " not in ("+value+")");
							}else{
								sql.append(" and " + key + " <> '"+value+"' ");
							}
						}else{
							if(isIn){
								sql.append(" and " + key + " in ("+value+")");
							}else{
								sql.append(" and " + key + " = '"+value+"' ");
							}
						}
					}
				}
			}
			sql.deleteCharAt(sql.length()-1);
			
			System.out.println(sql);
			int n=BaseDaoHelper.executeDelete(sql.toString());
			result.put("state", 1);
			result.put("count", n);
		} catch (Exception e) {
			result.put("state", 0);
			result.put("message", e.getMessage());
			throw e;
		} finally {
		}
		return result;
	}

	/**
	 * 删除方法 ，使用此方法切记，如果SQL有任何参数来自页面，那么一定要使用占位符的方式，否则很容易被SQL注入
	 * @param sql
	 * @param keyData
	 * @return
	 */
	public ParaMap deleteSimpleData(String sql,ParaMap keyData){
		ParaMap result=new ParaMap();
		if((keyData==null || keyData.size()==0 )&& sql.toUpperCase().indexOf(" WHERE ")==-1){
			result.put("state", 0);
			result.put("message", "不允许不带条件的删除语句，如果SQL语句带了条件，请用占位符（:）的形式将参数放入keyData中");
			return result;
		}
		
		String sqlb=BaseDaoHelper.replaceSQLFLag(sql, keyData);
		try{
			System.out.println(sqlb);
			int n=BaseDaoHelper.executeDelete(sqlb);
			result.put("state", 1);
			result.put("count", n);
		}catch(Exception e){
			result.put("state", 0);
			result.put("count", e.getMessage());
			throw e;
		}
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		ParaMap inMap=new ParaMap();
		inMap.put("user_name", "新增功能1");
		inMap.put("password", "123444");
//		format.put("create_date", "str_to_date('?', '%Y%m.%d %h:%i:%s')");
		DeleteDataDao updateDataDao=new DeleteDataDao();
		ParaMap out=updateDataDao.deleteSimpleData("delete sys_user where user_name=:user_name and password=:password", inMap);
		System.out.println(out.getString("message"));
	}
}
