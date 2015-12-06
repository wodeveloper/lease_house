package com.base.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

import com.base.utils.StrUtils;

/**
 * ID号生成类
 * @author SAMONHUA
 *
 */
public class IDGenerator {
	//ID号生成方式：0GUID，1按时间戳生成随机ID，2数据库create_table_id过程生成，按数据库生成方式如果要求返回的是guid则仍然为按0值直接返回guid
	//对性能要示较高时（如需要快速频繁的插入多条记录），请调用newGUID方法
	//如果是调用DataSetDao.updateData方法时，请先通过newGUID方法获取ID值
	private static final int GENERATOR_ID_MODE = 2;
	
	/**
	 * 生成新的GUID
	 * @param secure true表示返回安全的GUID，如“479A9159DA2A4A8EBD660B188BB07F5D”<br/>
	 *   否则返回如“479A9159-DA2A-4A8E-BD66-0B188BB07F5D”
	 * @return 返回GUID
	 */
	public static String newGUID(boolean secure){
    	return StrUtils.newGUID(secure);
    }
	
	/**
	 * 生成新的安全的GUID
	 * @return 返回GUID，如：479A9159DA2A4A8EBD660B188BB07F5D
	 */
	public static String newGUID(){
		return newGUID(true);
	}
	
	/**
	 * 返回数据库生成的ID值，可以给表主键值，或者业务编号（此情况请手工在sys_table中中注册）
	 * @param tableName 表名
	 * @param keyField 键值字段名，并非一定是主键字段，可以为空（系统自动按主键字段处理）
	 * @param returnCount 返回多个Id值时占用数
	 * @param returnType 0按系统默认（目前同1），1guid（此时应用可以不通过数据库取），2返回时间戳（并发时不一定可靠），3返回新的可用的最大Id值，4返回最大Id整数值（已使用过的），5返回前缀（不影响最大值），其它值按默认值
	 * @return 新的最大ID值，请参考returnType参数
	 * @throws SQLException
	 */
	
	
	/**
	 * 返回数据库生成的主键字段ID值
	 * @param tableName 表名
	 * @return 新的最大ID值
	 * @throws SQLException
	 */
    public static String generatorID(String tableName){
    	return generatorID(tableName, null, 0, 0);
    }
    
    /**
	 * 返回数据库生成的指定键值字段的ID值
	 * @param tableName 表名
	 * @param keyField 键值字段名，并非一定是主键字段，可以为空（系统自动按主键字段处理）
	 * @return 新的最大ID值
	 * @throws SQLException
	 */
    public static String generatorID(String tableName, String keyField){
    	return generatorID(tableName, keyField, 0, 0);
    }
    
    /**
	 * 返回数据库生成的ID值，可以给表主键值，或者业务编号（此情况请手工在sys_table中中注册）
	 * @param tableName 表名
	 * @param keyField 键值字段名，并非一定是主键字段，可以为空（系统自动按主键字段处理）
	 * @param returnCount 返回多个Id值时占用数
	 * @return 新的最大ID值
	 * @throws SQLException
	 */
    public static String generatorID(String tableName, String keyField, int returnCount){
    	return generatorID(tableName, keyField, returnCount, 0);
    }
    
    /**
	 * 返回数据库生成的ID值，可以给表主键值，或者业务编号（此情况请手工在sys_table中中注册）
	 * @param tableName 表名
	 * @param keyField 键值字段名，并非一定是主键字段，可以为空（系统自动按主键字段处理）
	 * @param returnCount 返回多个Id值时占用数
	 * @param returnType 返回Id值类别。0按系统默认（目前同1），1guid（此时应用可以不通过数据库取），2返回时间戳（并发时不一定可靠），3返回新的可用的最大Id值，4返回最大Id整数值（已使用过的），5返回前缀（不影响最大值），其它值按默认值
	 * @return 新的最大ID值，请参考returnType参数
	 * @throws SQLException
	 */
    public static String generatorID(String tableName, String keyField, int returnCount, int returnType) {
    	if (returnType == 0 || returnType == 1)//指定要求guid则直接返回，其它按GENERATOR_ID_MODE
    		return newGUID(true);
    	else if (GENERATOR_ID_MODE == 1 || returnType == 2) {
    		return StrUtils.newTimestampRandomId();
    	} else if (GENERATOR_ID_MODE == 2) {
    		try {
    			return newGUID();
    		} catch(Exception e) {
    			Logger log = Logger.getLogger(IDGenerator.class);
    			log.error("create_table_id调用失败");
    			return null;
    		}
    	} else {
    		return newGUID(true);
    	}
    }
}
