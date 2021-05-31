package com.hardik.hedwig.utility;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MapUtility {

	@SuppressWarnings("unchecked")
	public static Map<String, String> convert(Object object) {
		return new ObjectMapper().convertValue(object, Map.class);
	}

}