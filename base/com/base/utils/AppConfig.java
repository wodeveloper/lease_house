package com.base.utils;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.base.utils.ParaMap;

public class AppConfig {
	private static ParaMap map;
	private static final Logger log = Logger.getLogger(AppConfig.class);

	public static synchronized void init() {
		if (map != null)
			return;
		try {
			map = new ParaMap();
			InputStream in = AppConfig.class
					.getResourceAsStream("/appConfig.properties");
			Properties appConfig = new Properties();
			appConfig.load(in);
			Iterator it = appConfig.keySet().iterator();
			while (it.hasNext()) {
				String key = String.valueOf(it.next()).trim();
				String value = appConfig.getProperty(key).trim();
				map.put(key, value);
			}
			Class.forName(map.getString("driverClassName"));
		} catch (Exception ex) {
			log.error(ex);
		}
	}
	
	public static boolean hasPro(String key) {
		init();
		if (key == null)
			return false;
		else
			return map.containsKey(key);
	}

	public static String getPro(String key) {
		if (hasPro(key))
			return map.getString(key);
		else
			return null;
	}

	public static Integer getIntegerPro(String key) {
		if (hasPro(key))
			return map.getInteger(key);
		else
			return null;
	}
	
	public static int getIntPro(String key, int defaultValue) {
		Integer value = getIntegerPro(key);
		return value == null ? defaultValue : value;
	}
	
	public static int getIntPro(String key) {
		return getIntPro(key, 0);
	}
	
	public static ParaMap getAll() {
		init();
		return new ParaMap(map);
	}
}
