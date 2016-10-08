/*
 * LZKingsoft, All Right Reserved, 2012-2013.
 * 
 * http://www.zhiyao99.com/
 *
 */
package com.zhiyao99.daemon.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Locale;

/**
 * 
 * <p>名称:com.zhiyao99.daemon.util.DateTools.java</p>
 * <p>描述:</p>
 * <p>日期:2013-5-1 下午10:41:51</p>
 * 
 * @author  wangxw
 * @version 1.0
 *
 */
public class DateTools {

	/**
	 * For unit test
	 * @param args
	 */
	public static void main(String[] args) {
		Date d = new Date(System.currentTimeMillis());

		System.out.println("Current Date (Year):   "
				+ DateTools.dateToString(d, Resolution.YEAR));
		System.out.println("Current Date (Month):  "
				+ DateTools.dateToString(d, Resolution.MONTH));
		System.out.println("Current Date (Day):    "
				+ DateTools.dateToString(d, Resolution.DAY));
		System.out.println("Current Date (Hour):   "
				+ DateTools.dateToString(d, Resolution.HOUR));
		System.out.println("Current Date (Minute): "
				+ DateTools.dateToString(d, Resolution.MINUTE));
		System.out.println("Current Date (Second): "
				+ DateTools.dateToString(d, Resolution.SECOND));
		System.out.println("Current Date (MiliSec):"
				+ DateTools.dateToString(d, Resolution.MILLISECOND));

		System.out.println("\nsleep 2 seconds, please wait...\n");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Date d2 = new Date(System.currentTimeMillis());
		System.out.println(getRelativeDateTime(d2, d)
				+ ", You run this program.");
	}

	/** Set timeZone is Asia/Shanghai */
	private final static TimeZone CN = TimeZone.getTimeZone("Asia/Shanghai");

	/** Date format with Year */
	private static final SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat(
			"yyyy", Locale.CHINA);
	
	/** Date format with Year and Month */
	private static final SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat(
			"yyyyMM", Locale.CHINA);
	
	/** Date format with Year and Month and Day */
	private static final SimpleDateFormat DAY_FORMAT = new SimpleDateFormat(
			"yyyyMMdd", Locale.CHINA);
	
	/** Date format with Year and Month and Day and Hour */
	private static final SimpleDateFormat HOUR_FORMAT = new SimpleDateFormat(
			"yyyyMMddHH", Locale.CHINA);
	
	/** Date format with Year and Month and Day and Hour and Minute */
	private static final SimpleDateFormat MINUTE_FORMAT = new SimpleDateFormat(
			"yyyyMMddHHmm", Locale.CHINA);
	
	/** Date format with Year and Month and Day and Hour and Minute and Second */
	private static final SimpleDateFormat SECOND_FORMAT = new SimpleDateFormat(
			"yyyyMMddHHmmss", Locale.CHINA);
	/** Date format with Year and Month and Day and Hour and Minute and Second and ??? */
	private static final SimpleDateFormat MILLISECOND_FORMAT = new SimpleDateFormat(
			"yyyyMMddHHmmssSSS", Locale.CHINA);

	/** 
	 * Init TimeZone is Asia/Shanghai
	 */
	static {
		// times need to be normalized so the value doesn't depend on the
		// location the index is created/used:
		YEAR_FORMAT.setTimeZone(CN);
		MONTH_FORMAT.setTimeZone(CN);
		DAY_FORMAT.setTimeZone(CN);
		HOUR_FORMAT.setTimeZone(CN);
		MINUTE_FORMAT.setTimeZone(CN);
		SECOND_FORMAT.setTimeZone(CN);
		MILLISECOND_FORMAT.setTimeZone(CN);
	}

	/** Set calendar instatce timeZone is Asia/Shanghai */
	private static final Calendar calInstance = Calendar.getInstance(CN);

	/**
	 * Private constructor which cannot create, the class has static methods only
	 */
	private DateTools() {
	}

	/**
	 * Converts a Date to a string suitable for indexing.
	 * 
	 * @param date
	 *            the date to be converted
	 * @param resolution
	 *            the desired resolution, see
	 *            {@link #round(Date, DateTools.Resolution)}
	 * @return a string in format <code>yyyyMMddHHmmssSSS</code> or shorter,
	 *         depending on <code>resolution</code>; using GMT as timezone
	 */

	public static synchronized String dateToString(Date date,
			Resolution resolution) {
		return timeToString(date.getTime(), resolution);
	}

