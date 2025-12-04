package home.thienph.investing_api_java.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> T toObject(String json, Class<?> clazz) {
        try {
            @SuppressWarnings("unchecked")
            T object = (T) OBJECT_MAPPER.readValue(json, clazz);
            return object;
        } catch (JsonProcessingException e) {
            log.error("toObject(String,Class) / error: {}", e.getMessage());
            return null;
        }
    }

    public static String toJson(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("toJson(Object) / error: {}", e.getMessage());
            return null;
        }
    }
}
