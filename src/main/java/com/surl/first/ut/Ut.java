package com.surl.first.ut;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surl.first.global.config.AppConfig;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

public class Ut {
    public static class Json{
        @SneakyThrows
        public static String toJson(Object object){
            return AppConfig.getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(object);
        }
    }
}
