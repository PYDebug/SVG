package edu.tongji.webgis.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtils {
	private final static ObjectMapper mapper;
	static {
		mapper = new ObjectMapper();
	}
	
	public static String serialize(Object o) {
		try {
			//mapper.registerModule(new Hibernate4Module());
			//mapper.setSerializationInclusion(Include.NON_NULL);
			return mapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static Object deserialize(String jsonText, TypeReference type) {
	   try {
	      return mapper.readValue(jsonText, type);
	   } catch (JsonParseException e) {
	      e.printStackTrace();
	   } catch (JsonMappingException e) {
	      e.printStackTrace();
	   } catch (IOException e) {
	      e.printStackTrace();
	   }
	   return null;
	}
}
