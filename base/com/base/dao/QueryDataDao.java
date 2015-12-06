package com.base.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.mybatis.spring.SqlSessionTemplate;

import com.base.utils.ParaMap;
import com.base.utils.StrUtils;

public class QueryDataDao {
	/**
	 * 查询指定数据集(按模块Id、数据集No)的所有或分页结果集
	 * 
	 * @param moduleId
	 *            指定模块ID，查询sys_dataset.module_id字段
	 * @param dataSetNo
	 *            指定数据集编号，查询sys_dataset.no字段
	 * @param sqlParams
	 *            SQL中的参数值MAP，替换参数请查看convertSQLParams等方法。可包含SQL_ORDER_BY、
	 *            SQL_PAGE_INDEX等特殊键（请查看相关注释）
	 * @return 
	 *         返回结果集MAP，state参数为1表示查询成功，0为查询失败；message参数在失败时写入错误原因。数据集相关请查看ParaMap说明
	 *         MAP中还包含：<br/>
	 *         totalRowCount: 总记录数<br/>
	 *         rowCount: 当前结果集返回的记录数<br/>
	 *         pageIndex: 页码<br/>
	 *         pageRowCount: 每页记录数<br/>
	 *         orderBy: 排序字段<br/>
	 * @throws Exception
	 */
//	public ParaMap queryData(String moduleId, String dataSetNo, ParaMap sqlParams) throws Exception {
//		ParaMap sqls = getDataSetSQL(moduleId, dataSetNo);
//		if (sqls.getInt("sqlCount") > 0)
//			return queryData(sqls.getString("sql0"), sqlParams);
//		else {
//			ParaMap result = new ParaMap();
//			result.put("state", 0);
//			result.put("message", "未定义指定的数据集，无法继续查询数据操作，请检查相关配置");
//			return result;
//		}
//	}
	
