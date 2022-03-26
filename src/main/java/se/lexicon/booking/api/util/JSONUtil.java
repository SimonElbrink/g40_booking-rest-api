package se.lexicon.booking.api.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JSONUtil {


    private ObjectMapper objectMapper;

    private static JSONUtil instance = new JSONUtil();

    private JSONUtil() {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static JSONUtil getInstance() {
        return instance;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
