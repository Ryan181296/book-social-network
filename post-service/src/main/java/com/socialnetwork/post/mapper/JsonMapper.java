package com.socialnetwork.post.mapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JsonMapper {
    static final Gson gson = new GsonBuilder().create();

    public static <T> T map(Object obj, Class<T> clazz) {
        try {
            var json = gson.toJson(obj);
            return gson.fromJson(json, clazz);
        } catch (Exception e) {
            log.error("Deserialize object error {}", e.getMessage());
            return null;
        }
    }

    public static String toJson(Object obj) {
        try {
            return gson.toJson(obj);
        } catch (Exception e) {
            log.error("Serialize object error {}", e.getMessage());
            return null;
        }
    }
}