	/**
	 * Converts a millisecond time to a string suitable for indexing.
	 * 
	 * @param time
	 *            the date expressed as milliseconds since January 1, 1970,
	 *            00:00:00 GMT
	 * @param resolution
	 *            the desired resolution, see
	 *            {@link #round(long, DateTools.Resolution)}
	 * @return a string in format <code>yyyyMMddHHmmssSSS</code> or shorter,
	 *         depending on <code>resolution</code>; using GMT as timezone
	 */
	public static synchronized String timeToString(long time,
			Resolution resolution) {

		calInstance.setTimeInMillis(round(time, resolution));
		Date date = calInstance.getTime();

		if (resolution == Resolution.YEAR) {
			return YEAR_FORMAT.format(date);
		} else if (resolution == Resolution.MONTH) {
			return MONTH_FORMAT.format(date);
		} else if (resolution == Resolution.DAY) {
			return DAY_FORMAT.format(date);
		} else if (resolution == Resolution.HOUR) {
			return HOUR_FORMAT.format(date);
		} else if (resolution == Resolution.MINUTE) {
			return MINUTE_FORMAT.format(date);
		} else if (resolution == Resolution.SECOND) {
			return SECOND_FORMAT.format(date);
		} else if (resolution == Resolution.MILLISECOND) {
			return MILLISECOND_FORMAT.format(date);
		} else
			throw new IllegalArgumentException("unknown resolution "
					+ resolution);
	}

	/**
	 * Converts a string produced by <code>timeToString</code> or
	 * <code>dateToString</code> back to a time, represented as the number of
	 * milliseconds since January 1, 1970, 00:00:00 GMT.
	 * 
	 * @param dateString
	 *            the date string to be converted
	 * @return the number of milliseconds since January 1, 1970, 00:00:00 GMT
	 * @throws ParseException
	 *             if <code>dateString</code> is not in the expected format
	 */
	public static synchronized long stringToTime(String dateString)
			throws ParseException {
		return stringToDate(dateString).getTime();
	}

	/**
	 * Converts a string produced by <code>timeToString</code> or
	 * <code>dateToString</code> back to a time, represented as a Date object.
	 * 
	 * @param dateString
	 *            the date string to be converted
	 * @return the parsed time as a Date object
	 * @throws ParseException
	 *             if <code>dateString</code> is not in the expected format
	 */
	public static synchronized Date stringToDate(String dateString)
			throws ParseException {

		if (dateString.length() == 4) {
			return YEAR_FORMAT.parse(dateString);
		} else if (dateString.length() == 6) {
			return MONTH_FORMAT.parse(dateString);
		} else if (dateString.length() == 8) {
			return DAY_FORMAT.parse(dateString);
		} else if (dateString.length() == 10) {
			return HOUR_FORMAT.parse(dateString);
		} else if (dateString.length() == 12) {
			return MINUTE_FORMAT.parse(dateString);
		} else if (dateString.length() == 14) {
			return SECOND_FORMAT.parse(dateString);
		} else if (dateString.length() == 17) {
			return MILLISECOND_FORMAT.parse(dateString);
		} else
			throw new ParseException("Input is not valid date string: "
					+ dateString, 0);
	}

	/**
	 * Limit a date's resolution. For example, the date
	 * <code>2004-09-21 13:50:11</code> will be changed to
	 * <code>2004-09-01 00:00:00</code> when using <code>Resolution.MONTH</code>
	 * .
	 * 
	 * @param resolution
	 *            The desired resolution of the date to be returned
	 * @return the date with all values more precise than
	 *         <code>resolution</code> set to 0 or 1
	 */
	public static synchronized Date round(Date date, Resolution resolution) {
		return new Date(round(date.getTime(), resolution));
	}

	/**
	 * Limit a date's resolution. For example, the date
	 * <code>1095767411000</code> (which represents 2004-09-21 13:50:11) will be
	 * changed to <code>1093989600000</code> (2004-09-01 00:00:00) when using
	 * <code>Resolution.MONTH</code>.
	 * 
	 * @param resolution
	 *            The desired resolution of the date to be returned
	 * @return the date with all values more precise than
	 *         <code>resolution</code> set to 0 or 1, expressed as milliseconds
	 *         since January 1, 1970, 00:00:00 GMT
	 */
	public static synchronized long round(long time, Resolution resolution) {

		calInstance.setTimeInMillis(time);

		if (resolution == Resolution.YEAR) {
			calInstance.set(Calendar.MONTH, 0);
			calInstance.set(Calendar.DAY_OF_MONTH, 1);
			calInstance.set(Calendar.HOUR_OF_DAY, 0);
			calInstance.set(Calendar.MINUTE, 0);
			calInstance.set(Calendar.SECOND, 0);
			calInstance.set(Calendar.MILLISECOND, 0);
		} else if (resolution == Resolution.MONTH) {
			calInstance.set(Calendar.DAY_OF_MONTH, 1);
			calInstance.set(Calendar.HOUR_OF_DAY, 0);
			calInstance.set(Calendar.MINUTE, 0);
			calInstance.set(Calendar.SECOND, 0);
			calInstance.set(Calendar.MILLISECOND, 0);
		} else if (resolution == Resolution.DAY) {
			calInstance.set(Calendar.HOUR_OF_DAY, 0);
			calInstance.set(Calendar.MINUTE, 0);
			calInstance.set(Calendar.SECOND, 0);
			calInstance.set(Calendar.MILLISECOND, 0);
		} else if (resolution == Resolution.HOUR) {
			calInstance.set(Calendar.MINUTE, 0);
			calInstance.set(Calendar.SECOND, 0);
			calInstance.set(Calendar.MILLISECOND, 0);
		} else if (resolution == Resolution.MINUTE) {
			calInstance.set(Calendar.SECOND, 0);
			calInstance.set(Calendar.MILLISECOND, 0);
		} else if (resolution == Resolution.SECOND) {
			calInstance.set(Calendar.MILLISECOND, 0);
		} else if (resolution == Resolution.MILLISECOND) {
			// don't cut off anything
		} else
			throw new IllegalArgumentException("unknown resolution "
					+ resolution);

		return calInstance.getTimeInMillis();
	}

