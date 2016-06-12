package org.brjia.qs.util;

public class StringUtil {

	/**
	 * null or ""
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		
		if(str==null || str.length()==0){
			return true;
		}
		return false;
		
	}
}

