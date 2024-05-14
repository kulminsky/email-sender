package com.emailsender.util;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MimeTypesUtils {

    private static final String PROPERTIES_FILE_NAME = "src/main/resources/mime.properties";
    private static final String DEFAULT_MIME = "application/octet-stream";
    private static final Logger LOGGER = Logger.getLogger(MimeTypesUtils.class);
    private static final Map<String, String> MIME_TYPES = new HashMap<>();

    static {
        loadMimeTypes();
    }

    private static void loadMimeTypes() {
        Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream(PROPERTIES_FILE_NAME)) {
            properties.load(inputStream);
            for (String key : properties.stringPropertyNames()) {
                String value = properties.getProperty(key);
                MIME_TYPES.put(key, value);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static String getMimeType(String extension) {
        return MIME_TYPES.getOrDefault(extension.toLowerCase(), DEFAULT_MIME);
    }
}