	/** Specifies the time granularity. */
	public static class Resolution {

		public static final Resolution YEAR = new Resolution("year");
		public static final Resolution MONTH = new Resolution("month");
		public static final Resolution DAY = new Resolution("day");
		public static final Resolution HOUR = new Resolution("hour");
		public static final Resolution MINUTE = new Resolution("minute");
		public static final Resolution SECOND = new Resolution("second");
		public static final Resolution MILLISECOND = new Resolution(
				"millisecond");

		private String resolution;

		private Resolution() {
		}

		private Resolution(String resolution) {
			this.resolution = resolution;
		}

		@Override
		public String toString() {
			return resolution;
		}
	}

	/*
	 * Stand alone utility. Precondition: c1>c2
	 */
	public static String getRelativeDateTime(Date d1, Date d2) {

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);

		String szRETURN = "";

		long span = c1.getTimeInMillis() - c2.getTimeInMillis();
		long SECOND = 1000;
		long MINUTE = 60 * SECOND;
		long HOUR = 60 * MINUTE;
		long DAY = 24 * HOUR;

		long nDay = span / DAY;
		if (nDay > 0)
			szRETURN = nDay + "天前";
		else {
			long nHour = span / HOUR;
			if (nHour > 0) {
				szRETURN = nHour + "小时";

				span -= nHour * HOUR;
				long nMin = span / MINUTE;
				if (nMin > 0)
					szRETURN += nMin + "分";
				szRETURN += "前";
			} else {
				long nMin = span / MINUTE;
				if (nMin > 0) {
					szRETURN = nMin + "分";

					span -= nMin * MINUTE;
					long nSec = span / SECOND;
					if (nSec > 0)
						szRETURN += nSec + "秒";
					szRETURN += "前";
				} else {
					long nSec = span / SECOND;
					szRETURN = nSec + "秒前";
				}
			}
		}

		return szRETURN;
	}

	/*
	 * Stand alone utility. Precondition: c1>c2
	 */
	public static String getRelativeDateTime(Calendar c1, Calendar c2) {
		String szRETURN = "";

		long span = c1.getTimeInMillis() - c2.getTimeInMillis();
		long SECOND = 1000;
		long MINUTE = 60 * SECOND;
		long HOUR = 60 * MINUTE;
		long DAY = 24 * HOUR;

		long nDay = span / DAY;
		if (nDay > 0)
			szRETURN = nDay + "天前";
		else {
			long nHour = span / HOUR;
			if (nHour > 0) {
				szRETURN = nHour + "小时";

				span -= nHour * HOUR;
				long nMin = span / MINUTE;
				if (nMin > 0)
					szRETURN += nMin + "分";
				szRETURN += "前";
			} else {
				long nMin = span / MINUTE;
				if (nMin > 0) {
					szRETURN = nMin + "分";

					span -= nMin * MINUTE;
					long nSec = span / SECOND;
					if (nSec > 0)
						szRETURN += nSec + "秒";
					szRETURN += "前";
				} else {
					long nSec = span / SECOND;
					szRETURN = nSec + "秒前";
				}
			}
		}

		return szRETURN;
	}

	public static String getShortDisplayTime(String szDT) {
		if (szDT == null || szDT.length() != 14)
			return "";

		String szM = szDT.substring(4, 6) + "月";
		String szD = szDT.substring(6, 8) + "日";
		String szHM = szDT.substring(8, 10) + ":" + szDT.substring(10, 12);

		String szRETURN = szM + szD + " " + szHM;
		return szRETURN;
	}

	public static String timeFromThen(Date then) {
		String szTimeElapsed = "";

		if (then == null)
			return szTimeElapsed;

		Date now = new Date(System.currentTimeMillis());

		long l = now.getTime() - then.getTime();
		long day = l / (24 * 60 * 60 * 1000);
		long hour = (l / (60 * 60 * 1000) - day * 24);
		long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

		StringBuilder sb = new StringBuilder();

		if (day > 0) {
			sb.append(day);
			sb.append("天");
		}
		if (hour > 0 || hour == 0 && sb.length() > 0) {
			sb.append(hour);
			sb.append("小时");
		}
		if (min > 0 || min == 0 && sb.length() > 0) {
			sb.append(min);
			sb.append("分");
		}
		if (s > 0 || s == 0 && sb.length() > 0) {
			sb.append(s);
			sb.append("秒");
		}

		szTimeElapsed = sb.toString();
		return szTimeElapsed;
	}

	public static Timestamp Date2Timestamp(Date jvmDate) {
		if (jvmDate != null) {
			return new Timestamp(jvmDate.getTime());
		} else {
			return new Timestamp(System.currentTimeMillis());
		}
	}
}
