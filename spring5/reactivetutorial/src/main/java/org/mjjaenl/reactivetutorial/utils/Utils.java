package org.mjjaenl.reactivetutorial.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.MultiValueMap;

public class Utils {
	public static boolean isEmpty(Object obj) {
		boolean empty = false;
		if(obj == null)
			empty = true;
		else if(obj instanceof Object[]) {
			empty = ((Object[])obj).length == 0;
			if(!empty) {
				empty = Arrays.stream((Object[])obj).filter(m -> !isEmpty(m)).count() == 0;
			}
		} else if(obj instanceof Collection)
			empty = ((Collection<?>)obj).size() == 0;
		else if(obj instanceof Map<?, ?>)
			empty = ((Map<?, ?>)obj).size() == 0;
		return empty;
	}
	
	public static Map<String, String> convertMultiValueMapStringStringToMap(MultiValueMap<String, String> mvm) {
		Map<String, String> map = new HashMap<String, String>();
		for(Map.Entry<String, List<String>> entry : mvm.entrySet()) {
			for(String string : entry.getValue()) {
				map.put((String)entry.getKey(), string);
			}
		}
		return map;
	}
}
