package com.base.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.mybatis.spring.SqlSessionTemplate;

import com.base.utils.ParaMap;
import com.base.utils.StrUtils;

public class UpdateDataDao {

	
	/**
	 * 更新数据
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
	 * @param checkTableFields
	 *            如果data中有键值非tableName表中的字段，请将本参数设置为true
	 * @param ignoreNullValue
	 *            true表示忽略data中的null值，不会被组织到更新SQL语句中
	 * @return 返回更新结果，通过state=1表示更新成功，否则请检查message参数
	 * @throws Exception
	 */
	public ParaMap updateData(String tableName, String keyField, ParaMap data,ParaMap formatData)
			throws Exception {
		ParaMap result = new ParaMap();
		if (StrUtils.isNull(tableName)) {
			result.put("state", 0);
			result.put("message", "更新数据必须传入目标表名");
			return result;
		}
		if (StrUtils.isNull(keyField)) {
			result.put("state", 0);
			result.put("message", "更新数据必须传入目标表" + tableName + "的主键字段名，如果无主键字段则无法通过此方法更新");
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
				if(keyFieldName.substring(0, 1).equals("!")){
					keyFieldName=keyFieldName.substring(1);
				}
				if (!convertData.containsKey(keyFieldName) || StrUtils.isNull(convertData.getString(keyFieldName))){
					result.put("state", 0);
					result.put("message", "更新数据主键在keydata中无值");
					result.put("add", true);
					return result;
					
				}
			}
			if (keyFields.size() == convertData.size()) {
				// 如果仅包含主键字段则直接返回更新成功
				result.put("state", 1);
				result.put("message", "只有主键字段不需要更新");
				return result;
			}
			StringBuffer sql = new StringBuffer("update ");
			sql.append(tableName + " set ");
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
							sql.append(key + " = " + convertValue + ",");
						}else if(value.toString().toUpperCase().trim().equals("NOW()")){
							sql.append(key + " = " + value + ",");
						}else{
							convertValue=value.toString();
							sql.append(key + " = '" + convertValue + "',");
						}
						
					}
				} else {
					result.put("state", 0);
					result.put("message", "不允许更新空字段");
					return result;
				}
			}
			sql.deleteCharAt(sql.length()-1);
			sql.append(" where 1 = 1 ");
			for (int i = 0; i < keyFields.size(); i++) {
				String fieldName = keyFields.get(i);
				boolean blnExclude = false;
				if (fieldName.substring(0, 1).equals("!")) {
					fieldName = fieldName.substring(1);
					blnExclude = true;
				}
				Object fieldValue = convertData.get(fieldName);
				String fieldValueFormat = null;
				if (convertFormat != null && convertFormat.containsKey(fieldName))
					fieldValueFormat = convertFormat.getString(fieldName);
				if (fieldValue == null) {
					if (blnExclude)
						sql.append(" and " + fieldName + " is not null ");
					else
						sql.append(" and " + fieldName + " is null ");
				} else {
					String convertValue=null;
					if(fieldValueFormat!=null){
						convertValue=fieldValueFormat.replace("?", fieldValue.toString());
					}else{
						convertValue=fieldValue.toString();
					}
					if (blnExclude){
						sql.append(" and " + fieldName + " <> '"+convertValue+"' ");
					}
					else{
						sql.append(" and " + fieldName + " = '"+convertValue+"' ");
					}
				}
			}
			System.out.println(sql);
			int n=BaseDaoHelper.executeUpdate(sql.toString());
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
	 * 更新方法 ，使用此方法切记，如果SQL有任何参数来自页面，那么一定要使用占位符的方式，否则很容易被SQL注入
	 * @param sql
	 * @param keyData
	 * @return
	 */
	public ParaMap updateSimpleData(String sql,ParaMap keyData){
		ParaMap result=new ParaMap();
		if(sql.toUpperCase().indexOf(" WHERE ")==-1){
			result.put("state", 0);
			result.put("message", "不允许不带条件的更新语句，如果SQL语句带了条件，请用占位符（:）的形式将参数放入keyData中");
			return result;
		}
		String sqlb=BaseDaoHelper.replaceSQLFLag(sql, keyData);;
		try{
			System.out.println(sqlb);
			int n=BaseDaoHelper.executeUpdate(sqlb);
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
//		List list=new ArrayList<>(Arrays.asList("id","name"));
//		System.out.println(list.contains("id"));
		ParaMap inMap=new ParaMap();
		inMap.put("id", "1");
		inMap.put("no", "2");
		inMap.put("name", "123");
		inMap.put("sex", "345");
		inMap.put("create_date", "now()");
		ParaMap format=new ParaMap();
		UpdateDataDao updateDataDao=new UpdateDataDao();
		updateDataDao.updateSimpleData("update sys_user set id=:id,no=:no where name=:name", inMap);
	}
}
