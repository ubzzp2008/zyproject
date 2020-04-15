package com.fl.web.utils;

import java.util.UUID;

public class UUIDUtil {

	public static String get32UUID(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}

}