	/**
	 * 简单查询数据，主要用于编辑数据时查询单条记录无需定义数据集
	 * 
	 * @param tableName
	 *            查询数据的表名
	 * @param keyData
	 *            查询条件键值对
	 * @param returnFields
	 *            查询哪些列，为空则查询*
	 * @return 返回查询结果，通过state=1表示更新成功，否则请检查message参数 MAP中还包含：<br/>
	 *         totalRowCount: 总记录数<br/>
	 *         rowCount: 当前结果集返回的记录数，实际同totalRowCount。主要是包含queryData返回值基本一致<br/>
	 * @throws Exception
	 */
	public ParaMap querySimpleData(String tableName, ParaMap keyData,String returnFields,String orderBy) throws Exception {
		ParaMap result = new ParaMap();
		if (StrUtils.isNull(tableName)) {
			result.put("state", 0);
			result.put("message", "查询数据必须传入目标表名");
			return result;
		}
		Map<Integer, Object> sqlParamsPos = new HashMap<Integer, Object>();
		int intParamPos = 0;
		try {
			StringBuffer sql = new StringBuffer("select ");
			if (StrUtils.isNotNull(returnFields))
				sql.append(returnFields);
			else
				sql.append("*");
			sql.append(" from " + tableName + " where 1 = 1");
			if (keyData != null && keyData.size() > 0) {
				Iterator it = keyData.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry map=(Entry) it.next();
					String strFieldName = (String) map.getKey();
					Object value = map.getValue();
					if(BaseDaoHelper.checkSQL(strFieldName)==false || BaseDaoHelper.checkSQL(value.toString())==false){
						result.put("state", 0);
						result.put("message", "查询参数包括SQL注入内容");
						return result;
					}
					if (StrUtils.isNotNull(strFieldName)) {
						boolean blnExclude = false;
						if (strFieldName.substring(0, 1).equals("!")) {
							strFieldName = strFieldName.substring(1);
							blnExclude = true;
						}
						if (strFieldName.substring(0, 1).equals("@")) {
							strFieldName = strFieldName.substring(1);
						}
						if (value instanceof String &&  StrUtils.isNull(String.valueOf(value))) {
							if (blnExclude){
								sql.append(" and " + strFieldName + " is not null ");
							}else{
								sql.append(" and " + strFieldName + " is null ");
							}
						} else if (value instanceof List) {
							// 值列表，sql转换如: f in (1, 2, 4)
							List list = (List) value;
							if (list.size() > 0) {
								if (blnExclude)
									sql.append(" and " + strFieldName + " not in (");
								else
									sql.append(" and " + strFieldName + " in (");
								for (int i = 0; i < list.size(); i++) {
									sql.append("?,");
									sqlParamsPos.put(intParamPos, list.get(i));
									intParamPos++;
								}
								sql.deleteCharAt(sql.length() - 1);
								sql.append(")");
							}
						}  else {
							if (blnExclude)
								sql.append(" and " + strFieldName + " <> '"+value+"' ");
							else
								sql.append(" and " + strFieldName + " = '"+value+"' ");
						}
					}
				}
			}
			if (!StrUtils.isNull(orderBy)) {
				sql.append(" order by " + orderBy);
			}
			System.out.println(sql);
			List list=BaseDaoHelper.executeSelectList(sql.toString());
			result.put("list", list);
			result.put("state", 1);
		} catch (Exception e) {
			result = new ParaMap();
			result.put("state", 0);
			result.put("message", e.getMessage());
			throw e;
		} finally {
		}
		return result;
	}
	
	/**
	 * 查询SQL 使用此方法切记，如果SQL有任何参数来自页面，那么一定要使用占位符的方式，否则很容易被SQL注入
	 * @param sql
	 * @param keyData
	 * @param pageIndex 第几页
	 * @param pageRowCount 一页多少条
	 * @return
	 */
	public ParaMap queryData(String sql,ParaMap keyData,int pageIndex,int pageRowCount){
		ParaMap result = new ParaMap();
		if (StrUtils.isNull(sql)) {
			result.put("state", 0);
			result.put("message", "未指定SQL，无法继续查询数据操作");
			return result;
		}
		ParaMap queryParams = keyData;
		if (queryParams == null)
			queryParams = new ParaMap();
		String pageSQL = BaseDaoHelper.convertSQLParams(sql, keyData,false);
		if(pageIndex!=-128 && pageRowCount!=-128){
			pageIndex=setDefaultPageIndex(pageIndex);
			pageRowCount=setDefaultPageRowCount(pageRowCount);
			pageSQL=BaseDaoHelper.convertSQLPageQuery(sql, null, pageIndex, pageRowCount);
		}
		
		pageSQL=BaseDaoHelper.replaceSQLFLag(pageSQL, keyData);
		System.out.println(pageSQL);
		try{
			List list=BaseDaoHelper.executeSelectList(pageSQL);
			result.put("state", 1);
			result.put("list", list);
		}catch(Exception e){
			result.put("state", 0);
			result.put("message", e.getMessage());
			throw e;
		}
		return result;
	}
	
	private int setDefaultPageIndex(final int pageIndex){
		int returnValue=pageIndex;
		if(returnValue<0){
			returnValue=0;
		}
		return returnValue;
	}
	
	private int setDefaultPageRowCount(final int pageRowCount){
		int returnValue=pageRowCount;
		if(returnValue<0){
			returnValue=10;
		}
		return pageRowCount;
	}
	
	
	
	
	
	public static void main(String[] args) throws Exception {
		QueryDataDao queryDataDao=new QueryDataDao();
		String sql="select *from sys_user where 1=1 and name=:name and no=:no and id like '%:id%'";
		ParaMap sqlParams=new ParaMap();
		sqlParams.put("name", "唐运文");
		sqlParams.put("no", "123454");
		sqlParams.put("id", "333");
//		queryDataDao.queryData(sql, sqlParams, 1, 10);
		queryDataDao.querySimpleData("sys_user", sqlParams, null, null);
	}
}
