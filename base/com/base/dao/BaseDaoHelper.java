package com.base.dao;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.mybatis.spring.SqlSessionTemplate;

import com.base.utils.ParaMap;
import com.base.utils.StrUtils;

public class BaseDaoHelper {
	private static SqlSessionTemplate sessionTemplate;

	public SqlSessionTemplate getSessionTemplate() {
		return sessionTemplate;
	}

	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}
	private static final Comparator GLOBAL_COMPARATOR = Collator.getInstance();// 全局比较器实例，是否线程安全？
	/**
	 * 替换SQL参数，将参数名替换参数值。已经进行简单的SQL注入处理，不建议调用
	 * 
	 * @param sql
	 *            需替换参数的SQL，SQL中参数以“:”为前缀，如“select * from sys_user where name =
	 *            :name and no = :no”
	 * @param sqlParams
	 *            替换参数Map，参数名和参数值键值对。参数名不带“:”
	 * @param quoted
	 *            True表示sqlParams中的参数值已经引号包含，本函数不对参数值作任何转换
	 * @return 返回替换好的SQL，如“select * from sys_user where name = 'abc' and no =
	 *         'xyz'”
	 */
	protected static String convertSQLParams(final String sql, Map sqlParams, boolean quoted) {
		if (StrUtils.isNull(sql) || sqlParams==null || sqlParams.size() == 0)
			return sql;
		String convertAfterSql=sql;
		List paramList = new ArrayList();
		Iterator it = sqlParams.keySet().iterator();
		while (it.hasNext()) {
			paramList.add(it.next().toString());
		}
		// 参数排序
		// Comparator cmp = Collator.getInstance();
		// Collections.sort(paramList, cmp);
		Collections.sort(paramList, GLOBAL_COMPARATOR);
		for (int i = paramList.size() - 1; i >= 0; i--) {
			String paramName = (String) paramList.get(i);
			String paramValue = String.valueOf(sqlParams.get(paramName));
			if (!quoted) { // 避免SQL注入
				if (StrUtils.isNull(paramValue))
					paramValue = "";
				if (paramValue.indexOf("'") > -1)
					paramValue = paramValue.replaceAll("'", "''");
				if (paramValue.indexOf("\"") > -1)
					paramValue = paramValue.replaceAll("\"", "\\\"");
				paramValue = "'" + paramValue + "'";
			} else if (StrUtils.isNull(paramValue))
				paramValue = "";
			convertAfterSql = convertAfterSql.replaceAll("(?u):" + paramName, paramValue);
		}
		return convertAfterSql;
	}
	
	/**
	 * 转换SQL为分页查询SQL，仅支持mysql，其它数据库需重写本方法
	 * 
	 * @param sql
	 *            需分页查询的SQL
	 * @param orderBy
	 *            SQL排序字段列表，格式如：type desc, name, turn asc
	 * @param pageIndex
	 *            需查询的页码数
	 * @param pageRowCount
	 *            需查询的每页记录数
	 * @return 返回转换后可返回指定页码数的记录集的SQL
	 */
	protected static String convertSQLPageQuery(String sql, String orderBy, int pageIndex, int pageRowCount) {
		String resultSQL;
		if (StrUtils.isNotNull(orderBy))
			resultSQL = "select * from (" + sql + ") order by " + orderBy;
		else
			resultSQL = sql;
		if (pageIndex < 0 || pageRowCount <= 0)
			return resultSQL;
		int m = pageRowCount*(pageIndex-1);
		int n = pageRowCount;
		StringBuffer result = new StringBuffer(resultSQL);
		result.append(" limit "+m+","+n);
		return result.toString();
	}
	
	
	/**
	 * 检查是否有SQL注入关键字
	 * @param value
	 * @return false不通过  true通过
	 */
	protected static boolean checkSQL(String value){
		ArrayList checkList=new ArrayList(Arrays.asList(" select "," update "," insert "," delelte "," drop "," create "," grant "));
		Iterator it=checkList.iterator();
		while(it.hasNext()){
			if(value.indexOf(it.next().toString())!=-1){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 检查提交更新的数据MAP中的字段是否是指定表中的字段字段，删除非表中的字段。本方法需访问一次数据库，返回的字段名全部为小写
	 * 
	 * @param checkTableName
	 *            需检查的表名，不传表名则仅将所有字段名转换为小写
	 * @param data
	 *            需检查的数据MAP，调用格式如：<br/>
	 *            module: sysman <br/>
	 *            service: Module <br/>
	 *            method: updateModule <br/>
	 *            id: 1234567890 <br/>
	 *            name: abcdefg <br/>
	 * @return 清除非指定表名字段后的数据MAP，如：<br/>
	 *         id: 1234567890 <br/>
	 *         name: abcdefg <br/>
	 * @throws Exception
	 */
	protected static ParaMap convertDataFieldName(Map data) throws Exception {
		if (data == null || data.size() == 0)
			return null;
		ParaMap result = new ParaMap();
		Iterator it = data.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry=(Entry) it.next();
			result.put(entry.getKey().toString().toLowerCase(), entry.getValue());
		}
		return result;
	}
	
	protected static List<String> convertFields(String keyFields){
		List<String> result=new ArrayList<String>();
		if(keyFields.indexOf(",")==-1){
			if(keyFields.indexOf(";")==-1){
				return null;
			}else{
				result=convertArrayToList(splitString(keyFields, ";"));
			}
		}else{
			result=convertArrayToList(splitString(keyFields, ","));
		}
		return result;
	}
	
	private static List<String> convertArrayToList(String[] sp){
		List<String> list=new ArrayList<String>();
		for (int i = 0; i < sp.length; i++) {
			list.add(sp[i]);
		}
		return list;
	}
	
	private static String[] splitString(String s,String flag){
		String sp[]=s.split(flag);
		return sp;
	}
	
	/**
	 * 替换SQL点位符
	 * @return
	 */
	public static String replaceSQLFLag(String sql,ParaMap keyData){
		String sqlb=sql;
		Iterator iterator=keyData.entrySet().iterator();
		while(iterator.hasNext()){
			Map.Entry entry=(Entry) iterator.next();
			String key=(String) entry.getKey();
			String value=(String) entry.getValue();
			sqlb=sqlb.replace(":"+key, "'"+value+"'");
		}
		return sqlb;
	}
	
	
	/**
	 * 执行新增的SQL
	 * @param sql
	 * @return
	 */
	public static int executeInsert(String sql){
		int n=sessionTemplate.insert("com.base.baseInsert",createSQLMap(sql));
		return n;
	} 
	
	/**
	 * 执行修改的SQL
	 * @param sql
	 * @return
	 */
	public static int executeUpdate(String sql){
		int n=sessionTemplate.update("com.base.baseUpdate",createSQLMap(sql));
		return n;
	} 

	/**
	 * 执行查询LIST的SQL
	 * @param sql
	 * @return
	 */
	public static List executeSelectList(String sql){
		List list=sessionTemplate.selectList("com.base.baseSelect",createSQLMap(sql));
		return list;
	} 
	
	public static int executeDelete(String sql){
		int n=sessionTemplate.delete("com.base.baseDelete",createSQLMap(sql));
		return n;
	} 
	
	private static HashMap createSQLMap(String sql){
		HashMap<String, String> map=new HashMap<>();
		map.put("sql", sql);
		return map;
	}
}
