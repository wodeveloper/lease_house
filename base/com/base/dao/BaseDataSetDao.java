package com.base.dao;

import com.base.utils.ParaMap;

/**
 * 通用数据集访问
 * 
 * @author 
 */
public class BaseDataSetDao extends BaseDao {
	
	
//	static private boolean DATA_SET_SQL_DEFINE_DB = true; // 数据集定义使用数据库
//	public static final String SQL_ORDER_BY = "_ORDER_BY_"; // 结果集排序字段，分页时必须。值格式：name
//															// desc, no
//	public static final String SQL_PAGE_INDEX = "_PAGE_INDEX_"; // 分页查询指定页码数
//	public static final String SQL_PAGE_ROW_COUNT = "_PAGE_ROW_COUNT_"; // 分页查询指定每页记录数
//	public static final String SQL_PARAMS_QUOTED = "_PARAMS_QUOTED_"; // SQL参数自动添加引号，无参数缺省为0。可一定程度避免SQL注入
//	public static final String SQL_PARAMS_BY_POS = "_PARAMS_BY_POS_"; // SQL参数替换按占位符方式，暂未实现
//	public static final String SQL_CUSTOM_CONDITIONS = "_CUSTOM_CONDITIONS_"; // SQL自定义参数集
//	public static final String SQL_LOOKUP_TABLE_NAME = "_LOOKUP_TABLE_NAME_"; // 简单查询时子查询表名
//	public static final String SQL_LOOKUP_KEY_FIELD_NAME = "_LOOKUP_KEY_FIELD_NAME_"; // 简单查询时子查询关联字段名
//
//	private static long serverDateOffset = 0; // 数据库服务器时间同应用服务器时间偏移量，单位毫秒
//	private static final String SQL_EXCLUDE_PARAM_NAMES = "mi;ss";
//	private static final Comparator GLOBAL_COMPARATOR = Collator.getInstance();// 全局比较器实例，是否线程安全？
//
//	private boolean queryDataReturnFieldMeta = false;
//	private int generatorIDType = 0;//生成主要方式：0按系统默认（目前同1），1guid（此时应用可以不通过数据库取），2返回时间戳（并发时不一定可靠），3返回新的可用的最大Id值，4返回最大Id整数值（已使用过的），5返回前缀（不影响最大值），其它值按默认值

	private QueryDataDao queryDataDao;
	private UpdateDataDao updateDataDao;
	private InsertDataDao insertDataDao;
	public QueryDataDao getQueryDataDao() {
		return queryDataDao;
	}

	public void setQueryDataDao(QueryDataDao queryDataDao) {
		this.queryDataDao = queryDataDao;
	}

	public UpdateDataDao getUpdateDataDao() {
		return updateDataDao;
	}

	public void setUpdateDataDao(UpdateDataDao updateDataDao) {
		this.updateDataDao = updateDataDao;
	}

	public InsertDataDao getInsertDataDao() {
		return insertDataDao;
	}

	public void setInsertDataDao(InsertDataDao insertDataDao) {
		this.insertDataDao = insertDataDao;
	}

