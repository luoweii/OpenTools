package com.luowei.opentools.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * @author 骆巍
 */
public class JsonUtil {
	private static Gson gson = null;
	private static JsonUtil instance = null;

	static {
		gson = new GsonBuilder().setDateFormat("yyyyMMddHHmmss").create();
	}

	public static JsonUtil getInstance() {
		if (null == instance) {
			instance = new JsonUtil();
		}

		return instance;
	}

	public static String objectToJson(Object obj) {
		return gson.toJson(obj);
	}

	public static <T> T fromJson(String str, Type type) {
		return gson.fromJson(str, type);
	}
}
