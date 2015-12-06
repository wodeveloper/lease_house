package com.base.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.mybatis.spring.SqlSessionTemplate;

import com.base.utils.IDGenerator;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;

public class InsertDataDao {
	/**
	 * 新增数据
	 * 
	 * @param tableName
	 *            更新数据的表名
	 * @param keyField
	 *            更新数据表的主键字段，多个主键字段请使用用半角逗号或者分号分隔，如“id”、“department_id,emp_id”
	 * @param data
	 *            更新的数据内容，如：<br/>
	 *            id: 1234567890<br/>
	 *            no: abcdefg<br/>
	 *            name: xxxxxx<br/>
	 *            主键字段也需包含，单主键字段为空值将自动新增数据，并且自动获取主键字段值。
	 * @param format
	 *            data中有特殊格式的字段值则需要在此map中添加格式转换说明，如：<br/>
	 *            create_date: to_date(?, 'yyyy-mm-dd hh24:mi:ss')
	 * @return 返回更新结果，通过state=1表示更新成功，否则请检查message参数
	 * @throws Exception
	 */
	public ParaMap insertData(String tableName, String keyField, ParaMap data,ParaMap formatData)
			throws Exception {
		ParaMap result = new ParaMap();
		if (StrUtils.isNull(tableName)) {
			result.put("state", 0);
			result.put("message", "新增数据必须传入目标表名");
			return result;
		}
		if (StrUtils.isNull(keyField)) {
			result.put("state", 0);
			result.put("message", "新增数据必须传入目标表" + tableName + "的主键字段名，如果无主键字段则无法通过此方法新增");
			return result;
		}
		ParaMap convertData = BaseDaoHelper.convertDataFieldName(data);
		ParaMap convertFormat = BaseDaoHelper.convertDataFieldName(formatData);
		List<String> keyFields = new ArrayList<String>();
		try {
			// 提取所有主键字段
			if (keyField.indexOf(',') == -1 && keyField.indexOf(';') == -1)
				keyFields.add(keyField);
			else {
				keyFields = BaseDaoHelper.convertFields(keyField);
			}
			// 检查主键字段是否都在更新字段列表中
			for (int i = 0; i < keyFields.size(); i++) {
				String keyFieldName = keyFields.get(i);
				if (convertData.containsKey(keyFieldName) && StrUtils.isNotNull(convertData.getString(keyFieldName))){
					result.put("state", 0);
					result.put("message", "新增时不能带ID的值，ID值会自动生成。");
					return result;
				}
			}
			StringBuffer sql = new StringBuffer("insert ignore into ");
			sql.append(tableName);
			StringBuffer fieldString=new StringBuffer("(");
			StringBuffer valueString=new StringBuffer("(");
			Iterator it = convertData.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Entry) it.next();
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				if (StrUtils.isNotNull(key)) {
					if (keyFields.contains(key) == false) {
						String fieldValueFormat = null;
						if (convertFormat != null && convertFormat.containsKey(key)){
							fieldValueFormat = convertFormat.getString(key);
						}
						String convertValue=null;
						if(fieldValueFormat!=null){
							convertValue=fieldValueFormat.replace("?", value.toString());
							valueString.append(convertValue+",");
						}else if(value.toString().toUpperCase().trim().equals("NOW()")){
							valueString.append(value+",");
						}else{
							convertValue=value.toString();
							valueString.append("'"+convertValue+"',");
						}
						fieldString.append(key+",");
					}
				} else {
					result.put("state", 0);
					result.put("message", "不允许更新空字段");
					return result;
				}
			}
			for (int i = 0; i < keyFields.size(); i++) {
				String fieldName = keyFields.get(i);
				String fieldValue=IDGenerator.newGUID(true);
				fieldString.append(fieldName+",");
				valueString.append("'"+fieldValue+"',");
				result.put(fieldName, fieldValue);
			}
			fieldString.deleteCharAt(fieldString.length()-1);
			valueString.deleteCharAt(valueString.length()-1);
			sql.append(fieldString);
			sql.append(" ) values ");
			sql.append(valueString);
			sql.append(")");
			System.out.println(sql);
			int n=BaseDaoHelper.executeInsert(sql.toString());
			if(n>0){
				result.put("state", 1);
				result.put("count", n);
			}else{
				result.put("state", 0);
				result.put("count", n);
			}
		} catch (Exception e) {
			result.put("state", 0);
			result.put("message", e.getMessage());
			throw e;
		} finally {
		}
		return result;
	}
	public static void main(String[] args) throws Exception {
		ParaMap inMap=new ParaMap();
		inMap.put("user_name", "新增功能1");
		inMap.put("password", "123444");
		inMap.put("create_date", "now()");
		ParaMap format=new ParaMap();
//		format.put("create_date", "str_to_date('?', '%Y%m.%d %h:%i:%s')");
		InsertDataDao updateDataDao=new InsertDataDao();
		ParaMap out=updateDataDao.insertData("sys_user", "id", inMap, format);
		System.out.println(out.getString("message"));
	}
}
