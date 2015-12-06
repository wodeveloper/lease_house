package com.base.dao;

import com.base.utils.ParaMap;

/**
 * 数据集访问助手类
 * @author huafc
 * 2015-04-28
 */
public class BaseDataSetHelper {
	private BaseDataSetDao dataSetDao = new BaseDataSetDao();
	
//	static {
//
//	}
//	
//	static public BaseDataSetHelper newInstance() {
//		BaseDataSetHelper result = null;
//		if (!BaseDataSetDao.isSqlDefineDb())
//			result = new DataSetXMLHelper();
//		else if (DataSetEhcacheHelper.getDataSetDefineApplyCache())
//			result = new DataSetEhcacheHelper();
//		return result;
//	}
//	
//	protected boolean isUseHelper() {
//		return true;
//	}
//	
//	/**
//	 * 根据模块id、数据集编号获取数据集sql
//	 * @param moduleId 模块id
//	 * @param dataSetNo 数据集编号
//	 * @return
//	 * @throws Exception
//	 */
//	protected ParaMap doGetDataSetSQL(String moduleId, String dataSetNo) throws Exception {
//		return dataSetDao.getDataSetSQL(moduleId, dataSetNo);
//	}
//	
//	/**
//	 * 根据模块id、数据集编号获取数据集sql
//	 * @param moduleId 模块id
//	 * @param dataSetNo 数据集编号
//	 * @return
//	 * @throws Exception
//	 */
//	public ParaMap getDataSetSQL(String moduleId, String dataSetNo) throws Exception {
//		if (isUseHelper()) {
//			ParaMap result = doGetDataSetSQL(moduleId, dataSetNo);
//			return result;
//		} else
//			return dataSetDao.getDataSetSQL(moduleId, dataSetNo);
//	}
//	
//	/**
//	 * 根据模块编号、数据集编号获取数据集sql
//	 * @param moduleNo 模块编号
//	 * @param dataSetNo 数据集编号
//	 * @return
//	 * @throws Exception
//	 */
//	protected ParaMap doGetDataSetSQLByModuleNo(String moduleNo, String dataSetNo) throws Exception {
//		return dataSetDao.getDataSetSQLByModuleNo(moduleNo, dataSetNo);
//	}
//	
//	/**
//	 * 根据模块编号、数据集编号获取数据集sql
//	 * @param moduleNo 模块编号
//	 * @param dataSetNo 数据集编号
//	 * @return
//	 * @throws Exception
//	 */
//	public ParaMap getDataSetSQLByModuleNo(String moduleNo, String dataSetNo) throws Exception {
//		if (isUseHelper()) {
//			ParaMap result = doGetDataSetSQLByModuleNo(moduleNo, dataSetNo);
//			return result;
//		} else
//			return dataSetDao.getDataSetSQLByModuleNo(moduleNo, dataSetNo);
//	}
//	
//	/**
//	 * 根据数据集id获取数据集sql
//	 * @param dataSetId 数据集id
//	 * @return
//	 * @throws Exception
//	 */
//	protected ParaMap doGetDataSetSQLById(String dataSetId) throws Exception {
//		return dataSetDao.getDataSetSQLById(dataSetId);
//	}
//	
//	/**
//	 * 根据数据集id获取数据集sql
//	 * @param dataSetId 数据集id
//	 * @return
//	 * @throws Exception
//	 */
//	public ParaMap getDataSetSQLById(String dataSetId) throws Exception {
//		if (isUseHelper()) {
//			ParaMap result = doGetDataSetSQLById(dataSetId);
//			return result;
//		} else
//			return dataSetDao.getDataSetSQLById(dataSetId);
//	}
//	
//	/**
//	 * 根据表名获取元数据
//	 * @param tableName 表名
//	 * @return
//	 * @throws Exception
//	 */
//	protected ParaMap doGetTableMetaData(String tableName) throws Exception {
//		return dataSetDao.getTableMetaData(tableName);
//	}
//	
//	/**
//	 * 根据表名获取元数据
//	 * @param tableName 表名
//	 * @return
//	 * @throws Exception
//	 */
//	public ParaMap getTableMetaData(String tableName) throws Exception {
//		if (isUseHelper()) {
//			ParaMap result = doGetTableMetaData(tableName);
//			return result;
//		} else
//			return dataSetDao.getTableMetaData(tableName);
//	}
}