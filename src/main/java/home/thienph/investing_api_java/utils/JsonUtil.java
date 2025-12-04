package home.thienph.investing_api_java.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> T toObject(String json, Class<?> clazz) {
        try {
            @SuppressWarnings("unchecked")
            T object = (T) OBJECT_MAPPER.readValue(json, clazz);
            return object;
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static String toJson(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
