package com.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtils {
	public static final int DATE_YEAR = 0;
    public static final int DATE_MONTH = 1;
    public static final int DATE_DAY = 2;
    public static final int DATE_HOUR = 3;
    public static final int DATE_MINUTE = 4;
    public static final int DATE_SECOND = 5;
    public static final int DATE_MILLISECOND = 6;
    
	public static String sub(Date thisTime, Date endTime) {
		long l1 = thisTime.getTime() / 1000;
		long l2 = endTime.getTime() / 1000;
		long ll = l2 - l1;
		long dd = ll / 24 / 60 / 60;
		long hh = (ll - dd * 24 * 60 * 60) / (60 * 60);
		long mm = (ll - dd * 24 * 60 * 60 - hh * 60 * 60) / 60;
		long ss = (ll - dd * 24 * 60 * 60 - hh * 60 * 60 - mm * 60);
		String ddStr = dd == 0 ? "" : dd + "天 ";
		String hhStr = hh == 0 ? "" : hh + "小时 ";
		String mmStr = mm == 0 ? "" : mm + "分钟 ";
		String ssStr = ss == 0 ? "" : ss + "秒";
		String result = ddStr + hhStr + mmStr + ssStr;
		return result;
	}

	public static int getProcess(Date beginTime, Date endTime) {
		long l1 = beginTime.getTime() / 1000;
		long l2 = endTime.getTime() / 1000;
		long now = (DateUtils.now()).getTime() / 1000;
		long d1 = l2 - now;
		long d2 = l2 - l1;
		Double d = d1 * 100.0 / d2;
		int dd = 100 - d.intValue();
		if (dd > 100) {
			dd = 100;
		}
		if (dd < 0) {
			dd = 0;
		}
		return dd;
	}

	public static String getStr(Date date) {
		if (date == null)
			return null;
		else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(date);
		}
	}

	public static String getStr(long delay) {
		long now = (DateUtils.now()).getTime();
		Date d = new Date(now + delay);
		return getStr(d);
	}

	public static String nowStr() {
		return getStr(DateUtils.now());
	}

	public static String nowStr2() {
		return getStr(new Date());
	}

	public static long nowTime() {
		Date d1 = DateUtils.now();
		long now = d1.getTime();
		return now;
	}

	public static Date now() {
		//return DataSetDao.getServerDate();
		return new Date();
	}

	public static Date getDate(String date) {
		Date d1 = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			d1 = sdf.parse(date);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return d1;
	}

	public static int getInt(double d) {
		return (new Double(d)).intValue();
	}

	/**
	 * 校正为本机时间
	 * 
	 * @param dbDate
	 *            数据库时间
	 * @return
	 */
	public static Date adjust(Date dbDate) {
		long dbTime = nowTime();
		long before = dbDate.getTime();
		//
		long localTime = (new Date()).getTime();
		long after = before + (localTime - dbTime);
		//
		Date d1 = new Date(after);
		return d1;
	}

	public static String nowLocal() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String t = sdf.format(new Date());
		return t;
	}
	
	/**
     * 日期相减的值，返回为正数为date2>date1的差额，负数为ate2<date1的差额  
     * @param date1 日期1
     * @param date2 日期2
     * @param returnType 返回类型。可取DATE_YEAR、DATE_MONTH、DATE_DAY、DATE_HOUR、DATE_MINUTE、DATE_SECOND、DATE_MILLISECOND之一
     * @param onlyDiff true表示只返回差额，无论date1和date2的大小都返回正数
     * @return
     */
    public static long dateDiff(Date date1, Date date2, int returnType, boolean onlyDiff) {
    	if (date1 == null || date2 == null || returnType < DATE_YEAR || returnType > DATE_MILLISECOND)
    		return 0;
    	if (returnType == DATE_MILLISECOND)
    		return date2.getTime() - date1.getTime();
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        // 获得两个时间的毫秒时间差异
        long diff = onlyDiff ? Math.abs(date2.getTime() - date1.getTime()) : date2.getTime() - date1.getTime();
        day = diff / nd;// 计算差多少天
        hour = diff % nd / nh + day * 24;// 计算差多少小时
        min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
        sec = diff % nd % nh % nm / ns;// 计算差多少秒
//      System.out.println("时间相差：" + day + "天" + (hour - day * 24) + "小时"
//          + (min - day * 24 * 60) + "分钟" + sec + "秒。");
        if (returnType == DATE_DAY)
            return day;
        else if (returnType == DATE_HOUR)
            return hour;
        else if (returnType == DATE_MINUTE)
            return min;
        else
            return sec;
    }
    
    /**
     * 日期相减的值，返回为正数的差额  
     * @param date1 日期1
     * @param date2 日期2
     * @param returnType 返回类型。可取DATE_YEAR、DATE_MONTH、DATE_DAY、DATE_HOUR、DATE_MINUTE、DATE_SECOND、DATE_MILLISECOND之一
     * @return
     */
    public static long dateDiff(Date date1, Date date2, int returnType) {
    	return dateDiff(date1, date2, returnType, true);
    }
    
    /**
     * 日期相减的值，返回为正数的秒差额  
     * @param date1 日期1
     * @param date2 日期2
     * @return
     */
    public static long dateDiff(Date date1, Date date2) {
    	return dateDiff(date1, date2, DATE_SECOND, true);
    }
    
    /**
     * 日期加值
     * @param date 需要加的日期
     * @param add 需要加的值
     * @param ddType 需要加的值类型。可取DATE_YEAR、DATE_MONTH、DATE_DAY、DATE_HOUR、DATE_MINUTE、DATE_SECOND、DATE_MILLISECOND之一
     * @return
     */
	public static Date dateAdd(Date date, int add, int addType) {
		if (date == null || add == 0 || addType < DATE_YEAR || addType > DATE_MILLISECOND)
			return date;
		Calendar c = Calendar.getInstance();  
		c.setTime(date);
		if (addType == DATE_YEAR)
			c.add(c.YEAR, add);
		else if (addType == DATE_MONTH)
			c.add(c.MONTH, add);
		else if (addType == DATE_DAY)
			c.add(c.DAY_OF_MONTH, add);
		else if (addType == DATE_HOUR)
			c.add(c.HOUR, add);
		else if (addType == DATE_MINUTE)
			c.add(c.MINUTE, add);
		else if (addType == DATE_SECOND)
			c.add(c.SECOND, add);
		else if (addType == DATE_MILLISECOND)
			c.add(c.MILLISECOND, add);
		return c.getTime();
    }
	
	/**
     * 日期加秒值
     * @param date 需要加的日期
     * @param add 需要加的秒值
     * @return
     */
	public static Date dateAdd(Date date, int add) {
		return dateAdd(date, add, DATE_SECOND);
	}
	
	/**
	 * 去除日期的时分秒（实际是返回指定日期的00:00:00时间）
	 * @param date 需要处理的日期
	 * @return
	 */
	public static Date trunc(Date date) {
		if (date == null)
			return null;
		Date result = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String s = sdf.format(date);
		try {
			result = sdf.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = sdf.parse("2012-03-01 10:00:00");
		Date d2 = sdf.parse("2012-03-01 10:56:00");
		System.out.println(StrUtils.dateToStr(DateUtils.trunc(DateUtils.dateAdd(d2, 1, DateUtils.DATE_DAY))));
	}

}
