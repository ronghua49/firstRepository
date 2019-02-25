package com.haohua.util;

import java.io.IOException;
import java.util.Properties;

public class Config {
	public  static String  DEFAULT_VIEW="0";
	public static String DEFAULT_REPLY="0";
	public static int UNREVIEWD_STATE=0;
	public static int REVIEWDPASS_STATE=1;
	public static int REVIEWDUNPUSS_STATE=2;
	public static String FILM_CACHE="film";
	public static String TYPE_CACHE="type";
	public static String USER_CACHE="user";
	public static String REPLY_CACHE="reply";
	private static Properties prop = new Properties();//获得加载过配置文件的 properties  对象

	static {
		try {
			prop.load(Config.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public static String getProperty(String key) {
		return prop.getProperty(key);
	}
}