	static {
//		String sqlDefine = AppConfig.getPro("dataSetSqlDefine");
//		if ("xml".equalsIgnoreCase(sqlDefine)) {
//			DATA_SET_SQL_DEFINE_DB = false;
//			System.out.println("---->[" + StrUtils.dateToStr(ServerTimeTask.getDBServerDate()) + "] 数据集定义使用离线XML模式");
//		} else {
//			DATA_SET_SQL_DEFINE_DB = true;
//			System.out.println("---->[" + StrUtils.dateToStr(ServerTimeTask.getDBServerDate()) + "] 数据集定义使用在线DB模式");
//		}
	}

//	public static boolean isSqlDefineDb() {
//		return DATA_SET_SQL_DEFINE_DB;
//	}
//
//	public boolean isQueryDataReturnFieldMeta() {
//		return queryDataReturnFieldMeta;
//	}
//
//	public void setQueryDataReturnFieldMeta(boolean queryDataReturnFieldMeta) {
//		this.queryDataReturnFieldMeta = queryDataReturnFieldMeta;
//	}
//	
//	public int getGeneratorIDType() {
//		return this.generatorIDType;
//	}
//	
//	public void setGeneratorIDType(int generatorIDType) {
//		this.generatorIDType = generatorIDType;
//	}
//	
//	/**
//	 * 生成主键id
//	 * @param tableName 表名
//	 * @return
//	 */
//	private String generatorID(String tableName) {
//		//return IDGenerator.generatorID(tableName);
//		return IDGenerator.generatorID(tableName, null, 0, generatorIDType);
//	}
//
//	/**
//	 * 获取数据库服务器时间
//	 * 
//	 * @param forceRefresh
//	 *            true强制刷新服务器时间，否则第一取服务器时间后与应用服务器时间取差值
//	 * @return
//	 */
//	private static Date getDBServerDate(boolean forceRefresh) {
//		Date now = new Date();
//		if (serverDateOffset == 0 || forceRefresh) {// 第一次取或者强制取服务器时间
//			DataSetDao dataSetDao = new DataSetDao();
//			try {
//				ParaMap sqlParams = new ParaMap();
//				sqlParams.put("date_format", "yyyy-mm-dd hh24:mi:ss.ff3");
//				ParaMap data = dataSetDao.queryData("select to_char(systimestamp, :date_format) as now from dual", sqlParams);
//				if (data.getInt("state") == 1 && data.getInt("totalRowCount") > 0) {
//					String strServerDate = data.getRecordString(0, 0);
//					Date serverDate = StrUtils.strToDate(strServerDate, "yyyy-MM-dd HH:mm:ss.SSS");
//					// 记录本地时间与服务器时间的偏移量，之后非强制取服务器时间的都以本地时间（应用服务器时间）再加上偏移量。如果应用服务器时间做了修改将导致不准确
//					serverDateOffset = (serverDate.getTime() - now.getTime());
//				}
//			} catch (Exception ex) {
//				serverDateOffset = 0;
//			}
//		}
//		long d = now.getTime() + serverDateOffset;
//		return new Date(d);
//	}
//
//	public static Date getServerDate() {
//		return getDBServerDate(true);
//	}
//
//	/**
//	 * 替换SQL参数，将参数名替换参数值。已经进行简单的SQL注入处理，不建议调用
//	 * 
//	 * @param sql
//	 *            需替换参数的SQL，SQL中参数以“:”为前缀，如“select * from sys_user where name =
//	 *            :name and no = :no”
//	 * @param sqlParams
//	 *            替换参数Map，参数名和参数值键值对。参数名不带“:”
//	 * @param quoted
//	 *            True表示sqlParams中的参数值已经引号包含，本函数不对参数值作任何转换
//	 * @return 返回替换好的SQL，如“select * from sys_user where name = 'abc' and no =
//	 *         'xyz'”
//	 */
//	public static String convertSQLParams(String sql, Map sqlParams, boolean quoted) {
//		if (sqlParams == null || sqlParams.size() == 0)
//			return sql;
//		List paramList = new ArrayList();
//		Iterator it = sqlParams.keySet().iterator();
//		while (it.hasNext()) {
//			paramList.add(it.next().toString());
//		}
//		// 参数排序
//		// Comparator cmp = Collator.getInstance();
//		// Collections.sort(paramList, cmp);
//		Collections.sort(paramList, GLOBAL_COMPARATOR);
//		for (int i = paramList.size() - 1; i >= 0; i--) {
//			String paramName = (String) paramList.get(i);
//			String paramValue = String.valueOf(sqlParams.get(paramName));
//			if (!quoted) { // 避免SQL注入
//				if (StrUtils.isNull(paramValue))
//					paramValue = "";
//				if (paramValue.indexOf("'") > -1)
//					paramValue = paramValue.replaceAll("'", "''");
//				if (paramValue.indexOf("\"") > -1)
//					paramValue = paramValue.replaceAll("\"", "\\\"");
//				paramValue = "'" + paramValue + "'";
//			} else if (StrUtils.isNull(paramValue))
//				paramValue = "";
//			sql = sql.replaceAll("(?u):" + paramName, paramValue);
//		}
//		return sql;
//	}
//
//	/**
//	 * 替换SQL参数为?，按占位符方式
//	 * 
//	 * @param sql
//	 *            需替换参数的SQL，SQL中参数以“:”为前缀，如“select * from sys_user where name =
//	 *            :name and no = :no”
//	 * @param sqlParamsPos
//	 *            返回各参数的位置以便preparedStatement写入参数值。返回的参数名不带“:” 返回格式如：<br/>
//	 *            0: name <br/>
//	 *            1: no <br/>
//	 * @return 返回后的SQL，如“select * from sys_user where name = ? and no = ?”
//	 */
//	public static String convertSQLParams(String sql, Map<Integer, String> sqlParamsPos, List<String> excludeParamNames) {
//		if (sql.indexOf(":") == -1)
//			return sql;
//		ParaMap replaceParams = new ParaMap();
//		// 提取所有参数的序
////		int i = -1;
////		StringTokenizer st = new StringTokenizer(sql, ":");
////		while (st.hasMoreTokens()) {
////			String paramName = st.nextToken();
////			char[] arrayParam = paramName.toCharArray();
////			StringBuilder sbParam = new StringBuilder(100);
////			for (char c : arrayParam) {
////				int cAscii = (int) c;
////				if ((c >= 48 && c <= 57) || (c >= 65 && c <= 90) || (c >= 97 && c <= 122) || c == 95)
////					sbParam.append(c);
////				else
////					break;
////			}
////			paramName = sbParam.toString();
////			boolean blnStaticExcluded = StrUtils.paramExists(SQL_EXCLUDE_PARAM_NAMES, paramName, true);
////			boolean blnExcluded = excludeParamNames != null && excludeParamNames.indexOf(paramName) >= 0;
////			if (i > -1 && StrUtils.isNotNull(paramName) && !blnStaticExcluded && !blnExcluded) {
////				if (!replaceParams.containsKey(paramName))
////					replaceParams.put(paramName, "?");
////				sqlParamsPos.put(i, paramName);
////				i++;
////			} else if (i == -1)
////				i++;
////		}
//		int paramPos = 0;
//		boolean inQuoted = false;
//		boolean paramNameStarted = false;
//		StringBuilder sbParam = new StringBuilder();
//		for(int i = 0; i < sql.length(); i++) {
//			char c = sql.charAt(i);
//			if (c == '\'')
//				inQuoted = !inQuoted;
//			if (c == ':' && !inQuoted) {
//				paramNameStarted = true;
//				String paramName = sbParam.toString();
//				if (StrUtils.isNotNull(paramName)) {
//					boolean blnStaticExcluded = StrUtils.paramExists(SQL_EXCLUDE_PARAM_NAMES, paramName, true);
//					boolean blnExcluded = excludeParamNames != null && excludeParamNames.indexOf(paramName) >= 0;
//					if (!blnStaticExcluded && !blnExcluded) {
//						if (!replaceParams.containsKey(paramName))
//							replaceParams.put(paramName, "?");
//						sqlParamsPos.put(paramPos, paramName);
//						paramPos++;
//					}
//				}
//				sbParam = new StringBuilder();
//			} else if (paramNameStarted) {
//				int cAscii = (int) c;
//				if ((c >= 48 && c <= 57) || (c >= 65 && c <= 90) || (c >= 97 && c <= 122) || c == 95)
//					sbParam.append(c);
//				else {
//					paramNameStarted = false;
//					String paramName = sbParam.toString();
//					if (StrUtils.isNotNull(paramName)) {
//						boolean blnStaticExcluded = StrUtils.paramExists(SQL_EXCLUDE_PARAM_NAMES, paramName, true);
//						boolean blnExcluded = excludeParamNames != null && excludeParamNames.indexOf(paramName) >= 0;
//						if (!blnStaticExcluded && !blnExcluded) {
//							if (!replaceParams.containsKey(paramName))
//								replaceParams.put(paramName, "?");
//							sqlParamsPos.put(paramPos, paramName);
//							paramPos++;
//						}
//					}
//					sbParam = new StringBuilder();
//				}
//			}
//		}
//		String paramName = sbParam.toString();
//		if (StrUtils.isNotNull(paramName)) {
//			boolean blnStaticExcluded = StrUtils.paramExists(SQL_EXCLUDE_PARAM_NAMES, paramName, true);
//			boolean blnExcluded = excludeParamNames != null && excludeParamNames.indexOf(paramName) >= 0;
//			if (!blnStaticExcluded && !blnExcluded) {
//				if (!replaceParams.containsKey(paramName))
//					replaceParams.put(paramName, "?");
//				sqlParamsPos.put(paramPos, paramName);
//				paramPos++;
//			}
//		}
//		// 替换所有参数为?
//		sql = convertSQLParams(sql, replaceParams, true);
//		return sql;
//	}
//
//	public static String convertSQLParams(String sql, Map<Integer, String> sqlParamsPos, String excludeParamNames) {
//		return convertSQLParams(sql, sqlParamsPos, StrUtils.getSubStrs(excludeParamNames, ";"));
//	}
//
//	public static String convertSQLParams(String sql, Map<Integer, String> sqlParamsPos) {
//		return convertSQLParams(sql, sqlParamsPos, (List<String>) null);
//	}
//
//	/**
//	 * 替换所有自定义条件，用于查询界面传入更多的自定义查询条件
//	 * 
//	 * @param sql
//	 *            需替换自定义条件的SQL，如“select * from sys_user where name = :name
//	 *            :custom_conditions1 and id in (:custom_conditions2)”
//	 * @param sqlCustomConditions
//	 *            自定义条件Map。 <br/>
//	 *            格式示例为： <br/>
//	 *            custom_conditions1: and no = :no and abc = :abc <br/>
//	 *            custom_conditions2: select id from xxxx where aaa = :aaa <br/>
//	 * @return 返回替换完成的SQL。如“select * from sys_user where name = :name and no =
//	 *         :no and id in (select id from xxxx where aaa = :aaa)”
//	 */
//	public static String convertCustomConditions(String sql, Map sqlCustomConditions) {
//		return convertSQLParams(sql, sqlCustomConditions, true);
//	}
//
//	/**
//	 * 转换SQL为分页查询SQL，仅支持Oracle，其它数据库需重写本方法
//	 * 
//	 * @param sql
//	 *            需分页查询的SQL
//	 * @param orderBy
//	 *            SQL排序字段列表，格式如：type desc, name, turn asc
//	 * @param pageIndex
//	 *            需查询的页码数
//	 * @param pageRowCount
//	 *            需查询的每页记录数
//	 * @return 返回转换后可返回指定页码数的记录集的SQL
//	 */
//	public static String convertSQLPageQuery(String sql, String orderBy, int pageIndex, int pageRowCount) {
//		String resultSQL;
//		if (StrUtils.isNotNull(orderBy))
//			resultSQL = "select * from (" + sql + ") order by " + orderBy;
//		else
//			resultSQL = sql;
//		if (pageIndex <= 0 || pageRowCount <= 0)
//			return resultSQL;
//		int intHighRowCount = pageIndex * pageRowCount;
//		int intLowRowCount = intHighRowCount - pageRowCount + 1;
//		StringBuffer result = new StringBuffer("SELECT * FROM (SELECT A.*, ROWNUM RN__1 FROM (");
//		result.append(resultSQL);
//		result.append(") A WHERE ROWNUM <= " + intHighRowCount + ") WHERE RN__1 >= " + intLowRowCount);
//		return result.toString();
//	}
//	
//	protected ParaMap getTableMetaData(String tableName) throws Exception {
//		ParaMap result = queryData("select * from " + tableName + " where 1 = 2", null);
//		return result;
//	}
//
//	/**
//	 * 检查提交更新的数据MAP中的字段是否是指定表中的字段字段，删除非表中的字段。本方法需访问一次数据库，返回的字段名全部为小写
//	 * 
//	 * @param checkTableName
//	 *            需检查的表名，不传表名则仅将所有字段名转换为小写
//	 * @param data
//	 *            需检查的数据MAP，调用格式如：<br/>
//	 *            module: sysman <br/>
//	 *            service: Module <br/>
//	 *            method: updateModule <br/>
//	 *            id: 1234567890 <br/>
//	 *            name: abcdefg <br/>
//	 * @return 清除非指定表名字段后的数据MAP，如：<br/>
//	 *         id: 1234567890 <br/>
//	 *         name: abcdefg <br/>
//	 * @throws Exception
//	 */
//	public ParaMap convertDataFieldName(Map data, String checkTableName) throws Exception {
//		if (data == null || data.size() == 0)
//			return null;
//		ParaMap result = new ParaMap();
//		if (StrUtils.isNull(checkTableName)) {// 没传入表名则将所有字段名转换为小写
//			Iterator it = data.keySet().iterator();
//			while (it.hasNext()) {
//				String key = it.next().toString();
//				result.put(key.toLowerCase(), data.get(key));
//			}
//		} else {// 否则仅包含指定表的列，列名转换为小写
//			ParaMap tableMeta = getTableMetaData(checkTableName);
//			List fields = tableMeta.getFields();
//			Iterator it = data.keySet().iterator();
//			while (it.hasNext()) {
//				String key = it.next().toString();
//				if (fields.indexOf(key.toLowerCase()) != -1)
//					result.put(key.toLowerCase(), data.get(key));
//			}
//		}
//		return result;
//	}
//
//	/**
//	 * 转换字段
//	 * 
//	 * @param fields
//	 *            字段列表，以半角逗号“,”或者分号“;”分隔的串
//	 * @param checkTableName
//	 *            表名，有值则表示删除fields中不存在于checkTableName表中的字段。空则仅转换字段为小写
//	 * @param allowNotCondition
//	 *            true表示允许出现反条件字段。即字段名前添加“!”
//	 * @return
//	 * @throws Exception
//	 */
//	public List<String> convertFields(String fields, String checkTableName, boolean allowNotCondition) throws Exception {
//		if (StrUtils.isNull(fields))
//			return null;
//		List<String> result = new ArrayList<String>();
//		if (fields.indexOf(',') == -1 && fields.indexOf(';') == -1)
//			result.add(fields.toLowerCase());
//		else {
//			StringTokenizer st = null;
//			if (fields.indexOf(',') == -1)
//				st = new StringTokenizer(fields, ";");
//			else
//				st = new StringTokenizer(fields, ",");
//			while (st.hasMoreTokens())
//				result.add(st.nextToken().toLowerCase());
//		}
//		if (StrUtils.isNotNull(checkTableName)) {
//			ParaMap tableMeta = getTableMetaData(checkTableName);
//			List columns = tableMeta.getFields();
//			List<String> checkFields = new ArrayList<String>();
//			for (int i = 0; i < result.size(); i++) {
//				String fieldName = result.get(i);
//				boolean hasNotCondition = false;
//				if (allowNotCondition && fieldName.startsWith("!")) {
//					fieldName = fieldName.substring(1);
//					hasNotCondition = true;
//				}
//				if (columns.indexOf(fieldName) != -1)
//					if (hasNotCondition)
//						checkFields.add("!" + fieldName);
//					else
//						checkFields.add(fieldName);
//			}
//			result = checkFields;
//		}
//		return result;
//	}
//
//	public List<String> convertFields(String fields, String checkTableName) throws Exception {
//		return convertFields(fields, checkTableName, false);
//	}
//
//	public List<String> convertFields(String fields) throws Exception {
//		return convertFields(fields, null);
//	}
//
//	/**
//	 * 获取指定模块Id、数据集No的SQL
//	 * 
//	 * @param moduleId
//	 *            指定模块ID，查询sys_dataset.module_id字段
//	 * @param dataSetNo
//	 *            指定数据集编号，查询sys_dataset.no字段
//	 * @return 符合条件的数据集SQL Map，返回sys_dataset.sql字段<br/>
//	 *         Map中包含sqlCount为符合条件的SQL数，SQL按sql0、sql1、sql2、...方式返回
//	 * @throws Exception
//	 */
//	public ParaMap getDataSetSQL(String moduleId, String dataSetNo) throws Exception {
//		ParaMap result = new ParaMap();
//		StringBuffer sql = new StringBuffer("select id, no, name, module_id, sql, is_valid from sys_dataset where ");
//		if (StrUtils.isNull(moduleId))
//			sql.append(" module_id is null");
//		else
//			sql.append(" module_id = ?");
//		sql.append(" and no = ?");
//		PreparedStatement pstm = null;
//		ResultSet rs = null;
//		try {
//			pstm = getCon().prepareStatement(sql.toString());
//			if (StrUtils.isNull(moduleId)) {
//				pstm.setString(1, dataSetNo);
//			} else {
//				pstm.setString(1, moduleId);
//				pstm.setString(2, dataSetNo);
//			}
//			rs = pstm.executeQuery();
//			int i = 0;
//			// result.put("sqlCount", rs.getRow());
//			while (rs.next()) {
//				result.put("sql" + i, rs.getString(5));
//				i++;
//			}
//			result.put("sqlCount", i);
//		} finally {
//			try {
//				if (rs != null)
//					rs.close();
//				if (pstm != null)
//					pstm.close();
//			} catch(Exception e) {
//			}
//		}
//		return result;
//	}
//
//	/**
//	 * 获取指定模块No、数据集No的SQL
//	 * 
//	 * @param moduleNo
//	 *            指定模块No，查询sys_module.no字段
//	 * @param dataSetNo
//	 *            指定数据集编号，查询sys_dataset.no字段
//	 * @return 符合条件的数据集SQL Map，返回sys_dataset.sql字段<br/>
//	 *         Map中包含sqlCount为符合条件的SQL数，SQL按sql0、sql1、sql2、...方式返回
//	 * @throws Exception
//	 */
//	public ParaMap getDataSetSQLByModuleNo(String moduleNo, String dataSetNo) throws Exception {
//		ParaMap result = new ParaMap();
//		PreparedStatement pstm = null;
//		ResultSet rs = null;
//		try {
//			pstm = getCon()
//				.prepareStatement(
//						"select id, no, name, module_id, sql, is_valid from sys_dataset where module_id in (select id from sys_module where no = ?) and no = ?");
//			pstm.setString(1, moduleNo);
//			pstm.setString(2, dataSetNo);
//			rs = pstm.executeQuery();
//			int i = 0;
//			// result.put("sqlCount", rs.getRow());
//			while (rs.next()) {
//				result.put("sql" + i, rs.getString(5));
//				i++;
//			}
//			result.put("sqlCount", i);
//		} finally {
//			try {
//				if (rs != null)
//					rs.close();
//				if (pstm != null)
//					pstm.close();
//			} catch(Exception e) {
//			}
//		}
//		return result;
//	}
//
//	/**
//	 * 获取指定数据集Id的SQL
//	 * 
//	 * @param dataSetId
//	 *            指定数据集Id，查询sys_dataset.id字段
//	 * @return 符合条件的数据集SQL Map，返回sys_dataset.sql字段<br/>
//	 *         Map中包含sqlCount为符合条件的SQL数，SQL按sql0、sql1、sql2、...方式返回
//	 * @throws Exception
//	 */
//	public ParaMap getDataSetSQLById(String dataSetId) throws Exception {
//		ParaMap result = new ParaMap();
//		PreparedStatement pstm = null;
//		ResultSet rs = null;
//		try {
//			pstm = getCon().prepareStatement("select id, no, name, module_id, sql, is_valid from sys_dataset where id = ?");
//			pstm.setString(1, dataSetId);
//			rs = pstm.executeQuery();
//			if (rs.next()) {
//				result.put("sql0", rs.getString(5));
//				result.put("sqlCount", 1);
//			} else
//				result.put("sqlCount", 0);
//		} finally {
//			try {
//				if (rs != null)
//					rs.close();
//				if (pstm != null)
//					pstm.close();
//			} catch(Exception e) {
//			}
//		}
//		return result;
//	}
//
//	/**
//	 * 获取指定数据集的SQL
//	 * 
//	 * @param inMap
//	 *            数据集的条件MAP，可包含moduleId、dataSetNo、dataSetId等键值
//	 * @return 按inMap中的参数自动调用其它重载的getDataSetSQL方法并返回SQL结果
//	 * @throws Exception
//	 */
//	public ParaMap getDataSetSQL(ParaMap inMap) throws Exception {
//		ParaMap result = null;
//		if (inMap.containsKey("moduleId") && inMap.containsKey("dataSetNo"))
//			result = getDataSetSQL(inMap.getString("moduleId"), inMap.getString("dataSetNo"));
//		else if (inMap.containsKey("moduleNo") && inMap.containsKey("dataSetNo"))
//			result = getDataSetSQLByModuleNo(inMap.getString("moduleNo"), inMap.getString("dataSetNo"));
//		else if (inMap.containsKey("dataSetId"))
//			result = getDataSetSQLById(inMap.getString("dataSetId"));
//		return result;
//	}
//
//	/**
//	 * 查询指定数据集(按模块Id、数据集No)的所有或分页结果集
//	 * 
//	 * @param moduleId
//	 *            指定模块ID，查询sys_dataset.module_id字段
//	 * @param dataSetNo
//	 *            指定数据集编号，查询sys_dataset.no字段
//	 * @param sqlParams
//	 *            SQL中的参数值MAP，替换参数请查看convertSQLParams等方法。可包含SQL_ORDER_BY、
//	 *            SQL_PAGE_INDEX等特殊键（请查看相关注释）
//	 * @return 
//	 *         返回结果集MAP，state参数为1表示查询成功，0为查询失败；message参数在失败时写入错误原因。数据集相关请查看ParaMap说明
//	 *         MAP中还包含：<br/>
//	 *         totalRowCount: 总记录数<br/>
//	 *         rowCount: 当前结果集返回的记录数<br/>
//	 *         pageIndex: 页码<br/>
//	 *         pageRowCount: 每页记录数<br/>
//	 *         orderBy: 排序字段<br/>
//	 * @throws Exception
//	 */
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
//
//	/**
//	 * 查询指定数据集(按模块No、数据集No)的所有或分页结果集
//	 * 
//	 * @param moduleNo
//	 *            指定模块编号，查询sys_module.no字段
//	 * @param dataSetNo
//	 *            指定数据集编号，查询sys_dataset.no字段
//	 * @param sqlParams
//	 *            SQL中的参数值MAP，替换参数请查看convertSQLParams等方法。可包含SQL_ORDER_BY、
//	 *            SQL_PAGE_INDEX等特殊键（请查看相关注释）
//	 * @return 
//	 *         返回结果集MAP，state参数为1表示查询成功，0为查询失败；message参数在失败时写入错误原因。数据集相关请查看ParaMap说明
//	 *         MAP中还包含：<br/>
//	 *         totalRowCount: 总记录数<br/>
//	 *         rowCount: 当前结果集返回的记录数<br/>
//	 *         pageIndex: 页码<br/>
//	 *         pageRowCount: 每页记录数<br/>
//	 *         orderBy: 排序字段<br/>
//	 * @throws Exception
//	 */
//	public ParaMap queryDataByModuleNo(String moduleNo, String dataSetNo, ParaMap sqlParams) throws Exception {
//		ParaMap sqls = getDataSetSQLByModuleNo(moduleNo, dataSetNo);
//		if (sqls.getInt("sqlCount") > 0)
//			return queryData(sqls.getString("sql0"), sqlParams);
//		else {
//			ParaMap result = new ParaMap();
//			result.put("state", 0);
//			result.put("message", "未定义指定的数据集，无法继续查询数据操作，请检查相关配置");
//			return result;
//		}
//	}
//
//	/**
//	 * 查询指定数据集(按数据集Id)的所有或分页结果集
//	 * 
//	 * @param dataSetId
//	 *            指定数据集Id，查询sys_dataset.id字段
//	 * @param sqlParams
//	 *            SQL中的参数值MAP，替换参数请查看convertSQLParams等方法。可包含SQL_ORDER_BY、
//	 *            SQL_PAGE_INDEX等特殊键（请查看相关注释）
//	 * @return 
//	 *         返回结果集MAP，state参数为1表示查询成功，0为查询失败；message参数在失败时写入错误原因。数据集相关请查看ParaMap说明
//	 *         MAP中还包含：<br/>
//	 *         totalRowCount: 总记录数<br/>
//	 *         rowCount: 当前结果集返回的记录数<br/>
//	 *         pageIndex: 页码<br/>
//	 *         pageRowCount: 每页记录数<br/>
//	 *         orderBy: 排序字段<br/>
//	 * @throws Exception
//	 */
//	public ParaMap queryDataById(String dataSetId, ParaMap sqlParams) throws Exception {
//		ParaMap sqls = getDataSetSQLById(dataSetId);
//		if (sqls.getInt("sqlCount") > 0)
//			return queryData(sqls.getString("sql0"), sqlParams);
//		else {
//			ParaMap result = new ParaMap();
//			result.put("state", 0);
//			result.put("message", "未定义指定的数据集，无法继续查询数据操作，请检查相关配置");
//			return result;
//		}
//	}
//
//	/**
//	 * 查询指定SQL的所有或分页结果集
//	 * 
//	 * @param sql
//	 *            指定查询数据的SQL
//	 * @param sqlParams
//	 *            SQL中的参数值MAP，替换参数请查看convertSQLParams等方法。可包含SQL_ORDER_BY、
//	 *            SQL_PAGE_INDEX等特殊键（请查看相关注释）
//	 * @return 
//	 *         返回结果集MAP，state参数为1表示查询成功，0为查询失败；message参数在失败时写入错误原因。数据集相关请查看ParaMap说明
//	 * <br/>
//	 *         MAP中还包含：<br/>
//	 *         totalRowCount: 总记录数<br/>
//	 *         rowCount: 当前结果集返回的记录数<br/>
//	 *         pageIndex: 页码<br/>
//	 *         pageRowCount: 每页记录数<br/>
//	 *         orderBy: 排序字段<br/>
//	 * @throws Exception
//	 */
//	public ParaMap queryData(String sql, ParaMap sqlParams) throws Exception {
//		ParaMap result = null;
//		if (StrUtils.isNull(sql)) {
//			result = new ParaMap();
//			result.put("state", 0);
//			result.put("message", "未指定SQL，无法继续查询数据操作");
//			return result;
//		}
//		ParaMap queryParams = sqlParams;
//		if (queryParams == null)
//			queryParams = new ParaMap();
//		// 分页查询必须单独传排序参数，参数名为“_orderBy_”，值格式：no desc, name, turn
//		String sqlOrderBy = null;
//		if (queryParams.containsKey(SQL_ORDER_BY))
//			sqlOrderBy = queryParams.getString(SQL_ORDER_BY);
//		// 分页返回的页索引数，无此参数或者值小于或等于0则不分页
//		int intPageIndex = 0;
//		if (queryParams.containsKey(SQL_PAGE_INDEX)) {
//			String strPageIndex = queryParams.getString(SQL_PAGE_INDEX);
//			if (strPageIndex != null && !strPageIndex.equals("") && !strPageIndex.equalsIgnoreCase("null")
//					&& !strPageIndex.equalsIgnoreCase("undefined"))
//				intPageIndex = Integer.parseInt(strPageIndex);
//		}
//		// 分页返回的每页记录数，无此参数或者值小于或等于0则不分页
//		int intPageRowCount = 0;
//		if (queryParams.containsKey(SQL_PAGE_ROW_COUNT)) {
//			String strPageRowCount = queryParams.getString(SQL_PAGE_ROW_COUNT);
//			if (strPageRowCount != null && !strPageRowCount.equals("") && !strPageRowCount.equalsIgnoreCase("null")
//					&& !strPageRowCount.equalsIgnoreCase("undefined"))
//				intPageRowCount = Integer.parseInt(strPageRowCount);
//		}
//		boolean blnQuoted = false;
//		if (queryParams.containsKey(SQL_PARAMS_QUOTED))
//			blnQuoted = queryParams.getInt(SQL_PARAMS_QUOTED) == 1;
//		PreparedStatement pstm = null;
//		PreparedStatement pstm1 = null;
//		Statement stm = null;
//		Statement stm1 = null;
//		ResultSet rs = null;
//		ResultSet rs1 = null;
//		String convertSQL = null;
//		String beforePageSQL = null;
//		try {
//			if (!queryParams.containsKey(SQL_PARAMS_BY_POS) || queryParams.getInt(SQL_PARAMS_BY_POS) == 1) {// 按占位符方式，避免SQL注入
//				Map<Integer, String> sqlParamsPos = new HashMap<Integer, String>();
//				if (queryParams.containsKey(SQL_CUSTOM_CONDITIONS)) {
//					beforePageSQL = convertSQLParams(convertCustomConditions(sql, (ParaMap) queryParams.get(SQL_CUSTOM_CONDITIONS)), sqlParamsPos);
//					convertSQL = convertSQLPageQuery(beforePageSQL, sqlOrderBy, intPageIndex, intPageRowCount);
//				} else {
//					beforePageSQL = convertSQLParams(sql, sqlParamsPos);
//					convertSQL = convertSQLPageQuery(beforePageSQL, sqlOrderBy, intPageIndex, intPageRowCount);
//				}
//				log.debug("Query SQL = " + convertSQL);
//				log.debug("SQL Params = " + queryParams);
//				log.debug("SQL Params Pos = " + sqlParamsPos);
//				// 忽略大小写的参数列表
//				ParaMap ignoreCaseMap = new ParaMap();
//				Iterator it = queryParams.keySet().iterator();
//				while (it.hasNext()) {
//					String key = it.next().toString();
//					ignoreCaseMap.put(key.toUpperCase(), queryParams.get(key));
//				}
//				pstm = getCon().prepareStatement(convertSQL);
//				if (sqlParamsPos.size() > 0) {
//					for (int i = 0; i < sqlParamsPos.size(); i++) {
//						Object paramValue = ignoreCaseMap.get(sqlParamsPos.get(i).toUpperCase());
//						String strParamValue = String.valueOf(paramValue);
//						if (StrUtils.isNull(strParamValue))
//							pstm.setObject(i + 1, "");// 仅使SQL不出错，但一般不是调用处希望的结果，如果该参数不接受null值则不影响结果集
//						else
//							pstm.setObject(i + 1, paramValue);
//					}
//				}
//				rs = pstm.executeQuery();
//				result = convert(rs, isQueryDataReturnFieldMeta());
//				// 返回分页总记录数
//				if (intPageIndex > 0 && intPageRowCount > 0) {
//					String totalRowCountSQL = "select count(*) as total_row_count from (" + beforePageSQL + ")";
//					pstm1 = getCon().prepareStatement(totalRowCountSQL);
//					if (sqlParamsPos.size() > 0) {
//						for (int i = 0; i < sqlParamsPos.size(); i++) {
//							Object paramValue = ignoreCaseMap.get(sqlParamsPos.get(i).toUpperCase());
//							String strParamValue = String.valueOf(paramValue);
//							if (StrUtils.isNull(strParamValue))
//								pstm1.setObject(i + 1, "");
//							else
//								pstm1.setObject(i + 1, paramValue);
//						}
//					}
//					rs1 = pstm1.executeQuery();
//					rs1.next();
//					int intTotalRowCount = rs1.getInt(1);
//					int rowCount = result.getRecords().size();
//					result.put("totalRowCount", intTotalRowCount);
//					result.put("rowCount", rowCount);
//					result.put("pageIndex", intPageIndex);
//					result.put("pageRowCount", intPageRowCount);
//					result.put("pageCount", Math.round(Math.ceil(intTotalRowCount / (intPageRowCount + 0.0))));
//					result.put("orderBy", sqlOrderBy);
//				} else {
//					int rowCount = result.getRecords().size();
//					result.put("totalRowCount", rowCount);
//					result.put("rowCount", rowCount);
//				}
//				log.debug("Query data row count = " + result.getInt("totalRowCount"));
//			} else { // 普通参数方式，可以通过SQL_PARAMS_QUOTED参数一定程度上避免SQL注入
//				if (queryParams.containsKey(SQL_CUSTOM_CONDITIONS)) {
//					beforePageSQL = convertSQLParams(convertCustomConditions(sql, (ParaMap) queryParams.get(SQL_CUSTOM_CONDITIONS)), queryParams,
//							blnQuoted);
//					convertSQL = convertSQLPageQuery(beforePageSQL, sqlOrderBy, intPageIndex, intPageRowCount);
//				} else {
//					beforePageSQL = convertSQLParams(sql, queryParams, blnQuoted);
//					convertSQL = convertSQLPageQuery(beforePageSQL, sqlOrderBy, intPageIndex, intPageRowCount);
//				}
//				log.debug("Query SQL = " + convertSQL);
//				stm = getCon().createStatement();
//				rs = stm.executeQuery(convertSQL);
//				result = convert(rs, isQueryDataReturnFieldMeta());
//				// 返回分页总记录数
//				if (intPageIndex > 0 && intPageRowCount > 0) {
//					String totalRowCountSQL = "select count(*) as total_row_count from (" + beforePageSQL + ")";
//					stm1 = getCon().createStatement();
//					rs1 = stm1.executeQuery(totalRowCountSQL);
//					rs1.next();
//					int intTotalRowCount = rs1.getInt(1);
//					int rowCount = result.getRecords().size();
//					result.put("totalRowCount", intTotalRowCount);
//					result.put("rowCount", rowCount);
//					result.put("pageIndex", intPageIndex);
//					result.put("pageRowCount", intPageRowCount);
//					result.put("pageCount", Math.round(Math.ceil(intTotalRowCount / (intPageRowCount + 0.0))));
//					result.put("orderBy", sqlOrderBy);
//				} else {
//					int rowCount = result.getRecords().size();
//					result.put("totalRowCount", rowCount);
//					result.put("rowCount", rowCount);
//				}
//				log.debug("Query data row count = " + result.getInt("totalRowCount"));
//			}
//			result.put("state", 1);
//		} catch (SQLException e) {
//			result = new ParaMap();
//			result.put("state", 0);
//			result.put("message", e.getMessage());
//			log.error("Query data error: " + e.getMessage());
//			throw e;
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (pstm != null) {
//					pstm.close();
//				}
//				if (rs1 != null) {
//					rs1.close();
//				}
//				if (pstm1 != null) {
//					pstm1.close();
//				}
//				if (stm != null) {
//					stm.close();
//				}
//				if (stm1 != null) {
//					stm1.close();
//				}
//			} catch (SQLException e) {
//				log.error("Close query data resource error: " + e.getMessage());
//				throw e;
//			}
//		}
//		return result;
//	}
//
//	/**
//	 * 查询指定数据集的所有或分页结果集，查询数据应该以本方法为主
//	 * 
//	 * @param inMap
//	 *            指定需查询的数据集的相关条件，如sql、moduleId、datasetId、datasetNo等。
//	 *            同时包含数据集SQL参数键值对
//	 * @return 
//	 *         返回结果集MAP，state参数为1表示查询成功，0为查询失败；message参数在失败时写入错误原因。数据集相关请查看ParaMap说明
//	 *         MAP中还包含：<br/>
//	 *         totalRowCount: 总记录数<br/>
//	 *         rowCount: 当前结果集返回的记录数<br/>
//	 *         pageIndex: 页码<br/>
//	 *         pageRowCount: 每页记录数<br/>
//	 *         orderBy: 排序字段<br/>
//	 * @throws Exception
//	 */
//	public ParaMap queryData(ParaMap inMap) throws Exception {
//		ParaMap result = null;
//		boolean blnOperated = false;
//		if (!blnOperated && inMap.containsKey("moduleNo") && inMap.containsKey("dataSetNo")) {
//			String moduleNo = inMap.getString("moduleNo");
//			String dataSetNo = inMap.getString("dataSetNo");
//			if (StrUtils.isNotNull(moduleNo) && StrUtils.isNotNull(dataSetNo)) {
//				result = queryDataByModuleNo(moduleNo, dataSetNo, inMap);
//				blnOperated = true;
//			}
//		}
//		if (!blnOperated && inMap.containsKey("moduleId") && inMap.containsKey("dataSetNo")) {
//			String moduleId = inMap.getString("moduleId");
//			String dataSetNo = inMap.getString("dataSetNo");
//			if (StrUtils.isNotNull(moduleId) && StrUtils.isNotNull(dataSetNo)) {
//				result = queryData(moduleId, dataSetNo, inMap);
//				blnOperated = true;
//			}
//		}
//		if (!blnOperated && inMap.containsKey("dataSetId")) {
//			String dataSetId = inMap.getString("dataSetId");
//			if (StrUtils.isNotNull(dataSetId)) {
//				result = queryDataById(dataSetId, inMap);
//				blnOperated = true;
//			}
//		}
//		if (!blnOperated && inMap.containsKey("sql")) {
//			String sql = inMap.getString("sql");
//			if (StrUtils.isNotNull(sql)) {
//				result = queryData(sql, inMap);
//				blnOperated = true;
//			}
//		}
//		if (!blnOperated) {
//			result = new ParaMap();
//			result.put("state", 0);
//			result.put("message", "未指定获取有效数据集或者SQL的参数，必须传入moduleId、dataSetNo或者moduleNo、dataSetNo或者dataSetId或者sql等参数");
//		}
//		return result;
//	}
//
//	/**
//	 * 简单查询数据，主要用于编辑数据时查询单条记录无需定义数据集
//	 * 
//	 * @param tableName
//	 *            查询数据的表名
//	 * @param keyData
//	 *            查询条件键值对
//	 * @param orderBy
//	 *            排序字段，格式如：abc desc, efg, xyz asc
//	 * @param returnFields
//	 *            返回结果集的字段列表
//	 * @return 返回查询结果，通过state=1表示更新成功，否则请检查message参数 MAP中还包含：<br/>
//	 *         totalRowCount: 总记录数<br/>
//	 *         rowCount: 当前结果集返回的记录数，实际同totalRowCount。主要是包含queryData返回值基本一致<br/>
//	 * @throws Exception
//	 */
//	public ParaMap querySimpleData(String tableName, ParaMap keyData, String orderBy, String returnFields) throws Exception {
//		ParaMap result = new ParaMap();
//		if (StrUtils.isNull(tableName)) {
//			result.put("state", 0);
//			result.put("message", "查询数据必须传入目标表名");
//			return result;
//		}
//		// if (keyData == null || keyData.size() == 0) {
//		// result.put("state", 0);
//		// result.put("message", "查询数据必须传入目标表" + tableName + "的主键字段名及值列表信息");
//		// return result;
//		// }
//		PreparedStatement pstm = null;
//		ResultSet rs = null;
//		Map<Integer, Object> sqlParamsPos = new HashMap<Integer, Object>();
//		int intParamPos = 0;
//		try {
//			StringBuffer sql = new StringBuffer("select ");
//			if (StrUtils.isNotNull(returnFields))
//				sql.append(returnFields);
//			else
//				sql.append("*");
//			sql.append(" from " + tableName + " where 1 = 1");
//			if (keyData != null && keyData.size() > 0) {
//				Iterator it = keyData.keySet().iterator();
//				while (it.hasNext()) {
//					String key = it.next().toString();
//					Object value = keyData.get(key);
//					String strFieldName = key;
//					if (StrUtils.isNotNull(strFieldName)) {
//						boolean blnExclude = false;
//						boolean blnLookup = false;
//						if (strFieldName.substring(0, 1).equals("!")) {
//							strFieldName = strFieldName.substring(1);
//							blnExclude = true;
//						}
//						if (strFieldName.substring(0, 1).equals("@")) {
//							strFieldName = strFieldName.substring(1);
//							blnLookup = true;
//						}
//						if (value == null) {
//							if (blnExclude)
//								sql.append(" and " + strFieldName + " is not null ");
//							else
//								sql.append(" and " + strFieldName + " is null ");
//						} else if (value instanceof List) {
//							// 值列表，sql转换如: f in (1, 2, 4)
//							List list = (List) value;
//							if (list.size() > 0) {
//								if (blnExclude)
//									sql.append(" and " + strFieldName + " not in (");
//								else
//									sql.append(" and " + strFieldName + " in (");
//								for (int i = 0; i < list.size(); i++) {
//									sql.append("?,");
//									sqlParamsPos.put(intParamPos, list.get(i));
//									intParamPos++;
//								}
//								sql.deleteCharAt(sql.length() - 1);
//								sql.append(")");
//							}
//						} else if (value instanceof Map || blnLookup) {
//							// 简单子查询过滤
//							ParaMap valueMap = blnLookup ? new ParaMap(StrUtils.getParams(String.valueOf(value))) : new ParaMap((Map) value);
//							String refTableName = valueMap.getString(SQL_LOOKUP_TABLE_NAME);
//							String refKeyFieldName = valueMap.getString(SQL_LOOKUP_KEY_FIELD_NAME);
//							if (StrUtils.isNotNull(refTableName) && StrUtils.isNotNull(refKeyFieldName)) {// &&
//																											// valueMap.size()
//																											// >
//																											// 2
//								valueMap.remove(SQL_LOOKUP_TABLE_NAME);
//								valueMap.remove(SQL_LOOKUP_KEY_FIELD_NAME);
//								sql.append(" and " + strFieldName + " in (select " + refKeyFieldName + " from " + refTableName + " where 1 = 1 ");
//								Iterator refIt = valueMap.keySet().iterator();
//								while (refIt.hasNext()) {
//									String refKey = refIt.next().toString();
//									Object refValue = valueMap.get(refKey);
//									boolean blnRefExclude = false;
//									if (refKey.substring(0, 1).equals("!")) {
//										refKey = refKey.substring(1);
//										blnRefExclude = true;
//									}
//									if (refValue == null) {
//										if (blnRefExclude)
//											sql.append(" and " + refKey + " is not null ");
//										else
//											sql.append(" and " + refKey + " is null ");
//									} else if (refValue instanceof List) {
//										List refList = (List) refValue;
//										if (refList.size() > 0) {
//											if (blnRefExclude)
//												sql.append(" and " + refKey + " not in (");
//											else
//												sql.append(" and " + refKey + " in (");
//											for (int i = 0; i < refList.size(); i++) {
//												sql.append("?,");
//												sqlParamsPos.put(intParamPos, refList.get(i));
//												intParamPos++;
//											}
//											sql.deleteCharAt(sql.length() - 1);
//											sql.append(")");
//										}
//									} else {
//										if (blnRefExclude)
//											sql.append(" and " + refKey + " <> ? ");
//										else
//											sql.append(" and " + refKey + " = ? ");
//										sqlParamsPos.put(intParamPos, refValue);
//										intParamPos++;
//									}
//								}
//								sql.append(")");
//							}
//						} else {
//							if (blnExclude)
//								sql.append(" and " + strFieldName + " <> ? ");
//							else
//								sql.append(" and " + strFieldName + " = ? ");
//							sqlParamsPos.put(intParamPos, value);
//							intParamPos++;
//						}
//					}
//				}
//			}
//			if (!StrUtils.isNull(orderBy)) {
//				sql.append(" order by " + orderBy);
//			}
//			pstm = getCon().prepareStatement(sql.toString());
//			int intParamCount = 1;
//			for (int i = 0; i < sqlParamsPos.size(); i++) {
//				Object paramValue = sqlParamsPos.get(i);
//				String strParamValue = String.valueOf(paramValue);
//				if (StrUtils.isNull(strParamValue))
//					pstm.setObject(intParamCount, "");// 仅使SQL不出错，但一般不是调用处希望的结果，如果该参数不接受null值则不影响结果集
//				else
//					pstm.setObject(intParamCount, paramValue);
//				intParamCount++;
//			}
//			rs = pstm.executeQuery();
//			result = convert(rs, isQueryDataReturnFieldMeta());
//			int rowCount = result.getRecords().size();
//			result.put("totalRowCount", rowCount);
//			result.put("rowCount", rowCount);
//			result.put("state", 1);
//		} catch (SQLException e) {
//			result = new ParaMap();
//			result.put("state", 0);
//			result.put("message", e.getMessage());
//			log.error("Query simple data error: " + e.getMessage());
//			throw e;
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (pstm != null) {
//					pstm.close();
//				}
//			} catch (SQLException e) {
//				log.error("Close query simple data resource error: " + e.getMessage());
//				throw e;
//			}
//		}
//		return result;
//	}
//
//	/**
//	 * 简单查询数据，主要用于编辑数据时查询单条记录无需定义数据集
//	 * 
//	 * @param tableName
//	 *            查询数据的表名
//	 * @param keyData
//	 *            查询条件键值对
//	 * @param orderBy
//	 *            排序字段，格式如：abc desc, efg, xyz asc
//	 * @return 返回查询结果，通过state=1表示更新成功，否则请检查message参数 MAP中还包含：<br/>
//	 *         totalRowCount: 总记录数<br/>
//	 *         rowCount: 当前结果集返回的记录数，实际同totalRowCount。主要是包含queryData返回值基本一致<br/>
//	 * @throws Exception
//	 */
//	public ParaMap querySimpleData(String tableName, ParaMap keyData, String orderBy) throws Exception {
//		return querySimpleData(tableName, keyData, orderBy, null);
//	}
//
//	/**
//	 * 简单查询数据，主要用于编辑数据时查询单条记录无需定义数据集
//	 * 
//	 * @param tableName
//	 *            查询数据的表名
//	 * @param keyData
//	 *            查询条件键值对
//	 * @return 返回查询结果，通过state=1表示更新成功，否则请检查message参数 MAP中还包含：<br/>
//	 *         totalRowCount: 总记录数<br/>
//	 *         rowCount: 当前结果集返回的记录数，实际同totalRowCount。主要是包含queryData返回值基本一致<br/>
//	 * @throws Exception
//	 */
//	public ParaMap querySimpleData(String tableName, ParaMap keyData) throws Exception {
//		return querySimpleData(tableName, keyData, null);
//	}
//
//	/**
//	 * 简单查询数据（重载方法）
//	 * 
//	 * @param tableName
//	 *            查询数据的表名
//	 * @param keyField
//	 *            查询条件字段，多个字段请使用用半角逗号或者分号分隔。如“id”、“department_id,emp_id”
//	 * @param data
//	 *            字段键值对，如：<br/>
//	 *            id: 1234567890<br/>
//	 *            no: abcdefg<br/>
//	 *            name: xxxxxx<br/>
//	 *            主键字段也需包含
//	 * @param orderBy
//	 *            排序字段，格式如：abc desc, efg, xyz asc
//	 * @param returnFields
//	 *            返回结果集的字段列表
//	 * @return 返回查询结果，通过state=1表示更新成功，否则请检查message参数
//	 * @throws Exception
//	 */
//	public ParaMap querySimpleData(String tableName, String keyField, ParaMap data, String orderBy, String returnFields) throws Exception {
//		ParaMap keyData = new ParaMap();
//		ParaMap convertData = convertDataFieldName(data, null);
//		List<String> keyFields = new ArrayList<String>();
//		// 提取所有主键字段
//		if (keyField.indexOf(',') == -1 && keyField.indexOf(';') == -1)
//			keyData.put(keyField.toLowerCase(), convertData.get(keyField.toLowerCase()));
//		else {
//			keyFields = convertFields(keyField, null, true);
//			for (int i = 0; i < keyFields.size(); i++) {
//				String fieldName = keyFields.get(i);
//				if (convertData.containsKey(fieldName))
//					keyData.put(fieldName, convertData.get(fieldName));
//				else if (fieldName.startsWith("!") && convertData.containsKey(fieldName.substring(1)))
//					keyData.put(fieldName, convertData.get(fieldName.substring(1)));
//			}
//		}
//		return querySimpleData(tableName, keyData, orderBy, returnFields);
//	}
//
//	/**
//	 * 简单查询数据（重载方法）
//	 * 
//	 * @param tableName
//	 *            查询数据的表名
//	 * @param keyField
//	 *            查询条件字段，多个字段请使用用半角逗号或者分号分隔。如“id”、“department_id,emp_id”
//	 * @param data
//	 *            字段键值对，如：<br/>
//	 *            id: 1234567890<br/>
//	 *            no: abcdefg<br/>
//	 *            name: xxxxxx<br/>
//	 *            主键字段也需包含
//	 * @param orderBy
//	 *            排序字段，格式如：abc desc, efg, xyz asc
//	 * @return 返回查询结果，通过state=1表示更新成功，否则请检查message参数
//	 * @throws Exception
//	 */
//	public ParaMap querySimpleData(String tableName, String keyField, ParaMap data, String orderBy) throws Exception {
//		return querySimpleData(tableName, keyField, data, orderBy, null);
//	}
//
//	/**
//	 * 简单查询数据（重载方法）
//	 * 
//	 * @param tableName
//	 *            查询数据的表名
//	 * @param keyField
//	 *            查询条件字段，多个字段请使用用半角逗号或者分号分隔。如“id”、“department_id,emp_id”
//	 * @param data
//	 *            字段键值对，如：<br/>
//	 *            id: 1234567890<br/>
//	 *            no: abcdefg<br/>
//	 *            name: xxxxxx<br/>
//	 *            主键字段也需包含
//	 * @return 返回查询结果，通过state=1表示更新成功，否则请检查message参数
//	 * @throws Exception
//	 */
//	public ParaMap querySimpleData(String tableName, String keyField, ParaMap data) throws Exception {
//		return querySimpleData(tableName, keyField, data, null);
//	}
//
//	/**
//	 * 简单查询数据（重载方法）
//	 * 
//	 * @param tableName
//	 *            查询数据的表名
//	 * @param keyField
//	 *            查询条件字段，多个字段请使用用半角逗号或者分号分隔。如“id”、“department_id,emp_id”
//	 * @param keyFieldValue
//	 *            查询条件字段值，多个字段请使用用半角逗号或者分号分隔。如“0001”、“0001,0002”
//	 * @param orderBy
//	 *            排序字段，格式如：abc desc, efg, xyz asc
//	 * @param returnFields
//	 *            返回结果集的字段列表
//	 * @return 返回查询结果，通过state=1表示更新成功，否则请检查message参数
//	 * @throws Exception
//	 */
//	public ParaMap querySimpleData(String tableName, String keyField, String keyFieldValue, String orderBy, String returnFields) throws Exception {
//		if (keyField != null) {
//			List<String> fields = keyField.indexOf(";") >= 0 ? StrUtils.getSubStrs(keyField, ";") : StrUtils.getSubStrs(keyField, ",");
//			List<String> fieldValues = keyFieldValue != null ? (keyFieldValue.indexOf(";") >= 0 ? StrUtils.getSubStrs(keyFieldValue, ";") : StrUtils
//					.getSubStrs(keyFieldValue, ",")) : null;
//			ParaMap keyData = new ParaMap();
//			for (int i = 0; i < fields.size(); i++)
//				if (fieldValues != null && fieldValues.size() > i)
//					keyData.put(fields.get(i), fieldValues.get(i));
//				else
//					keyData.put(fields.get(i), null);
//			return querySimpleData(tableName, keyData, orderBy, returnFields);
//		} else
//			return querySimpleData(tableName, (ParaMap) null, orderBy, returnFields);
//	}
//
//	/**
//	 * 简单查询数据（重载方法）
//	 * 
//	 * @param tableName
//	 *            查询数据的表名
//	 * @param keyField
//	 *            查询条件字段，多个字段请使用用半角逗号或者分号分隔。如“id”、“department_id,emp_id”
//	 * @param keyFieldValue
//	 *            查询条件字段值，多个字段请使用用半角逗号或者分号分隔。如“0001”、“0001,0002”
//	 * @param orderBy
//	 *            排序字段，格式如：abc desc, efg, xyz asc
//	 * @return 返回查询结果，通过state=1表示更新成功，否则请检查message参数
//	 * @throws Exception
//	 */
//	public ParaMap querySimpleData(String tableName, String keyField, String keyFieldValue, String orderBy) throws Exception {
//		return querySimpleData(tableName, keyField, keyFieldValue, orderBy, null);
//	}
//
//	/**
//	 * 简单查询数据（重载方法）
//	 * 
//	 * @param tableName
//	 *            查询数据的表名
//	 * @param keyField
//	 *            查询条件字段，多个字段请使用用半角逗号或者分号分隔。如“id”、“department_id,emp_id”
//	 * @param keyFieldValue
//	 *            查询条件字段值，多个字段请使用用半角逗号或者分号分隔。如“0001”、“0001,0002”
//	 * @return 返回查询结果，通过state=1表示更新成功，否则请检查message参数
//	 * @throws Exception
//	 */
//	public ParaMap querySimpleData(String tableName, String keyField, String keyFieldValue) throws Exception {
//		return querySimpleData(tableName, keyField, keyFieldValue, null);
//	}
//
//	/**
//	 * 简单查询数据（重载方法）
//	 * 
//	 * @param tableName
//	 *            查询数据的表名
//	 * @param idFieldValue
//	 *            id字段值
//	 * @param orderBy
//	 *            排序字段，格式如：abc desc, efg, xyz asc
//	 * @param returnFields
//	 *            返回结果集的字段列表
//	 * @return 返回查询结果，通过state=1表示更新成功，否则请检查message参数
//	 * @throws Exception
//	 */
//	public ParaMap querySimpleDataById(String tableName, String idFieldValue, String orderBy, String returnFields) throws Exception {
//		return querySimpleData(tableName, "id", idFieldValue, orderBy, returnFields);
//	}
//
//	/**
//	 * 简单查询数据（重载方法）
//	 * 
//	 * @param tableName
//	 *            查询数据的表名
//	 * @param idFieldValue
//	 *            id字段值
//	 * @param orderBy
//	 *            排序字段，格式如：abc desc, efg, xyz asc
//	 * @return 返回查询结果，通过state=1表示更新成功，否则请检查message参数
//	 * @throws Exception
//	 */
//	public ParaMap querySimpleDataById(String tableName, String idFieldValue, String orderBy) throws Exception {
//		return querySimpleDataById(tableName, idFieldValue, orderBy, null);
//	}
//
//	/**
//	 * 简单查询数据（重载方法）
//	 * 
//	 * @param tableName
//	 *            查询数据的表名
//	 * @param idFieldValue
//	 *            id字段值
//	 * @return 返回查询结果，通过state=1表示更新成功，否则请检查message参数
//	 * @throws Exception
//	 */
//	public ParaMap querySimpleDataById(String tableName, String idFieldValue) throws Exception {
//		return querySimpleDataById(tableName, idFieldValue, null);
//	}
//
//	/**
//	 * 查询指定数据集记录数(按模块Id、数据集No)
//	 * 
//	 * @param moduleId
//	 *            指定模块ID，查询sys_dataset.module_id字段
//	 * @param dataSetNo
//	 *            指定数据集编号，查询sys_dataset.no字段
//	 * @param sqlParams
//	 *            SQL中的参数值MAP，替换参数请查看convertSQLParams等方法
//	 * @return 
//	 *         返回结果集MAP，state参数为1表示查询成功，0为查询失败；message参数在失败时写入错误原因。。从返回值中取rowCount
//	 * @throws Exception
//	 */
//	public ParaMap queryRowCount(String moduleId, String dataSetNo, ParaMap sqlParams) throws Exception {
//		ParaMap sqls = getDataSetSQL(moduleId, dataSetNo);
//		if (sqls.getInt("sqlCount") > 0)
//			return queryRowCount(sqls.getString("sql0"), sqlParams);
//		else {
//			ParaMap result = new ParaMap();
//			result.put("state", 0);
//			result.put("message", "未定义指定的数据集，无法继续查询记录数操作，请检查相关配置");
//			return result;
//		}
//	}
//
//	/**
//	 * 查询指定数据集记录数(按模块No、数据集No)
//	 * 
//	 * @param moduleNo
//	 *            指定模块编号，查询sys_module.no字段
//	 * @param dataSetNo
//	 *            指定数据集编号，查询sys_dataset.no字段
//	 * @param sqlParams
//	 *            SQL中的参数值MAP，替换参数请查看convertSQLParams等方法
//	 * @return 
//	 *         返回结果集MAP，state参数为1表示查询成功，0为查询失败；message参数在失败时写入错误原因。从返回值中取rowCount
//	 * @throws Exception
//	 */
//	public ParaMap queryRowCountByModuleNo(String moduleNo, String dataSetNo, ParaMap sqlParams) throws Exception {
//		ParaMap sqls = getDataSetSQLByModuleNo(moduleNo, dataSetNo);
//		if (sqls.getInt("sqlCount") > 0)
//			return queryRowCount(sqls.getString("sql0"), sqlParams);
//		else {
//			ParaMap result = new ParaMap();
//			result.put("state", 0);
//			result.put("message", "未定义指定的数据集，无法继续查询记录数操作，请检查相关配置");
//			return result;
//		}
//	}
//
//	/**
//	 * 查询指定数据集记录数(按数据集Id)
//	 * 
//	 * @param dataSetId
//	 *            指定数据集Id，查询sys_dataset.id字段
//	 * @param sqlParams
//	 *            SQL中的参数值MAP，替换参数请查看convertSQLParams等方法
//	 * @return 
//	 *         返回结果集MAP，state参数为1表示查询成功，0为查询失败；message参数在失败时写入错误原因。。从返回值中取rowCount
//	 * @throws Exception
//	 */
//	public ParaMap queryRowCountById(String dataSetId, ParaMap sqlParams) throws Exception {
//		ParaMap sqls = getDataSetSQLById(dataSetId);
//		if (sqls.getInt("sqlCount") > 0)
//			return queryRowCount(sqls.getString("sql0"), sqlParams);
//		else {
//			ParaMap result = new ParaMap();
//			result.put("state", 0);
//			result.put("message", "未定义指定的数据集，无法继续查询记录数操作，请检查相关配置");
//			return result;
//		}
//	}
//
//	/**
//	 * 查询SQL记录数，如果调用queryData方法不需要调用本方法，该方法返回值中包含此信息
//	 * 
//	 * @param sql
//	 *            指定查询记录数的SQL
//	 * @param sqlParams
//	 *            SQL中的参数值MAP，替换参数请查看convertSQLParams等方法。可包含SQL_ORDER_BY、
//	 *            SQL_PAGE_INDEX等特殊键（请查看相关注释）
//	 * @return 
//	 *         返回结果集MAP，state参数为1表示查询成功，0为查询失败；message参数在失败时写入错误原因。从返回值中取rowCount
//	 * @throws Exception
//	 */
//	public ParaMap queryRowCount(String sql, ParaMap sqlParams) throws Exception {
//		ParaMap result = null;
//		if (StrUtils.isNull(sql)) {
//			result = new ParaMap();
//			result.put("state", 0);
//			result.put("message", "未指定SQL，无法继续查询记录数操作");
//			return result;
//		}
//		boolean blnQuoted = false;
//		ParaMap queryParams = sqlParams;
//		if (queryParams == null)
//			queryParams = new ParaMap();
//		if (queryParams.containsKey(SQL_PARAMS_QUOTED))
//			blnQuoted = queryParams.getInt(SQL_PARAMS_QUOTED) == 1;
//		PreparedStatement pstm = null;
//		Statement stm = null;
//		ResultSet rs = null;
//		String convertSQL = null;
//		try {
//			if (!queryParams.containsKey(SQL_PARAMS_BY_POS) || queryParams.getInt(SQL_PARAMS_BY_POS) == 1) {// 按占位符方式，避免SQL注入
//				Map<Integer, String> sqlParamsPos = new HashMap<Integer, String>();
//				if (queryParams.containsKey(SQL_CUSTOM_CONDITIONS)) {
//					convertSQL = convertSQLParams(convertCustomConditions(sql, (ParaMap) queryParams.get(SQL_CUSTOM_CONDITIONS)), sqlParamsPos);
//				} else {
//					convertSQL = convertSQLParams(sql, sqlParamsPos);
//				}
//				convertSQL = "select count(*) as total_row_count from (" + convertSQL + ")";
//				ParaMap ignoreCaseMap = new ParaMap();
//				Iterator it = queryParams.keySet().iterator();
//				while (it.hasNext()) {
//					String key = it.next().toString();
//					ignoreCaseMap.put(key.toUpperCase(), queryParams.get(key));
//				}
//				pstm = getCon().prepareStatement(convertSQL);
//				if (sqlParamsPos.size() > 0) {
//					for (int i = 0; i < sqlParamsPos.size(); i++) {
//						Object paramValue = ignoreCaseMap.get(sqlParamsPos.get(i).toUpperCase());
//						String strParamValue = String.valueOf(paramValue);
//						if (StrUtils.isNull(strParamValue))
//							pstm.setObject(i + 1, "");
//						else
//							pstm.setObject(i + 1, paramValue);
//					}
//				}
//				rs = pstm.executeQuery();
//				rs.next();
//				result.put("rowCount", rs.getInt(1));
//			} else { // 普通参数方式，可以通过SQL_PARAMS_QUOTED参数一定程度上避免SQL注入
//				if (queryParams.containsKey(SQL_CUSTOM_CONDITIONS)) {
//					convertSQL = convertSQLParams(convertCustomConditions(sql, (ParaMap) queryParams.get(SQL_CUSTOM_CONDITIONS)), queryParams,
//							blnQuoted);
//				} else {
//					convertSQL = convertSQLParams(sql, queryParams, blnQuoted);
//				}
//				convertSQL = "select count(*) as total_row_count from (" + convertSQL + ")";
//				stm = getCon().createStatement();
//				rs = stm.executeQuery(convertSQL);
//				rs.next();
//				result.put("rowCount", rs.getInt(1));
//			}
//			result.put("state", 1);
//		} catch (SQLException e) {
//			result = new ParaMap();
//			result.put("state", 0);
//			result.put("message", e.getMessage());
//			log.error("Query row count error: " + e.getMessage());
//			throw e;
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (pstm != null) {
//					pstm.close();
//				}
//				if (stm != null) {
//					stm.close();
//				}
//			} catch (SQLException e) {
//				log.error("Close query row count resource error: " + e.getMessage());
//				throw e;
//			}
//		}
//		return result;
//	}
//
//	/**
//	 * 查询指定数据集的记录数，查询记录数应该以本方法为主
//	 * 
//	 * @param inMap
//	 *            指定需查询的数据集的相关条件，如sql、moduleId、datasetId、datasetNo等。
//	 *            同时包含数据集SQL参数键值对
//	 * @return 
//	 *         返回结果集MAP，state参数为1表示查询成功，0为查询失败；message参数在失败时写入错误原因。。从返回值中取rowCount
//	 * @throws Exception
//	 */
//	public ParaMap queryRowCount(ParaMap inMap) throws Exception {
//		ParaMap result = null;
//		boolean blnOperated = false;
//		if (inMap.containsKey("moduleId") && inMap.containsKey("dataSetNo")) {
//			String moduleId = inMap.getString("moduleId");
//			String dataSetNo = inMap.getString("dataSetNo");
//			if (StrUtils.isNotNull(moduleId) && StrUtils.isNotNull(dataSetNo)) {
//				result = queryRowCount(moduleId, dataSetNo, inMap);
//				blnOperated = true;
//			}
//		}
//		if (!blnOperated && inMap.containsKey("moduleNo") && inMap.containsKey("dataSetNo")) {
//			String moduleNo = inMap.getString("moduleNo");
//			String dataSetNo = inMap.getString("dataSetNo");
//			if (StrUtils.isNotNull(moduleNo) && StrUtils.isNotNull(dataSetNo)) {
//				result = queryRowCountByModuleNo(moduleNo, dataSetNo, inMap);
//				blnOperated = true;
//			}
//		}
//		if (inMap.containsKey("dataSetId")) {
//			String dataSetId = inMap.getString("dataSetId");
//			if (StrUtils.isNotNull(dataSetId)) {
//				result = queryRowCountById(dataSetId, inMap);
//				blnOperated = true;
//			}
//		}
//		if (inMap.containsKey("sql")) {
//			String sql = inMap.getString("sql");
//			if (StrUtils.isNotNull(sql)) {
//				result = queryRowCount(sql, inMap);
//				blnOperated = true;
//			}
//		}
//		if (!blnOperated) {
//			result = new ParaMap();
//			result.put("state", 0);
//			result.put("message", "未指定获取有效数据集或者SQL的参数，必须传入moduleId、dataSetNo或者moduleNo、dataSetNo或者dataSetId或者sql等参数");
//		}
//		return result;
//	}
//
//	/**
//	 * 简单查询记录数
//	 * 
//	 * @param tableName
//	 *            查询记录数的表名
//	 * @param keyData
//	 *            查询条件键值对
//	 * @return 返回查询结果，通过state=1表示更新成功，否则请检查message参数。从返回值中取rowCount
//	 * @throws Exception
//	 */
//	public ParaMap querySimpleRowCount(String tableName, ParaMap keyData) throws Exception {
//		ParaMap result = new ParaMap();
//		if (StrUtils.isNull(tableName)) {
//			result.put("state", 0);
//			result.put("message", "查询记录数必须传入目标表名");
//			return result;
//		}
//		if (keyData == null || keyData.size() == 0) {
//			result.put("state", 0);
//			result.put("message", "查询记录数必须传入目标表" + tableName + "的主键字段名及值列表信息");
//			return result;
//		}
//		PreparedStatement pstm = null;
//		ResultSet rs = null;
//		Map<Integer, Object> sqlParamsPos = new HashMap<Integer, Object>();
//		int intParamPos = 0;
//		try {
//			StringBuffer sql = new StringBuffer("select count(*) as total_row_count from " + tableName + " where 1 = 1");
//			Iterator it = keyData.keySet().iterator();
//			while (it.hasNext()) {
//				String key = it.next().toString();
//				Object value = keyData.get(key);
//				String strFieldName = key;
//				if (StrUtils.isNotNull(strFieldName)) {
//					boolean blnExclude = false;
//					boolean blnLookup = false;
//					if (strFieldName.substring(0, 1).equals("!")) {
//						strFieldName = strFieldName.substring(1);
//						blnExclude = true;
//					}
//					if (strFieldName.substring(0, 1).equals("@")) {
//						strFieldName = strFieldName.substring(1);
//						blnLookup = true;
//					}
//					if (value == null) {
//						if (blnExclude)
//							sql.append(" and " + strFieldName + " is not null ");
//						else
//							sql.append(" and " + strFieldName + " is null ");
//					} else if (value instanceof List) {
//						List list = (List) value;
//						if (list.size() > 0) {
//							if (blnExclude)
//								sql.append(" and " + strFieldName + " not in (");
//							else
//								sql.append(" and " + strFieldName + " in (");
//							for (int i = 0; i < list.size(); i++) {
//								sql.append("?,");
//								sqlParamsPos.put(intParamPos, list.get(i));
//								intParamPos++;
//							}
//							sql.deleteCharAt(sql.length() - 1);
//							sql.append(")");
//						}
//					} else if (value instanceof Map || blnLookup) {
//						// 简单子查询过滤
//						ParaMap valueMap = blnLookup ? new ParaMap(StrUtils.getParams(String.valueOf(value))) : new ParaMap((Map) value);
//						String refTableName = valueMap.getString(SQL_LOOKUP_TABLE_NAME);
//						String refKeyFieldName = valueMap.getString(SQL_LOOKUP_KEY_FIELD_NAME);
//						if (StrUtils.isNotNull(refTableName) && StrUtils.isNotNull(refKeyFieldName)) {// &&
//																										// valueMap.size()
//																										// >
//																										// 2
//							valueMap.remove(SQL_LOOKUP_TABLE_NAME);
//							valueMap.remove(SQL_LOOKUP_KEY_FIELD_NAME);
//							sql.append(" and " + strFieldName + " in (select " + refKeyFieldName + " from " + refTableName + " where 1 = 1 ");
//							Iterator refIt = valueMap.keySet().iterator();
//							while (refIt.hasNext()) {
//								String refKey = refIt.next().toString();
//								Object refValue = valueMap.get(refKey);
//								boolean blnRefExclude = false;
//								if (refKey.substring(0, 1).equals("!")) {
//									refKey = refKey.substring(1);
//									blnRefExclude = true;
//								}
//								if (refValue == null) {
//									if (blnRefExclude)
//										sql.append(" and " + refKey + " is not null ");
//									else
//										sql.append(" and " + refKey + " is null ");
//								} else if (refValue instanceof List) {
//									List refList = (List) refValue;
//									if (refList.size() > 0) {
//										if (blnRefExclude)
//											sql.append(" and " + refKey + " not in (");
//										else
//											sql.append(" and " + refKey + " in (");
//										for (int i = 0; i < refList.size(); i++) {
//											sql.append("?,");
//											sqlParamsPos.put(intParamPos, refList.get(i));
//											intParamPos++;
//										}
//										sql.deleteCharAt(sql.length() - 1);
//										sql.append(")");
//									}
//								} else {
//									if (blnRefExclude)
//										sql.append(" and " + refKey + " <> ? ");
//									else
//										sql.append(" and " + refKey + " = ? ");
//									sqlParamsPos.put(intParamPos, refValue);
//									intParamPos++;
//								}
//							}
//							sql.append(")");
//						}
//					} else {
//						if (blnExclude)
//							sql.append(" and " + strFieldName + " <> ? ");
//						else
//							sql.append(" and " + strFieldName + " = ? ");
//						sqlParamsPos.put(intParamPos, value);
//						intParamPos++;
//					}
//				}
//			}
//			pstm = getCon().prepareStatement(sql.toString());
//			int intParamCount = 1;
//			for (int i = 0; i < sqlParamsPos.size(); i++) {
//				Object paramValue = sqlParamsPos.get(i);
//				String strParamValue = String.valueOf(paramValue);
//				if (StrUtils.isNull(strParamValue))
//					pstm.setObject(intParamCount, "");// 仅使SQL不出错，但一般不是调用处希望的结果，如果该参数不接受null值则不影响结果集
//				else
//					pstm.setObject(intParamCount, paramValue);
//				intParamCount++;
//			}
//			rs = pstm.executeQuery();
//			rs.next();
//			result.put("rowCount", rs.getInt(1));
//			result.put("state", 1);
//		} catch (SQLException e) {
//			result = new ParaMap();
//			result.put("state", 0);
//			result.put("message", e.getMessage());
//			log.error("Query simple row count error: " + e.getMessage());
//			throw e;
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (pstm != null) {
//					pstm.close();
//				}
//			} catch (SQLException e) {
//				log.error("Close query simple row count resource error: " + e.getMessage());
//				throw e;
//			}
//		}
//		return result;
//	}
//
//	/**
//	 * 简单查询记录数（重载方法）
//	 * 
//	 * @param tableName
//	 *            查询记录数的表名
//	 * @param keyField
//	 *            查询条件字段，多个字段请使用用半角逗号或者分号分隔。如“id”、“department_id,emp_id”
//	 * @param data
//	 *            字段键值对，如：<br/>
//	 *            id: 1234567890<br/>
//	 *            no: abcdefg<br/>
//	 *            name: xxxxxx<br/>
//	 *            主键字段也需包含
//	 * @return 返回查询结果，通过state=1表示更新成功，否则请检查message参数。从返回值中取rowCount
//	 * @throws Exception
//	 */
//	public ParaMap querySimpleRowCount(String tableName, String keyField, ParaMap data) throws Exception {
//		ParaMap keyData = new ParaMap();
//		ParaMap convertData = convertDataFieldName(data, null);
//		// 提取所有主键字段
//		if (keyField.indexOf(',') == -1 && keyField.indexOf(';') == -1)
//			keyData.put(keyField.toLowerCase(), convertData.get(keyField.toLowerCase()));
//		else {
//			List<String> keyFields = convertFields(keyField, null, true);
//			for (int i = 0; i < keyFields.size(); i++) {
//				String fieldName = keyFields.get(i);
//				if (convertData.containsKey(fieldName))
//					keyData.put(fieldName, convertData.get(fieldName));
//				else if (fieldName.startsWith("!") && convertData.containsKey(fieldName.substring(1)))
//					keyData.put(fieldName, convertData.get(fieldName.substring(1)));
//			}
//		}
//		return querySimpleRowCount(tableName, keyData);
//	}
//	
//	/**
//	 * 简单查询记录数（重载方法）
//	 * @param tableName 表名
//	 * @param keyField 主键字段名称，多个字段时以半角分号或者逗号分隔
//	 * @param keyFieldValue 主键字段值
//	 * @return
//	 * @throws Exception
//	 */
//	public ParaMap querySimpleRowCount(String tableName, String keyField, String keyFieldValue) throws Exception {
//		ParaMap keyData = new ParaMap();
//		if (keyField.indexOf(',') == -1 && keyField.indexOf(';') == -1)
//			keyData.put(keyField.toLowerCase(), keyFieldValue);
//		else {
//			String split = keyField.indexOf(',') != -1 ? "," : ";";
//			List<String> keyFields = StrUtils.getSubStrs(keyField, split);
//			for (int i = 0; i < keyFields.size(); i++) {
//				String fieldName = keyFields.get(i);
//				if (StrUtils.isNotNull(fieldName))
//					keyData.put(fieldName, StrUtils.getSubStr(keyFieldValue, split, i));
//			}
//		}
//		return querySimpleRowCount(tableName, keyData);
//	}
//
//	/**
//	 * 执行指定数据集(按模块Id、数据集No)的SQL
//	 * 
//	 * @param moduleId
//	 *            指定模块Id，查询sys_dataset.module_id字段
//	 * @param dataSetNo
//	 *            指定数据集Id，查询sys_dataset.no字段
//	 * @param sqlParams
//	 *            SQL中的参数值MAP，替换参数请查看convertSQLParams等方法
//	 * @return 返回SQL执行情况，state参数为1表示查询成功，0为查询失败；message参数在失败时写入错误原因
//	 * @throws Exception
//	 */
//	public ParaMap executeSQL(String moduleId, String dataSetNo, ParaMap sqlParams) throws Exception {
//		ParaMap sqls = getDataSetSQL(moduleId, dataSetNo);
//		if (sqls.getInt("sqlCount") > 0)
//			return executeSQL(sqls.getString("sql0"), sqlParams);
//		else {
//			ParaMap result = new ParaMap();
//			result.put("state", 0);
//			result.put("message", "未定义指定的数据集，无法继续执行操作，请检查相关配置");
//			return result;
//		}
//	}
//
//	/**
//	 * 执行指定数据集(按模块No、数据集No)的SQL
//	 * 
//	 * @param moduleNo
//	 *            指定模块Id，查询sys_module.no字段
//	 * @param dataSetNo
//	 *            指定数据集Id，查询sys_dataset.no字段
//	 * @param sqlParams
//	 *            SQL中的参数值MAP，替换参数请查看convertSQLParams等方法
//	 * @return 返回SQL执行情况，state参数为1表示查询成功，0为查询失败；message参数在失败时写入错误原因
//	 * @throws Exception
//	 */
//	public ParaMap executeSQLByModuleNo(String moduleNo, String dataSetNo, ParaMap sqlParams) throws Exception {
//		ParaMap sqls = getDataSetSQLByModuleNo(moduleNo, dataSetNo);
//		if (sqls.getInt("sqlCount") > 0)
//			return executeSQL(sqls.getString("sql0"), sqlParams);
//		else {
//			ParaMap result = new ParaMap();
//			result.put("state", 0);
//			result.put("message", "未定义指定的数据集，无法继续执行操作，请检查相关配置");
//			return result;
//		}
//	}
//
//	/**
//	 * 执行指定数据集(按数据集Id)的SQL
//	 * 
//	 * @param dataSetId
//	 *            指定数据集Id，查询sys_dataset.id字段
//	 * @param sqlParams
//	 *            SQL中的参数值MAP，替换参数请查看convertSQLParams等方法
//	 * @return 返回SQL执行情况，state参数为1表示查询成功，0为查询失败；message参数在失败时写入错误原因
//	 * @throws Exception
//	 */
//	public ParaMap executeSQLById(String dataSetId, ParaMap sqlParams) throws Exception {
//		ParaMap sqls = getDataSetSQLById(dataSetId);
//		if (sqls.getInt("sqlCount") > 0)
//			return executeSQL(sqls.getString("sql0"), sqlParams);
//		else {
//			ParaMap result = new ParaMap();
//			result.put("state", 0);
//			result.put("message", "未定义指定的数据集，无法继续执行操作，请检查相关配置");
//			return result;
//		}
//	}
//
//	/**
//	 * 执行指定SQL
//	 * 
//	 * @param sql
//	 *            需执行的SQL
//	 * @param sqlParams
//	 *            SQL中的参数值MAP，替换参数请查看convertSQLParams等方法
//	 * @return 返回SQL执行情况，state参数为1表示查询成功，0为查询失败；message参数在失败时写入错误原因
//	 * @throws Exception
//	 */
//	public ParaMap executeSQL(String sql, ParaMap sqlParams) throws Exception {
//		ParaMap result = new ParaMap();
//		if (StrUtils.isNull(sql)) {
//			result.put("state", 0);
//			result.put("message", "未指定SQL，无法继续执行操作");
//			return result;
//		}
//		boolean blnQuoted = false;
//		ParaMap queryParams = sqlParams;
//		if (queryParams == null)
//			queryParams = new ParaMap();
//		if (queryParams.containsKey(SQL_PARAMS_QUOTED))
//			blnQuoted = queryParams.getInt(SQL_PARAMS_QUOTED) == 1;
//		int sqlRowCount = -1;
//		PreparedStatement pstm = null;
//		Statement stm = null;
//		try {
//			if (!queryParams.containsKey(SQL_PARAMS_BY_POS) || queryParams.getInt(SQL_PARAMS_BY_POS) == 1) {// 按占位符方式，避免SQL注入
//				Map<Integer, String> sqlParamsPos = new HashMap<Integer, String>();
//				String convertSQL = null;
//				if (queryParams.containsKey(SQL_CUSTOM_CONDITIONS))
//					convertSQL = convertSQLParams(convertCustomConditions(sql, (ParaMap) queryParams.get(SQL_CUSTOM_CONDITIONS)), sqlParamsPos);
//				else
//					convertSQL = convertSQLParams(sql, sqlParamsPos);
//				log.debug("Exec SQL = " + convertSQL);
//				log.debug("SQL Params = " + queryParams);
//				log.debug("SQL Params Pos = " + sqlParamsPos);
//				// 忽略大小写的参数列表
//				ParaMap ignoreCaseMap = new ParaMap();
//				Iterator it = queryParams.keySet().iterator();
//				while (it.hasNext()) {
//					String key = it.next().toString();
//					ignoreCaseMap.put(key.toUpperCase(), queryParams.get(key));
//				}
//				pstm = getCon().prepareStatement(convertSQL);
//				if (sqlParamsPos.size() > 0) {
//					for (int i = 0; i < sqlParamsPos.size(); i++) {
//						Object paramValue = ignoreCaseMap.get(sqlParamsPos.get(i).toUpperCase());
//						String strParamValue = String.valueOf(paramValue);
//						if (StrUtils.isNull(strParamValue))
//							pstm.setObject(i + 1, "");// 仅使SQL不出错，但一般不是调用处希望的结果，如果该参数不接受null值则不影响结果集
//						else
//							pstm.setObject(i + 1, paramValue);
//					}
//				}
//				sqlRowCount = pstm.executeUpdate();
//			} else { // 普通参数方式，可以通过SQL_PARAMS_QUOTED参数一定程度上避免SQL注入
//				String convertSQL = convertSQLParams(sql, queryParams, blnQuoted);
//				stm = getCon().createStatement();
//				sqlRowCount = stm.executeUpdate(convertSQL);
//			}
//			// getCon().commit();
//			result.put("state", 1);
//			result.put("sqlRowCount", sqlRowCount);
//		} catch (SQLException e) {
//			result = new ParaMap();
//			result.put("state", 0);
//			result.put("message", e.getMessage());
//			log.error("Execute sql error: " + e.getMessage());
//			throw e;
//		} finally {
//			try {
//				if (pstm != null) {
//					pstm.close();
//				}
//				if (stm != null) {
//					stm.close();
//				}
//			} catch (SQLException e) {
//				log.error("Close execute sql resource error: " + e.getMessage());
//				throw e;
//			}
//		}
//		return result;
//	}
//
//	/**
//	 * 执行指定SQL
//	 * 
//	 * @param inMap
//	 *            指定需执行的数据集的相关条件，如sql、moduleId、datasetId、datasetNo等。
//	 *            同时包含数据集SQL参数键值对
//	 * @return 返回SQL执行情况，state参数为1表示查询成功，0为查询失败；message参数在失败时写入错误原因
//	 * @throws Exception
//	 */
//	public ParaMap executeSQL(ParaMap inMap) throws Exception {
//		ParaMap result = null;
//		if (inMap.containsKey("moduleId") && inMap.containsKey("dataSetNo"))
//			result = executeSQL(inMap.getString("moduleId"), inMap.getString("dataSetNo"), inMap);
//		else if (inMap.containsKey("moduleNo") && inMap.containsKey("dataSetNo"))
//			result = executeSQLByModuleNo(inMap.getString("moduleNo"), inMap.getString("dataSetNo"), inMap);
//		else if (inMap.containsKey("dataSetId"))
//			result = executeSQLById(inMap.getString("dataSetId"), inMap);
//		else if (inMap.containsKey("sql"))
//			result = executeSQL(inMap.getString("sql"), inMap);
//		return result;
//	}
//
//	/**
//	 * 更新数据
//	 * 
//	 * @param tableName
//	 *            更新数据的表名
//	 * @param keyField
//	 *            更新数据表的主键字段，多个主键字段请使用用半角逗号或者分号分隔，如“id”、“department_id,emp_id”
//	 * @param data
//	 *            更新的数据内容，如：<br/>
//	 *            id: 1234567890<br/>
//	 *            no: abcdefg<br/>
//	 *            name: xxxxxx<br/>
//	 *            主键字段也需包含，单主键字段为空值将自动新增数据，并且自动获取主键字段值。
//	 * @param format
//	 *            data中有特殊格式的字段值则需要在此map中添加格式转换说明，如：<br/>
//	 *            create_date: to_date(?, 'yyyy-mm-dd hh24:mi:ss')
//	 * @param checkTableFields
//	 *            如果data中有键值非tableName表中的字段，请将本参数设置为true
//	 * @param ignoreNullValue
//	 *            true表示忽略data中的null值，不会被组织到更新SQL语句中
//	 * @return 返回更新结果，通过state=1表示更新成功，否则请检查message参数
//	 * @throws Exception
//	 */
//	public ParaMap updateData(String tableName, String keyField, ParaMap data, ParaMap format, boolean checkTableFields, boolean ignoreNullValue)
//			throws Exception {
//		ParaMap result = new ParaMap();
//		if (StrUtils.isNull(tableName)) {
//			result.put("state", 0);
//			result.put("message", "更新数据必须传入目标表名");
//			return result;
//		}
//		if (StrUtils.isNull(keyField)) {
//			result.put("state", 0);
//			result.put("message", "更新数据必须传入目标表" + tableName + "的主键字段名，如果无主键字段则无法通过此方法更新");
//			return result;
//		}
//		ParaMap convertData = checkTableFields ? convertDataFieldName(data, tableName) : convertDataFieldName(data, null);
//		ParaMap convertFormat = convertDataFieldName(format, null);
//		PreparedStatement pstm = null;
//		Map<Integer, Object> sqlParamsPos = new HashMap<Integer, Object>();
//		List<String> keyFields = new ArrayList<String>();
//		try {
//			// 提取所有主键字段
//			if (keyField.indexOf(',') == -1 && keyField.indexOf(';') == -1)
//				keyFields.add(keyField);
//			else {
//				keyFields = convertFields(keyField, null);
//			}
//			// 检查主键字段是否都在更新字段列表中
//			for (int i = 0; i < keyFields.size(); i++) {
//				String keyFieldName = keyFields.get(i);
//				if (!convertData.containsKey(keyFieldName))
//					convertData.put(keyFieldName, null);
//			}
//			if (keyFields.size() == convertData.size()) {
//				// 如果仅包含主键字段则直接返回更新成功
//				result.put("state", 1);
//				result.put("message", "只有主键字段不需要更新");
//				return result;
//			}
//			String keyFieldValue = convertData.containsKey(keyField) ? String.valueOf(convertData.get(keyField)) : null;
//			int intParamPos = 0;
//			if (keyFields.size() == 1 && StrUtils.isNull(keyFieldValue)) { // 单字段主键新增数据
//				StringBuffer sqlLeft = new StringBuffer("insert into " + tableName + "(");
//				StringBuffer sqlRight = new StringBuffer(" values(");
//				Iterator it = convertData.keySet().iterator();
//				while (it.hasNext()) {
//					String key = it.next().toString();
//					Object fieldValue = convertData.get(key);
//					String fieldValueFormat = convertFormat != null && convertFormat.containsKey(key) ? convertFormat.getString(key) : null;
//					// 仅当不忽略null值、主键字段、值不为null、有格式化等之一的字段
//					if (!ignoreNullValue || fieldValue != null || fieldValueFormat != null || key.equalsIgnoreCase(keyField)) {
//						String keyValue = null;
//						sqlLeft.append(key + ",");
//						if (fieldValueFormat != null) {
//							keyValue = fieldValueFormat;
//							sqlRight.append(keyValue + ",");
//						} else
//							sqlRight.append("?,");
//						if (key.equalsIgnoreCase(keyField)) {// 新增的主键字段暂不允许从format中取值
//							String newKeyFieldValue = generatorID(tableName);
//							result.put(keyField, newKeyFieldValue);// 仅当主键字段值是本方法产生的才返回，其它任何情况都不会返回
//							sqlParamsPos.put(intParamPos, newKeyFieldValue);
//							intParamPos++;
//						} else if (keyValue == null || keyValue.indexOf("?") >= 0) {
//							sqlParamsPos.put(intParamPos, fieldValue);
//							intParamPos++;
//						}
//					}
//				}
//				sqlLeft.deleteCharAt(sqlLeft.length() - 1);
//				sqlLeft.append(")");
//				sqlRight.deleteCharAt(sqlRight.length() - 1);
//				sqlRight.append(")");
//				sqlLeft.append(sqlRight.toString());
//				pstm = getCon().prepareStatement(sqlLeft.toString());
//			} else { // 修改数据或者多主键字段新增数据(仅支持oracle，其它类型数据库需修改)
//				StringBuffer sql = new StringBuffer("declare");
//				sql.append(" row_count int;");
//				sql.append(" begin");
//				// 添加判断数据是否存在语句
//				sql.append(" select count(*) into row_count from " + tableName + " where 1 = 1 ");
//				for (int i = 0; i < keyFields.size(); i++) {
//					String fieldName = keyFields.get(i);
//					boolean blnExclude = false;
//					if (fieldName.substring(0, 1).equals("!")) {
//						fieldName = fieldName.substring(1);
//						blnExclude = true;
//					}
//					Object fieldValue = convertData.get(fieldName);
//					String fieldValueFormat = null;
//					if (convertFormat != null && convertFormat.containsKey(fieldName))
//						fieldValueFormat = convertFormat.getString(fieldName);
//					if (fieldValue == null) {
//						if (blnExclude)
//							sql.append(" and " + fieldName + " is not null ");
//						else
//							sql.append(" and " + fieldName + " is null ");
//					} else if (fieldValue instanceof List) {
//						List list = (List) fieldValue;
//						if (list.size() > 0) {
//							if (blnExclude)
//								sql.append(" and " + fieldName + " not in (");
//							else
//								sql.append(" and " + fieldName + " in (");
//							for (int j = 0; j < list.size(); j++) {
//								if (fieldValueFormat != null) {
//									sql.append(fieldValueFormat + ",");
//									if (fieldValueFormat.indexOf("?") >= 0) {
//										sqlParamsPos.put(intParamPos, list.get(j));
//										intParamPos++;
//									}
//								} else {
//									sql.append("?,");
//									sqlParamsPos.put(intParamPos, list.get(j));
//									intParamPos++;
//								}
//							}
//							sql.deleteCharAt(sql.length() - 1);
//							sql.append(")");
//						}
//					} else {
//						if (blnExclude)
//							sql.append(" and " + fieldName + " <> ? ");
//						else
//							sql.append(" and " + fieldName + " = ? ");
//						sqlParamsPos.put(intParamPos, fieldValue);
//						intParamPos++;
//					}
//				}
//				sql.append(";");
//				sql.append(" if row_count > 0 then ");
//				// 添加更新语句
//				sql.append(" update " + tableName + " set ");
//				Iterator it = convertData.keySet().iterator();
//				while (it.hasNext()) {
//					String key = it.next().toString();
//					Object fieldValue = convertData.get(key);
//					String fieldValueFormat = convertFormat != null && convertFormat.containsKey(key) ? convertFormat.getString(key) : null;
//					if (keyFields.indexOf(key) == -1 && (!ignoreNullValue || fieldValue != null || fieldValueFormat != null)) {
//						if (fieldValueFormat != null) {
//							String keyValue = fieldValueFormat;
//							sql.append(key + " = " + keyValue + ",");
//							if (keyValue.indexOf("?") >= 0) {
//								sqlParamsPos.put(intParamPos, fieldValue);
//								intParamPos++;
//							}
//						} else {
//							sql.append(key + " = ?,");
//							sqlParamsPos.put(intParamPos, fieldValue);
//							intParamPos++;
//						}
//					}
//				}
//				sql.deleteCharAt(sql.length() - 1);
//				sql.append(" where 1 = 1 ");
//				for (int i = 0; i < keyFields.size(); i++) {
//					String fieldName = keyFields.get(i);
//					boolean blnExclude = false;
//					if (fieldName.substring(0, 1).equals("!")) {
//						fieldName = fieldName.substring(1);
//						blnExclude = true;
//					}
//					Object fieldValue = convertData.get(fieldName);
//					String fieldValueFormat = null;
//					if (convertFormat != null && convertFormat.containsKey(fieldName))
//						fieldValueFormat = convertFormat.getString(fieldName);
//					if (fieldValue == null) {
//						if (blnExclude)
//							sql.append(" and " + fieldName + " is not null ");
//						else
//							sql.append(" and " + fieldName + " is null ");
//					} else if (fieldValue instanceof List) {
//						List list = (List) fieldValue;
//						if (list.size() > 0) {
//							if (blnExclude)
//								sql.append(" and " + fieldName + " not in (");
//							else
//								sql.append(" and " + fieldName + " in (");
//							for (int j = 0; j < list.size(); j++) {
//								if (fieldValueFormat != null) {
//									sql.append(fieldValueFormat + ",");
//									if (fieldValueFormat.indexOf("?") >= 0) {
//										sqlParamsPos.put(intParamPos, list.get(j));
//										intParamPos++;
//									}
//								} else {
//									sql.append("?,");
//									sqlParamsPos.put(intParamPos, list.get(j));
//									intParamPos++;
//								}
//							}
//							sql.deleteCharAt(sql.length() - 1);
//							sql.append(")");
//						}
//					} else {
//						if (blnExclude)
//							sql.append(" and " + fieldName + " <> ? ");
//						else
//							sql.append(" and " + fieldName + " = ? ");
//						sqlParamsPos.put(intParamPos, fieldValue);
//						intParamPos++;
//					}
//				}
//				sql.append(";");
//				// 添加修改语句
//				sql.append(" else ");// 新系统数据库无组合主键，全部符合第二范式，即所有表都有id主键字段
//				sql.append(" insert into " + tableName + "(");
//				StringBuffer sqlRight = new StringBuffer(" values(");
//				it = convertData.keySet().iterator();
//				while (it.hasNext()) {
//					String key = it.next().toString();
//					Object fieldValue = convertData.get(key);
//					String fieldValueFormat = convertFormat != null && convertFormat.containsKey(key) ? convertFormat.getString(key) : null;
//					if (!ignoreNullValue || fieldValue != null || fieldValueFormat != null) {
//						sql.append(key + ",");
//						if (fieldValueFormat != null) {
//							String keyValue = fieldValueFormat;
//							sqlRight.append(keyValue + ",");
//							if (keyValue.indexOf("?") >= 0) {
//								sqlParamsPos.put(intParamPos, fieldValue);
//								intParamPos++;
//							}
//						} else {
//							sqlRight.append("?,");
//							if (fieldValue instanceof List) {
//								// 新增数据时不允许有list键值出现，如果出现则取第一个值。无法按list排列组合生成多条记录
//								List list = (List) fieldValue;
//								if (list.size() > 0)
//									sqlParamsPos.put(intParamPos, list.get(0));
//								else
//									sqlParamsPos.put(intParamPos, null);
//							} else
//								sqlParamsPos.put(intParamPos, fieldValue);
//							intParamPos++;
//						}
//					}
//				}
//				sql.deleteCharAt(sql.length() - 1);
//				sql.append(")");
//				sqlRight.deleteCharAt(sqlRight.length() - 1);
//				sqlRight.append(");");
//				sql.append(sqlRight.toString());
//				sql.append(" end if;");
//				sql.append(" end;");
//				pstm = getCon().prepareStatement(sql.toString());
//			}
//			for (int i = 0; i < sqlParamsPos.size(); i++) {
//				Object paramValue = sqlParamsPos.get(i);
//				String strParamValue = String.valueOf(paramValue);
//				if (strParamValue == null || strParamValue.equals("") || strParamValue.equalsIgnoreCase("null"))
//					pstm.setObject(i + 1, "");// 仅使SQL不出错，但一般不是调用处希望的结果，如果该参数不接受null值则不影响结果集
//				else
//					pstm.setObject(i + 1, paramValue);
//			}
//			pstm.executeUpdate();
//			// getCon().commit();
//			result.put("state", 1);
//		} catch (SQLException e) {
//			result = new ParaMap();
//			result.put("state", 0);
//			result.put("message", e.getMessage());
//			log.error("Update data error: " + e.getMessage());
//			throw e;
//		} finally {
//			try {
//				if (pstm != null) {
//					pstm.close();
//				}
//			} catch (SQLException e) {
//				log.error("Close update data resource error: " + e.getMessage());
//				throw e;
//			}
//		}
//		return result;
//	}
//
//	/**
//	 * 更新数据
//	 * 
//	 * @param tableName
//	 *            更新数据的表名
//	 * @param keyField
//	 *            更新数据表的主键字段，多个主键字段请使用用半角逗号或者分号分隔，如“id”、“department_id,emp_id”
//	 * @param data
//	 *            更新的数据内容，如：<br/>
//	 *            id: 1234567890<br/>
//	 *            no: abcdefg<br/>
//	 *            name: xxxxxx<br/>
//	 *            主键字段也需包含，单主键字段为空值将自动新增数据，并且自动获取主键字段值。
//	 * @param format
//	 *            data中有特殊格式的字段值则需要在此map中添加格式转换说明，如：<br/>
//	 *            create_date: to_date(?, 'yyyy-mm-dd hh24:mi:ss')
//	 * @param checkTableFields
//	 *            如果data中有键值非tableName表中的字段，请将本参数设置为true
//	 * @return 返回更新结果，通过state=1表示更新成功，否则请检查message参数
//	 * @throws Exception
//	 */
//	public ParaMap updateData(String tableName, String keyField, ParaMap data, ParaMap format, boolean checkTableFields) throws Exception {
//		return updateData(tableName, keyField, data, format, checkTableFields, true);
//	}
//
//	/**
//	 * 更新数据
//	 * 
//	 * @param tableName
//	 *            更新数据的表名
//	 * @param keyField
//	 *            主键字段名。非空时表示如果按checkField未查找到记录则调用updateData方法新增记录，为空则不会添加任何记录
//	 * @param checkField
//	 *            查找记录条件字段，如果存在记录则更新，否则有keyField值则新增。<br/>
//	 *            多个主键字段请使用用半角逗号或者分号分隔，如“id”、“department_id,emp_id”
//	 * @param data
//	 *            更新的数据内容，如：<br/>
//	 *            id: 1234567890<br/>
//	 *            no: abcdefg<br/>
//	 *            name: xxxxxx<br/>
//	 *            主键字段也需包含，单主键字段为空值将自动新增数据，并且自动获取主键字段值。
//	 * @param format
//	 *            data中有特殊格式的字段值则需要在此map中添加格式转换说明，如：<br/>
//	 *            create_date: to_date(?, 'yyyy-mm-dd hh24:mi:ss')
//	 * @param checkTableFields
//	 *            如果data中有键值非tableName表中的字段，请将本参数设置为true
//	 * @param ignoreNullValue
//	 *            true表示忽略data中的null值，不会被组织到更新SQL语句中
//	 * @return 返回更新结果，通过state=1表示更新成功，否则请检查message参数
//	 * @throws Exception
//	 */
//	public ParaMap updateData(String tableName, String keyField, String checkField, ParaMap data, ParaMap format, boolean checkTableFields,
//			boolean ignoreNullValue) throws Exception {
//		ParaMap result = new ParaMap();
//		if (StrUtils.isNull(tableName)) {
//			result.put("state", 0);
//			result.put("message", "更新数据必须传入目标表名");
//			return result;
//		}
//		if (StrUtils.isNull(checkField)) {
//			if (StrUtils.isNull(keyField)) {
//				result.put("state", 0);
//				result.put("message", "更新数据必须传入目标表" + tableName + "的键值字段名，如果无键值字段则无法通过此方法更新");
//				return result;
//			} else {
//				// if (keyField.indexOf(',') == -1 && keyField.indexOf(';') ==
//				// -1)
//				// data.put(keyField, "");
//				return updateData(tableName, keyField, data, format, checkTableFields);
//			}
//		}
//		// 提取所有键值字段
//		List<String> keyFields = convertFields(keyField);
//		List<String> checkFields = convertFields(checkField, null, true);
//		ParaMap convertData = null;
//		ParaMap convertFormat = null;
//		// 保存更新值，仅当checkField中的字段需要更新时。其它字段不需要(主键字段keyField不能被更新)
//		ParaMap newKeyFieldData = new ParaMap();
//		ParaMap notConditionFieldData = new ParaMap();
//		Iterator itData = data.keySet().iterator();
//		while (itData.hasNext()) {
//			String key = itData.next().toString();
//			if (StrUtils.isNotNull(key)) {
//				String fieldName = key.toLowerCase();
//				if (fieldName.startsWith("new.")) {
//					fieldName = fieldName.substring("new.".length());
//					if (checkFields.indexOf(fieldName) != -1 || checkFields.indexOf("!" + fieldName) != -1)
//						newKeyFieldData.put(fieldName, data.get(key));
//				}
//			}
//		}
//		// 转换值列表中的字段名为小写，按checkTableFields决定是否清除非指定表的字段
//		if (checkTableFields) {
//			convertData = convertDataFieldName(data, tableName);// 检查data中不存在于表tableName中的字段名Key值，并且将所有字段名转换为小写。多访问一次数据库
//		} else {
//			convertData = convertDataFieldName(data, null);// 不检查字段名，要求data中所有的Key值都是tableName表中的字段，由调用方来处理
//		}
//		convertFormat = convertDataFieldName(format, null);
//		PreparedStatement pstm = null;
//		Map<Integer, Object> sqlParamsPos = new HashMap<Integer, Object>();
//		try {
//			int intParamPos = 0;
//			boolean hasUpdateField = false;
//			ParaMap checkDataExists = querySimpleRowCount(tableName, checkField, data);
//			if (checkDataExists.getInt("rowCount") == 0) {// 无记录
//				if (StrUtils.isNotNull(keyField)) {// 如果键值字段不为空则
//					if (keyField.indexOf(',') == -1 && keyField.indexOf(';') == -1)
//						data.put(keyField, "");
//					return updateData(tableName, keyField, convertData, convertFormat, false, ignoreNullValue);
//				} else {
//					result.put("state", 1);
//					result.put("message", "未返回记录");
//					return result;
//				}
//			} else {// 修改数据
//				StringBuffer sql = new StringBuffer("update " + tableName + " set ");
//				Iterator it = convertData.keySet().iterator();
//				while (it.hasNext()) {
//					String key = it.next().toString().toLowerCase();
//					Object fieldValue = newKeyFieldData.containsKey(key) ? newKeyFieldData.get(key) : convertData.get(key);
//					String fieldValueFormat = convertFormat != null && convertFormat.containsKey(key) ? convertFormat.getString(key) : null;
//					// 主键字段不能更新，并且仅当不忽略null值、主键字段、值不为null、有格式化等之一的字段
//					if (((checkFields.indexOf(key) == -1 && checkFields.indexOf("!" + key) == -1) || newKeyFieldData.containsKey(key))
//							&& (keyFields == null || keyFields.indexOf(key) == -1)
//							&& (!ignoreNullValue || fieldValue != null || fieldValueFormat != null)) {
//						if (fieldValueFormat != null) {
//							String keyValue = fieldValueFormat;
//							sql.append(key + " = " + keyValue + ",");
//							if (keyValue.indexOf("?") >= 0) {
//								sqlParamsPos.put(intParamPos, fieldValue);
//								intParamPos++;
//							}
//						} else {
//							sql.append(key + " = ?,");
//							sqlParamsPos.put(intParamPos, fieldValue);
//							intParamPos++;
//						}
//						hasUpdateField = true;
//					}
//				}
//				sql.deleteCharAt(sql.length() - 1);
//				sql.append(" where 1 = 1 ");
//				for (int i = 0; i < checkFields.size(); i++) {
//					String checkFieldName = checkFields.get(i);
//					String strFieldName = checkFieldName;
//					boolean blnExclude = false;
//					if (strFieldName.startsWith("!")) {
//						strFieldName = strFieldName.substring(1);
//						blnExclude = true;
//					}
//					Object fieldValue = convertData.get(strFieldName);
//					if (convertFormat != null && convertFormat.containsKey(strFieldName)) {
//						// 需要格式化的值不支持多个值
//						String keyValue = convertFormat.getString(strFieldName);
//						if (blnExclude)
//							sql.append(" and " + strFieldName + " <> " + keyValue + " ");
//						else
//							sql.append(" and " + strFieldName + " = " + keyValue + " ");
//						if (keyValue.indexOf("?") >= 0) {
//							sqlParamsPos.put(intParamPos, fieldValue);
//							intParamPos++;
//						}
//					} else {
//						if (fieldValue == null) {
//							if (blnExclude)
//								sql.append(" and " + strFieldName + " is not null ");
//							else
//								sql.append(" and " + strFieldName + " is null ");
//						} else if (fieldValue instanceof List) {
//							List list = (List) fieldValue;
//							if (list.size() > 0) {
//								if (blnExclude)
//									sql.append(" and " + strFieldName + " not in (");
//								else
//									sql.append(" and " + strFieldName + " in (");
//								for (int j = 0; j < list.size(); j++) {
//									sql.append("?,");
//									sqlParamsPos.put(intParamPos, list.get(j));
//									intParamPos++;
//								}
//								sql.deleteCharAt(sql.length() - 1);
//								sql.append(")");
//							}
//						} else {
//							if (blnExclude)
//								sql.append(" and " + strFieldName + " <> ? ");
//							else
//								sql.append(" and " + strFieldName + " = ? ");
//							sqlParamsPos.put(intParamPos, fieldValue);
//							intParamPos++;
//						}
//					}
//				}
//				pstm = getCon().prepareStatement(sql.toString());
//			}
//			for (int i = 0; i < sqlParamsPos.size(); i++) {
//				Object paramValue = sqlParamsPos.get(i);
//				String strParamValue = String.valueOf(paramValue);
//				if (StrUtils.isNull(strParamValue))
//					pstm.setObject(i + 1, "");// 仅使SQL不出错，但一般不是调用处希望的结果，如果该参数不接受null值则不影响结果集
//				else
//					pstm.setObject(i + 1, paramValue);
//			}
//			if (hasUpdateField)// 仅更新时有需要修改的字段，新增记录前面已跳出
//				pstm.executeUpdate();
//			// getCon().commit();
//			result.put("state", 1);
//		} catch (SQLException e) {
//			result = new ParaMap();
//			result.put("state", 0);
//			result.put("message", e.getMessage());
//			log.error("Save data error: " + e.getMessage());
//			throw e;
//		} finally {
//			try {
//				if (pstm != null) {
//					pstm.close();
//				}
//			} catch (SQLException e) {
//				log.error("Close save data resource error: " + e.getMessage());
//				throw e;
//			}
//		}
//		return result;
//	}
//
//	/**
//	 * 更新数据
//	 * 
//	 * @param tableName
//	 *            更新数据的表名
//	 * @param keyField
//	 *            主键字段名。非空时表示如果按checkField未查找到记录则调用updateData方法新增记录，为空则不会添加任何记录
//	 * @param checkField
//	 *            查找记录条件字段，如果存在记录则更新，否则有keyField值则新增。<br/>
//	 *            多个主键字段请使用用半角逗号或者分号分隔，如“id”、“department_id,emp_id”
//	 * @param data
//	 *            更新的数据内容，如：<br/>
//	 *            id: 1234567890<br/>
//	 *            no: abcdefg<br/>
//	 *            name: xxxxxx<br/>
//	 *            主键字段也需包含，单主键字段为空值将自动新增数据，并且自动获取主键字段值。
//	 * @param format
//	 *            data中有特殊格式的字段值则需要在此map中添加格式转换说明，如：<br/>
//	 *            create_date: to_date(?, 'yyyy-mm-dd hh24:mi:ss')
//	 * @param checkTableFields
//	 *            如果data中有键值非tableName表中的字段，请将本参数设置为true
//	 * @return 返回更新结果，通过state=1表示更新成功，否则请检查message参数
//	 * @throws Exception
//	 */
//	public ParaMap updateData(String tableName, String keyField, String checkField, ParaMap data, ParaMap format, boolean checkTableFields)
//			throws Exception {
//		return updateData(tableName, keyField, checkField, data, format, checkTableFields, true);
//	}
//
//	/**
//	 * 更新数据（重载方法），自动设置checkTableFields为true
//	 * 
//	 * @param tableName
//	 *            更新数据的表名
//	 * @param keyField
//	 *            更新数据表的主键字段，多个主键字段请使用用半角逗号或者分号分隔，未传入则无法更新。如“id”、“department_id,
//	 *            emp_id”
//	 * @param data
//	 *            更新的数据内容，如：<br/>
//	 *            id: 1234567890<br/>
//	 *            no: abcdefg<br/>
//	 *            name: xxxxxx<br/>
//	 *            主键字段也需包含，单主键字段为空值将自动新增数据，并且自动获取主键字段值。
//	 * @param format
//	 *            data中有特殊格式的字段值则需要在此map中添加格式转换说明，如：<br/>
//	 *            create_date: to_date(?, 'yyyy-mm-dd hh24:mi:ss')
//	 * @return 返回更新结果，通过state=1表示更新成功，否则请检查message参数
//	 * @throws Exception
//	 */
//	public ParaMap updateData(String tableName, String keyField, ParaMap data, ParaMap format) throws Exception {
//		return updateData(tableName, keyField, data, format, true);
//	}
//
//	/**
//	 * 更新数据（重载方法），自动设置checkTableFields为true
//	 * 
//	 * @param tableName
//	 *            更新数据的表名
//	 * @param keyField
//	 *            更新数据表的主键字段，多个主键字段请使用用半角逗号或者分号分隔，未传入则无法更新。如“id”、“department_id,
//	 *            emp_id”
//	 * @param checkField
//	 *            查找记录条件字段，如果存在记录则更新，否则有keyField值则新增。<br/>
//	 *            多个主键字段请使用用半角逗号或者分号分隔，如“id”、“department_id,emp_id”
//	 * @param data
//	 *            更新的数据内容，如：<br/>
//	 *            id: 1234567890<br/>
//	 *            no: abcdefg<br/>
//	 *            name: xxxxxx<br/>
//	 *            主键字段也需包含，单主键字段为空值将自动新增数据，并且自动获取主键字段值。
//	 * @param format
//	 *            data中有特殊格式的字段值则需要在此map中添加格式转换说明，如：<br/>
//	 *            create_date: to_date(?, 'yyyy-mm-dd hh24:mi:ss')
//	 * @return 返回更新结果，通过state=1表示更新成功，否则请检查message参数
//	 * @throws Exception
//	 */
//	public ParaMap updateData(String tableName, String keyField, String checkField, ParaMap data, ParaMap format) throws Exception {
//		return updateData(tableName, keyField, checkField, data, format, true);
//	}
//
//	/**
//	 * 更新数据（重载方法），自动设置checkTableFields为true
//	 * 
//	 * @param tableName
//	 *            更新数据的表名
//	 * @param keyField
//	 *            更新数据表的主键字段，多个主键字段请使用用半角逗号或者分号分隔，未传入则无法更新。如“id”、“department_id,
//	 *            emp_id”
//	 * @param data
//	 *            更新的数据内容，如：<br/>
//	 *            id: 1234567890<br/>
//	 *            no: abcdefg<br/>
//	 *            name: xxxxxx<br/>
//	 *            主键字段也需包含，单主键字段为空值将自动新增数据，并且自动获取主键字段值。
//	 * @return 返回更新结果，通过state=1表示更新成功，否则请检查message参数
//	 * @throws Exception
//	 */
//	public ParaMap updateData(String tableName, String keyField, ParaMap data) throws Exception {
//		return updateData(tableName, keyField, data, null);
//	}
//
//	/**
//	 * 更新数据（重载方法），自动设置checkTableFields为true
//	 * 
//	 * @param tableName
//	 *            更新数据的表名
//	 * @param keyField
//	 *            更新数据表的主键字段，多个主键字段请使用用半角逗号或者分号分隔，未传入则无法更新。如“id”、“department_id,
//	 *            emp_id”
//	 * @param checkField
//	 *            查找记录条件字段，如果存在记录则更新，否则有keyField值则新增。<br/>
//	 *            多个主键字段请使用用半角逗号或者分号分隔，如“id”、“department_id,emp_id”
//	 * @param data
//	 *            更新的数据内容，如：<br/>
//	 *            id: 1234567890<br/>
//	 *            no: abcdefg<br/>
//	 *            name: xxxxxx<br/>
//	 *            主键字段也需包含，单主键字段为空值将自动新增数据，并且自动获取主键字段值。
//	 * @return 返回更新结果，通过state=1表示更新成功，否则请检查message参数
//	 * @throws Exception
//	 */
//	public ParaMap updateData(String tableName, String keyField, String checkField, ParaMap data) throws Exception {
//		return updateData(tableName, keyField, checkField, data, null);
//	}
//
//	/**
//	 * 更新数据（重载方法），批量更新
//	 * 
//	 * @param records
//	 *            更新数据的表列表，每项都是个MAP，包含tableName、keyField、data、format、
//	 *            checkTableFields等内容
//	 * @return 返回更新结果，通过state=1表示更新成功，否则请检查message参数
//	 * @throws Exception
//	 */
//	public ParaMap updateData(List records) throws Exception {
//		if (records == null || records.size() == 0) {
//			ParaMap result = new ParaMap();
//			result.put("state", 0);
//			result.put("message", "未传入需要更新的数据列表");
//			return result;
//		}
//		List recordIds = new ArrayList();
//		for (int i = 0; i < records.size(); i++) {
//			ParaMap record = (ParaMap) records.get(i);
//			String tableName = record.getString("tableName");
//			String keyField = record.getString("keyField");
//			String checkField = record.getString("checkField");
//			ParaMap data = (ParaMap) record.get("data");
//			ParaMap format = (ParaMap) record.get("format");
//			boolean checkTableFields = true;
//			if (record.containsKey("checkTableFields"))
//				checkTableFields = record.getInt("checkTableFields") == 1;
//			String keyValue = data.getString(keyField);
//			ParaMap updateResult = updateData(tableName, keyField, checkField, data, format, checkTableFields);
//			if (updateResult.getInt("state") == 0) {// 有执行不成功则中断执行直接跳出
//				return updateResult;
//			} else if (updateResult.containsKey(keyField)) {
//				recordIds.add(updateResult.getString(keyField));
//			} else {
//				recordIds.add(keyValue);
//			}
//		}
//		ParaMap result = new ParaMap();
//		result.put("state", 1);
//		result.put("ids", recordIds);
//		return result;
//	}
//
//	/**
//	 * 简单删除数据
//	 * 
//	 * @param tableName
//	 *            删除数据的表名
//	 * @param key
//	 *            删除数据表的主键字段及值键值对
//	 * @return 返回更新结果，通过state=1表示更新成功，否则请检查message参数
//	 * @throws Exception
//	 */
//	public ParaMap deleteSimpleData(String tableName, ParaMap keyData) throws Exception {
//		ParaMap result = new ParaMap();
//		if (StrUtils.isNull(tableName)) {
//			result.put("state", 0);
//			result.put("message", "删除数据必须传入目标表名");
//			return result;
//		}
//		if (keyData == null || keyData.size() == 0) {
//			result.put("state", 0);
//			result.put("message", "删除数据必须传入目标表" + tableName + "的主键字段名及值列表信息");
//			return result;
//		}
//		PreparedStatement pstm = null;
//		Map<Integer, Object> sqlParamsPos = new HashMap<Integer, Object>();
//		int intParamPos = 0;
//		try {
//			StringBuffer sql = new StringBuffer("delete from " + tableName + " where 1 = 1");
//			Iterator it = keyData.keySet().iterator();
//			while (it.hasNext()) {
//				String key = it.next().toString();
//				Object value = keyData.get(key);
//				String strFieldName = key;
//				if (StrUtils.isNotNull(strFieldName)) {
//					boolean blnExclude = false;
//					boolean blnLookup = false;
//					if (strFieldName.substring(0, 1).equals("!")) {
//						strFieldName = strFieldName.substring(1);
//						blnExclude = true;
//					}
//					if (strFieldName.substring(0, 1).equals("@")) {
//						strFieldName = strFieldName.substring(1);
//						blnLookup = true;
//					}
//					if (value == null) {
//						if (blnExclude)
//							sql.append(" and " + strFieldName + " is not null ");
//						else
//							sql.append(" and " + strFieldName + " is null ");
//					} else if (value instanceof List) {
//						List list = (List) value;
//						if (list.size() > 0) {
//							if (blnExclude)
//								sql.append(" and " + strFieldName + " not in (");
//							else
//								sql.append(" and " + strFieldName + " in (");
//							for (int i = 0; i < list.size(); i++) {
//								sql.append("?,");
//								sqlParamsPos.put(intParamPos, list.get(i));
//								intParamPos++;
//							}
//							sql.deleteCharAt(sql.length() - 1);
//							sql.append(")");
//						}
//					} else if (value instanceof Map || blnLookup) {
//						// 简单子查询过滤
//						ParaMap valueMap = blnLookup ? new ParaMap(StrUtils.getParams(String.valueOf(value))) : new ParaMap((Map) value);
//						String refTableName = valueMap.getString(SQL_LOOKUP_TABLE_NAME);
//						String refKeyFieldName = valueMap.getString(SQL_LOOKUP_KEY_FIELD_NAME);
//						if (StrUtils.isNotNull(refTableName) && StrUtils.isNotNull(refKeyFieldName)) {// &&
//																										// valueMap.size()
//																										// >
//																										// 2
//							valueMap.remove(SQL_LOOKUP_TABLE_NAME);
//							valueMap.remove(SQL_LOOKUP_KEY_FIELD_NAME);
//							sql.append(" and " + strFieldName + " in (select " + refKeyFieldName + " from " + refTableName + " where 1 = 1 ");
//							Iterator refIt = valueMap.keySet().iterator();
//							while (refIt.hasNext()) {
//								String refKey = refIt.next().toString();
//								Object refValue = valueMap.get(refKey);
//								boolean blnRefExclude = false;
//								if (refKey.substring(0, 1).equals("!")) {
//									refKey = refKey.substring(1);
//									blnRefExclude = true;
//								}
//								if (refValue == null) {
//									if (blnRefExclude)
//										sql.append(" and " + refKey + " is not null ");
//									else
//										sql.append(" and " + refKey + " is null ");
//								} else if (refValue instanceof List) {
//									List refList = (List) refValue;
//									if (refList.size() > 0) {
//										if (blnRefExclude)
//											sql.append(" and " + refKey + " not in (");
//										else
//											sql.append(" and " + refKey + " in (");
//										for (int i = 0; i < refList.size(); i++) {
//											sql.append("?,");
//											sqlParamsPos.put(intParamPos, refList.get(i));
//											intParamPos++;
//										}
//										sql.deleteCharAt(sql.length() - 1);
//										sql.append(")");
//									}
//								} else {
//									if (blnRefExclude)
//										sql.append(" and " + refKey + " <> ? ");
//									else
//										sql.append(" and " + refKey + " = ? ");
//									sqlParamsPos.put(intParamPos, refValue);
//									intParamPos++;
//								}
//							}
//							sql.append(")");
//						}
//					} else {
//						if (blnExclude)
//							sql.append(" and " + strFieldName + " <> ? ");
//						else
//							sql.append(" and " + strFieldName + " = ? ");
//						sqlParamsPos.put(intParamPos, value);
//						intParamPos++;
//					}
//				}
//			}
//			pstm = getCon().prepareStatement(sql.toString());
//			int intParamCount = 1;
//			for (int i = 0; i < sqlParamsPos.size(); i++) {
//				Object paramValue = sqlParamsPos.get(i);
//				String strParamValue = String.valueOf(paramValue);
//				if (StrUtils.isNull(strParamValue))
//					pstm.setObject(intParamCount, "");// 仅使SQL不出错，但一般不是调用处希望的结果，如果该参数不接受null值则不影响结果集
//				else
//					pstm.setObject(intParamCount, paramValue);
//				intParamCount++;
//			}
//			pstm.executeUpdate();
//			// getCon().commit();
//			result.put("state", 1);
//		} catch (SQLException e) {
//			result = new ParaMap();
//			result.put("state", 0);
//			result.put("message", e.getMessage());
//			log.error("Delete data error: " + e.getMessage());
//			throw e;
//		} finally {
//			try {
//				if (pstm != null) {
//					pstm.close();
//				}
//			} catch (SQLException e) {
//				log.error("Close delete data resource error: " + e.getMessage());
//				throw e;
//			}
//		}
//		return result;
//	}
//
//	/**
//	 * 简单删除数据（重载方法）
//	 * 
//	 * @param tableName
//	 *            删除数据的表名
//	 * @param keyField
//	 *            删除数据表的主键字段，多个主键字段请使用用半角逗号或者分号分隔，未传入则无法更新。如“id”、“department_id,
//	 *            emp_id”
//	 * @param data
//	 *            字段键值对，如：<br/>
//	 *            id: 1234567890<br/>
//	 *            no: abcdefg<br/>
//	 *            name: xxxxxx<br/>
//	 *            主键字段也需包含
//	 * @return 返回删除结果，通过state=1表示更新成功，否则请检查message参数
//	 * @throws Exception
//	 */
//	public ParaMap deleteSimpleData(String tableName, String keyField, ParaMap data) throws Exception {
//		ParaMap keyData = new ParaMap();
//		ParaMap convertData = convertDataFieldName(data, null);
//		// 提取所有主键字段
//		if (keyField.indexOf(',') == -1 && keyField.indexOf(';') == -1)
//			keyData.put(keyField.toLowerCase(), convertData.get(keyField.toLowerCase()));
//		else {
//			List<String> keyFields = convertFields(keyField, null, true);
//			for (int i = 0; i < keyFields.size(); i++) {
//				String fieldName = keyFields.get(i);
//				if (convertData.containsKey(fieldName))
//					keyData.put(fieldName, convertData.get(fieldName));
//				else if (fieldName.startsWith("!") && convertData.containsKey(fieldName.substring(1)))
//					keyData.put(fieldName, convertData.get(fieldName.substring(1)));
//			}
//		}
//		return deleteSimpleData(tableName, keyData);
//	}
//
//	public ParaMap deleteSimpleData(String tableName, String keyField, String keyFieldValue) throws Exception {
//		ParaMap keyData = new ParaMap();
//		keyData.put(keyField, keyFieldValue);
//		return deleteSimpleData(tableName, keyData);
//	}
//
//	/**
//	 * 简单删除数据（重载方法），批量更新
//	 * 
//	 * @param records
//	 *            删除数据的表列表，每项都是个MAP，包含tableName、keyField、data等内容
//	 * @return 返回删除结果，通过state=1表示更新成功，否则请检查message参数
//	 * @throws Exception
//	 */
//	public ParaMap deleteSimpleData(List records) throws Exception {
//		if (records == null || records.size() == 0) {
//			ParaMap result = new ParaMap();
//			result.put("state", 0);
//			result.put("message", "未传入需要删除的数据列表");
//			return result;
//		}
//		for (int i = 0; i < records.size(); i++) {
//			ParaMap record = (ParaMap) records.get(i);
//			String tableName = record.getString("tableName");
//			String keyField = record.getString("keyField");
//			ParaMap data = (ParaMap) record.get("data");
//			ParaMap tableResult = deleteSimpleData(tableName, keyField, data);
//			if (tableResult.getInt("state") == 0) {// 有执行不成功则中断执行直接跳出
//				return tableResult;
//			}
//		}
//		ParaMap result = new ParaMap();
//		result.put("state", 1);
//		return result;
//	}
//
//	/**
//	 * 执行存储过程，参数按索引位置传
//	 * 
//	 * @param stroedProcName
//	 *            存储过程名称
//	 * @param params
//	 *            存储过程参数，如果执行无参数过程请传入null。所有参数都必须传入占位，即使是返回参数，除非之后所有参数都忽略
//	 * @param outParams
//	 *            存储过程返回参数，key为传出参数索引（指存储过程中传出参数位置索引，本方法从0开始，非按jdbc从1开始），
//	 *            value是返回参数类型（必须是java.sql.Types（
//	 *            或者oracle.jdbc.driver.OracleTypes）中定义的常量）。返回参数也必须在params中占位
//	 * @return 
//	 *         执行成功state返回1，返回Map中包含键为outParams的参数，其中键为传入参数outParams对应的键（注意必须按字符串键取值
//	 *         ），值为存储过程返回的参数值
//	 * @throws Exception
//	 */
//	public Map callStoredProc(String stroedProcName, List params, Map<Integer, Integer> outParams) throws Exception {
//		Map result = null;
//		if (StrUtils.isNull(stroedProcName)) {
//			result = new ParaMap();
//			result.put("state", 0);
//			result.put("message", "未指定存储过程名称，无法继续执行存储过程操作");
//			return result;
//		}
//		StringBuffer callCmd = new StringBuffer("{call " + stroedProcName);
//		if (params != null && params.size() > 0) {
//			callCmd.append("(");
//			for (int i = 0; i < params.size(); i++)
//				callCmd.append("?,");
//			callCmd.deleteCharAt(callCmd.length() - 1);
//			callCmd.append(")");
//		}
//		callCmd.append("}");
//		CallableStatement callStmt = getCon().prepareCall(callCmd.toString());
//		try {
//			if (params != null && params.size() > 0) {
//				for (int i = 0; i < params.size(); i++) {
//					Object paramValue = params.get(i);
//					if (paramValue == null) {
//						if (outParams == null || !outParams.containsKey(i))
//							callStmt.setObject(i + 1, paramValue);
//					} else
//						callStmt.setObject(i + 1, paramValue);
//				}
//			}
//			// 注册返回参数信息
//			if (outParams != null && outParams.size() > 0) {
//				Iterator<Integer> it = outParams.keySet().iterator();
//				while (it.hasNext()) {
//					int paramIndex = it.next();
//					if (paramIndex < 0 || paramIndex >= params.size())
//						continue;
//					int paramType = outParams.get(paramIndex);
//					callStmt.registerOutParameter(paramIndex + 1, paramType);
//				}
//			}
//			callStmt.execute();
//			result = new HashMap();
//			result.put("state", 1);
//			if (outParams != null && outParams.size() > 0) {
//				Map resultParams = new HashMap();
//				Iterator<Integer> it = outParams.keySet().iterator();
//				while (it.hasNext()) {
//					Integer paramIndex = it.next();
//					// if (paramIndex < 0 || paramIndex >= params.size())
//					// continue;
//					resultParams.put(paramIndex, callStmt.getObject(paramIndex + 1));
//				}
//				result.put("outParams", resultParams);
//			}
//		} catch (SQLException e) {
//			result = new ParaMap();
//			result.put("state", 0);
//			result.put("message", e.getMessage());
//			log.error("Call stored proc error: " + e.getMessage());
//			throw e;
//		} finally {
//			try {
//				if (callStmt != null) {
//					callStmt.close();
//				}
//			} catch (SQLException e) {
//				log.error("Close call stored proc resource error: " + e.getMessage());
//				throw e;
//			}
//		}
//		return result;
//	}
//
//	public Map callStoredProc(String stroedProcName, List params) throws Exception {
//		return callStoredProc(stroedProcName, params, null);
//	}
//
//	/**
//	 * 执行存储过程，参数按名称传
//	 * 
//	 * @param stroedProcName
//	 *            存储过程名称
//	 * @param params
//	 *            存储过程参数，如果执行无参数过程请传入null
//	 * @param outParams
//	 *            存储过程返回参数，key为传出参数名称，value是返回参数类型（必须是java.sql.Types（
//	 *            或者oracle.jdbc.driver.OracleTypes）中定义的常量）
//	 * @return 执行成功state返回1，outParams参数类似调用参数outParams，但value为返回的参数值
//	 * @throws Exception
//	 */
//	public Map callStoredProc(String stroedProcName, Map<String, Object> params, Map<String, Integer> outParams) throws Exception {
//		Map result = null;
//		if (StrUtils.isNull(stroedProcName)) {
//			result = new ParaMap();
//			result.put("state", 0);
//			result.put("message", "未指定存储过程名称，无法继续执行存储过程操作");
//			return result;
//		}
//		StringBuffer callCmd = new StringBuffer("{call " + stroedProcName);
//		if (params != null && params.size() > 0) {
//			callCmd.append("(");
//			Iterator it = params.keySet().iterator();
//			while (it.hasNext())
//				callCmd.append("?,");
//			callCmd.deleteCharAt(callCmd.length() - 1);
//			callCmd.append(")");
//		}
//		callCmd.append("}");
//		CallableStatement callStmt = getCon().prepareCall(callCmd.toString());
//		try {
//			if (params != null && params.size() > 0) {
//				Iterator<String> it = params.keySet().iterator();
//				while (it.hasNext()) {
//					String paramName = it.next();
//					Object paramValue = params.get(paramName);
//					callStmt.setObject(paramName, paramValue);
//				}
//			}
//			// 注册返回参数信息
//			if (outParams != null && outParams.size() > 0) {
//				Iterator<String> it = outParams.keySet().iterator();
//				while (it.hasNext()) {
//					String paramName = it.next();
//					if (!params.containsKey(paramName))
//						continue;
//					int paramType = outParams.get(paramName);
//					callStmt.registerOutParameter(paramName, paramType);
//				}
//			}
//			callStmt.execute();
//			result = new HashMap();
//			result.put("state", 1);
//			if (outParams != null && outParams.size() > 0) {
//				Map resultParams = new HashMap();
//				Iterator<String> it = outParams.keySet().iterator();
//				while (it.hasNext()) {
//					String paramName = it.next();
//					// if (!params.containsKey(paramName))
//					// continue;
//					resultParams.put(paramName, callStmt.getObject(paramName));
//				}
//				result.put("outParams", resultParams);
//			}
//		} catch (SQLException e) {
//			result = new ParaMap();
//			result.put("state", 0);
//			result.put("message", e.getMessage());
//			log.error("Call stored proc error: " + e.getMessage());
//			throw e;
//		} finally {
//			try {
//				if (callStmt != null) {
//					callStmt.close();
//				}
//			} catch (SQLException e) {
//				log.error("Close call stored proc resource error: " + e.getMessage());
//				throw e;
//			}
//		}
//		return result;
//	}
//
//	public Map callStoredProc(String stroedProcName, Map<String, Object> params) throws Exception {
//		return callStoredProc(stroedProcName, params, null);
//	}
//
//	public Map callStoredProc(String stroedProcName) throws Exception {
//		return callStoredProc(stroedProcName, (List) null, null);
//	}
//
//	/**
//	 * 转换记录为对象
//	 * 
//	 * @param records
//	 * @param row
//	 *            记录行数，从0开始
//	 * @return
//	 */
//	public static ParaMap convertRecord2Map(ParaMap records, int row) {
//		if (row >= 0 && records.getRecordCount() > 0 && row < records.getRecordCount()) {
//			ParaMap record = new ParaMap();
//			for (int i = 0; i < records.getFieldCount(); i++) {
//				String fieldName = records.getField(i);
//				record.put(fieldName, records.getRecordValue(row, fieldName));
//			}
//			return record;
//		} else
//			return null;
//	}
//
//	public static List convertRecords2List(ParaMap records) {
//		if (records.getRecordCount() > 0) {
//			List recordList = new ArrayList();
//			for (int i = 0; i < records.getRecordCount(); i++) {
//				ParaMap record = new ParaMap();
//				for (int j = 0; j < records.getFieldCount(); j++) {
//					String fieldName = records.getField(j);
//					record.put(fieldName, records.getRecordValue(i, fieldName));
//				}
//				recordList.add(record);
//			}
//			return recordList;
//		} else
//			return null;
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
	 *         ParaMap取出list表示结果集
	 *         rowCount: 当前结果集返回的记录数，实际同totalRowCount。主要是包含queryData返回值基本一致<br/>
	 * @throws Exception
	 */
	public ParaMap querySimpleData(String tableName,ParaMap keyData) throws Exception{
		return queryDataDao.querySimpleData(tableName, keyData, null, null);
	}
	
	public ParaMap querySimpleData(String tableName,ParaMap keyData,String returnFields,String orderBy) throws Exception{
		return queryDataDao.querySimpleData(tableName, keyData, returnFields, orderBy);
	}
	
	/**
	 * 执行select sql
	 * @param sql
	 * @return
	 */
	public ParaMap queryData(String sql){
		return queryDataDao.queryData(sql, null,-128,-128);
	}
	
	/**
	 * 执行select sql
	 * @param sql   select *from a where name=:name 
	 * @param sqlParams  sqlParams.put("name","名字");
	 * @return
	 */
	public ParaMap queryData(String sql,ParaMap sqlParams){
		return queryDataDao.queryData(sql, sqlParams,-128,-128);
	}
	
	/**
	 * 执行select sql
	 * @param sql
	 * @param sqlParams
	 * @param pageIndex
	 * @param pageRowCount
	 * @return
	 */
	public ParaMap queryData(String sql,ParaMap sqlParams,int pageIndex,int pageRowCount){
		return queryDataDao.queryData(sql, sqlParams,pageIndex,pageRowCount);
	}
	
	public ParaMap queryData(String sql,int pageIndex,int pageRowCount){
		return queryDataDao.queryData(sql, null,pageIndex,pageRowCount);
	}
	
	/**
	 * 更新方法
	 * @param tableName 表名
	 * @param keyField 主键，即where条件可多个 以,隔开
	 * @param keyData  要更新的字段map
	 * @param formatData 格式化，比如时间为to_date('?','yyyy-mm-dd')
	 * @return
	 * @throws Exception
	 */
	public ParaMap updateData(String tableName,String keyField,ParaMap keyData,ParaMap formatData) throws Exception{
		ParaMap updateOut=updateDataDao.updateData(tableName, keyField, keyData, formatData);
		if(updateOut.getInt("state")==0 && updateOut.containsKey("add")){
			return insertDataDao.insertData(tableName, keyField, keyData, formatData);
		}
		return updateOut;
	}
	
	/**
	 * 更新方法  同上 无格式化
	 * @param tableName
	 * @param keyField
	 * @param keyData
	 * @return
	 * @throws Exception
	 */
	public ParaMap updateData(String tableName,String keyField,ParaMap keyData) throws Exception{
		ParaMap updateOut=updateDataDao.updateData(tableName, keyField, keyData, null);
		if(updateOut.getInt("state")==0 && updateOut.containsKey("add")){
			return insertDataDao.insertData(tableName, keyField, keyData, null);
		}
		return updateOut;
	}
	
	
	
	/**
	 * 新增方法
	 * @param tableName 表名
	 * @param keyField  主键 可多主键以英文逗号隔开
	 * @param data  新增数据键值对
	 * @param formatData
	 * @return
	 * @throws Exception
	 */
	public ParaMap insertData(String tableName, String keyField, ParaMap data, ParaMap formatData) throws Exception{
		return insertDataDao.insertData(tableName, keyField, data, formatData);
		
	}
	
	/**
	 * 新增方法
	 * @param tableName 表名
	 * @param keyField  主键 可多主键以英文逗号隔开
	 * @param data  新增数据键值对
	 * @param formatData
	 * @return
	 * @throws Exception
	 */
	public ParaMap insertData(String tableName, String keyField, ParaMap data) throws Exception{
		return insertDataDao.insertData(tableName, keyField, data,null);
		
	}
	public static void main(String[] args) throws Exception {
//		//Statement
//		for(int i = 0; i < 10000; i++) {
//			Thread thread = new Thread() {
//				public void run() {
//					try {
//						BaseDataSetDao dao = new BaseDataSetDao();
//						ParaMap data = dao.queryData("select * from sys_user where rownum <= 100", null);
//						System.out.println(data);
//						DataSourceManager.commit();
//					} catch (Exception e) {
//						DataSourceManager.rollback();
//					}
//				}
//			};
//			thread.start();
//		}
////		DataSourceManager.rollback();
////		BaseDataSetDao dao = new BaseDataSetDao();
////		for(int i = 0; i < 10000; i++) {
////			ParaMap data = dao.queryData("select * from sys_user where rownum <= 100", null);
////			System.out.println(data);
////		}
//		DataSourceManager.rollback();
	}
}